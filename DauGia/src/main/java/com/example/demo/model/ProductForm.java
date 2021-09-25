package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class ProductForm implements Validator {

    private MultipartFile hinh;

    @NotEmpty(message = "Vui lòng nhập tên sản phẩm.")
    private String tenSanPham;
    @Min(value = 1000, message = "Giá khởi điểm vui lòng nhập lớn hơn 1000đ.")
    private double giaKhoiDiem;
    private String tinhTrang;

    @Column(length = 2000)
    @NotEmpty(message = "Vui lòng nhập mô tả")
    private String moTa;
    private String ngayBatDau;
    private String ngayKetThuc;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String ngayDangKi;
    @NotEmpty(message = "Không được để trống giờ.")
    private String gioBatDau;
    @NotEmpty(message = "Không được để trống giờ.")
    private String gioKetThuc;
    @Min(value = 1, message = "Số lượng phải lớn hơn 1.")
    private int soLuong;
    @Min(value = 1000, message = "Bước giá phải lớn hơn 1000đ.")
    private double mucGia;
    private DanhMuc danhMuc;
    private TaiKhoan taiKhoans;


    public TaiKhoan getTaiKhoans() {
        return taiKhoans;
    }

    public void setTaiKhoans(TaiKhoan taiKhoans) {
        this.taiKhoans = taiKhoans;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public ProductForm() {
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public MultipartFile getHinh() {
        return hinh;
    }

    public void setHinh(MultipartFile hinh) {
        this.hinh = hinh;
    }

    public double getGiaKhoiDiem() {
        return giaKhoiDiem;
    }

    public void setGiaKhoiDiem(double giaKhoiDiem) {
        this.giaKhoiDiem = giaKhoiDiem;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getNgayDangKi() {
        return ngayDangKi;
    }

    public void setNgayDangKi(String ngayDangKi) {
        this.ngayDangKi = ngayDangKi;
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public String getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(String gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getMucGia() {
        return mucGia;
    }

    public void setMucGia(double mucGia) {
        this.mucGia = mucGia;
    }

    public MultipartFile gethinh() {
        return hinh;
    }

    public void sethinh(MultipartFile hinh) {
        this.hinh= hinh;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SanPham.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SanPham sanPham = (SanPham) target;
        String ngayDangKy = sanPham.getNgayDangKi();
        String ngayBatDau = sanPham.getNgayBatDau();
        String ngayKetThuc = sanPham.getNgayKetThuc();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();

        try {
            date1 = formatter.parse(ngayDangKy);
            date2 = formatter.parse(ngayBatDau);
            date3 = formatter.parse(ngayKetThuc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date1 + "\n" + date1 + "\n" + date3);
        if (date1.after(date2)) {
            errors.rejectValue("ngayBatDau", "ngayBatDau.date");
        }
        if (date2.after(date3)) {
            errors.rejectValue("ngayKetThuc", "ngayKetThuc.date");
        }
    }

}



