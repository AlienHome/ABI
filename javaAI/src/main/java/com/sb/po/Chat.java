package com.sb.po;

public class Chat{
    private String to;
    private String from;
    private String content;

    public void setTo(String to){
        this.to = to;
    }
    public String getTo(){
        return to;
    }

    public void setFrom(String from){
        this.from = from;
    }
    public String getFrom(){
        return from;
    }

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return content;
    }
}