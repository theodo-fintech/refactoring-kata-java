package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;
    
    public static final String STANDARD_CUSTOMER = "STANDARD_CUSTOMER";
    public static final String PREMIUM_CUSTOMER = "PREMIUM_CUSTOMER";
    public static final String PLATINUM_CUSTOMER = "PLATINUM_CUSTOMER";
	
    public static final String TSHIRT = "TSHIRT";
    public static final String DRESS = "DRESS";
    public static final String JACKET = "JACKET";

    @Test
    void shouldNotThrow() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new Item[] {}, STANDARD_CUSTOMER))
        );
    }
    
    @Test
    void getPriceNoItems() {
        var price = controller.getPrice(new Body(new Item[] {}, STANDARD_CUSTOMER));
        Assertions.assertEquals("0.0", price);
    }
    
    @Test
    void getPriceTooHighStandardCustomer() {
        Item tshirt = new Item(TSHIRT,2);
        Item dress = new Item(DRESS,2);
        Item jacket = new Item(JACKET,2);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, STANDARD_CUSTOMER)));

        Assertions.assertEquals("400 BAD_REQUEST \"Price (360.0) is too high for standard customer\"", exception.getMessage());
    }
    
    @Test
    void getPriceTooHighPremiumCustomer() {
        Item tshirt = new Item(TSHIRT,20);
        Item dress = new Item(DRESS,20);
        Item jacket = new Item(JACKET,20);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, PREMIUM_CUSTOMER)));

        Assertions.assertEquals("400 BAD_REQUEST \"Price (3240.0) is too high for premium customer\"", exception.getMessage());
    }
    
    @Test
    void getPriceTooHighPlatinumCustomer() {
        Item tshirt = new Item(TSHIRT,200);
        Item dress = new Item(DRESS,200);
        Item jacket = new Item(JACKET,200);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, PLATINUM_CUSTOMER)));

        Assertions.assertEquals("400 BAD_REQUEST \"Price (18000.0) is too high for platinum customer\"", exception.getMessage());
    }
    
    @Test
    void getPriceStandardCustomer() {
        Item tshirt = new Item(TSHIRT,1);
        Item dress = new Item(DRESS,1);
        Item jacket = new Item(JACKET,1);

        var price = controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, STANDARD_CUSTOMER));
        Assertions.assertEquals("180.0", price);
    }
    
    @Test
    void getPricePremiumCustomer() {
        Item tshirt = new Item(TSHIRT,1);
        Item dress = new Item(DRESS,1);
        Item jacket = new Item(JACKET,1);

        var price = controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, PREMIUM_CUSTOMER));
        Assertions.assertEquals("162.0", price);
    }
    
    @Test
    void getPricePlatinumCustomer() {
        Item tshirt = new Item(TSHIRT,1);
        Item dress = new Item(DRESS,1);
        Item jacket = new Item(JACKET,1);

        var price = controller.getPrice(new Body(new Item[] {tshirt, dress, jacket}, PLATINUM_CUSTOMER));
        Assertions.assertEquals("90.0", price);
    }
}
