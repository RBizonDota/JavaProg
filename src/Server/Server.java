package Server;

import java.net.*;
import java.io.*;
import java.util.*;
class Server{
    public LinkedList<ServerThread> serverList = new LinkedList<>(); // список всех веток
    protected final int PORT_NUMBER = 3345;
    private int codegen;
    public Server(){
        codegen = 0;
    }
    public void start()
    {
        try {
            FuncStack fs = new FuncStack();
            ServerSocket serverSock = new ServerSocket(PORT_NUMBER);
            System.out.println(fs.TimeStamp() + " - Server is up and ready to work");
            while (codegen < 60000) {//TODO исправить этот некрасивый костыль
                try {
                    //System.out.println(fs.TimeStamp()+" - Listening...");
                    Socket clientSock = serverSock.accept();
                    serverList.add(new ServerThread(this, clientSock, codegen++));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            serverSock.close();
        }
        catch(Exception ignoring){}
    }
}

class ServerThread extends Thread{
    private FuncStack fs = new FuncStack();
    private Socket sock;
    private PrintWriter out;
    private BufferedReader br;
    private Server serv;
    public int code;
    public ServerThread(Server ser,Socket s,int c)throws IOException{
        sock = s;
        br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
        serv = ser;
        code = c;
        start();
    }
    public void run() {
        try {
            boolean flag = true;
            System.out.println(fs.TimeStamp()+" <"+code+"> Connected client. Thread code = "+code);
            String clientName = br.readLine();
            System.out.println(fs.TimeStamp()+" <"+code+"> Client Name: "+clientName);
            //Working cycle
            while(flag) {
                String clientMes = br.readLine();
                String client_string = fs.TimeStamp() + " - " + clientName + ": " + clientMes;
                out.println(client_string);
                flag = (fs.rooter(clientMes, out, clientName,code)!=1);
            }
            //Closing
            out.close();
            br.close();
            sock.close();
            System.out.println(fs.TimeStamp()+" <"+code+"> "+clientName+" disconnected");
            for(ServerThread that:serv.serverList){//TODO сделать более эффективный проход с итератором
                if(that.code == this.code) {
                    serv.serverList.remove(that);
                    System.out.println(fs.TimeStamp()+" <"+code+"> Thread deleted");
                }
            }
        } catch (IOException e) {
            System.out.println(fs.TimeStamp()+" <"+code+"> !!!EXCEPTION!!! "+e.getMessage());
        }
    }

}
