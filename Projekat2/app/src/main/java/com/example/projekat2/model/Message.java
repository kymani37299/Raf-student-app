package com.example.projekat2.model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Message {

    @Exclude
    private final static SimpleDateFormat format = new SimpleDateFormat("hh:mm a dd.MM.yyyy");

    @Exclude
    private String id;

    private String sender;
    private String content;
    private String time;

    private MessageType type;

    public Message() { }

    public Message(String sender,MessageType type,String content) {
        this.type = type;
        this.sender = sender;
        switch (type) {
            case TEXT:
                this.content = content.replaceAll("(.{30})", "$1-\n");
                break;
            case IMAGE:
                this.content = content;
                break;
            default:
                this.content = content;
        }
        Date currTime = Calendar.getInstance().getTime();
        time = format.format(currTime);
    }

    public Message(String sender,String reciever,MessageType type,String content) {
        this.id = getMessageKey(sender,reciever);
        this.type = type;
        this.sender = sender;
        switch (type) {
            case TEXT:
                this.content = content.replaceAll("(.{30})", "$1-\n");
                break;
            case IMAGE:
                this.content = content;
                break;
            default:
                this.content = content;
        }
        Date currTime = Calendar.getInstance().getTime();
        time = format.format(currTime);
    }

    @Exclude
    public static String getMessageKey(String userIndex1,String userIndex2) {
        return userIndex1.compareTo(userIndex2) < 0 ? userIndex1 + userIndex2 : userIndex2 + userIndex1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String text) {
        this.content = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
