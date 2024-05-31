package com.freddan.ministore.controllers;

import com.freddan.ministore.entities.ShoppingCartItem;
import com.freddan.ministore.services.ShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ShoppingCartItemController {

    private ShoppingCartItemService shoppingCartItemService;

    @Autowired
    public ShoppingCartItemController(ShoppingCartItemService shoppingCartItemService) {
        this.shoppingCartItemService = shoppingCartItemService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoppingCartItem>> allShoppingCartItems() {
        return ResponseEntity.ok(shoppingCartItemService.findAllItems());
    }
}
