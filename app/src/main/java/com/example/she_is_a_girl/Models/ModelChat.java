package com.example.she_is_a_girl.Models;

public class ModelChat {
    String message,sender,reciver,time;
    boolean isSeen;

    public ModelChat() {
    }

    public ModelChat(String message, String sender, String reciver, String time, boolean isSeen) {
        this.message = message;
        this.sender = sender;
        this.reciver = reciver;
        this.time = time;
        this.isSeen = isSeen;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getReciver() {
        return reciver;
    }

    public String getTime() {
        return time;
    }

    public boolean isSeen() {
        return isSeen;
    }
}
