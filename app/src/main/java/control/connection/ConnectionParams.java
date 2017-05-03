package control.connection;

/**
 * Created by phiin on 11/04/2017.
 */

public class ConnectionParams {

    //SINGLETON
    private static ConnectionParams mInstance = null;
    public static ConnectionParams getInstance(){
        if(mInstance == null)
        {
            mInstance = new ConnectionParams();
        }
        return mInstance;
    }

    public ConnectionParams(){
        setIp("192.168.0.104");
        setPort(2525);
    }

    private String ip;
    private int port;

    public void setIp(String ip){this.ip = ip; }
    public String getIp(){return this.ip;}

    public void setPort(int port){this.port = port;}
    public int getPort(){return this.port;}
}
