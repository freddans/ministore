package com.freddan.ministore.services;

import com.freddan.ministore.entities.Product;
import com.freddan.ministore.entities.ShoppingCart;
import com.freddan.ministore.entities.ShoppingCartItem;
import com.freddan.ministore.entities.User;
import com.freddan.ministore.repositories.ShoppingCartRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public String removeProductFromCart(long userId, long shoppingCartItemId, int quantity) {
        User existingUser = userService.findUserById(userId);

        // if user exist
        if (existingUser != null) {

            ShoppingCartItem existingItem = shoppingCartItemService.findShoppingCartItemById(shoppingCartItemId);

            // if item exists
            if (existingItem != null) {


                if (quantity > 0 && quantity <= existingItem.getQuantity()) {
                    ShoppingCart userShoppingCart = existingUser.getShoppingCart();

                    String nameOfItem = null;

                    boolean itemFound = false;

                    for (ShoppingCartItem item : userShoppingCart.getItems()) {
                        if (existingItem.getId() == (item.getId())) {
                            itemFound = true;
                            nameOfItem = item.getName();
                        }
                    }

                    if (itemFound) {

                        Product product = productService.findProductByName(nameOfItem);
                        int productQuantity = product.getQuantity();
                        int quantityToAdd = quantity;
                        int totalQuantity = productQuantity+quantityToAdd;

                        product.setQuantity(totalQuantity);
                        productService.saveOrUpdate(product);

                        existingItem.setQuantity(existingItem.getQuantity() - quantity);
                        shoppingCartItemService.saveOrUpdate(existingItem);

                        if (existingItem.getQuantity() == 0) {
                            userShoppingCart.removeItem(existingItem);

                            shoppingCartItemService.deleteShoppingCartItem(existingItem);
                        }

                        shoppingCartRepository.save(userShoppingCart);

                        if (quantity > 1) {
                            return "Removed " + quantity + "x " + nameOfItem + "s from your shopping cart.";
                        } else {
                            return "Removed " + quantity + "x " + nameOfItem + " from your shopping cart.";
                        }

                    } else {

                        return "item does not exist in " + existingUser.getUsername() + "'s shopping cart.";
                    }

                } else if (quantity <= 0) {

                    return "ERROR: Provided quantity has to be at least 1 or above.\n" +
                            "Provided quantity: " + quantity;
                } else {

                    return "ERROR: Provided quantity is higher than the quantity of the item in your shopping cart.";
                }

            } else {

                return "ERROR: Item with provided ID does not exist.\n" +
                        "Provided ID: " + shoppingCartItemId;
            }

        } else {

            return "ERROR: Provided User ID does not exist.\n" +
                    "Provided ID: " + userId;
        }

    }

    public String clearShoppingCartList(long id) {
        User user = userService.findUserById(id);

        if (user != null) {
            ShoppingCart shoppingCart = user.getShoppingCart();



            if (!shoppingCart.getItems().isEmpty()) {

                List<ShoppingCartItem> cartItemsToDelete = new ArrayList<>(shoppingCart.getItems());

                for (ShoppingCartItem item : cartItemsToDelete) {
                    Product product = productService.findProductByName(item.getName());
                    int itemQuantity = item.getQuantity();
                    product.setQuantity(product.getQuantity() + itemQuantity);
                    productService.saveOrUpdate(product);

                    shoppingCart.removeItem(item);
                    shoppingCartRepository.save(shoppingCart);

                    shoppingCartItemService.deleteShoppingCartItem(item);
                }

                cartItemsToDelete.clear();

                return "Cleared shopping cart for " + user.getUsername();

            } else {

                return "ERROR: " + user.getUsername() + "'s shopping cart is empty";
            }


        } else {

            return "User does not exist with provided User ID.\n" +
                    "Provided ID: " + id;
        }
    }

}


