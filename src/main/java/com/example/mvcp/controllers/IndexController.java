package com.example.mvcp.controllers;

import com.example.mvcp.repositories.CartPropsRepository;
import com.example.mvcp.repositories.CategoryRepository;
import com.example.mvcp.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    final ProductRepository proRepo;
    final CategoryRepository catRepo;
    final CartPropsRepository cartPropsRepo;

    public IndexController(ProductRepository proRepo, CategoryRepository catRepo, CartPropsRepository cartPropsRepo) {
        this.proRepo = proRepo;
        this.catRepo = catRepo;
        this.cartPropsRepo = cartPropsRepo;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("catlist", catRepo.findAll());
        model.addAttribute("menlist", proRepo.findByCid(1));
        model.addAttribute("womenlist", proRepo.findByCid(2));
        model.addAttribute("kidslist", proRepo.findByCid(3));
        model.addAttribute("allproduct", proRepo.findAll());
        return "index";
    }






}
