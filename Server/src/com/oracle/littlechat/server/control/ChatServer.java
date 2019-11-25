package com.oracle.littlechat.server.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 这是聊天软件的服务器类，用来对客户端提供聊天功能中的各种服务
 *
 * 本次项目中，服务器充当两个角色
 * 1.业务处理中心，比如处理用户发送过来的登陆，或者注册请求
 * 2.消息中转中心，A跟B聊天，A发送的消息先发送到Server端，Server端解析完毕后，再转发到B
 */
public class ChatServer {

    private ServerSocket server;//定义一个serversocket对象，用来让客户端链接

    //1.在动态代码块中将，serversocket对象初始化。
    {
        try {
            server=new ServerSocket(8888);
            System.out.printf("服务器启动成功！");
        } catch (IOException e) {
            System.err.println("服务器启动失败！请检查端口是否被占用！");
        }
    }

    //2.在类的构造器里面开启对外服务的方法（调用accpet方法）
    public ChatServer(){
        //服务器启动成功后，构造器里准备开启对外服务
        try {
            while(true) {//服务器要对多个用户提供链接服务，所以这里必须开启一个循环不停的接受新的用户链接
                Socket client = server.accept();//调用accept方法接受客户端链接
                ObjectInputStream  in=new ObjectInputStream(client.getInputStream());
                ObjectOutputStream  out=new ObjectOutputStream(client.getOutputStream());
                System.out.println(client.getInetAddress().getHostAddress()+"链接进来了！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new ChatServer();
    }
}
