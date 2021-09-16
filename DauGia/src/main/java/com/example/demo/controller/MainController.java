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
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping(value = "/signUp")
    public ModelAndView viewsingup1() {
        ModelAndView modelAndView = new ModelAndView("/phuoc/signUp", "dangkys", new NguoiDungTaiKhoan());

        return modelAndView;
    }


    @PostMapping("/singup")
    public String singUp(@ModelAttribute NguoiDungTaiKhoan nguoiDungTaiKhoan,BindingResult bindingResult)
    {

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTenNguoiDung(nguoiDungTaiKhoan.getTenNguoiDung1());
        nguoiDung.setTaiKhoan(new TaiKhoan(nguoiDungTaiKhoan.getTaiKhoan1(), bCryptPasswordEncoder.encode(nguoiDungTaiKhoan.getMatKhau1())));
        nguoiDung.setEmail(nguoiDungTaiKhoan.getEmail1());
        nguoiDung.setNgaySinh(nguoiDungTaiKhoan.getNgaySinh1());
        nguoiDung.setSoDienThoai(nguoiDungTaiKhoan.getSoDienThoai1());
        nguoiDung.setCmnd(nguoiDungTaiKhoan.getCmnd1());
        nguoiDung.setDiaChi(nguoiDungTaiKhoan.getDiaChi1());
        nguoiDung.setGioiTinh(nguoiDungTaiKhoan.isGioiTinh1());
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
