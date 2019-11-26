package com.oracle.littlechat.server.model;

import com.oracle.littlechat.client.model.ChatUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class UserDAO {
    private Connection con;
    private Statement  sta;

    public Connection getCon() {
        try {
            con= DriverManager.getConnection(properties.get("url").toString(),properties.get("username").toString(),properties.get("password").toString());
            sta=con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public Statement getSta() {
        try {
            return getCon().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PreparedStatement getPre(String sql) {
        try {
            return getCon().prepareCall(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PreparedStatement pre;
    private Properties  properties;
    {
        properties=new Properties();
        try {
            System.out.println(getClass().getClassLoader().getResource("jdbc.properties"));
            properties.load(getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
            Class.forName(properties.get("driverClassName").toString());
            con= DriverManager.getConnection(properties.get("url").toString(),properties.get("username").toString(),properties.get("password").toString());
            sta=con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库登陆方法，根据传入的用户名和密码查询返回一个用户对象
     * @param user
     * @return
     */
    public  ChatUser  login(ChatUser  user){
        ChatUser u=null;
        PreparedStatement pre=getPre("select * from chatuser where username=? and password=?");
        try {
            pre.setLong(1,user.getUsername());
            pre.setString(2,user.getPassword());
            ResultSet rs=pre.executeQuery();
            if(rs.next()){
              u=new ChatUser();
              u.setUsername(rs.getLong("username"));
              u.setNickname(rs.getString("nickname"));
              u.setSex(rs.getString("sex"));
              u.setAge(rs.getInt("age"));
              u.setImage(rs.getString("image"));
              u.setSignature(rs.getString("signature"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return u;
        }
    }

    /**
     * 注册新用户的方法，根据传入的注册用户信息向数据库添加一个新的用户
     * @param user
     * @return
     */
    public boolean register(ChatUser user){
        boolean added=false;
        PreparedStatement pre=getPre("insert into chatuser(username,password,nickname,sex,age,image,signature) values(?,?,?,?,?,?,?)");
        try {
            pre.setLong(1,user.getUsername());
            pre.setString(2 ,user.getPassword());
            pre.setString(3,user.getNickname());
            pre.setString(4,user.getSex());
            pre.setInt(5,(int)user.getAge());
            pre.setString(6,user.getImage());
            pre.setString(7,user.getSignature());
            added=pre.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return added;
    }
}
