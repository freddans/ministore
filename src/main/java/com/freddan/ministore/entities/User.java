package com.freddan.ministore.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String roles;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Receipt> receipts;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = "USER";
        this.shoppingCart = new ShoppingCart();
    }

    public User(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.shoppingCart = new ShoppingCart();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }

    public void removeReceipt(Receipt receipt) {
        receipts.remove(receipt);
    }
}
