package com.example.qlchdt.Model;

import java.util.List;

public class ReturnAPISearchHD {
    String hoten;
    int page,pageSize,totalItem;
    List<HoaDon> data;

    public ReturnAPISearchHD(String hoten, int page, int pageSize, int totalItem, List<HoaDon> data) {
        this.hoten = hoten;
        this.page = page;
        this.pageSize = pageSize;
        this.totalItem = totalItem;
        this.data = data;
    }

    public ReturnAPISearchHD() {
    }

    public ReturnAPISearchHD(String hoten, int page, int pageSize) {
        this.hoten = hoten;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public List<HoaDon> getData() {
        return data;
    }

    public void setData(List<HoaDon> data) {
        this.data = data;
    }
}
