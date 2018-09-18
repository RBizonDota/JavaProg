package Server;
import java.io.PrintWriter;
import java.time.*;


public class FuncStack {
    public String TimeStamp(){
        String h = Integer.toString(LocalTime.now().getHour());
        if(h.length()<2)
            h="0"+h;
        String m = Integer.toString(LocalTime.now().getMinute());
        if(m.length()<2)
            m="0"+m;
        String s = Integer.toString(LocalTime.now().getSecond());
        if(s.length()<2)
            s="0"+s;
        String res = h + ":" + m + ":" + s;
        return res;
    }
    public static int fact(int n)
    {
        if (n == 0) return 1;
        return n * fact(n-1);
    }
    public int rooter(String s, PrintWriter out, String clientName, int code){
        try{
            switch(s){
                case "date":{
                    func_Date(out,clientName,s,code);
                }
                break;
                case "hi":{
                    func_Hi(out,clientName,s,code);
                }
                break;
                case "hello":{
                    func_Hi(out,clientName,s,code);
                }
                break;
                case "good morning":{
                    func_Hi(out,clientName,s,code);
                }
                break;
                case "good day":{
                    func_Hi(out,clientName,s,code);
                }
                break;
                case "good evening":{
                    func_Hi(out,clientName,s,code);
                }
                break;
                case "-dc":{
                    func_dc(out,clientName,s,code);
                    return 1;
                }
                case "cvar1":{
                    func_code(out,clientName,s,code);
                }
                break;
                default:{
                    func_default(out, clientName,s,code);
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

    int func_default(PrintWriter out, String clientName,String cmd,int code){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + "Unknown command");
            System.out.println(TimeStamp() + " <"+code+"> Task failed (Client " + clientName + ") Unknown command: "+cmd);
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
    int func_Date(PrintWriter out, String clientName,String cmd,int code){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + LocalDate.now().toString());
            System.out.println(TimeStamp() + " <"+code+"> Task completed (Client " + clientName + ") command "+cmd);
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
    int func_Hi(PrintWriter out, String clientName,String cmd,int code){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + "Hello, The Living One, I am machine.");
            System.out.println(TimeStamp() + " <"+code+"> Task completed (Client " + clientName + ") command "+cmd);
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
    int func_dc(PrintWriter out, String clientName,String cmd,int code){
        try {
            out.println(1);
            out.println(TimeStamp() + " - " + "Server: " + "Disconnecting...");
            System.out.println(TimeStamp() + " <"+code+"> Task completed (Client " + clientName + ") command "+cmd);
            return 0;
        }catch(Exception ex)
        {
            return 1;
        }
    }
    int func_code(PrintWriter out, String clientName,String cmd,int code){

        int ax_start = 0b1010;
        int ax = ax_start;
        int gx = 0b1011;
        int sx;
        ax<<=3;
        int lx = ax;//TODO ошибка!!!

        for (int i = 3;i>-1;i--){
            int lol = (int)(Math.pow(2,(double)(i+3)));
            if(lx/lol==1){
                lx^=gx<<i;
            }
        }
        sx = ax ^ (lx);
        out.println(12);
        out.println(TimeStamp() + " - " + "Server: " + "AX = "+Integer.toBinaryString(ax_start));
        out.println(TimeStamp() + " - " + "Server: " + "SX = "+Integer.toBinaryString(sx));
        lx = sx;
        for (int i = 3;i>-1;i--){
            int lol = (int)(Math.pow(2,(double)(i+3)));
            if(lx/lol==1){
                lx^=gx<<i;
            }
        }
        out.println(TimeStamp() + " - " + "Server: " + "SX%GX = "+Integer.toBinaryString(lx));

        int l;
        int buf;
        int st=0;
        int[] stat = new int[8];
        int[] stall = new int[8];
        for (int exept = 0;exept<128;exept++)
        {
            st = 0;
            buf = exept;
            for(int i = 6;i>-1;i--)
            {
                l = (int)(Math.pow(2,(double)(i)));
                if(buf/l==1){
                    st++;// Получили количество значащих единиц в векторе ошибок
                    buf -=l;
                }
            }
            stall[st]++;
            lx = sx;
            lx^=exept;//выражение с ошибкой
            for (int i = 3;i>-1;i--){
                int lol = (int)(Math.pow(2,(double)(i+3)));
                if(lx/lol==1){
                    lx^=gx<<i;
                }
            }
            if(lx!=0)
                stat[st]++;
        }
        out.println(TimeStamp() + " - " + "Server: " + "Decoding:");
        for (int i=1;i<8;i++) {
            out.println(TimeStamp() + " - " + "Server: " + "Number of changed bits = "+i+", number of codes = "+stall[i]+", detected exceptions = "+stat[i]+", "+(double)(stat[i])/(double)(stall[i])*100+"%");
        }

        System.out.println(TimeStamp() + " <"+code+"> Task completed (Client " + clientName + ") command "+cmd);
        return 0;
    }
}
