package com.example.siteinspring.controllers;
import com.example.siteinspring.entities.Brands;
import com.example.siteinspring.entities.Items;
import com.example.siteinspring.entities.Roles;
import com.example.siteinspring.entities.Users;
import com.example.siteinspring.repositories.RolesRepository;
import com.example.siteinspring.services.BrandService;
import com.example.siteinspring.services.ItemService;
import com.example.siteinspring.services.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping(value = "/")
    public String home(Model model){
        model.addAttribute("tops", itemService.getTopItems());
        Users users = new Users();
        List<Roles> roles = new ArrayList<>();
        roles.add(rolesRepository.findByRole("ROLE_USER"));
        users.setEmail("mekhrat@mail.ru");
        users.setFullname("Mekhrat Ashirbekov");
        users.setPassword("mekhrat");
        users.setRoles(roles);userService.createUser(users);

        return "home";
    }
    @GetMapping(value = "/login")
    public String login(Model model){
//        model.addAttribute("currentUser", getUserData());
        return "/login";
    }

    @GetMapping(value = "/items")
    public String items(Model model){
        model.addAttribute("items",itemService.getItems());
        return "items";
    }

    @GetMapping(value = "/additems")
    public String additems(Model model){
        List<Brands> brands = brandService.getBrands();
        model.addAttribute("brands",brands);
        return "additems";
    }

    @PostMapping(value = "/additems")
    public String additem(@RequestParam(name = "inTopPage",defaultValue = "null") String inTopPage,
                          @RequestParam(name = "addedDate",defaultValue = "null") Date date,
                          @RequestParam(name = "name",defaultValue = "null") String name,
                          @RequestParam(name = "price",defaultValue = "0") Double price,
                          @RequestParam(name = "stars",defaultValue = "0") Integer stars,
                          @RequestParam(name = "description",defaultValue = "null") String description,
                          @RequestParam(name = "smallPicURL",defaultValue = "null") String smallPicURL,
                          @RequestParam(name = "largePicURL",defaultValue = "null") String largePicURL,
                          @RequestParam(name = "brand",defaultValue = "null") Long id){

        Items item = new Items();
        item.setInTopPage(inTopPage.equals("top"));
        item.setAddedDate(date);
        item.setName(name);
        item.setPrice(price);
        item.setStars(stars);
        item.setDescription(description);
        item.setSmallPicURL(smallPicURL);
        item.setLargePicURL(largePicURL);
        item.setBrand(brandService.getBrand(id));

        itemService.addItem(item);
        return "redirect:/items";
    }

    @GetMapping(value = "/details/{id}")
    public String details(Model model,
                          @PathVariable(name = "id") Long id){
        Items item = itemService.getItem(id);
        String stars = Strings.repeat("* ", item.getStars());
        List<Brands> brands = brandService.getBrands();
        model.addAttribute("item", item);
        model.addAttribute("brands" ,brands);
        model.addAttribute("stars" ,stars );

        return "details";
    }

    @PostMapping(value = "/edititem")
    public String edititem(@RequestParam(name = "inTopPage",defaultValue = "null") String inTopPage,
                          @RequestParam(name = "addedDate",defaultValue = "null") Date date,
                          @RequestParam(name = "name",defaultValue = "null") String name,
                          @RequestParam(name = "price",defaultValue = "0") Double price,
                          @RequestParam(name = "stars",defaultValue = "0") Integer stars,
                          @RequestParam(name = "description",defaultValue = "null") String description,
                          @RequestParam(name = "smallPicURL",defaultValue = "null") String smallPicURL,
                          @RequestParam(name = "largePicURL",defaultValue = "null") String largePicURL,
                          @RequestParam(name = "brand",defaultValue = "null") Long brandId,
                           @RequestParam(name = "id" , defaultValue = "0") Long id)
    {

        Items item = itemService.getItem(id);
        item.setInTopPage(inTopPage.equals("top"));
        item.setAddedDate(date);
        item.setName(name);
        item.setPrice(price);
        item.setStars(stars);
        item.setDescription(description);
        item.setSmallPicURL(smallPicURL);
        item.setLargePicURL(largePicURL);
        item.setBrand(brandService.getBrand(brandId));

        itemService.saveItem(item);
        return "redirect:/items";
    }

    @PostMapping(value = "/deleteitem")
    public String deleteitem(@RequestParam(name = "id") Long id){
        Items item = itemService.getItem(id);
        if (item != null){
            itemService.deleteItem(item);
            return "redirect:/items";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/more/{id}")
    public String more(Model model, @PathVariable(name = "id") Long id){
        Items item = itemService.getItem(id);
        String[] description = item.getDescription().split(";");
        model.addAttribute("item",item );
        model.addAttribute("description",description );
        return "more";
    }
}
