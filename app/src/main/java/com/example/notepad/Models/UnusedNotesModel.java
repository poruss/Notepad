package com.example.notepad.Models;

public class UnusedNotesModel {
    private int notesId;
    private String notesTitle;
    private String notesMessage;
    private String notesDate;



    public void setNotesId(int notesId) {
        this.notesId = notesId;
    }
    public int getNotesId() {
        return notesId;
    }

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public void setNotesMessage(String notesMessage) {
        this.notesMessage = notesMessage;
    }

    public String getNotesMessage() {
        return notesMessage;
    }


    public void setNotesDate(String notesDate) {
        this.notesDate = notesDate;
    }
    public String getNotesDate() {
        return notesDate;
    }
}
