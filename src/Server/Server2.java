package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.ArrayDeque;

class Server2{
    public LinkedList<SocketStruct> socketstack = new LinkedList<>(); // список всех веток
    public ArrayDeque<DequeStruct> och = new ArrayDeque<>(500);
    protected final int PORT_NUMBER = 3345;
    public void start()
    {
        boolean f = true;
        try {
            ServerSocket serverSock = new ServerSocket(PORT_NUMBER);
            FuncStack fs = new FuncStack();
            AccessThread acc = new AccessThread(this);
            ServerThread2[] tharr = new ServerThread2[4];
            for(int i = 0;i<4;i++)
                tharr[i] = new ServerThread2(this,i);
            System.out.println(fs.TimeStamp() + " - Server2 is up and ready to work");
            while (f) {
                Socket bufs = serverSock.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(bufs.getInputStream()));
                String s = br.readLine();
                SocketStruct a = new SocketStruct(bufs,s);
                socketstack.addLast(a);
                System.out.println("Client connected");

            }
        }
        catch(Exception ignoring){}
    }
}
class DequeStruct{
    public String command;
    public SocketStruct sock;
    public DequeStruct(String c, SocketStruct s){
        command = c;
        sock = s;
    }

}
class SocketStruct{
    public Socket sock;
    public BufferedReader br;
    public PrintWriter out;
    public String clientName;
    SocketStruct(Socket s,String cn) throws IOException{
        sock = s;
        br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
        clientName = cn;
    }
}
class AccessThread extends Thread{
    private Server2 mom;
    protected final int PORT_NUMBER = 3345;

    public AccessThread(Server2 mother){
        mom = mother;
        start();
    }
    public void run(){
        boolean f = true;
        try {
            while (f) {
                if (!mom.socketstack.isEmpty())
                for (SocketStruct  obj:mom.socketstack)
                {
                    if(obj.sock.isClosed()){
                        mom.socketstack.remove(obj);
                        continue;
                    }
                    if(obj.br.ready())
                    {
                        String s = obj.br.readLine();
                        mom.och.addLast(new DequeStruct(s,obj));
                        System.out.println("Query in deque");
                    }
                }else
                sleep(5+ (int)(Math.random()*10));
            }
        }
        catch(Exception ignoring){}
    }
}
class ServerThread2 extends Thread{
    private FuncStack fs = new FuncStack();
    private Server2 serv;
    public int code;
    public ServerThread2(Server2 ser,int c){
        serv = ser;
        code = c;
        start();
    }
    public void run(){
        try {
            boolean flag = true;
            //Working cycle
            while(flag) {
                if(!serv.och.isEmpty()){
                    DequeStruct buf = serv.och.pop();
                    System.out.println("running query");
                    buf.sock.out.println(fs.TimeStamp()+" - "+buf.sock.clientName+": "+buf.command);
                    if(fs.rooter(buf.command,buf.sock.out,buf.sock.clientName,code)==1)
                        buf.sock.sock.close();
                }
                else{
                    sleep(5+ (int)(Math.random()*10));
                }
            }
        } catch (Exception e) {
            System.out.println(fs.TimeStamp()+" <"+code+"> !!!EXCEPTION!!! "+e.getMessage());
        }
    }

}
