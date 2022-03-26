package com.sipios.refactoring.services;

import com.sipios.refactoring.models.dtos.ShoppingCartRequest;
import org.springframework.stereotype.Service;

@Service
public interface PriceService {

    double getTotalPrice(ShoppingCartRequest shoppingCartRequest);

}
