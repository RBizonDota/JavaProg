class Client{
	public static void start()
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
            out.close();
            sock.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
