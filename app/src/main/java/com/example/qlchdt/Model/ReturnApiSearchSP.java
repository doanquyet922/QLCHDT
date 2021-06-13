package com.example.qlchdt.Model;

import java.util.List;

public class ReturnApiSearchSP {
    String TenSp;
    int page,pageSize,totalItem;
    List<SanPham> data;

    public ReturnApiSearchSP() {
    }

    public ReturnApiSearchSP(String tenSp, int page, int pageSize, int totalItem, List<SanPham> lsp) {
        TenSp = tenSp;
        this.page = page;
        this.pageSize = pageSize;
        this.totalItem = totalItem;
        this.data = lsp;
    }

    public ReturnApiSearchSP(String tenSp, int page, int pageSize) {
        TenSp = tenSp;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getTenSp() {
        return TenSp;
    }

    public void setTenSp(String tenSp) {
        TenSp = tenSp;
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

    public List<SanPham> getLsp() {
        return data;
    }

    public void setLsp(List<SanPham> lsp) {
        this.data = lsp;
    }
}
