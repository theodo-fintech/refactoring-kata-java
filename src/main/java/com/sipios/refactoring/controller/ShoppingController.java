package com.sipios.refactoring.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sipios.refactoring.dto.BodyDto;
import com.sipios.refactoring.dto.ItemDto;
import com.sipios.refactoring.service.ShoppingService;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @PostMapping
    public String getPrice(@RequestBody Body b) {
    	
    	if (b.getItems() == null) {
            return "0";
        }
    	
    	final ShoppingService shoppingService = new ShoppingService();
        String price = "Error";
        
        try {
        	price = shoppingService.getPrice(convertToBodyDto(b));
        } catch (Exception e) {
        	logger.trace("getPrice Controller: Bad Request");
        	logger.info(e.getMessage());
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return price;
    }

	private BodyDto convertToBodyDto(Body body) {
		List<ItemDto> listItemDto = new ArrayList<>();
	    for (Item item : body.getItems()) {
	        listItemDto.add(new ItemDto(item.getType(), item.getNb()));
	    }
	    ItemDto[] itemDto = new ItemDto[body.getItems().length];
	    itemDto = listItemDto.toArray(itemDto);
	    return new BodyDto(itemDto, body.getType());
	}
}