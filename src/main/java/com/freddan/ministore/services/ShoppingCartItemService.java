package com.freddan.ministore.services;

import com.freddan.ministore.entities.ShoppingCartItem;
import com.freddan.ministore.repositories.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartItemService {

    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    public ShoppingCartItemService(ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    public ShoppingCartItem findShoppingCartItemById(long id) {
        Optional<ShoppingCartItem> optionalShoppingCartItem = shoppingCartItemRepository.findById(id);

        if (optionalShoppingCartItem.isPresent()) {

            return optionalShoppingCartItem.get();
        } else {

            return null;
        }
    }

    public List<ShoppingCartItem> findAllItems() {
        return shoppingCartItemRepository.findAll();
    }

    public void saveOrUpdate(ShoppingCartItem shoppingCartItem) {
        shoppingCartItemRepository.save(shoppingCartItem);
    }

    public void deleteShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        shoppingCartItemRepository.delete(shoppingCartItem);
    }
}
