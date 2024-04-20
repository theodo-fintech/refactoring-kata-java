package com.sipios.refactoring.services;


import com.sipios.refactoring.utils.Constantes;
import org.springframework.stereotype.Service;

@Service
public class PriceService {


    /**
     * fetch item price by item type
     * @param itemType the given item type
     * @return the price of the item type
     */
    public Double fetchPriceByItemType(String itemType) {

        switch (itemType) {
            case Constantes.ITEM_TSHIRT:
                return 30d;
            case Constantes.ITEM_DRESS:
                return 50d;
            case Constantes.ITEM_JACKET:
                return 100d;
            default:
                return 0d;
        }

    }

}
