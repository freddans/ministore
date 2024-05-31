package com.freddan.ministore.controllers;

import com.freddan.ministore.entities.ShoppingCart;
import com.freddan.ministore.services.ProductService;
import com.freddan.ministore.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoppingCart>> allShoppingCarts() {
        return ResponseEntity.ok(shoppingCartService.findAllShoppingCarts());
    }

    @PostMapping("/addtouser/{id}")
    public ResponseEntity<List> addProductToCart(@PathVariable long id, @RequestParam("productId") long productId) {
        return ResponseEntity.ok(shoppingCartService.addProductToCart(id, productId));
    }
}
