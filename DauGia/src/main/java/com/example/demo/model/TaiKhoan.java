package com.example.demo.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class TaiKhoan {
    @Id
    @Column(name = "taiKhoan")
    private String taiKhoan;
    private String matKhau;
    private boolean tinhTrang;
    private String ghiChu;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "taikhoan_quyen",
            joinColumns = @JoinColumn(name = "tai_khoan"),
            inverseJoinColumns = @JoinColumn(name = "ma_quyen")
    )
    private Set<Quyen> quyens;

    @OneToOne(mappedBy = "taiKhoan")
    private NguoiDung nguoiDung;

    @OneToMany(mappedBy = "taiKhoans")
    private Set<SanPham> sanPham;

    public TaiKhoan() {
    }

    public TaiKhoan(String taiKhoan, String matKhau, boolean tinhTrang, String ghiChu, Set<Quyen> quyens, NguoiDung nguoiDung, Set<SanPham> sanPham) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.tinhTrang = tinhTrang;
        this.ghiChu = ghiChu;
        this.quyens = quyens;
        this.nguoiDung = nguoiDung;
        this.sanPham = sanPham;
    }

    public TaiKhoan(String tendangnhap, String encode) {
        this.taiKhoan = tendangnhap;
        this.matKhau = encode;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Set<Quyen> getQuyens() {
        return quyens;
    }

    public void setQuyens(Set<Quyen> quyens) {
        this.quyens = quyens;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public Set<SanPham> getSanPham() {
        return sanPham;
    }

    public void setSanPham(Set<SanPham> sanPham) {
        this.sanPham = sanPham;
    }
}
