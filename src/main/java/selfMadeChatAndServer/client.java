package selfMadeChatAndServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class client{
    //ArrayList<String> MessageDatabese = new ArrayList<String>();
    File filename;
    JTextArea message;
    BufferedReader reader;
    BufferedWriter writer;
    JTextArea chat;
    public static void main(String []args){
        new client().go();
    }
    void go(){
        JFrame frame=new JFrame("JoelChat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        JPanel panel=new JPanel();
        panel.setBackground(Color.BLACK);

        message=new JTextArea(3,20);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.addKeyListener(new EnterPressed());

        JScrollPane MessagePane=new JScrollPane(message);
        MessagePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        MessagePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        chat=new JTextArea(10,20);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setEditable(false);

        JScrollPane ChatPane=new JScrollPane(chat);
        ChatPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        ChatPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton send=new JButton("send");
        send.setFocusable(false);
        send.addActionListener(new SendListener());


        panel.add(ChatPane);
        panel.add(MessagePane);
        panel.add(send);
        frame.add(panel);


        SetUpConnection();

        frame.setSize(300,300);
        frame.setVisible(true);

        Thread chatThread=new Thread(new ChatReader());
        chatThread.start();

    }


    void SetUpConnection(){
        try {
            Socket socket=new Socket("localhost",5000);
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("connected to server!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ChatReader implements Runnable{
        public void run(){
            String chatText;

            while(true){
                try {

                   while (((chatText=reader.readLine())!=null)){
                        chat.append(chatText+"\n");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }



    class SendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Send is Pressed!");
            sendMessage(true);

        }
    }

    class EnterPressed implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {


            if(e.getExtendedKeyCode()==10){
                sendMessage(false);
            }

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
    void sendMessage(boolean b){
        if(b==true){
            try {
                writer.write(message.getText()+"\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            try {
                writer.write(message.getText());
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        message.setText("");
        message.requestFocus();
    }


}