package com.example.assiduity;

public class savescan {
    private String saves, date;

    public savescan(){}

    public savescan(String saves, String date) {
        this.saves = saves;
        this.date = date;
    }

    public String getSaves() {
        return saves;
    }

    public String getDate() {
        return date;
    }

    public void setSaves(String saves) {
        this.saves = saves;
    }
}
