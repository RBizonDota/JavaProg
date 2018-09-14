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
                case "Date":{
                    func_Date(out,clientName);
                }
                break;
                case "Hi":{
                    func_Hi(out,clientName);
                }
                break;
                case "Hello":{
                    func_Hi(out,clientName);
                }
                break;
                case "Good morning":{
                    func_Hi(out,clientName);
                }
                break;
                case "Good day":{
                    func_Hi(out,clientName);
                }
                break;
                case "Good evening":{
                    func_Hi(out,clientName);
                }
                break;
                default:{
                    func_default(out, clientName);
                }
                break;
            }
            return 0;
        }
        catch (Exception ex)
        {
            return 1;
        }
    }

    int func_default(PrintWriter out, String clientName){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + "Unknown command");
            System.out.println(TimeStamp() + " - Task failed (Client " + clientName + ")");
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
    int func_Date(PrintWriter out, String clientName){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + LocalDate.now().toString());
            System.out.println(TimeStamp() + " - Task completed (Client " + clientName + ")");
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
    int func_Hi(PrintWriter out, String clientName){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + "Hello, The Living One, I am machine.");
            System.out.println(TimeStamp() + " - Task completed (Client " + clientName + ")");
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
}
