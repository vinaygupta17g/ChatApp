package com.vinay.chatapp.Models;

public class Users
{
    String uid;
    String username;
    String mobile;
    String email;
    String password;
    String lastmessage;
    String profilepic;
    String coverphoto;
    int followercount;
    public int getFollowercount() {
        return followercount;
    }
    public void setFollowercount(int followercount) {
        this.followercount = followercount;
    }
    public String getCoverphoto() {
        return coverphoto;
    }
    public void setCoverphoto(String coverphoto) {
        this.coverphoto = coverphoto;
    }
    public Users(String uid, String username, String mobile, String email, String password) {
        this.uid = uid;
        this.username = username;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }
    public Users(){}
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getLastmessage() {
        return lastmessage;
    }
    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }
    public String getProfilepic() {
        return profilepic;
    }
}
