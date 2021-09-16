package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;


public class NguoiDungTaiKhoan  implements Validator {

//    @NotEmpty(message = "Tên đăng nhập không được để trống")
    private String tenNguoiDung1;

//    @Id
//    @Email(message = "Sai định dạng Email")
    private String email1;
//    @Size(min = 9,max = 13,message = "Số điện thoai phải có độ dài 10-12 số")
    private String soDienThoai1;
    private String diaChi1;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String ngaySinh1;

    private boolean gioiTinh1;
//    @Pattern(regexp = "(^$|[0-9]*$)", message = "CMND phải theo định dạng [0][0-9]")
    private String cmnd1;

//    @NotBlank(message = "vui lòng nhập tên đăng nhập")
    private String taiKhoan1;

//    @NotBlank(message = "vui lòng nhập mật khẩu")
    private String matKhau1;

    public NguoiDungTaiKhoan(String tenNguoiDung1, String email1, String soDienThoai1, String diaChi1, String ngaySinh1, boolean gioiTinh1, String cmnd1, String taiKhoan1, String matKhau1) {
        this.tenNguoiDung1 = tenNguoiDung1;
        this.email1 = email1;
        this.soDienThoai1 = soDienThoai1;
        this.diaChi1 = diaChi1;
        this.ngaySinh1 = ngaySinh1;
        this.gioiTinh1 = gioiTinh1;
        this.cmnd1 = cmnd1;
        this.taiKhoan1 = taiKhoan1;
        this.matKhau1 = matKhau1;
    }

    public NguoiDungTaiKhoan() {

    }

    public String getTenNguoiDung1() {
        return tenNguoiDung1;
    }

    public void setTenNguoiDung1(String tenNguoiDung1) {
        this.tenNguoiDung1 = tenNguoiDung1;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getSoDienThoai1() {
        return soDienThoai1;
    }

    public void setSoDienThoai1(String soDienThoai1) {
        this.soDienThoai1 = soDienThoai1;
    }

    public String getDiaChi1() {
        return diaChi1;
    }

    public void setDiaChi1(String diaChi1) {
        this.diaChi1 = diaChi1;
    }

    public String getNgaySinh1() {
        return ngaySinh1;
    }

    public void setNgaySinh1(String ngaySinh1) {
        this.ngaySinh1 = ngaySinh1;
    }

    public boolean isGioiTinh1() {
        return gioiTinh1;
    }

    public void setGioiTinh1(boolean gioiTinh1) {
        this.gioiTinh1 = gioiTinh1;
    }

    public String getCmnd1() {
        return cmnd1;
    }

    public void setCmnd1(String cmnd1) {
        this.cmnd1 = cmnd1;
    }

    public String getTaiKhoan1() {
        return taiKhoan1;
    }

    public void setTaiKhoan1(String taiKhoan1) {
        this.taiKhoan1 = taiKhoan1;
    }

    public String getMatKhau1() {
        return matKhau1;
    }

    public void setMatKhau1(String matKhau1) {
        this.matKhau1 = matKhau1;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
