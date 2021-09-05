package selfMadeChatAndServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThreadServer {
    ServerSocket socket;
    ArrayList<String> listOfMessages = new ArrayList<>();
    ArrayList<PrintWriter> writers=new ArrayList<>();
    int i=1;
    public static void main(String[] args) {
        new MultiThreadServer().go();
    }
    void go(){
        try {
            File folder=new File("/Users/yoelufland/Desktop/ServerArchive");                                    //creating directory of messages archive
            folder.mkdir();

            socket=new ServerSocket(5000);                                                                          //running up server
            Socket s;
            while(true) {
                s=socket.accept();
                System.out.println("New client request received");
                Thread NewClient = new Thread(new NewClient(s));                                                         //running new thread for each client
                NewClient.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    class NewClient implements Runnable{
        Socket sock;
        NewClient(Socket s){
            sock=s;
        }
        public void run(){                                                                                               //creating IO streams
            try {
                int id=i;
                i++;
                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
                writers.add(writer);                                                                                     //adding writer of client list of writers

                    String s;
                    while ((s = reader.readLine()) != null) {                                                            //sending back message from client
                        //MessageDatabase.add(s);
                        listOfMessages.add("Client id: "+id+".\nMessage: "+s);                                           //adding message to list of messages
                        SendMessageDatabase("id"+id+": "+s);
                    }


            } catch (Exception e) {
                e.printStackTrace();
            }
      }
    }
    void SendMessageDatabase(String s){                                                                                  //writing message to each client
        PrintTextToFile();
            for(PrintWriter w:writers){
                try {
                    w.print(s+"\n");
                    w.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }





    }
    void PrintTextToFile(){                                                                                              //adding message to archive
        try {
            File messagesLog=new File("/Users/yoelufland/Desktop/ServerArchive/MessagesLog.txt");
            messagesLog.createNewFile();
            FileWriter wr=new FileWriter(messagesLog);

            for(String s:listOfMessages){
                wr.write(s+"\n"+"\n");
            }
            wr.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

