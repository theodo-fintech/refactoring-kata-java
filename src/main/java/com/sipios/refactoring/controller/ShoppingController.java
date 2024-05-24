package com.sipios.refactoring.controller;

import com.sipios.refactoring.data.domain.CustomerType;
import com.sipios.refactoring.data.requests.ItemRequest;
import com.sipios.refactoring.data.requests.ShoppingRequest;
import com.sipios.refactoring.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

//    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    private final DiscountService discountService;

    public ShoppingController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public String getPrice(@RequestBody ShoppingRequest shoppingRequest) {
        double price = 0;

        CustomerType customerType;
        try {
            customerType = CustomerType.valueOf(shoppingRequest.getType().replaceAll("_CUSTOMER", ""));
        } catch (IllegalArgumentException exception) {
            String message = "Unexpected customer type: " + shoppingRequest.getType()
                + ". Supported values: " + Arrays.toString(CustomerType.values());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }

        double customerDiscount = customerType.getDiscount();



        if (discountService.isSeasonal(LocalDate.now())) {
            if (shoppingRequest.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < shoppingRequest.getItems().length; i++) {
                ItemRequest item = shoppingRequest.getItems()[i];

                if (item.getType().equals("TSHIRT")) {
                    price += 30 * item.getQuantity() * customerDiscount;
                } else if (item.getType().equals("DRESS")) {
                    price += 50 * item.getQuantity() * customerDiscount;
                } else if (item.getType().equals("JACKET")) {
                    price += 100 * item.getQuantity() * customerDiscount;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        } else {
            if (shoppingRequest.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < shoppingRequest.getItems().length; i++) {
                ItemRequest item = shoppingRequest.getItems()[i];

                if (item.getType().equals("TSHIRT")) {
                    price += 30 * item.getQuantity() * customerDiscount;
                } else if (item.getType().equals("DRESS")) {
                    price += 50 * item.getQuantity() * 0.8 * customerDiscount;
                } else if (item.getType().equals("JACKET")) {
                    price += 100 * item.getQuantity() * 0.9 * customerDiscount;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        }



        if (!customerType.accepts(price)) {
            String message = "Price (" + price + ") is too high for "
                + customerType + " customer. "
                + "Limit is: " + customerType.getPriceLimit();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }

        return String.valueOf(price);
    }
}
