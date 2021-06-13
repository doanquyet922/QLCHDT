package com.example.qlchdt.Model;

import java.io.Serializable;
import java.util.List;

public class HoaDon implements Serializable {
    String maHdb,hoten,diaChi;
    List<CthoaDon>cthoaDons;
    public HoaDon(String maHdb, String hoten, String diaChi) {
        this.maHdb = maHdb;
        this.hoten = hoten;
        this.diaChi = diaChi;
    }

    public HoaDon(String hoten, String diaChi, List<CthoaDon> cthoaDons) {
        this.hoten = hoten;
        this.diaChi = diaChi;
        this.cthoaDons = cthoaDons;
    }

    public HoaDon(String maHdb, String hoten, String diaChi, List<CthoaDon> cthoaDons) {
        this.maHdb = maHdb;
        this.hoten = hoten;
        this.diaChi = diaChi;
        this.cthoaDons = cthoaDons;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHdb='" + maHdb + '\'' +
                ", hoten='" + hoten + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", list=" + cthoaDons +
                '}';
    }

    public List<CthoaDon> getList() {
        return cthoaDons;
    }

    public void setList(List<CthoaDon> list) {
        this.cthoaDons = list;
    }

    public HoaDon() {
    }

    public String getMaHdb() {
        return maHdb;
    }

    public void setMaHdb(String maHdb) {
        this.maHdb = maHdb;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
