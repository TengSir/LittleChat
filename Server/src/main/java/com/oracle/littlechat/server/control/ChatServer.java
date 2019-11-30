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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private Map<Long,ObjectOutputStream> allClients=new HashMap<>();//定义一个键值对集合，存储所有链接进来对用户对QQ号和它底层对应对输出流

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

                            //应该在登陆成功对时候，将当前用户的qq号和当前线程里面的socket输出流存储到服务器的集合中，方便后期服务器能找到这个人给他转发消息
                            if(user!=null){
                                allClients.put(user.getUsername(),out);
                            }

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

                            boolean result=dao.register(c.getFrom());
                            System.out.println("注册"+(result?"成功":"失败"));
                            c.setContent(Boolean.toString(result));
                            out.writeObject(c);
                            out.flush();
                            break;
                        }
                        case TEXT:{
                        }
                        case SHAKE:{
                            System.out.println("抖动消息和文本消息都执行如下代码，服务器将会把这条消息转发给具体的聊天用户");
                            //1.更新消息时间
                            c.setTime(new Date().toLocaleString());
                            //2.到服务器的那个所有客户端的集合里去找消息接受人是否在这个集合里
                            long to=c.getTo().getUsername();//获取消息接受人的QQ号
                            if(allClients.containsKey(to)){//if 说明从服务器的列表里找到了消息接受人（对方在线的）
                                //既然找到这个用户了，就从服务器的集合里拿出这个消息接受用户的输出流，将消息发送给这个用户即可
                                ObjectOutputStream out= allClients.get(to);
                                out.writeObject(c);
                                out.flush();
                                System.out.println("对方在线，服务器已经将消息转发过去");
                            }else{
                                System.out.println("对方不在线，服务器不转发消息");
                                //else说明对方不在线，这里可以熟悉额外的代码将消息暂存到数据库，等用户登陆后再提取（离线消息缓存）
                            }
                            //System.out.println("文本消息，服务器将会把这条消息转发给具体的聊天用户");
                            break;
                        }
                        case GROUPTEXT:{
                            System.out.println("这是群聊消息");
                            c.setTime(new Date().toLocaleString());
                            for( long  username:allClients.keySet()){
                                if(username!=c.getFrom().getUsername()){
                                    allClients.get(username).writeObject(c);
                                    allClients.get(username).flush();
                                }

                            }
                            break;
                        }
                        case UPDATENICKNAME:{
                            boolean result=dao.updateNickname(c.getFrom());
                            System.out.println("更新昵称"+(result?"成功":"失败"));
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