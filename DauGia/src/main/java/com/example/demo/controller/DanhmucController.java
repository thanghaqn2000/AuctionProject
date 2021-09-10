package com.example.demo.controller;


import com.example.demo.model.DanhMuc;
import com.example.demo.model.NguoiDung;
import com.example.demo.repository.nguoi_dung.NguoiDungRepo;
import com.example.demo.service.danh_muc.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class DanhmucController {
    @Autowired
    private DanhMucService danhMucService;

    @Autowired
    NguoiDungRepo nguoiDungRepo;

    @GetMapping(value = "/danhmuc/list")
    private String viewList(Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("danhmucs", danhMucService.findAll());
        return "/nhu/danhmuc/list";
    }

    @GetMapping(value = "/danhmuc/create")
    private String viewCreate(Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("danhmucs", new DanhMuc());
        return "/nhu/danhmuc/create";
    }

    @PostMapping(value = "/danhmuc/create")
    private String Create(DanhMuc danhMuc, Model model, Principal principal) {
        danhMucService.create(danhMuc);
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("mgsedit", "Thêm mới danh mục thành công");
        model.addAttribute("danhmucs", danhMucService.findAll());
        return "/nhu/danhmuc/list";
    }

    @GetMapping(value = "/danhmuc/edit")
    public String ViewEdit(@RequestParam("id") Integer id, Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("danhmucs", danhMucService.findById(id));
        return "/nhu/danhmuc/edit";
    }

    @PostMapping(value = "/danhmuc/edit")
    public String Edit(DanhMuc danhMuc, Model model,Principal principal) {
        this.danhMucService.create(danhMuc);
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("mgsedit", "Sửa danh mục thành công");
        model.addAttribute("danhmucs", danhMucService.findAll());
        return "/nhu/danhmuc/list";
    }

    @GetMapping(value = "/danhmuc/delete/{maDanhMuc}")
    public String delete(@PathVariable int maDanhMuc, Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        this.danhMucService.delete(maDanhMuc);
        model.addAttribute("mgsedit", "Xóa danh mục thành công");
        return "/nhu/danhmuc/list";
    }

}