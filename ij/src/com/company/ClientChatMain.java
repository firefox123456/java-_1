package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ClientChatMain extends JFrame implements ActionListener,KeyListener {
    public static void main(String[] args) {
        new ClientChatMain();
    }
    private JTextArea jta;
    private JScrollPane jsp;
    private JPanel jp;
    private JTextField jtf;
    private JButton jb;
    private BufferedWriter bw=null;
    private  static  String clientIp;
    private static  int clientPort;
    static
    {
        Properties prop=new Properties();
        try {
            prop.load(new FileReader("chat.properties"));
            clientIp=prop.getProperty("clientIp");
            clientPort=Integer.parseInt(prop.getProperty("clientPort"));
        }
        catch (IOException e4){
            e4.printStackTrace();
        }
    }
    public ClientChatMain(){
        jta=new JTextArea();
        jsp=new JScrollPane(jta);
        jp=new JPanel();
        jtf=new JTextField(10);
        jb=new JButton("发送");
        jp.add(jtf);
        jp.add(jb);
        this.add(jsp,BorderLayout.CENTER);
        this.add(jp,BorderLayout.SOUTH);
        this.setTitle("happy_chat_client");
        this.setSize(300,300);
        this.setLocation(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        jb.addActionListener(this);
        jtf.addKeyListener(this);
        try {
            var socket=new Socket(clientIp,clientPort);
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line=null;
            while((line=br.readLine())!=null){
                jta.append(line+System.lineSeparator());
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String text=jtf.getText();
        text="客户端："+text;
        jta.append(text+System.lineSeparator());
        try {
            bw.write(text);
            bw.newLine();
            bw.flush();
            jtf.setText("");
        } catch (Exception el) {
            el.printStackTrace();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            String text=jtf.getText();
            text="客户端："+text;
            jta.append(text+System.lineSeparator());
            try {
                bw.write(text);
                bw.newLine();
                bw.flush();
                jtf.setText("");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}

