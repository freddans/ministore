package com.freddan.ministore.services;

import com.freddan.ministore.entities.Product;
import com.freddan.ministore.repositories.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final Logger logger = Logger.getLogger(ProductService.class);
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {

            return optionalProduct.get();
        } else {

            return null;
        }
    }

    public Product findProductByName(String productName) {
        Optional<Product> optionalProduct = productRepository.findProductByName(productName);

        if (optionalProduct.isPresent()) {

            return optionalProduct.get();
        } else {

            return null;
        }
    }

    public Product createProduct(Product productInfo) {
        Product existingProduct = findProductByName(productInfo.getName());

        System.out.println("Iconlink: " + productInfo.getIconlink());
        System.out.println("Product: " + productInfo.getName());
        System.out.println("Price: " + productInfo.getPrice());
        System.out.println("Quantity: " + productInfo.getQuantity());

        if (existingProduct != null) {
            System.out.println("product exists");

            logger.error("\nERROR: Product with provided name already exist.\n" +
                    "Provided name: " + productInfo.getName() + "\n");

            return null;
        } else {
            System.out.println("product not found");

            if (productInfo.getName() != null && !productInfo.getName().isEmpty() && productInfo.getPrice() != 0 && productInfo.getQuantity() != 0) {
                if (productInfo.getIconlink() == null || productInfo.getIconlink().isEmpty()) {

                    productInfo.setIconlink("/images/icons/noimagelink.png");

                    Product product = new Product(productInfo.getName(), productInfo.getIconlink(), productInfo.getPrice(), productInfo.getQuantity());

                    productRepository.save(product);

                    return product;
                } else {

                    Product product = new Product(productInfo.getName(), productInfo.getIconlink(), productInfo.getPrice(), productInfo.getQuantity());

                    productRepository.save(product);

                    return product;
                }


            } else {

                logger.error("\nERROR: Please provide a name, price and the quantity of the product.\n");

                return null;
            }
        }
    }

    public Product updateProduct(long id, Product newProductInfo) {
        Product existingProduct = findProductById(id);

        if (existingProduct != null) {

            if (newProductInfo.getName() != null && !newProductInfo.getName().isEmpty() && !newProductInfo.getName().equals(existingProduct.getName())) {

                existingProduct.setName(newProductInfo.getName());
            }
            if (newProductInfo.getPrice() != 0 && newProductInfo.getPrice() != existingProduct.getPrice()) {

                existingProduct.setPrice(newProductInfo.getPrice());
            }
            if (newProductInfo.getQuantity() != 0 && newProductInfo.getQuantity() != existingProduct.getQuantity()) {

                existingProduct.setQuantity(newProductInfo.getQuantity());
            }
            if (newProductInfo.getIconlink() != null && !newProductInfo.getIconlink().isEmpty() && !newProductInfo.getIconlink().equals(existingProduct.getIconlink())) {

                existingProduct.setIconlink(newProductInfo.getIconlink());
            }

            productRepository.save(existingProduct);

            return existingProduct;

        } else {

            logger.error("\nERROR: Product with provided ID does not exist.\n" +
                    "Provided ID: " + id + "\n");
            return null;
        }
    }

    public String deleteProduct(long id) {
        Product productToDelete = findProductById(id);

        if (productToDelete != null) {

            productRepository.delete(productToDelete);

            return "Product deleted";
        } else {

            return "\nERROR: Product with provided ID does not exist.\n" +
                    "Provided ID: " + id + "\n";
        }
    }

    public void saveOrUpdate(Product product) {
        productRepository.save(product);
    }
}
