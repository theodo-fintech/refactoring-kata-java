package com.sipios.refactoring.controller;

import com.sipios.refactoring.data.domain.CustomerType;
import com.sipios.refactoring.data.domain.Item;
import com.sipios.refactoring.data.domain.ItemType;
import com.sipios.refactoring.data.domain.ShoppingCart;
import com.sipios.refactoring.data.requests.ShoppingRequest;
import com.sipios.refactoring.service.ShoppingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

//    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    private final ShoppingService shoppingService;

    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping
    public String getPrice(@RequestBody ShoppingRequest shoppingRequest) {
        CustomerType customerType;
        try {
            customerType = CustomerType.valueOf(shoppingRequest.getType().replaceAll("_CUSTOMER", ""));
        } catch (IllegalArgumentException exception) {
            String message = "Unexpected customer type: " + shoppingRequest.getType()
                + ". Supported values: " + Arrays.toString(CustomerType.values());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }

        if (shoppingRequest.getItems() == null) {
            return "0";
        }

        List<Item> items = Stream.of(shoppingRequest.getItems())
            .map(request -> new Item(ItemType.valueOf(request.getType()), request.getQuantity()))
            .collect(Collectors.toList());

        var price = shoppingService.getPrice(new ShoppingCart(items, customerType), LocalDate.now());

        if (!customerType.accepts(price)) {
            String message = "Price (" + price + ") is too high for "
                + customerType + " customer. "
                + "Limit is: " + customerType.getPriceLimit();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }

        return String.valueOf(price);
    }
}
