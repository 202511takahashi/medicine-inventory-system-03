package com.example.medicineinventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * トップ画面を表示するためのコントローラーです。
 * まずは "/" にアクセスしたときに index.jsp を返します。
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome() {
        return "index";
    }
}
