package com.example.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maDanhMuc;
    private String tenDanhMuc;
    @OneToMany(mappedBy = "danhMuc")
    private Set<SanPham> sanPham;
    public DanhMuc() {
    }
    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public Set<SanPham> getSanPham() {
        return sanPham;
    }

    public void setSanPham(Set<SanPham> sanPham) {
        this.sanPham = sanPham;
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
                "maDanhMuc=" + maDanhMuc +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                ", sanPham=" + sanPham +
                '}';
    }
}
