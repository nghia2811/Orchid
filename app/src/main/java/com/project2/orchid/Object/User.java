package com.project2.orchid.Object;

public class User {
    private String Ten, AnhDaiDien, DiaChi, UID, Email, Date;


    public User(String uid, String ten, String anhDaiDien, String email, String diaChi, String date) {
        Email = email;
        UID = uid;
        Ten = ten;
        AnhDaiDien = anhDaiDien;
        DiaChi = diaChi;
        Date = date;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAnhDaiDien() {
        return AnhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        AnhDaiDien = anhDaiDien;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
