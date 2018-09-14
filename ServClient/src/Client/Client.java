package Client;

import java.io.*;
import java.net.*;
import java.util.*;

class Client{
    public int start()
    {
        final int PORT_NUMBER = 3345;
        final String HOSTNAME = "127.0.0.1";
        final String CLIENTNAME = "Client"; // TODO: use it in server class
        //Attempt to connect
        try {
            Socket sock = new Socket(HOSTNAME, PORT_NUMBER);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            //Output
            Scanner scan = new Scanner(System.in);

            String client_string =  scan.nextLine();
            out.println(client_string);
            out.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println(br.readLine());
            System.out.println(br.readLine());
            out.println(0);
            out.close();
            br.close();
            sock.close();
            return 0;
        } catch(Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
