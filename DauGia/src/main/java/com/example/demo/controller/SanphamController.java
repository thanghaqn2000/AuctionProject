package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.nguoi_dung.NguoiDungRepo;
import com.example.demo.service.danh_muc.DanhMucService;
import com.example.demo.service.nguoi_dung.NguoiDungService;
import com.example.demo.service.san_pham.SanPhamService;
import com.example.demo.service.tai_khoan.TaiKhoanQuyenService;
import com.example.demo.service.tai_khoan.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
    @Value("${upload.path}")
    private String fileUpload;

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
        model.addAttribute("listSP", sanPhamService.Findchuaduyetcuaban("Chưa duyệt", userName));
        return "/nhu/sanpham/listchuaduyet";
    }

    @GetMapping(value = "/sanpham/listkoduyet")
    public String Nguoidungkoduyet(SanPham sanPham, Model model, Principal principal) {

        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("listSP", sanPhamService.Findchuaduyetcuaban("Không duyệt", userName));
        return "/nhu/sanpham/listkhongduyet";
    }

    @GetMapping(value = "/sanpham/create")
    public String viewCreate(Model model, Principal principal) {
        LocalDate localDate = LocalDate.now();
//        SanPham sanPham = new SanPham();
        ProductForm sanPham = new ProductForm();
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
        model.addAttribute("chuaduyet", "Chưa duyệt");
        return "/nhu/sanpham/create_nguoidung";
    }


    @PostMapping(value = "/sanpham/create")
    public String Create(@Valid @ModelAttribute("sanphams") ProductForm sanPham1, BindingResult bindingResult, Model model, Principal principal) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        System.out.println(java.time.LocalDateTime.now());
        String dateFormat = formatter.format(date);
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        SanPham sanPham = new SanPham.sanphamBuilder(sanPham1.getTenSanPham()).giasanphamtion(sanPham1.getGiaKhoiDiem()).tinhtrangsanphamtion(sanPham1.getTinhTrang()).motasanphamtion(sanPham1.getMoTa()).ngaybdsanphamtion(sanPham1.getNgayBatDau()).ngayktsanphamtion(sanPham1.getNgayKetThuc()).soluongsanphamtion(sanPham1.getSoLuong()).mucgiasanphamtion(sanPham1.getMucGia()).taikhaonsanphamtion(sanPham1.getTaiKhoans()).goibdsanphamtion(sanPham1.getGioBatDau()).goiktsanphamtion(sanPham1.getGioKetThuc()).build();


        MultipartFile multipartFile = sanPham1.gethinh();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(sanPham1.gethinh().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sanPham.setHinh(fileName);
        sanPham.setDanhMuc(sanPham1.getDanhMuc());
        sanPham.setTinhTrang("Chưa duyệt");
        sanPham.setNgayDangKi(dateFormat);
        sanPham.setTaiKhoans(new TaiKhoan(userName));

        new SanPham().validate(sanPham, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
            model.addAttribute("nguoiDung", nguoiDung);
            model.addAttribute("usernams", principal.getName());
            model.addAttribute("taiKhoans", taiKhoanService.findAll());
            model.addAttribute("danhmucs", danhMucService.findAll());
            model.addAttribute("chuaduyet", "Chưa duyệt");
            return "/nhu/sanpham/create_nguoidung";
        }

        this.sanPhamService.create(sanPham);
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("listSP", sanPhamService.findCuaBan(userName));
        model.addAttribute("mgs", "thêm mới sản phẩm thành công");
        return "/nhu/sanpham/list";
    }


    @GetMapping(value = "/sanpham/edit")
    public String ViewEdit(@RequestParam("id") Integer id, Model model, Principal principal) {
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


    @GetMapping(value = "/sanpham/xem")
    public String Viewxem(@RequestParam("id") Integer id, Model model, Principal principal) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("sanphams", sanPhamService.findById(id));
        model.addAttribute("danhmucs", danhMucService.findAll());
        return "/nhu/sanpham/view";
    }

    @PostMapping(value = "/sanpham/edit")
    public String Edit(@ModelAttribute("sanphams") SanPham sanPham, Model model, Principal principal) {
        String userName = principal.getName();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", "là admin");
        }
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("listSP", sanPhamService.findCuaBan(userName));
        model.addAttribute("danhmucs", danhMucService.findAll());
        System.out.println("ten+ " + sanPham.getTaiKhoan().getTaiKhoan());
        this.sanPhamService.create(sanPham);
        model.addAttribute("mgsedit", "Sửa sản phẩm thành công");
        System.out.println("ten -----------" + sanPham.getNgayDangKi());
        System.out.println("ten -----------" + sanPham.getTinhTrang());
        System.out.println("ten -----------" + sanPham.getTenSanPham());
        System.out.println("ten -----------" + sanPham.getTaiKhoan());

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
        List<SanPham> sanPhams = sanPhamService.Findchuaduyetcuaban1("Chưa duyệt", userName, tenSp);
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
