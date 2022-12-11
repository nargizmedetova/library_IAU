package com.example.library_IAU.controller;


import com.example.library_IAU.model.Books;
import com.example.library_IAU.model.UsersModel;
import com.example.library_IAU.repository.BooksRepository;
import com.example.library_IAU.service.UsersService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@Controller
public class MainController {
    private final UsersService usersService;

    private final BooksRepository booksRepository;

    public MainController(UsersService usersService, BooksRepository booksRepository) {
        this.usersService = usersService;
        this.booksRepository = booksRepository;
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
       // Books books = new Books();
       // books.setId(1L);
        //books.setName("new book");

        System.out.println(usersModel.getId()+"the id is ------------------------------");

        System.out.println("usersModel is:" + usersModel);
        UsersModel authenticate = usersService.authenticate(usersModel.getEmail(), usersModel.getPassword());
        return authenticate == null?"signIn":"main";
    }
    

    @GetMapping("/main")
    public String main(Model model) throws IOException {
        ArrayList<Books> books = new ArrayList<Books>();
        String url= "https://openlibrary.org/search?q=programming&mode=everything";
        org.jsoup.nodes.Document page = Jsoup.parse(new URL(url), 10000);
        System.out.println(url);
      //  Element main = page.select("div[id=phpSonuclariGoster]").first();
        Elements rows = page.select("div[class=details]");
        Elements text = rows.select("a[class=results]");

        for(Element element: text){
            //System.out.println(element.text());
            Books books1 = new Books();
            String str = element.text();
            if(str.length() > 254){
                str = str.substring(0, 253);
            }

            System.out.println(str);
            books1.setName(str);
            booksRepository.save(books1);
           // books.add(element.text());
        }



        //System.out.println(text.text());

        return "main";
    }

    @GetMapping("/forgotPass")
    public String forgotPass(Model model){
        return "forgotPass";
    }
}
