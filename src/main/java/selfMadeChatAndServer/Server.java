package selfMadeChatAndServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    ArrayList <String>listOfMessages=new ArrayList<>();
    public static void main(String []a){
    new Server().runServer();
    }
    void runServer(){

        try {
            ServerSocket serverSock = new ServerSocket(5000);
            Socket s=serverSock.accept();


            BufferedReader reader=new BufferedReader(new InputStreamReader (s.getInputStream()));
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

            while(true) {
                String str=reader.readLine();
                System.out.println("Client: "+str);
                listOfMessages.add(str);
                if(str.equalsIgnoreCase("exit")){
                    writer.write("Server is shutting down"+"\n");
                    writer.flush();
                    break;
                }
                writer.write(str+"\n");
                writer.flush();

            }

            for(String string:listOfMessages){
                System.out.println(string);
            }
PrintTextToFile();


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    void PrintTextToFile(){
        try {
            File folder=new File("/Users/yoelufland/Desktop/ServerArchive");
            folder.mkdir();
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
