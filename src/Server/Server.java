package Server;

import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.*;

class Server{
    String TimeStamp(){
        String res = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                + LocalTime.now().getSecond();
        return res;
    }
    public void start()
    {
        final int PORT_NUMBER = 3345;
        while(true) {
            try {
                //Listen on port
                ServerSocket serverSock = new ServerSocket(PORT_NUMBER);
                System.out.println(TimeStamp()+" - Listening...");

                //Get connection
                Socket clientSock = serverSock.accept();
                System.out.println(TimeStamp()+" - Connected client");

                //Get input
                LocalTime time = LocalTime.now();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                String messageTime = time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
                String clientName = br.readLine();
                System.out.println(TimeStamp()+" - Client Name: "+clientName);
                String clientMes = br.readLine();
                String compareStr = "Date";
                String client_string = messageTime + " - " + clientName + ": " + clientMes;
                PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
                out.println(client_string);
                if( clientMes.equals(compareStr)){
                    out.println(TimeStamp() +" - " + "Server: " + LocalDate.now().toString());
                    System.out.println(TimeStamp()+" Task completed (Client "+clientName+")");
                }else
                {
                    out.println(TimeStamp() +" - " + "Server: " + "Unknown command");
                    System.out.println(TimeStamp()+" - Task failed (Client "+clientName+")");
                }
                out.close();
                /*try {
                    br.readLine();
                }
                catch(Exception e){}*/
                br.close();
                clientSock.close();
                System.out.println(TimeStamp()+" - "+clientName+" disconnected\n");
                serverSock.close();
            } catch(Exception e) {
                e.printStackTrace();

            }
        }

    }
}
