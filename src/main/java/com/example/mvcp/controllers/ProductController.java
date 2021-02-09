package com.example.mvcp.controllers;

import com.example.mvcp.repositories.CartPropsRepository;
import com.example.mvcp.repositories.CategoryRepository;
import com.example.mvcp.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    final CategoryRepository catRepo;
    final ProductRepository proRepo;
    final CartPropsRepository cartPropsRepo;

    public ProductController(CategoryRepository catRepo, ProductRepository proRepo, CartPropsRepository cartPropsRepo) {
        this.catRepo = catRepo;
        this.proRepo = proRepo;
        this.cartPropsRepo = cartPropsRepo;
    }

    @GetMapping("/product{tcid}")
    public String product(Model model, @RequestParam(value = "cid", required = false) String tcid) {
        model.addAttribute("catlist", catRepo.findAll());
        if (tcid == null) {
            model.addAttribute("prolist", proRepo.findAll());
        } else {
            int cid = Integer.parseInt(tcid);
            model.addAttribute("prolist",proRepo.findByCid(cid));
        }
        return "product";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam(value = "search", required = false) String search ){
        model.addAttribute("catlist", catRepo.findAll());
        if (search != null){
            model.addAttribute("prolist", proRepo.findProductByTitle("%" + search + "%"));
        }
        return "product";
    }


}
