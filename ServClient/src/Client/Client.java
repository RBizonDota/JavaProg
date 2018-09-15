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
    // Конструкторы
    Client(){}
    // Методы
    public int start()
    {
       while (flag) {
            try {
                System.out.print("Enter command: ");
                Scanner scan = new Scanner(System.in);
                String clientString = scan.nextLine();
                if( clientString.charAt(0) == '-')
                {
                    runClientCommand(clientString);
                }
                //Попытка подключения
                else{
                    Socket sock = new Socket(HOSTNAME, PORT_NUMBER);
                    PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                    out.println(CLIENTNAME);//Отправка имени клиента
                    //Вывод
                    out.println(clientString);//
                    out.flush();
                    BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    System.out.println(br.readLine());//Дублирование сообщения клиента
                    long j = Long.parseLong(br.readLine());//Сообщение о количестве строк
                    for (long i = 0; i < j; i++)
                        System.out.println(br.readLine());//Построчный вывод ответа
                    out.println(0);//Костыль, гарант, что сокет не закроется раньше времени
                    out.close();
                    br.close();
                    sock.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }
        return 0;
    }
    // Смена HOSTNAME
    private void changeHost(String newHostName)
    {
        HOSTNAME = newHostName;
    }
    // Обработка команд клиентом
    private void runClientCommand(String clientCommand)
    {
        //Вернется в начало цикла по continue cycle и завершится
        if(clientCommand.equals("-dc")){
            flag = false;
        }
        //Смена HOSTNAME по команде Change HOSTNAME
        else if(clientCommand.equals("-chh"))
        {
            String newHostName;
            System.out.print("Enter new host name: ");
            Scanner scan = new Scanner(System.in);
            newHostName = scan.nextLine();
            String oldHostName = HOSTNAME;
            changeHost(newHostName);
            try{
                Socket trySock = new Socket(HOSTNAME, PORT_NUMBER);
                trySock.close();
            }
            catch (SocketException e)
            {
                System.out.println("Invalid hostname");
                HOSTNAME = oldHostName;
            }
            catch(IOException e){}
        }
        else
        {
            System.out.println("Unknown client command");
        }
    }
}
