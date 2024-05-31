package com.freddan.ministore.controllers;

import com.freddan.ministore.entities.ShoppingCart;
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
    public ResponseEntity<String> addProductToCart(@PathVariable long id, @RequestParam("productId") long productId, @RequestParam("quantity") int quantity) {
        return ResponseEntity.ok(shoppingCartService.addProductToCart(id, productId, quantity));
    }

    @PostMapping("/removefromuser/{id}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable long id, @RequestParam("shoppingCartItemId") long shoppingCartItemId, @RequestParam("quantity") int quantity) {
        return ResponseEntity.ok(shoppingCartService.removeProductFromCart(id, shoppingCartItemId, quantity));
    }

    @PostMapping("/clearforuser/{id}")
    public ResponseEntity<String> clearShoppingCartList(@PathVariable long id) {
        return ResponseEntity.ok(shoppingCartService.clearShoppingCartList(id));
    }
}
