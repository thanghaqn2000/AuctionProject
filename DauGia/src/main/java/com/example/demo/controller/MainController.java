package com.example.demo.controller;

import com.example.demo.model.NguoiDung;
import com.example.demo.model.NguoiDungTaiKhoan;
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

    @GetMapping(value = "/singup")
    public String viewsingup1(Model model) {
        model.addAttribute("dangkys", new NguoiDungTaiKhoan());
        return "/phuoc/signUp";
    }


    @PostMapping("/singup")
    public String singUp(NguoiDungTaiKhoan nguoiDungTaiKhoan)
    {
//        if (bindingResult.hasFieldErrors()) {
//            return "/phuoc/signUp";
//        }
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTenNguoiDung(nguoiDungTaiKhoan.getTenNguoiDung());
        nguoiDung.setTaiKhoan(new TaiKhoan(nguoiDungTaiKhoan.getTaiKhoan(), bCryptPasswordEncoder.encode(nguoiDungTaiKhoan.getMatKhau())));
        nguoiDung.setEmail(nguoiDungTaiKhoan.getEmail());
        nguoiDung.setNgaySinh(nguoiDungTaiKhoan.getNgaySinh());
        nguoiDung.setSoDienThoai(nguoiDungTaiKhoan.getSoDienThoai());
        nguoiDung.setCmnd(nguoiDungTaiKhoan.getCmnd());
        nguoiDung.setDiaChi(nguoiDungTaiKhoan.getDiaChi());
        nguoiDung.setGioiTinh(nguoiDungTaiKhoan.isGioiTinh());
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
