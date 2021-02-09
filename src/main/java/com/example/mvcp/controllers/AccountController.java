package com.example.mvcp.controllers;

import com.example.mvcp.entities.Users;
import com.example.mvcp.repositories.CartPropsRepository;
import com.example.mvcp.repositories.CategoryRepository;
import com.example.mvcp.repositories.UsersRepository;
import com.example.mvcp.util.LoginControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AccountController {

    String error_msg = "";
    final LoginControl loginControl;
    final UsersRepository userRepo;
    int uid = 0;
    final CategoryRepository catRepo;
    final CartPropsRepository cartPropsRepo;

    public AccountController(CategoryRepository catRepo, UsersRepository userRepo, CartPropsRepository cartPropsRepo, LoginControl loginControl) {
        this.userRepo = userRepo;
        this.catRepo = catRepo;
        this.loginControl = loginControl;
        this.cartPropsRepo = cartPropsRepo;
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("catlist", catRepo.findAll());
        if (!error_msg.equals("")) {
            model.addAttribute("error_msg", error_msg);
        }
        error_msg = "";
        return "account";
    }

    @PostMapping("/userlogin")
    public String userlogin(Users user) {
        if (loginControl.userControl(user) != null) {
            return "redirect:/account";
        }
        error_msg = "Mail or Password Wrong!";
        return "redirect:/account";
    }

    @PostMapping("/userRegister")
    public String userRegister(Users user) {
        if (uid != 0) {
            user.setUid(uid);
        }
        userRepo.saveAndFlush(user);
        uid = 0;
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String userLogin(){
        loginControl.logout();
        return "redirect:/";
    }
}
