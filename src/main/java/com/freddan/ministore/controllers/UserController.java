package com.freddan.ministore.controllers;

import com.freddan.ministore.entities.Receipt;
import com.freddan.ministore.entities.User;
import com.freddan.ministore.services.ReceiptService;
import com.freddan.ministore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ReceiptService receiptService;

    @Autowired
    public UserController(UserService userService, ReceiptService receiptService) {
        this.userService = userService;
        this.receiptService = receiptService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }



    @PostMapping("/create")
    public ResponseEntity<User> createNewUser(@RequestBody User newUserInfo) {
        return ResponseEntity.ok(userService.createUser(newUserInfo));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User updatedUserInfo) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUserInfo));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/receipts")
    public ResponseEntity<List<Receipt>> allReceipts() {
        return ResponseEntity.ok(receiptService.allReceipts());
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable long id) {
        return ResponseEntity.ok(receiptService.findReceiptById(id));
    }
}
