package com.example.mvcp.controllers;

import com.example.mvcp.repositories.CartPropsRepository;
import com.example.mvcp.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    final CategoryRepository catRepo;
    final CartPropsRepository cartPropsRepo;

    public ContactController(CategoryRepository catRepo, CartPropsRepository cartPropsRepo) {
        this.catRepo = catRepo;
        this.cartPropsRepo = cartPropsRepo;
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("catlist", catRepo.findAll());
        return "contact";
    }

}
