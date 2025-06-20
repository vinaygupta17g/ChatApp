package com.vinay.chatapp.Models;

public class MessageModel {
    String uid,message,timestamp,msgid;
    public String getMsgid() {
        return msgid;
    }
    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public MessageModel() {}

    public MessageModel(String uid, String message, String timestamp) {
        this.uid = uid;
        this.message = message;
        this.timestamp = timestamp;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}