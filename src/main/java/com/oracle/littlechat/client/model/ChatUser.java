package com.oracle.littlechat.client.model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ChatUser  implements Serializable {
  public Set<ChatUser> getFriends() {
    return friends;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChatUser chatUser = (ChatUser) o;
    return username == chatUser.username &&
            age == chatUser.age &&
            Objects.equals(password, chatUser.password) &&
            Objects.equals(nickname, chatUser.nickname) &&
            Objects.equals(sex, chatUser.sex) &&
            Objects.equals(friends, chatUser.friends) &&
            Objects.equals(image, chatUser.image) &&
            Objects.equals(signature, chatUser.signature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, nickname, sex, age, friends, image, signature);
  }

  public void setFriends(Set<ChatUser> friends) {
    this.friends = friends;
  }

  private long username;
  private String password;
  private String nickname;
  private String sex;
  private long age;
  private Set<ChatUser> friends=new HashSet<>();

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
