package com.example.library_IAU.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/signIn")
    public String signIn(Model model){
        return "signIn";
    }
}
