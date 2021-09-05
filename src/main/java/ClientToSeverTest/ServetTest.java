package ClientToSeverTest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServetTest {
    ArrayList <String>listOfMessages=new ArrayList<>();
    public static void main(String []a){
    new ServetTest().runServer();
    }
    void runServer(){

        try {
            ServerSocket serverSock = new ServerSocket(5000);
            Socket s=serverSock.accept();
            InputStreamReader r= new InputStreamReader (s.getInputStream());
            OutputStreamWriter w= new OutputStreamWriter(s.getOutputStream());

            BufferedReader reader=new BufferedReader(r);
            BufferedWriter writer=new BufferedWriter(w);

            while(true) {
                String str=reader.readLine();
                System.out.println("Client: "+str);
                listOfMessages.add(str);
                if(str.equalsIgnoreCase("exit")){
                    writer.write("Server is shutting down"+"\n");
                    writer.flush();
                    break;
                }
                writer.write("message arrived!"+"\n");
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
