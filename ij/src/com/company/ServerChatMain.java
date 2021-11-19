package com.company;


import java.awt.BorderLayout;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.awt.event.*;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerChatMain extends JFrame implements ActionListener,KeyListener {
    public static void main(String[] args) {
        new ServerChatMain();
    }
    private JTextArea jta;
    private JScrollPane jsp;
    private JPanel jp;
    private JTextField jtf;
    private JButton jb;
    private BufferedWriter bw=null;
    private static  int serverPort;
    static
    {
        Properties prop=new Properties();
        try {
            prop.load(new FileReader("chat.properties"));
            serverPort=Integer.parseInt(prop.getProperty("serverPort"));
        }
        catch (IOException e3){
            e3.printStackTrace();
        }
    }
    public ServerChatMain(){
        jta=new JTextArea();
        jsp=new JScrollPane(jta);
        jp=new JPanel();
        jtf=new JTextField(10);
        jb=new JButton("发送");
        jp.add(jtf);
        jp.add(jb);
        this.add(jsp,BorderLayout.CENTER);
        this.add(jp,BorderLayout.SOUTH);
        this.setTitle("happy_chat_server");
        this.setSize(300,300);
        this.setLocation(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        jb.addActionListener(this);
        jtf.addKeyListener(this);
        try{
            var serverSocket=new ServerSocket(serverPort);
            Socket socket=serverSocket.accept();
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line=null;
            while((line=br.readLine())!=null){
                jta.append(line+System.lineSeparator());
            }
            serverSocket.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String text=jtf.getText();
        text="服务端："+text;
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
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            String text=jtf.getText();
            text="服务端："+text;
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


