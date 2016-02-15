package com.silptech.kdf;

/**
 * Created by Amrit on 2/9/2016.
 */
public class NoticesModule {

    private String message = "";
    private String author = "";
    private String date = "";
    private String notesTitle = "";
    private String notesContents = "";

    public String getNotesTitle() {
        return notesTitle;
    }

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public String getNotesContents() {
        return notesContents;
    }

    public void setNotesContents(String notesContents) {
        this.notesContents = notesContents;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
