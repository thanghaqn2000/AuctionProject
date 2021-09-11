package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.DonHang;
import com.example.demo.model.NguoiDung;
import com.example.demo.service.nguoi_dung.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/hoaDon")
public class HoaDonController {
    private static double totalMoney = 0;
    @Autowired
    NguoiDungService nguoiDungService;
    @GetMapping("/layDuLieu")
    public String getHoaDon(@RequestParam String tongTien, Model model ) {
        model.addAttribute("tongTien", tongTien);
        totalMoney=Double.parseDouble(tongTien);
        model.addAttribute("donHang", new DonHang());
        return "nha/ThongTinThanhToan";
    }

    @GetMapping("/thanhToan")
    public String thanhToan(@SessionAttribute("carts") HashMap<Integer, Cart> cartMap) {
        System.out.println("Total money: "+totalMoney);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        NguoiDung nguoiDung=nguoiDungService.findByTaiKhoan(auth.getName());
        System.out.println("id user: "+nguoiDung.getMaNguoiDung());
        for(Map.Entry<Integer, Cart> entry : cartMap.entrySet()) {
            Cart value = entry.getValue();
            System.out.println( "id sp "+value.getSanPham().getMaSanPham());

        }

        return "nha/HoaDon";
    }

}
