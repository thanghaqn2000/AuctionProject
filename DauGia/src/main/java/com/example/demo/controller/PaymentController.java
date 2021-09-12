package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.config.MyConstants;
import com.example.demo.config.PaypalPaymentIntent;
import com.example.demo.config.PaypalPaymentMethod;
import com.example.demo.model.*;
import com.example.demo.service.don_hang.DonHangService;
import com.example.demo.service.nguoi_dung.NguoiDungService;
import com.example.demo.service.paypal.PaypalService;
import com.example.demo.util.PaypalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {
    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    NguoiDungService nguoiDungService;
    @Autowired
    DonHangService donHangService;
    public static double totalMoney = 0;
    public static String emailCus = "";

    @GetMapping("/hoaDon/layDuLieu")
    public String getHoaDon(@RequestParam String tongTien, Model model) {
        totalMoney = Double.parseDouble(tongTien);
        totalMoney = totalMoney * 1000;
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("donHang", new DonHang());
        return "thang/thongTinThanhToan";
    }

    @GetMapping("/hoaDon/thanhToan")
    public String thanhToan(@SessionAttribute("carts") HashMap<Integer, Cart> cartMap, @ModelAttribute DonHang donHang) {
        Time time = new Time(System.currentTimeMillis());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        NguoiDung nguoiDung = nguoiDungService.findByTaiKhoan(auth.getName());
        LocalDate currentDate = java.time.LocalDate.now();
        donHang.setNgayMua(Date.valueOf(currentDate));
        donHang.setTrangThai("Đang giao");
        donHang.setNguoiDung(nguoiDung);
        donHang.setTongTien(totalMoney);
        donHangService.create(donHang);
        ChiTietDonHangKey chiTietDonHangKey = new ChiTietDonHangKey();
        ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
        for (Map.Entry<Integer, Cart> entry : cartMap.entrySet()) {
            Cart value = entry.getValue();
            chiTietDonHangKey.setMaDonHang(donHang.getMaDonHang());
            chiTietDonHangKey.setMaDonHang(value.getSanPham().getMaSanPham());
            chiTietDonHang.setDonHang(donHang);
            chiTietDonHang.setId(chiTietDonHangKey);
            chiTietDonHang.setSanPham(value.getSanPham());
            chiTietDonHang.setSoLuong(1);
            chiTietDonHang.setThanhTien(value.getGiaCaoNhat());
            donHangService.createChiTiet(chiTietDonHang);
        }
        return "thang/hoaDon";
    }

    @GetMapping("/paypal")
    public String index() {
        return "paypal/index";
    }

    @GetMapping("/pay")
    public String pay(HttpServletRequest request) {
        String cancelUrl = PaypalUtils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = PaypalUtils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                    totalMoney,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay() {
        return "paypal/cancel";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("thangit189@gmail.com");
                message.setTo(MyConstants.FRIEND_EMAIL);
                message.setSubject("THÔNG BÁO ĐÃ THANH TOÁN HÓA ĐƠN!");
                message.setText("Số tiền đã thanh toán: " + totalMoney);
                this.emailSender.send(message);
                return "thang/success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/paypal";
    }
}