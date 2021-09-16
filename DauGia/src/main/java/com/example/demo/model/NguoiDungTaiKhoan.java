package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;

public class NguoiDungTaiKhoan  implements Validator {

//    @NotEmpty(message = "Tên đăng nhập không được để trống")
    private String tenNguoiDung;

//    @Id
//    @Email(message = "Sai định dạng Email")
    private String email;
//    @Size(min = 9,max = 13,message = "Số điện thoai phải có độ dài 10-12 số")
    private String soDienThoai;
    private String diaChi;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String ngaySinh;

    private boolean gioiTinh;
//    @Pattern(regexp = "(^$|[0-9]*$)", message = "CMND phải theo định dạng [0][0-9]")
    private String cmnd;

//    @NotBlank(message = "vui lòng nhập tên đăng nhập")
    private String taiKhoan;

//    @NotBlank(message = "vui lòng nhập mật khẩu")
    private String matKhau;

    public NguoiDungTaiKhoan(String tenNguoiDung, String email, String soDienThoai, String diaChi, String ngaySinh, boolean gioiTinh, String cmnd, String taiKhoan, String matKhau) {

        this.tenNguoiDung = tenNguoiDung;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.cmnd = cmnd;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public NguoiDungTaiKhoan() {
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
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

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
