package com.example.library_IAU.controller;


import com.example.library_IAU.model.UsersModel;
import com.example.library_IAU.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    private final UsersService usersService;

    public MainController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/signIn")
    public String signIn(Model model){

        model.addAttribute("signInRequest", new UsersModel());
        return "signIn";
    }
    @GetMapping("/signUp")
    public String signUp(Model model){
        model.addAttribute("signUpRequest", new UsersModel());

        return "signUp";
    }

    @PostMapping("signUp")
    public String postSignUp(@ModelAttribute UsersModel usersModel){
        System.out.println("usersModel is:" + usersModel);
        UsersModel registeredUsers = usersService.registerUser(usersModel.getEmail(), usersModel.getPassword());
        return registeredUsers == null?"signUp":"signIn";
    }
    @PostMapping("signIn")
    public String postSignIn(@ModelAttribute UsersModel usersModel){
        System.out.println("usersModel is:" + usersModel);
        UsersModel authenticate = usersService.authenticate(usersModel.getEmail(), usersModel.getPassword());
        return authenticate == null?"signIn":"main";
    }
    @GetMapping("/main")
    public String main(Model model){
        return "main";
    }

    @GetMapping("/forgotPass")
    public String forgotPass(Model model){
        return "forgotPass";
    }
}
