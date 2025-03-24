package com.vinay.chatapp.Models;

public class Follower {
    String followedby;
    Long followedat;

    public String getFollowedby() {
        return followedby;
    }

    public void setFollowedby(String followedby) {
        this.followedby = followedby;
    }

    public Long getFollowedat() {
        return followedat;
    }

    public void setFollowedat(Long followedat) {
        this.followedat = followedat;
    }
}
