package com.freddan.ministore.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shoppingcarts")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<ShoppingCartItem> items;

    public ShoppingCart() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public void addItem(ShoppingCartItem product) {
        items.add(product);
    }

    public void removeItem(ShoppingCartItem product) {
        items.remove(product);
    }
}
