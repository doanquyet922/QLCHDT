package com.example.qlchdt.Model;

public class CthoaDon {
String MaHdb,maCthdb,maSp,tenSp;
int soLuong,tt,giaBan;

    public CthoaDon(String maHdb, String maCthdb, String maSp, String tenSp, int soLuong) {
        MaHdb = maHdb;
        this.maCthdb = maCthdb;
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.soLuong = soLuong;
    }

    public CthoaDon(String maHdb, String maCthdb, String maSp, int soLuong) {
        MaHdb = maHdb;
        this.maCthdb = maCthdb;
        this.maSp = maSp;
        this.soLuong = soLuong;
    }

    public CthoaDon(String maSp, String tenSp, int soLuong) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.soLuong = soLuong;
    }

    public CthoaDon() {
    }

    public CthoaDon(String maSp, String tenSp, int soLuong, int gia) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.soLuong = soLuong;
        this.tt = gia;
    }

    public CthoaDon(String maSp, String tenSp, int soLuong, int tt, int giaBan) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.soLuong = soLuong;
        this.tt = tt;
        this.giaBan = giaBan;
    }

    public int getTt() {
        return tt;
    }

    public void setTt(int tt) {
        this.tt = tt;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getTongTien() {
        return tt;
    }

    public void setTongTien(int gia) {
        this.tt = gia;
    }

    public String getMaHdb() {
        return MaHdb;
    }

    public void setMaHdb(String maHdb) {
        MaHdb = maHdb;
    }

    public String getMaCthdb() {
        return maCthdb;
    }

    public void setMaCthdb(String maCthdb) {
        this.maCthdb = maCthdb;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
