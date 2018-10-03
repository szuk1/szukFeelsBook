package com.example.stephen.szuk_feelsbook;

import java.time.LocalDateTime;
import java.util.Date;

public class Emotion {
    private String emotionType;
    private LocalDateTime date;
    private String Comment;

    public String getComment(){
        return this.Comment;
    }
    public LocalDateTime getDate(){
        return this.date;
    }
    public void setDate(LocalDateTime newDate){
        this.date = newDate;
    }

    public void setComment(String newComment){
        this.Comment = newComment;
    }

    public Emotion(String Comment){
        this.Comment = Comment;
        this.date = LocalDateTime.now();
    }
    public Emotion(){
        this.Comment = "";
        this.date =  LocalDateTime.now();
    }

    public String getType(){
        return this.emotionType;
    }

    public void setType(String emotionType){
        this.emotionType = emotionType;
    }


}

