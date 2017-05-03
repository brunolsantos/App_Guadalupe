package control.connection;

/**
 * Created by phiin on 02/05/2017.
 */

public class Message {
    //SINGLETON
    private static Message mInstance = null;
    public static Message getInstance(){
        if(mInstance == null)
        {
            mInstance = new Message();
        }
        return mInstance;
    }

    private String data_to_send[][];
    private String data_received[][];

    public void setData_to_send(String data_to_send[][]){this.data_to_send=data_to_send;}
    public String[][] getData_to_send(){return this.data_to_send;}

    public void setData_received(String data_received[][]){this.data_received = data_received;}
    public String[][] getData_received(){return this.data_received;}

}
