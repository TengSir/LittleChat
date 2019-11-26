package com.oracle.littlechat.client.model;


import java.io.Serializable;

public class ChatUser  implements Serializable {

  private long username;
  private String password;
  private String nickname;
  private String sex;
  private long age;

  @Override
  public String toString() {
    return "ChatUser{" +
            "username=" + username +
            ", password='" + password + '\'' +
            ", nickname='" + nickname + '\'' +
            ", sex='" + sex + '\'' +
            ", age=" + age +
            ", image='" + image + '\'' +
            ", signature='" + signature + '\'' +
            '}';
  }

  private String image;
  private String signature;


  public long getUsername() {
    return username;
  }

  public void setUsername(long username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }


  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }


  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

}
