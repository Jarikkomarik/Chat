package ClientToSeverTest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientTest {
    public static void main(String []a){
        new ClientTest().runClient();
    }
    void runClient(){
        try {
            Socket s=new Socket("localhost",5000);

            BufferedReader reader=new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

            Scanner scanner=new Scanner(System.in);

            while(true){
                writer.write(scanner.nextLine()+"\n");
                writer.flush();

                System.out.println(reader.readLine());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
