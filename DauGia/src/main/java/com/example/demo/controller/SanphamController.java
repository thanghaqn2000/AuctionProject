package com.example.demo.controller;

import com.example.demo.model.NguoiDung;
import com.example.demo.model.SanPham;
import com.example.demo.model.TaiKhoan;
import com.example.demo.repository.nguoi_dung.NguoiDungRepo;
import com.example.demo.service.danh_muc.DanhMucService;
import com.example.demo.service.nguoi_dung.NguoiDungService;
import com.example.demo.service.san_pham.SanPhamService;
import com.example.demo.service.tai_khoan.TaiKhoanQuyenService;
import com.example.demo.service.tai_khoan.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
public class SanphamController {
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
    NguoiDungRepo nguoiDungRepo;


    @GetMapping(value = "/sanpham/list")
    public String NguoiDung(SanPham sanPham, Model model, Principal principal) {

        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }

        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("listSP", sanPhamService.findCuaBan(userName));
        return "/nhu/sanpham/list";
    }

    @GetMapping(value = "/sanpham/listduyet")
    public String Nguoidungchuduyet(SanPham sanPham, Model model, Principal principal) {

        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("listSP", sanPhamService.Findchuaduyetcuaban(userName));
        return "/nhu/sanpham/listchuaduyet";
    }

    @GetMapping(value = "/sanpham/create")
    public String viewCreate(Model model, Principal principal) {
        LocalDate localDate = LocalDate.now();
        SanPham sanPham = new SanPham();
        sanPham.setNgayDangKi(localDate.toString());
        model.addAttribute("sanphams", sanPham);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("usernams", principal.getName());
        model.addAttribute("taiKhoans", taiKhoanService.findAll());
        model.addAttribute("danhmucs", danhMucService.findAll());
        return "/nhu/sanpham/create_nguoidung";
    }

    @PostMapping(value = "/sanpham/create")
    public String Create(@Valid @ModelAttribute("sanphams") SanPham sanPham, BindingResult bindingResult, Model model, Principal principal) {
        new SanPham().validate(sanPham, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return "/nhu/sanpham/create_nguoidung";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        System.out.println(java.time.LocalDateTime.now());
        String dateFormat = formatter.format(date);
        System.out.println("san pham thêm" + sanPham);
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        sanPham.setNgayDangKi(dateFormat);
        // vẫn cần id của cái user Name này hazzz :

        this.sanPhamService.create(sanPham);
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("listSP", sanPhamService.findCuaBan(userName));
        model.addAttribute("mgs", "thêm mới sản phẩm thành công");
        return "/nhu/sanpham/list";
    }

    @GetMapping(value = "/sanpham/edit")
    public String ViewEdit(@RequestParam("id") Integer id, Model model, Principal principal) {
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);

        model.addAttribute("sanphams", sanPhamService.findById(id));
        model.addAttribute("danhmucs", danhMucService.findAll());
        return "/nhu/sanpham/edit";
    }

    @PostMapping(value = "/sanpham/edit")
    public String Edit(SanPham sanPham, Model model, Principal principal) {
        this.sanPhamService.create(sanPham);
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("listSP", sanPhamService.findCuaBan(userName));
        System.out.println("người đăng là ia -------------------- +" + sanPham.getTaiKhoan());
        System.out.println("ngày đăng lý-------------------- +" + sanPham.getNgayDangKi());
        System.out.println("tình trạng là ia -------------------- +" + sanPham.isTinhTrang());
        System.out.println("người đăng là ia -------------------- +" + sanPham.getHinh());
        System.out.println("người đăng là ia -------------------- +" + sanPham.getNgayBatDau());
        System.out.println("người đăng là ia -------------------- +" + sanPham.getTenSanPham());

        model.addAttribute("mgsedit", "Sửa sản phẩm thành công");
        return "/nhu/sanpham/list";
    }

    @GetMapping(value = "/sanpham/delete/{maSanPham}")
    public String deleteSanpham(@PathVariable Integer maSanPham, Model model, Principal principal) {
        this.sanPhamService.delete(maSanPham);
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("listSP", sanPhamService.findCuaBan(userName));
        model.addAttribute("mgsdelete", "Xóa sản phẩm thành công!");
        return "/nhu/sanpham/list";
    }


    @GetMapping("/timKiemchoduyet")
    public String searchchoduyet(@RequestParam("tenSp") String tenSp,
                                 Model model, Principal principal) {
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        List<SanPham> sanPhams = sanPhamService.Findchuaduyetcuaban1(false, userName, tenSp);
        if (sanPhams.size() == 0) {
            System.out.println("Đang rỗng nè coi đo dai bao nhieu nao  ====================" + sanPhams.size());
            model.addAttribute("nguoiDung", nguoiDung);
            model.addAttribute("listSP", sanPhams);
            model.addAttribute("mgstk", "Không tìm thấy sản phẩm");
            return "/nhu/sanpham/listchuaduyet";
        } else {
            System.out.println("Đang rỗng nè coi đo dai bao nhieu nao  ====================" + sanPhams.size());

            model.addAttribute("nguoiDung", nguoiDung);
            model.addAttribute("listSP", sanPhams);
            model.addAttribute("mgstk1", "sản phẩm được tìm thấy");
            return "/nhu/sanpham/listchuaduyet";
        }
    }

    @GetMapping("/timKiemcuatoi")
    public String search(@RequestParam("tenSp") String tenSp,
                         Model model, Principal principal) {
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        List<SanPham> sanPhams = sanPhamService.findByNameCuaCoi(userName, tenSp);
        if (sanPhams.size() == 0) {
            System.out.println("Đang rỗng nè coi đo dai bao nhieu nao  ====================" + sanPhams.size());
            model.addAttribute("nguoiDung", nguoiDung);
            model.addAttribute("listSP", sanPhams);
            model.addAttribute("mgstk", "Không tìm thấy sản phẩm");
            return "/nhu/sanpham/list";
        } else {
            System.out.println("Đang rỗng nè coi đo dai bao nhieu nao  ====================" + sanPhams.size());
            model.addAttribute("nguoiDung", nguoiDung);
            model.addAttribute("listSP", sanPhams);
            model.addAttribute("mgstk1", "sản phẩm được tìm thấy");
            return "/nhu/sanpham/list";
        }
    }
}
