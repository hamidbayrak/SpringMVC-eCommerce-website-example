package com.example.mvcp.controllers;

import com.example.mvcp.entities.Users;
import com.example.mvcp.entities.Wishlist;
import com.example.mvcp.repositories.CartPropsRepository;
import com.example.mvcp.repositories.CategoryRepository;
import com.example.mvcp.repositories.WishlistPropsRepository;
import com.example.mvcp.repositories.WishlistRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class WishlistController {

    int wid = 0;
    final CategoryRepository catRepo;
    final WishlistPropsRepository wishPropsRepo;
    final WishlistRepository wRepo;
    final CartPropsRepository cartPropsRepo;

    public WishlistController(CategoryRepository catRepo, WishlistPropsRepository wishPropsRepo, WishlistRepository wRepo, CartPropsRepository cartPropsRepo) {
        this.wishPropsRepo = wishPropsRepo;
        this.catRepo = catRepo;
        this.wRepo = wRepo;
        this.cartPropsRepo = cartPropsRepo;
    }

    @GetMapping("/wishlist")
    public String wishlist(Model model, HttpServletRequest request) {
        model.addAttribute("catlist", catRepo.findAll());
        Users usr = (Users) request.getSession().getAttribute("user");
        request.setAttribute("user_info", usr);
        model.addAttribute("wl", wishPropsRepo.fillwishlist(usr.getUid()));
        return "wishlist";
    }

    @GetMapping("/wishDelete/{swid}")
    public String wishDelete(@PathVariable String swid) {
        try {
            int wid = Integer.parseInt(swid);
            wRepo.deleteById(wid);
        } catch (EmptyResultDataAccessException e) {
            return "redirect:/wishlist";
        }
        return "redirect:/wishlist";
    }

    @PostMapping("/wishlistAdd")
    public String wishlistAdd(Wishlist wishlist, HttpServletRequest request) {
        Users usr = (Users) request.getSession().getAttribute("user");
        request.setAttribute("user_info", usr);
        if (usr != null){
            wishlist.setUid(usr.getUid());
            if (wid != 0) {
                wishlist.setWid(wid);
            }
            wRepo.saveAndFlush(wishlist);
            wid = 0;
        } else {
            return "redirect:/";
        }
        return "redirect:/wishlist";
    }
}
