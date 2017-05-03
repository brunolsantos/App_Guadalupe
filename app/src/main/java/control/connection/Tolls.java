//########		 SENSOFT     #######

package control.connection;

public abstract class Tolls {
	
	//Tranforma Matriz em mensagem TCP
	public static String FlattenToString(String[][] dados){
		int [][] total;
		int[] lin,col,size,buffer;
		byte[] valor;
		int pos,nlin,ncol,nsize,ntotal;
		String mensagem="";
		
		nlin = dados.length;
		ncol = dados[0].length;
		total = new int[nlin][ncol];
		
		lin = getVetorDoNum(nlin);
		col = getVetorDoNum(ncol);
		
		ntotal = 8; //lin=4 + col=4
		
		//conta o total de posicoes q ter� o buffer
		for (int i = 0; i < nlin; i++) {
			for (int j = 0; j < ncol; j++) {
				total[i][j] = dados[i][j].getBytes().length;
				ntotal += 4 + total[i][j];
			}
		}
		
		buffer = new int[ntotal];
		pos = 0;
		//adiciona nro de linhas no buffer
		for (int i = 0; i < 4; i++) {
			buffer[pos]=lin[i];
			pos+=1;
		}
		//adiciona nro de colunas no buffer
		for (int i = 0; i < 4; i++) {
			buffer[pos]=col[i];
			pos+=1;
		}
		
		//adiciona valores no buffer
		for (int i = 0; i < nlin; i++) {
			for (int j = 0; j < ncol; j++) {
				size = getVetorDoNum(total[i][j]);
				//adiciona tamanho do valor no buffer
				for (int l = 0; l < 4; l++) {
					buffer[pos]=size[l];
					pos+=1;
				}
				nsize = total[i][j];
				valor = new byte[nsize];
				valor = dados[i][j].getBytes();
				//adiciona valor no buffer
				for (int l = 0; l < nsize; l++) {
					buffer[pos] = valor[l];
					pos+=1;
				}
			}
		}
		
		byte[] bytes = new byte[buffer.length];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = Byte.valueOf(String.valueOf(buffer[i]));
		}
		
		mensagem = new String(bytes);
		
		System.out.println(mensagem);
		return mensagem;
	}
	
	
	
	//transforma mensagem TCP em Matriz
	public static String[][] UnFlattenFromString(String str){
		int[] lin,col,size;
		byte[] valor;
		int pos,nlin,ncol,nsize;
		String[][] dados;
		
		byte[] buffer = str.getBytes();
		
		lin = new int[4];
		col = new int[4];
		size = new int[4];
		pos=0;
		
		//obtem nro de linhas
		for (int l = 0; l < 4; l++) {
			lin[l]=buffer[pos];
			pos+=1;
		}
		nlin = getNumDoVetor(lin);
		
		//obtem nro de colunas
		for (int l = 0; l < 4; l++) {
			col[l]=buffer[pos];
			pos+=1;
		}
		ncol = getNumDoVetor(col);
		
		//inicializa matriz de dados
		dados = new String[nlin][ncol];
		
		//obtem matriz de dados
		for (int i = 0; i < nlin; i++) {
			for (int j = 0; j < ncol; j++) {
				//obtem tamanho do valor
				for (int l = 0; l < 4; l++) {
					size[l]=buffer[pos];
					pos+=1;
				}
				nsize = getNumDoVetor(size);
				valor = new byte[nsize];
				//obtem valor
				for (int l = 0; l < nsize; l++) {
					valor[l]=buffer[pos];
					pos+=1;
				}
				String strr = new String(valor);
				dados [i][j] = strr;
			}
		}
		return dados;
	}
	
	//Transforma vetor num (mil,cent,dez,uni) em numero
	private static int getNumDoVetor (int[] num){
		int nro=0;
		nro+=num[0]*1000;
		nro+=num[1]*100;
		nro+=num[2]*10;
		nro=num[3]*1;
		return nro;
	}
	
	//Transforma um numero num vetor do tipo (mil,cent,dez,uni)
	private static int[] getVetorDoNum (int num){
		int[] nro = new int[4];
		nro[0] = num/1000;
		nro[1] = num/100;
		nro[2] = num/10;
		nro[3] = num/1;
		return nro;
	}
	
}
