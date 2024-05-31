package com.freddan.ministore.services;

import com.freddan.ministore.entities.Product;
import com.freddan.ministore.entities.ShoppingCart;
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

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService, UserService userService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<ShoppingCart> findAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public List addProductToCart(long id, long productId) {
        User existingUser = userService.findUserById(id);
        Product existingProduct = productService.findProductById(productId);

        if (existingUser != null) {
            User user = existingUser;

            if (existingProduct != null) {

                Product product = existingProduct;

                ShoppingCart shoppingCart = existingUser.getShoppingCart();

                if (product.getQuantity() > 0) {

                    product.setQuantity(product.getQuantity() - 1);

                    shoppingCart.addProduct(product);

                    shoppingCartRepository.save(shoppingCart);

                    System.out.println(product.getName() + " added to cart");

                    return shoppingCart.getProductList();


                } else {
                    logger.error("\nERROR: User tried to add Product that is out of stock.\n");
                    return Collections.emptyList();
                }

            } else {
                logger.error("\nERROR: Product with provided ID does not exist.\n" +
                        "Provided ID: " + productId + "\n");

                return Collections.emptyList();
            }
        } else {

            logger.error("\nERROR: USer with provided ID does not exist.\n" +
                    "Provided ID: " + id + "\n");

            return Collections.emptyList();
        }
    }

}
