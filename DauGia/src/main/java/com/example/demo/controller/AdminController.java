package com.example.demo.controller;

import com.example.demo.model.ChiTietDonHang;
import com.example.demo.model.NguoiDung;
import com.example.demo.model.SanPham;
import com.example.demo.repository.nguoi_dung.NguoiDungRepo;
import com.example.demo.service.danh_muc.DanhMucService;
import com.example.demo.service.don_hang.DonHangService;
import com.example.demo.service.nguoi_dung.NguoiDungService;
import com.example.demo.service.san_pham.SanPhamService;
import com.example.demo.service.tai_khoan.TaiKhoanQuyenService;
import com.example.demo.service.tai_khoan.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public DanhMucService danhMucService;
    @Autowired
    public SanPhamService sanPhamService;
    @Autowired
    public TaiKhoanService taiKhoanService;
    @Autowired
    public NguoiDungService nguoiDungService;
    @Autowired
    public TaiKhoanQuyenService taiKhoanQuyenService;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    NguoiDungRepo nguoiDungRepo;
//    @Autowired
//    public MyUserDetails myUserDetails = new  TaiKhoanQuyenService();

    @GetMapping(value = "")
    public String AdminTrangchu(Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        return "nhu/admin/trangchu";
    }


    @GetMapping(value = "/list")
    public String AdminList(@RequestParam(value = "page", defaultValue = "0") int page, Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        Sort sort = Sort.by("tenSanPham").descending();
        model.addAttribute("sanphams1", sanPhamService.findAllPhanTrang(PageRequest.of(page, 10, sort)));
        return "nhu/admin/list";
    }

    @GetMapping(value = "/view")
    public String AdminView(@RequestParam("id") int id, Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("sanphams", sanPhamService.findById(id));
        model.addAttribute("danhmucs", danhMucService.findAll());
//        model.addAttribute("appUser", userRoleService.fi )
        return "/nhu/admin/view";
    }

    @GetMapping(value = "/delete/{maSanPham}")
    public String delete(@PathVariable int maSanPham, Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        this.sanPhamService.delete(maSanPham);
        return "redirect:/admin/list";
    }

    @GetMapping(value = "/delete1/{maSanPham}")
    public String deletechuaduyejt(@PathVariable int maSanPham, Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        this.sanPhamService.delete(maSanPham);
        System.out.println("ma sa dang xoa ============AAAAAAAAAAAAa" + maSanPham);
        return "redirect:/admin/duyet";
    }

    @GetMapping(value = "/duyet")
    public String AdminDuyet(Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("sanphams1", sanPhamService.findByTinhTrang());
        return "nhu/admin/duyet";
    }

    @PostMapping(value = "/duyetok")
    public String AdminCreate(SanPham sanPham, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        sanPham.setTinhTrang(true);
        this.sanPhamService.create(sanPham);
        redirectAttributes.addFlashAttribute("mgs", "Phê duyệt sản phẩm thành công!");
        return "redirect:/admin/duyet";
    }


    @GetMapping(value = "/edit")
    public String AdminViewEdit(@RequestParam("id") Integer id, Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("sanphams", sanPhamService.findById(id));
        model.addAttribute("danhmucs", danhMucService.findAll());
        return "nhu/admin/edit";
    }

    @PostMapping(value = "/edit")
    public String AdminEdit(SanPham sanPham, Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        this.sanPhamService.create(sanPham);
        return "redirect:/admin/list";
    }
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
        return "redirect:/admin/admin-quanlygiaodich";
    }

}
