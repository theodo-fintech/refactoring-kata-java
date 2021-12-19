package com.sipios.refactoring.service;

import com.sipios.refactoring.controller.ShoppingController;
import com.sipios.refactoring.dto.BodyDto;
import com.sipios.refactoring.dto.ItemDto;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * ShoppingService class
 */
public class ShoppingService {
	
	private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    public static final String STANDARD_CUSTOMER = "STANDARD_CUSTOMER";
    public static final String PREMIUM_CUSTOMER = "PREMIUM_CUSTOMER";
    public static final String PLATINUM_CUSTOMER = "PLATINUM_CUSTOMER";
    
    public static final double STANDARD_CUSTOMER_DISCOUNT = 1;
	public static final double PREMIUM_CUSTOMER_DISCOUNT = 0.9;
	public static final double PLATINUM_CUSTOMER_DISCOUNT = 0.5;
	
    public static final String TSHIRT = "TSHIRT";
    public static final String DRESS = "DRESS";
    public static final String JACKET = "JACKET";
	
	public static final double TSHIRT_FACTOR = 1;
	public static final double DRESS_FACTOR = 0.8;
	public static final double JACKET_FACTOR = 0.9;
	
	public static final double TSHIRT_PRICE = 30;
	public static final double DRESS_PRICE = 50;
	public static final double JACKET_PRICE = 100; 
	
	public String getPrice(BodyDto b) throws Exception {
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        
        boolean isDiscountPeriod = isDiscountPeriod(cal);
        double customerTypeDiscount = getDiscountForCustomerType(b.getType());
        double price = getPriceForItemsAndCustomer(b.getItems(), customerTypeDiscount, isDiscountPeriod);

        if (b.getType().equals(STANDARD_CUSTOMER)) {
            if (price > 200) {
                throw new Exception("Price (" + price + ") is too high for standard customer");
            }
        } else if (b.getType().equals(PREMIUM_CUSTOMER)) {
            if (price > 800) {
                throw new Exception("Price (" + price + ") is too high for premium customer");
            }
        } else if (b.getType().equals(PLATINUM_CUSTOMER)) {
            if (price > 2000) {
                throw new Exception("Price (" + price + ") is too high for platinum customer");
            }
        } else {
            if (price > 200) {
                throw new Exception("Price (" + price + ") is too high for standard customer");
            }
        }
        
        return String.valueOf(price);
	}
    
    public double getPriceForItemsAndCustomer(ItemDto[] items, double customerTypeDiscount, boolean isDiscountPeriod) {
    	double price = 0;      
    	double factor = 1; 
    	
    	// Compute total amount depending on the types and quantity of product 
        for (int i = 0; i < items.length; i++) {
            ItemDto it = items[i];
            double itemPrice = 0;
            
            if (it.getType().equals(TSHIRT)) {
            	itemPrice = TSHIRT_PRICE;
            	if (isDiscountPeriod) factor = TSHIRT_FACTOR;
            } else if (it.getType().equals(DRESS)) {
            	itemPrice = DRESS_PRICE;
            	if (isDiscountPeriod) factor = DRESS_FACTOR;
            } else if (it.getType().equals(JACKET)) {
            	itemPrice = JACKET_PRICE;
            	if (isDiscountPeriod) factor = JACKET_FACTOR;
            }

            price += itemPrice * it.getNb() * factor * customerTypeDiscount;
        }
        return price;
    }
    
    private boolean isWinterDiscountPeriod(Calendar cal) {        
        return cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                cal.get(Calendar.MONTH) == 0;
    }
    
    private boolean isSummerDiscountPeriod(Calendar cal) {
    	return cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                cal.get(Calendar.MONTH) == 5;
    }
    
    public boolean isDiscountPeriod(Calendar cal) {
        return isWinterDiscountPeriod(cal) || isSummerDiscountPeriod(cal);
    }
    
    public double getDiscountForCustomerType(String type) {
    	double d;
    	logger.trace("Compute discount for customer");
    	
        if (type.equals(STANDARD_CUSTOMER)) {
            d = STANDARD_CUSTOMER_DISCOUNT;
        } else if (type.equals(PREMIUM_CUSTOMER)) {
            d = PREMIUM_CUSTOMER_DISCOUNT;
        } else if (type.equals(PLATINUM_CUSTOMER)) {
            d = PLATINUM_CUSTOMER_DISCOUNT;
        } else {
        	logger.trace("Customer Type not recognized: " + type);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    	return d;
    }
}