//########		 SENSOFT     #######
//########  CLASSE PRINCIPAL #######
package control.connection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPConn extends AsyncTask<Void, Void, String[][]> {
//public class TCPConn{
	
	private static final String TAG = "TCPConn";
	private DataOutputStream out;
	private DataInputStream in;
	byte[] buffer,buffer2,size,size2 = new byte[1024];
	private Socket socket;
	private String ipServer;
	private int porta;
	Message message = Message.getInstance();


	@Override
	protected String[][] doInBackground(Void... params) {
		// TODO Auto-generated method stub
		/**
		 * Do network related stuff
		 * return string response.
		 */
		String message_received[][];
		try {
			AbreConn();
			message_received = EnviaTCP(message.getData_to_send());
			message.setData_received(message_received);
			FechaConn();
			return message.getData_received();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}
	
	public TCPConn (String ipServ,int portaServ){
		this.ipServer = ipServ;
		this.porta = portaServ;
	}
	
	public String[][] EnviaTCP (String[][] dados) throws Exception {
		
		Log.i(TAG, "Enviando dados...");
		buffer = Tolls.FlattenToString(dados).getBytes();
		size = completa8Bytes(String.valueOf(buffer.length).getBytes());
		out.write(size,0,size.length);
        if (buffer.length > 0) {
            out.write(buffer, 0,buffer.length);
        }
        
        //Recebendo mensagem
        Log.i(TAG, "Recebendo dados...");
        size2 = new byte[8];
        socket.setSoTimeout(2500);
        in.read(size2, 0, 8);
        int len = Integer.parseInt(new String(size2)); 
        buffer2 = new byte[len];
        if (buffer.length > 0) {
        	in.read(buffer2, 0, len);
        }
   
        String mensagem = new String(buffer2);
        dados = Tolls.UnFlattenFromString(mensagem);
            
		return dados;
	}
	
	//Completa vetor de bytes at� 8 valores
	private static byte[] completa8Bytes (byte[] size){
		int i,zeros = 0;
		byte[] zero = new byte[8];
		if(size.length<8){
			for (int j = 0; j < 8-size.length; j++) {
				zeros+=1;
				zero[j]="0".getBytes()[0];
			}
			i=0;
			while (zeros+i < 8){
				zero[zeros+i] = size[i];
				i+=1;
			}
		}
		return zero;
	}
	
	//Abre conexao TCP
	public void AbreConn() throws Exception {
		socket = new Socket(ipServer,porta);
		socket.setSoTimeout(1000);
		OutputStream paraServidor = socket.getOutputStream();
	    InputStream doServidor = socket.getInputStream();
	    out = new DataOutputStream(paraServidor);
	    in = new DataInputStream(doServidor);
	}
	
	//Fecha conexao TCP
	public void FechaConn()throws Exception{
		in.close();
		out.close();
		socket.close();
	}
}

