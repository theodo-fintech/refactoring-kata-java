package com.sipios.refactoring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Purchase {

    @JsonProperty("items")
    private PurchaseItem[] purchasedItems;

    @JsonProperty("type")
    private String customerType;

    public Purchase(PurchaseItem[] is, String t) {
        this.purchasedItems = is;
        this.customerType = t;
    }

    public Purchase() {
    }

    public PurchaseItem[] getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(PurchaseItem[] purchasedPurchaseItems) {
        this.purchasedItems = purchasedPurchaseItems;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }


}
