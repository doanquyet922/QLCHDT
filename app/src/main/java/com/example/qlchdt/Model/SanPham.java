package com.example.qlchdt.Model;

public class SanPham {
    String maSp,tenSp,tenHang,cauHinh,anh;
    int soLuong,tgBh,giaNhap,giaBan;

    public SanPham() {
    }

    @Override
    public String toString() {
        return "Mã SP: "+maSp+
                "\nTên SP: '" + tenSp +
                "\ngiaBan=" + giaBan+
                "\n_______"
                ;
    }

    public SanPham(String maSp) {
        this.maSp = maSp;
    }

    public SanPham(String tenSp, String tenHang, String cauHinh, String anh, int soLuong, int tgBh, int giaNhap, int giaBan) {
        this.tenSp = tenSp;
        this.tenHang = tenHang;
        this.cauHinh = cauHinh;
        this.anh = anh;
        this.soLuong = soLuong;
        this.tgBh = tgBh;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
    }

    public SanPham(String maSp, String tenSp, String tenHang, String cauHinh, String anh, int soLuong, int tgBh, int giaNhap, int giaBan) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.tenHang = tenHang;
        this.cauHinh = cauHinh;
        this.anh = anh;
        this.soLuong = soLuong;
        this.tgBh = tgBh;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
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

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getCauHinh() {
        return cauHinh;
    }

    public void setCauHinh(String cauHinh) {
        this.cauHinh = cauHinh;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTgBh() {
        return tgBh;
    }

    public void setTgBh(int tgBh) {
        this.tgBh = tgBh;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }
}
