package Server;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

public class FuncStack {
    public String TimeStamp(){
        String res = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                + LocalTime.now().getSecond();
        return res;
    }

    public int rooter(String s, PrintWriter out, String clientName){
        try{
            switch(s){
                case "date":{
                    func_Date(out,clientName,s);
                }
                break;
                case "hi":{
                    func_Hi(out,clientName,s);
                }
                break;
                case "hello":{
                    func_Hi(out,clientName,s);
                }
                break;
                case "good morning":{
                    func_Hi(out,clientName,s);
                }
                break;
                case "good day":{
                    func_Hi(out,clientName,s);
                }
                break;
                case "good evening":{
                    func_Hi(out,clientName,s);
                }
                break;
                case "-dc":{
                    func_dc(out,clientName,s);
                    return 1;
                }
                default:{
                    func_default(out, clientName,s);
                }
                break;
            }
            return 0;
        }
        catch (Exception ex)
        {
            return -1;
        }
    }

    int func_default(PrintWriter out, String clientName,String cmd){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + "Unknown command");
            System.out.println(TimeStamp() + " - Task failed (Client " + clientName + ") Unknown command: "+cmd);
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
    int func_Date(PrintWriter out, String clientName,String cmd){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + LocalDate.now().toString());
            System.out.println(TimeStamp() + " - Task completed (Client " + clientName + ") command "+cmd);
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
    int func_Hi(PrintWriter out, String clientName,String cmd){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + "Hello, The Living One, I am machine.");
            System.out.println(TimeStamp() + " - Task completed (Client " + clientName + ") command "+cmd);
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
    int func_dc(PrintWriter out, String clientName,String cmd){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + "Disconnecting...");
            System.out.println(TimeStamp() + " - Task completed (Client " + clientName + ") command "+cmd);
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
}
