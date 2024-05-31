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
    private List<ShoppingCartItem> productList;

    public ShoppingCart() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ShoppingCartItem> getProductList() {
        return productList;
    }

    public void setProductList(List<ShoppingCartItem> productList) {
        this.productList = productList;
    }

    public void addProduct(ShoppingCartItem product) {
        productList.add(product);
    }

    public void removeProduct(ShoppingCartItem product) {
        productList.remove(product);
    }
}
