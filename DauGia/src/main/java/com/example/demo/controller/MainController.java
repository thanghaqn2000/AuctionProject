package com.example.demo.controller;

import com.example.demo.model.NguoiDung;
import com.example.demo.model.TaiKhoan;
import com.example.demo.repository.nguoi_dung.NguoiDungRepo;
import com.example.demo.service.nguoi_dung.NguoiDungService;
import com.example.demo.service.tai_khoan.TaiKhoanService;
import com.example.demo.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public TaiKhoanService taiKhoanService;

    @Autowired
    public NguoiDungService nguoiDungService;

    @Autowired
    NguoiDungRepo nguoiDungRepo;


    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        // 1 cái util( dùng chung) dùng để hiển thị principal
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "/dangnhap/userInfoPage";
    }

    @GetMapping("/singup")
    public String viewsingup() {
        return "/phuoc/signUp";
    }

    @PostMapping("/singup")
    public String singUp(@Validated @RequestParam("hovaten") String hovaten,
                         @RequestParam("tendangnhap") String tendangnhap,
                         @RequestParam("email") String email,
                         @RequestParam("ngaysinh") String ngaysinh,
                         @RequestParam("sodt") String sodt,
                         @RequestParam("socmnd") String socmnd,
                         @RequestParam("diachi") String diachi,
                         @RequestParam("matkhau") String matkhau,
                         @RequestParam("gioitinh") boolean gioitinh,
                         BindingResult bindingResult) {
//        if (bindingResult.hasFieldErrors()) {
//            return "/phuoc/signUp";
//        }
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTenNguoiDung(hovaten);
        nguoiDung.setTaiKhoan(new TaiKhoan(tendangnhap, bCryptPasswordEncoder.encode(matkhau)));
        nguoiDung.setEmail(email);
        nguoiDung.setNgaySinh(ngaysinh);
        nguoiDung.setSoDienThoai(sodt);
        nguoiDung.setCmnd(socmnd);
        nguoiDung.setDiaChi(diachi);
        nguoiDung.setGioiTinh(gioitinh);
        nguoiDungService.save(nguoiDung);
        System.out.println("nguoi dun  ==========" + nguoiDung);
        return "redirect:/login";
    }

    @GetMapping("/403")
    private String accessDenied(Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungRepo.findByTaiKhoan_TaiKhoan(principal.getName());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("mgs", "Bạn không có quyền truy cập !");
        return "/phuoc/403page";
    }
}
