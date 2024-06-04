package com.freddan.ministore.services;

import com.freddan.ministore.entities.Receipt;
import com.freddan.ministore.entities.ShoppingCartItem;
import com.freddan.ministore.entities.User;
import com.freddan.ministore.repositories.ReceiptRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptService {

    private final Logger logger = Logger.getLogger(ProductService.class);
    private ReceiptRepository receiptRepository;
    private UserService userService;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository, UserService userService) {
        this.receiptRepository = receiptRepository;
        this.userService = userService;
    }

    public List<Receipt> allReceipts() {
        return receiptRepository.findAll();
    }

    public Receipt findReceiptById(long id) {
        Optional<Receipt> optionalReceipt = receiptRepository.findById(id);

        if (optionalReceipt.isPresent()) {

            return optionalReceipt.get();
        } else {
            logger.error("\nERROR: Provided Receipt ID does not exist.\n" +
                    "Provided ID: " + id);
            return null;
        }
    }

    public Receipt createReceipt(User user) {
        if (user != null) {

            List<String> receiptList = new ArrayList<>();

            for (ShoppingCartItem item : user.getShoppingCart().getItems()) {

                receiptList
                        .add(item.getQuantity() + "x " + item.getName() + ": $" + item.getPrice() * item.getQuantity());
            }

            Receipt receipt = new Receipt(receiptList, user.getShoppingCart().getTotalCost());

            receiptRepository.save(receipt);

            return receipt;
        } else {

            logger.error("\nERROR: Could not create receipt.\n");
            return null;
        }
    }
}
