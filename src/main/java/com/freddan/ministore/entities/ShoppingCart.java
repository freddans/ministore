package com.freddan.ministore.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shoppingcarts")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double totalCost;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
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

    public void increaseTotalCost (ShoppingCartItem product, int quantity) {
        double productCost = product.getPrice();

        double totalCost = productCost*quantity;

        this.totalCost += totalCost;
    }

    public void decreaseTotalCost (ShoppingCartItem product, int quantity) {
        double productCost = product.getPrice();

        double totalCost = productCost*quantity;

        this.totalCost -= totalCost;
    }
}
