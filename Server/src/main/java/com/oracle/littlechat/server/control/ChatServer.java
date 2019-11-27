package com.oracle.littlechat.server.control;

import com.oracle.littlechat.client.model.ChatMessage;
import com.oracle.littlechat.client.model.ChatUser;
import com.oracle.littlechat.server.model.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private UserDAO  dao;//定义一个user数据库操作对象

    //1.在动态代码块中将，serversocket对象初始化。
    {
        try {
            server=new ServerSocket(8888);
            System.out.printf("服务器启动成功！");
            dao=new UserDAO();
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

                //为了保障每个客户端链接进来都可以独立和服务器发送消息而不影响其他客户端
                //所以每个链接进来的客户端都必须开启一个线程独立运行，接受客户端消息的代码
                MessageReciveThread  reciveThread=new MessageReciveThread(out,in);
                reciveThread.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //封装一个线程类，这个类用来不停的接受用户发过来的消息
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private class MessageReciveThread  extends  Thread{
        private ObjectOutputStream out;
        private ObjectInputStream  in;

        @Override
        public void run() {
            while(true){
                try {
                    ChatMessage c=(ChatMessage) in.readObject();//当前代码执行一次，代表服务器读取到客户端发送给过来了一条消息
                    System.out.println("服务器接收到一条消息："+c);
                    //使用一个条件分支结构对不同的消息做不同的处理
                    switch (c.getType()){
                        case LOGIN:{
                            System.out.println("登陆消息，这里应该链接数据库处理登陆业务");

                            //把登陆传过来的用户信息传入dao方法中，查询返回数据库里的用户对象
                            ChatUser user=dao.login(c.getFrom());

                            //这里应该链接数据库判断用户名和密码的正确与否,同时将判断结果封装到一个Message对象中
                            ChatMessage  loginResult=new ChatMessage();

                            //将查询返回的用户对象存储到消息对象中，返回给登陆客户端
                            loginResult.setFrom(user);

                            //消息封装好之后，就可以使用当前线程的输出流将这个登陆结果发送给客户端了
                            out.writeObject(loginResult);
                            out.flush();

                            break;
                        }
                        case REGISTER:{
                            System.out.println("注册消息，应该链接数据库添加新用户");
                            break;
                        }
                        case TEXT:{
                            System.out.println("文本消息，服务器将会把这条消息转发给具体的聊天用户");
                            break;
                        }
                        case SHAKE:{
                            System.out.println("抖动消息，服务器将会把这条消息转发给具体的聊天用户");
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("客户端已经关闭聊天程序！");
                   return ;
                }
            }
        }
    }


    public static void main(String[] args) {
        new ChatServer();
    }
}