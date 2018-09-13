import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.*;

class Server{
    public static void start()
    {
        final int PORT_NUMBER = 3345;

        while(true) {
            try {
                //Listen on port
                ServerSocket serverSock = new ServerSocket(PORT_NUMBER);
                System.out.println("Listening...");

                //Get connection
                Socket clientSock = serverSock.accept();
                System.out.println("Connected client");

                //Get input
                BufferedReader br = new (new InputStreamReader(clientSock.getInputStream()));
                LocalTime time = LocalTime.now();
                String messageTime = time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
                String clientMes = br.readLine();
                String compareStr = "Date";


                String client_string = messageTime + " " + "Alex" + ": " + clientMes;
                System.out.println(client_string);
                if( clientMes.equals(compareStr))  // change to "" string
                {
                    System.out.println(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                            + LocalTime.now().getSecond() +" " + "Server: " + LocalDate.now().toString());
                }
                br.close();
                serverSock.close();
                clientSock.close();
            } catch(Exception e) {
                e.printStackTrace();

            }
        }
    }
}
