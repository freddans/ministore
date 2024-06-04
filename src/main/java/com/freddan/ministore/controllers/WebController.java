package com.freddan.ministore.controllers;

import com.freddan.ministore.entities.Product;
import com.freddan.ministore.entities.User;
import com.freddan.ministore.services.ProductService;
import com.freddan.ministore.services.ShoppingCartService;
import com.freddan.ministore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebController {

    private UserService userService;
    private ProductService productService;
    private ShoppingCartService shoppingCartService;

    @Autowired
    public WebController(UserService userService, ProductService productService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerAccount(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String createUser(User user, Model model) {
        User result = userService.createUser(user);
        if (result != null) {
            return "redirect:/store";
        } else {
            model.addAttribute("error", "User creation failed");
            return "index";
        }
    }

    @PostMapping("/addProductToCart")
    public String addProductToCart(long id, long productId, int quantity) {
        String addProduct = shoppingCartService.addProductToCart(id, productId, quantity);

        if (addProduct.startsWith("Added")) {
            return "redirect:/store";
        } else {
            return "redirect:/store";
        }
    }

    @PostMapping("/clearshoppingcart")
    public String clearShoppingCart(long id, Model model) {
        String clearShoppingCart = shoppingCartService.clearShoppingCartList(id);

        if (clearShoppingCart.startsWith("Cleared")) {
            return "redirect:/store";
        } else {
            return "redirect:/store";
        }
    }

    @PostMapping("/removeItem")
    public String removeItem(long id, long shoppingCartItemId, int quantity) {
        String status = shoppingCartService.removeProductFromCart(id, shoppingCartItemId, quantity);

        if (status.startsWith("Removed")) {
            return "redirect:/store";
        } else {
            return "redirect:/store";
        }
    }

    @PostMapping("/checkout")
    public String checkout(long id) {
        String checkoutShoppingCart = shoppingCartService.checkout(id);

        System.out.println(checkoutShoppingCart);

        if (checkoutShoppingCart.startsWith("Receipt:")) {
            return "redirect:/store";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(long id) {
        String status = productService.deleteProduct(id);

        if (status.startsWith("Product")) {
            return "redirect:/store";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/updateProduct")
    public String updateProduct(long id, @ModelAttribute("newProductInfo") Product newProductInfo) {
        Product status = productService.updateProduct(id, newProductInfo);

        if (status != null) {
            return "redirect:/store";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/store")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public String storePage(Model model) {
        List<Product> products = productService.findAllProducts();
        User userDetails = userService.findUserInfo();
        model.addAttribute("productInfo", products);
        model.addAttribute("userInfo", userDetails);
        return "store";
    }
}
