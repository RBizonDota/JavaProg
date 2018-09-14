package Client;

import java.io.*;
import java.net.*;
import java.util.*;

class Client{
    private int PORT_NUMBER = 3345;
    private String HOSTNAME = "127.0.0.1";
    private String CLIENTNAME = "Roman";
    public int start()
    {
        String f="";
        while (!(f.equals("/dc"))) {
            try {
                //Attempt to connect
                Socket sock = new Socket(HOSTNAME, PORT_NUMBER);
                PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                out.println(CLIENTNAME);
                //Output
                System.out.print("Enter command: ");
                Scanner scan = new Scanner(System.in);
                String client_string = scan.nextLine();
                f = client_string;
                out.println(client_string);
                out.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                System.out.println(br.readLine());//Дублирование сообщения клиента
                long j = Long.parseLong(br.readLine());//Сообщение о количестве строк
                for (long i = 0; i < j; i++)
                    System.out.println(br.readLine());//Построчный вывод ответа
                out.println(0);
                out.close();
                br.close();
                sock.close();
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }
        return 0;
    }
}
