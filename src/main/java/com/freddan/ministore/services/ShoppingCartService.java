package com.freddan.ministore.services;

import com.freddan.ministore.entities.Product;
import com.freddan.ministore.entities.ShoppingCart;
import com.freddan.ministore.entities.ShoppingCartItem;
import com.freddan.ministore.entities.User;
import com.freddan.ministore.repositories.ShoppingCartRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ShoppingCartService {

    private final Logger logger = Logger.getLogger(ProductService.class);
    private ProductService productService;
    private UserService userService;
    private ShoppingCartRepository shoppingCartRepository;
    private ShoppingCartItemService shoppingCartItemService;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ShoppingCartItemService shoppingCartItemService, ProductService productService, UserService userService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemService = shoppingCartItemService;
        this.productService = productService;
        this.userService = userService;
    }

    public List<ShoppingCart> findAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public String addProductToCart(long id, long productId, int quantity) {
        User existingUser = userService.findUserById(id);
        Product existingProduct = productService.findProductById(productId);


        // If User exist
        if (existingUser != null) {
            User user = existingUser;

            // If Product exist
            if (existingProduct != null) {

                // If Quantity is higher than 0
                if (!(quantity <= 0)) {


                    Product product = existingProduct;

                    ShoppingCart shoppingCart = user.getShoppingCart();

                    boolean itemAlreadyInCart = false;
                    ShoppingCartItem existingItem = null;

                    // Check if item already exists in users shopping cart
                    for (ShoppingCartItem item : shoppingCart.getItems()) {
                        if (item.getName().equals(product.getName())) {

                            itemAlreadyInCart = true;
                            existingItem = item;
                        }
                    }

                    // If item does not exist in Users shopping cart, create item and add quantity.
                    if (!itemAlreadyInCart) {

                        if (quantity <= product.getQuantity() && product.getQuantity() != 0) {

                            product.setQuantity(product.getQuantity() - quantity);

                            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(product.getName(), quantity);

                            shoppingCart.addItem(shoppingCartItem);

                            shoppingCartItemService.saveOrUpdate(shoppingCartItem);
                            shoppingCartRepository.save(shoppingCart);

                            System.out.println(product.getName() + " added to cart");

                            if (quantity > 1) {
                                return "Added: " + quantity + "x " + shoppingCartItem.getName() + "s to Shopping Cart";

                            } else {
                                return "Added: " + quantity + "x " + shoppingCartItem.getName() + " to Shopping Cart";
                            }

                        } else if (quantity > product.getQuantity() && product.getQuantity() != 0) {
                            return "ERROR: User tried to add more items than what the store has in stock";

                        } else if (product.getQuantity() >= 0) {

                            return "ERROR: Currently " + product.getName() + " is out of stock";
                        } else {

                            return "ERROR: product quantity is -1";
                        }

                        // If item already exist in Users shopping cart, only add more quantity.
                    } else {

                        if (quantity <= product.getQuantity()) {
                            product.setQuantity(product.getQuantity() - quantity);

                            int oldQuantity = existingItem.getQuantity();
                            int newQuantity = oldQuantity+quantity;

                            existingItem.setQuantity(newQuantity);

                            shoppingCartItemService.saveOrUpdate(existingItem);
                            shoppingCartRepository.save(shoppingCart);

                            if (newQuantity > 1) {
                                return "Added " + quantity + "x " + existingItem.getName() + "s to Shopping Cart";
                            } else {
                                return "Added " + quantity + "x " + existingItem.getName() + " to Shopping Cart";
                            }
                        } else if (product.getQuantity() == 0){

                            return "ERROR: Currently " + product.getName() + " is out of stock";
                        } else {

                            return "ERROR: User tried to add more items than what the store has in stock";
                        }
                    }

                } else {

                    return "ERROR: Provided quantity is not enough. You need to provide at least 1 in quantity.\n" +
                            "Provided quantity: " + quantity;
                }


            } else {
                logger.error("\nERROR: Product with provided ID does not exist.\n" +
                        "Provided ID: " + productId + "\n");

                return "ERROR: Product with provided ID does not exist.\n" +
                        "Provided ID: " + productId;
            }
        } else {

            logger.error("\nERROR: User with provided ID does not exist.\n" +
                    "Provided ID: " + id + "\n");

            return "ERROR: User with provided ID does not exist.\n" +
                    "Provided ID: " + id;
        }

    }

}


