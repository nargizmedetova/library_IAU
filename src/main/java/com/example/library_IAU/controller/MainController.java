package com.example.library_IAU.controller;


import com.example.library_IAU.model.Books;
import com.example.library_IAU.model.UsersModel;
import com.example.library_IAU.repository.BooksRepository;
import com.example.library_IAU.service.BooksService;
import com.example.library_IAU.service.UsersService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@Controller
public class MainController {
    ArrayList<Books> addedBooks = new ArrayList<>();
    private BooksService booksService;
    Integer forLoad = 1;
    private ArrayList<Books> books = new ArrayList<Books>();
    private String url = "https://openlibrary.org/search?q=programming&mode=everything";
    private UsersService usersService;
    private BooksRepository booksRepository;
    public  MainController(BooksService booksService, UsersService usersService, BooksRepository booksRepository){
        this.booksService = booksService;
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
        emailOfUser = usersModel.getEmail();
        UsersModel registeredUsers = usersService.registerUser(usersModel.getEmail(), usersModel.getPassword());
        return registeredUsers == null?"signUp":"/signIn";
    }
String emailOfUser;
    @PostMapping("signIn")
    public String postSignIn(@ModelAttribute UsersModel usersModel){
        UsersModel authenticate = usersService.authenticate(usersModel.getEmail(), usersModel.getPassword());
        return authenticate == null?"signIn":"redirect:/";
    }
    @RequestMapping(value="/do-stuff")
    public String doStuffMethod(Model model) throws Exception {
        books.clear();
        forLoad++;
        url = "https://openlibrary.org/search?mode=everything&q=programming&page="+forLoad.toString();
        return main(model);
    }
    @RequestMapping(value="/do-stuff2")
    public String doStuffMethod2(Model model) throws Exception {
        if(forLoad > 2){
            forLoad--;
            url = "https://openlibrary.org/search?mode=everything&q=programming&page="+forLoad.toString();
        }

        else
            url = "https://openlibrary.org/search?q=programming&mode=everything";

        books.clear();
        return main(model);
    }
    @GetMapping("/")
    public String main(Model model) throws Exception {
        books.clear();
        RunPage();
        model.addAttribute("books",books);
        return "main";
    }
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
        addedBooks.add(addingBook);
        /*https://openlibrary.org/search?q=c%2B%2B&mode=everything
        * https://openlibrary.org/search?q=programming&mode=everything */
        return "redirect:/";
    }
    @GetMapping("/myBooks")
    public String myBooks(Model model){
        model.addAttribute("books", addedBooks);
        return "myBooks";
    }

    public void RunPage() throws IOException {
        org.jsoup.nodes.Document page = Jsoup.parse(new URL(url), 15000);
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
            books1.setUserId(usersService.getUsersModelByEmail(emailOfUser).get().getId());
            books.add(books1);
            booksRepository.save(books1);
        }
    }
    public ArrayList<Books> getAddedBooks() {
        return addedBooks;
    }

    public void setAddedBooks(ArrayList<Books> addedBooks) {
        this.addedBooks = addedBooks;
    }

    public Integer getForLoad() {
        return forLoad;
    }

    public void setForLoad(Integer forLoad) {
        this.forLoad = forLoad;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Books> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Books> books) {
        this.books = books;
    }
}
