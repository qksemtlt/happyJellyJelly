package com.ex.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ex.service.KakaoPayService;

@Controller
@RequiredArgsConstructor
@Log
@RequestMapping("/kakao/*")
public class KakaoPayController {
	
    private final KakaoPayService kakaoPay;

    @GetMapping("/kakaoPay")
    public String kakaoPayGet() {
    	return "kakaoPay/kakaoPay";
    }

    @PostMapping("/kakaoPay")
    public String kakaoPay(){
        return "redirect:" + kakaoPay.kakaoPayReady();
    }

    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token")String pgToken, Model model) {
        kakaoPay.payApprove(pgToken);
        return "redirect:/kakao/completed";
    }
    
    @GetMapping("/completed")
    @ResponseBody
    public String complate() {
    	return "success";
    }
    
    
    @GetMapping("/cancel")
    public String  cancel() {
    	return "kakaoPay/kakaoPayCancel";
    }
   
}








