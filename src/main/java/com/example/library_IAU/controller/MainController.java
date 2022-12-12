package com.example.library_IAU.controller;


import com.example.library_IAU.model.Books;
import com.example.library_IAU.model.UsersModel;
import com.example.library_IAU.repository.BooksRepository;
import com.example.library_IAU.service.BooksService;
import com.example.library_IAU.service.UsersService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@Controller
public class MainController {
    ArrayList<Books> addedBooks = new ArrayList<>();

/*    public MainController(BooksService booksRepository) {
        super();
        this.booksRepository = (BooksRepository) booksRepository;
    }*/
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

        System.out.println(usersModel +"the id is ------------------------------");

        System.out.println("usersModel is:" + usersModel);
        UsersModel authenticate = usersService.authenticate(usersModel.getEmail(), usersModel.getPassword());
        return authenticate == null?"signIn":"main";
    }

    Integer forLoad = 1;

    String url = "https://openlibrary.org/search?q=programming&mode=everything";
    @RequestMapping(value="/do-stuff")
    public String doStuffMethod(Model model) throws IOException {
        forLoad++;
        url = "https://openlibrary.org/search?mode=everything&q=programming&page="+forLoad.toString();
        return main(model);

    }
    @RequestMapping(value="/do-stuff2")
    public String doStuffMethod2(Model model) throws IOException {
        if(forLoad > 2){
            forLoad--;
            url = "https://openlibrary.org/search?mode=everything&q=programming&page="+forLoad.toString();
        }

        else
            url = "https://openlibrary.org/search?q=programming&mode=everything";



        return main(model);

    }
    ArrayList<Books> books = new ArrayList<Books>();
    @GetMapping("/")
    public String main(Model model) throws IOException {


        org.jsoup.nodes.Document page = Jsoup.parse(new URL(url), 15000);
        System.out.println(url);
      //  Element main = page.select("div[id=phpSonuclariGoster]").first();
        Elements rows = page.select("div[class=details]");
        Elements title = rows.select("div[class=resultTitle]");
        Elements text = title.select("a[class=results]");

        for(Element element: text){
            //System.out.println(element.text());
            Books books1 = new Books();
            String str = element.text();
            if(str.length() > 254){
                str = str.substring(0, 253);
            }

            System.out.println(str);
            books1.setName(str);
            books1.setUserId(1L);
            books.add(books1);
            booksRepository.save(books1);
           // books.add(element.text());
        }



        //System.out.println(text.text());
        model.addAttribute("books",books);

        return "main";
    }
    private BooksService booksService;
    @PostMapping("/")
    public String saveEmp(@ModelAttribute("employee") Books employee) {
        System.out.println(employee);
booksService.saveBook(employee);

        return "redirect:/";
    }



    @GetMapping("/forgotPass")
    public String forgotPass(Model model){
        return "forgotPass";
    }

    @GetMapping("/add/{id}")
    public String editEmpForm(@PathVariable Long id, Model model) {
        //model.addAttribute("employee", booksService.getBooksById(id));
        Books addingBook =  booksRepository.getById(id);
        System.out.println(addingBook+"/////////////////////////////////////////////./././///////////////////");
        addedBooks.add(addingBook);

        return "redirect:/";
    }
    @GetMapping("/allBooks")
    public String allBooks(Model model){
        model.addAttribute("books", addedBooks);
        return "allBooks";
    }

       /* public ModelAndView save(@ModelAttribute Books books)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allBooks");
        modelAndView.addObject("books", books);
        booksRepository.save(books);
        System.out.println(books+"--------------++++++++++++++++");
        return modelAndView;
    }*/
   /* @PostMapping("/main")
    public String mainPost(@RequestBody String books){
        System.out.println(books +" --------------------- is the name?");
        return "allBooks";
    }*/
}
