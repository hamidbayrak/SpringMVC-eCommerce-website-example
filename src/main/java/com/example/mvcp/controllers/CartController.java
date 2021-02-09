package com.example.mvcp.controllers;

import com.example.mvcp.entities.Cart;
import com.example.mvcp.entities.CartProps;
import com.example.mvcp.entities.Users;
import com.example.mvcp.repositories.CartPropsRepository;
import com.example.mvcp.repositories.CartRepositoy;
import com.example.mvcp.repositories.CategoryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CartController {

    int cartid = 0;
    final CategoryRepository catRepo;
    final CartRepositoy cartRepo;
    final CartPropsRepository cartPropsRepo;

    public CartController(CategoryRepository catRepo, CartRepositoy cartRepo, CartPropsRepository cartPropsRepo) {
        this.catRepo = catRepo;
        this.cartRepo = cartRepo;
        this.cartPropsRepo = cartPropsRepo;
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request) {
        model.addAttribute("catlist", catRepo.findAll());
        Users usr = (Users) request.getSession().getAttribute("user");
        request.setAttribute("user_info", usr);
        if (usr != null){
            List<CartProps> cartPropsList = cartPropsRepo.fillCart(usr.getUid());
            int total = 0;
            for (CartProps item : cartPropsList) {
                total = total + (Integer.parseInt(item.getPrice()) * item.getQuantity());
            }
            model.addAttribute("cartlist", cartPropsList);
            model.addAttribute("total", total);
        } else {
            return "redirect:/";
        }
        return "cart";
    }

    @GetMapping("/cartDelete/{scartid}")
    public String cartDelete(@PathVariable String scartid) {
        try {
            int cartid = Integer.parseInt(scartid);
            cartRepo.deleteById(cartid);
        } catch (EmptyResultDataAccessException e) {
            return "redirect:/cart";
        }
        return "redirect:/cart";
    }

    @PostMapping("/cartAdd")
    public String cartAdd(Cart cart, HttpServletRequest request) {
        Users usr = (Users) request.getSession().getAttribute("user");
        request.setAttribute("user_info", usr);
        if (usr != null){
            cart.setUid(usr.getUid());
            if (cartid != 0) {
                cart.setCartid(cartid);
            }
            cartRepo.saveAndFlush(cart);
            cartid = 0;
        } else {
            return "redirect:/";
        }
        return "redirect:/cart";
    }
}
