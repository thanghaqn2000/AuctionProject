package com.example.demo.controller;

import com.example.demo.model.ChiTietDonHang;
import com.example.demo.service.don_hang.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/nha")
public class DonHangController {
    @Autowired
    private DonHangService donHangService;

    @GetMapping("/admin-quanlygiaodich")
    public ModelAndView listAll(@RequestParam(defaultValue = "0") int page) {
        ModelAndView model = new ModelAndView("nga/QuanLyGiaoDich");
        Page<ChiTietDonHang> nguoiDungs;
        Pageable pageable = PageRequest.of(page, 5);
        nguoiDungs = donHangService.findAll(pageable);
        model.addObject("nguoidungs", nguoiDungs);
        return model;
    }

    @PostMapping("/pagaList")
    public ModelAndView getList(@RequestParam(defaultValue = "0") int page, @RequestParam String tenNguoiDung,
                                @RequestParam String tenSanPham) {
        ModelAndView modelAndView = new ModelAndView("/nga/QuanLyGiaoDich");
        Page<ChiTietDonHang> nguoiDungs;
        Pageable pageableSort = PageRequest.of(page, 5);
        if (tenNguoiDung.equals("")) {
            if (!tenSanPham.equals("")) {
                nguoiDungs = donHangService.findBySanPham_TenSanPhamContains(tenSanPham, pageableSort);
            } else {
                nguoiDungs = donHangService.findAll(pageableSort);
            }
        } else {
            if (!tenSanPham.equals("")) {
                nguoiDungs = donHangService.findByDonHang_NguoiDung_TenNguoiDungAndSanPham_TenSanPhamContains(tenNguoiDung, tenSanPham, pageableSort);
            } else {
                nguoiDungs = donHangService.findByDonHang_NguoiDung_TenNguoiDungContains(tenNguoiDung, pageableSort);
            }
        }
        modelAndView.addObject("tenNguoiDung", tenNguoiDung);
        modelAndView.addObject("nguoidungs", nguoiDungs);
        modelAndView.addObject("tenSanPham", tenSanPham);
        return modelAndView;
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        donHangService.remove(id);
        return "redirect:/nha/admin-quanlygiaodich";
    }

}
