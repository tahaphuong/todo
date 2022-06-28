package de.ls5.wt2.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class DBTodo extends DBIdentified {

    private Date publishedOn;
    private String headline;
    private String content;
    private String author;
    private boolean isPrivate = false;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = ISO.DATE_TIME)

    public Date getPublishedOn() {
        return publishedOn;
    }
    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getHeadline() {
        return headline;
    }
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {return author;}
    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean getIsPrivate() {return isPrivate;}
    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

}

