package Server;

import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.*;
class Server{
    protected final int PORT_NUMBER = 3345;
    public void start()
    {
        FuncStack fs = new FuncStack();
        while(true) {
            try {
                //Listen on port
                ServerSocket serverSock = new ServerSocket(PORT_NUMBER);
                System.out.println(fs.TimeStamp()+" - Listening...");

                //Get connection
                Socket clientSock = serverSock.accept();
                System.out.println(fs.TimeStamp()+" - Connected client");
                boolean flag = true;
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
                String clientName = br.readLine();
                System.out.println(fs.TimeStamp()+" - Client Name: "+clientName);
                //Working cycle
                while(flag) {
                    String clientMes = br.readLine();
                    String client_string = fs.TimeStamp() + " - " + clientName + ": " + clientMes;
                    out.println(client_string);
                    flag = (fs.rooter(clientMes, out, clientName)!=1);
                }
                //Closing
                out.close();
                br.close();
                clientSock.close();
                System.out.println(fs.TimeStamp()+" - "+clientName+" disconnected\n");
                serverSock.close();
            } catch(Exception e) {
                e.printStackTrace();

            }
        }

    }
}
