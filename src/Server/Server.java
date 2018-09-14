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

                //Get input
                LocalTime time = LocalTime.now();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                String messageTime = time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
                String clientName = br.readLine();
                System.out.println(fs.TimeStamp()+" - Client Name: "+clientName);
                String clientMes = br.readLine();
                String client_string = messageTime + " - " + clientName + ": " + clientMes;
                PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
                out.println(client_string);
                fs.rooter(clientMes,out,clientName);

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
