package com.example.firstjavaspring.controllers;

import com.example.firstjavaspring.entities.*;
import com.example.firstjavaspring.repositories.RolesRepository;
import com.example.firstjavaspring.services.ItemService;
import com.example.firstjavaspring.services.UserService;
import com.example.firstjavaspring.services.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.relation.Role;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("currentUser", getUserData());
        List<Countries> countries = itemService.getCountries();
        model.addAttribute("countries", countries);
        return "index";
    }


    @PostMapping(value = "/saveitem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveItem(@RequestParam(name = "id", defaultValue = "0") Long id,
                           @RequestParam(name = "item_name", defaultValue = "NO name") String name,
                           @RequestParam(name = "item_price", defaultValue = "0") int price,
                           @RequestParam(name = "item_amount", defaultValue = "0") int amount,
                           @RequestParam(name = "country_id", defaultValue = "0") Long country_id) {
        ShopItems items = itemService.getItem(id);
        items.setName(name);
        items.setAmount(amount);
        items.setCountry(itemService.getCountry(country_id));
        items.setPrice(price);
        itemService.saveItem(items);
        return "redirect:/about";
    }


    @PostMapping(value = "/deleteitem")
    public String deleteItem(@RequestParam(name = "id") Long id) {
        itemService.deleteItem(itemService.getItem(id));
        return "redirect:/about";
    }


    @GetMapping(value = "/about")
    public String about(Model model) {
        List<ShopItems> items = itemService.getAllItems();
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("tovary", items);
        return "about";
    }


    @PostMapping(value = "/deletecategory")
    public String deletecategory(@RequestParam(name = "item_id") Long item_id,
                                 @RequestParam(name = "category_id") Long category_id) {
        Categories category = itemService.getCategory(category_id);
        if (category != null) {
            ShopItems item = itemService.getItem(item_id);
            if (item != null) {
                List<Categories> categories = item.getCategories();

                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.remove(category);
                itemService.saveItem(item);

                return "redirect:/edititem/" + item_id;
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model){
        model.addAttribute("currentUser", getUserData());
        return "403";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        model.addAttribute("currentUser", getUserData());
        return "/login";
    }


    @PostMapping(value = "/additem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String additem(@RequestParam(name = "item_name", defaultValue = "NO name") String name,
                          @RequestParam(name = "item_price", defaultValue = "0") int price,
                          @RequestParam(name = "item_amount", defaultValue = "0") int amount,
                          @RequestParam(name = "country_id", defaultValue = "0") Long country_id) {
        ShopItems items = new ShopItems();
        items.setName(name);
        items.setAmount(amount);
        items.setCountry(itemService.getCountry(country_id));
        items.setPrice(price);
        itemService.addItem(items);
        return "redirect:/about";
    }


    @GetMapping(value = "/details/{idshka}")
    public String details(Model model, @PathVariable(name = "idshka") Long id) {
        ShopItems item = itemService.getItem(id);
        model.addAttribute("currentUser", getUserData());
        List<Countries> countries = itemService.getCountries();
        List<Categories> categories = itemService.getCategories();
        categories.removeAll(item.getCategories());
        model.addAttribute("item", item);
        model.addAttribute("countries", countries);

        model.addAttribute("categories", categories);
        return "details";
    }
    @GetMapping(value = "/edititem/{idshka}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edititem(Model model, @PathVariable(name = "idshka") Long id) {
        ShopItems item = itemService.getItem(id);

        List<Countries> countries = itemService.getCountries();
        List<Categories> categories = itemService.getCategories();
        categories.removeAll(item.getCategories());

        model.addAttribute("item", item);
        model.addAttribute("countries", countries);
        model.addAttribute("categories", categories);
        model.addAttribute("currentUser", getUserData());
        return "edititem";

    }


    @PostMapping(value = "/assigncategory")
    public String assigncategory(@RequestParam(name = "item_id") Long item_id,
                                 @RequestParam(name = "category_id") Long category_id) {

        Categories category = itemService.getCategory(category_id);
        if (category != null) {
            ShopItems item = itemService.getItem(item_id);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.add(category);

                itemService.saveItem(item);
                return "redirect:/edititem/" + item_id;
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser", getUserData());
        return "profile";
    }

    @GetMapping(value = "/additem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String additem(Model model){
        model.addAttribute("currentUser", getUserData());
        List<Countries> countries = itemService.getCountries();
        model.addAttribute("countries", countries);
        return "additem";
    }

    @GetMapping(value = "/register")
    public String register(Model model){
        return "register";
    }

    @PostMapping(value = "/register")
    public String toregister(@RequestParam(name = "user_email") String email,
                             @RequestParam(name = "user_password") String password,
                             @RequestParam(name = "user_repassword") String repassword,
                             @RequestParam(name = "user_fullname") String fullname){

        if (password.equals(repassword)){
        Users newUser = new Users();
        newUser.setFull_name(fullname);
        newUser.setEmail(email);
        newUser.setPassword(password);

            if (userService.createUser(newUser) != null){
                return "redirect:/register?success";
            }

        }
        return "";
    }

    private Users getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }


}
