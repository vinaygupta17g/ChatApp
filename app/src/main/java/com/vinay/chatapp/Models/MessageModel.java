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
    public String getMessage() {
        return message;
    }
    public String getTimestamp() {
        return timestamp;
    }
}