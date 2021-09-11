package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.config.MyConstants;
import com.example.demo.config.PaypalPaymentIntent;
import com.example.demo.config.PaypalPaymentMethod;
import com.example.demo.service.paypal.PaypalService;
import com.example.demo.util.PaypalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaymentController {
    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;
    @Autowired
    public JavaMailSender emailSender;
    @GetMapping("/paypal")
    public String index(){
        return "paypal/index";
    }
    public static double totalMoney=0;
    @PostMapping("/pay")
    public String pay(HttpServletRequest request,@RequestParam("price") double price ){
        totalMoney=price;
        String cancelUrl = PaypalUtils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = PaypalUtils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }
    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(){
        return "paypal/cancel";
    }
    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("thangit189@gmail.com");
                message.setTo(MyConstants.FRIEND_EMAIL);
                message.setSubject("THÔNG BÁO ĐÃ THANH TOÁN HÓA ĐƠN!");
                message.setText("Số tiền đã thanh toán: "+totalMoney);
                this.emailSender.send(message);
                return "paypal/success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/paypal";
    }
}