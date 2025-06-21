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
    public String getUsername() {
        return username;
    }
    public String getMobile() {
        return mobile;
    }
    public String getEmail() {
        return email;
    }
    public String getProfilepic() {
        return profilepic;
    }
}
