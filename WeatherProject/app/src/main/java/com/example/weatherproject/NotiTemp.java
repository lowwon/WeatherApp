package com.example.weatherproject;

public class NotiTemp {
    private int check;
    private int id;

    public NotiTemp(int check) {
        this.setCheck(check);
    }

    public NotiTemp(int id, int check) {
        this.setCheck(check);
        this.setId(id);
    }

    public NotiTemp() {

    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
