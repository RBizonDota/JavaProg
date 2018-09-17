package Client;


import java.io.*;
import java.net.*;
import java.util.*;

class Client{
    // Поля
    private int PORT_NUMBER = 3345; //Порт хоста
    private String HOSTNAME = "127.0.0.1"; //IP хоста
    private String CLIENTNAME = "Roman"; //Имя клиента, видимое на сервере
    private boolean flag = true; //Флаг работы системы

    private boolean connected;
    private Socket sock;
    private Scanner scan;
    private PrintWriter out;
    private BufferedReader br;
    // Конструкторы
    Client(){
        scan = new Scanner(System.in);
        sock = null;
        out = null;
        br = null;
        connected = false;
    }
    // Методы
    public int start()
    {
       while (flag) {
            try {
                System.out.print("Enter command: ");
                String clientString = scan.nextLine();
                if(clientString.length()==0)
                    continue;
                if( clientString.charAt(0) == '-')
                {
                    runClientCommand(clientString);
                }
                //Попытка подключения
                else{
                    if(connected){
                        clientString = handleClientString(clientString);
                        sendMsg(clientString,true);
                    }
                    else{
                        System.out.println("--No connection found");
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }
        return 0;
    }
    //обработка clientString
    private String handleClientString(String string)
    {
        String buffer = string.replaceAll("\\p{Punct}"," ");
        while( buffer.contains("  "))
        {
            String newBuffer = buffer.replace("  "," ");
            buffer = newBuffer;
        }

        return buffer.toLowerCase();
    }
    // Смена HOSTNAME
    private void changeHost()
    {
        String newHostName;
        System.out.print("Enter new host name: ");
        Scanner scan = new Scanner(System.in);
        newHostName = scan.nextLine();
        String oldHostName = HOSTNAME;
        HOSTNAME = newHostName;
        try{
            Socket trySock = new Socket(HOSTNAME, PORT_NUMBER);
            trySock.close();
        }
        catch (SocketException e)
        {
            System.out.println("!!! Invalid hostname, change failed");
            HOSTNAME = oldHostName;
        }
        catch(IOException e){}
    }
    private void sendMsg(String clientString, boolean printres){
        try {
            out.println(clientString);
            out.flush();
            if (printres) {
                System.out.println(br.readLine());//Дублирование сообщения клиента
                long j = Long.parseLong(br.readLine());//Сообщение о количестве строк
                for (long i = 0; i < j; i++)
                    System.out.println(br.readLine());//Построчный вывод ответа
            }
            else{
                br.readLine();
                long j = Long.parseLong(br.readLine());//Сообщение о количестве строк
                for (long i = 0; i < j; i++)
                    br.readLine();//Построчный вывод ответа
            }
        }
        catch(IOException ex){}
    }
    // Обработка команд клиентом
    private void runClientCommand(String clientCommand)
    {
        switch(clientCommand){
            case "-dc":{
                if(connected){
                    sendMsg("-dc",true);
                    sock = null;
                    out = null;
                    br = null;
                    connected = false;
                }
                else{
                    System.out.println("--No connection found");
                }
            }
            break;
            case "-chh":{
                changeHost();
            }
            break;
            case "-cn":{
                try {
                    sock = new Socket(HOSTNAME, PORT_NUMBER);
                    out = new PrintWriter(sock.getOutputStream(), true);
                    out.println(CLIENTNAME);//Отправка имени клиента
                    br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    connected = true;
                    System.out.println("--Connection made");
                }
                catch(Exception ex){
                    System.out.println("--Connection failed");
                }
            }
            break;
            case "-help":{
                System.out.println("--Client commands: ");
                System.out.println("--1) -cn     - Making connection between you and server (HOST IP = "+HOSTNAME+")");
                System.out.println("--2) -status - Shows status of current session");
                System.out.println("--2) -chh    - Change host to a new one (current HOST IP = "+HOSTNAME+")");
                System.out.println("--3) -dc     - Destroy connection between you and server");
                System.out.println("--4) -exit   - Close program");
                System.out.println("--Server commands (You must have connection up): ");
                System.out.println("--1) Date    - Shows today's date");
                System.out.println("--2) Hi      - Hello (has different variants: Hello, Good morning, etc");
            }
            break;
            case "-status":{
                if(connected){
                    System.out.println("--Connection:  up");
                }else{
                    System.out.println("--Connection:  no");
                }
                    System.out.println("--HOST IP:     "+HOSTNAME);
                    System.out.println("--PORT:        "+PORT_NUMBER);
                    System.out.println("--Client Name: "+CLIENTNAME);
            }
            break;
            case "-exit":{
                if(connected){
                    sendMsg("-dc",false);
                    sock = null;
                    out = null;
                    br = null;
                    connected = false;
                    System.out.println("--Connection broken");
                }
                flag = false;
                System.out.println("--Exiting...");
            }
            break;
            default:{
                System.out.println("--Unknown client command");
            }
            break;
        }
    }
}
