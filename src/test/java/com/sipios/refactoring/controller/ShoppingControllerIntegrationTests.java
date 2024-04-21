package com.sipios.refactoring.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sipios.refactoring.models.Body;
import com.sipios.refactoring.models.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testStandardCustomerNoDiscounts() throws Exception {
        Item[] items = { new Item("TSHIRT", 1), new Item("DRESS", 1) };
        Body body = new Body(items, "STANDARD_CUSTOMER");

        mockMvc.perform(post("/shopping")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isOk())
            .andExpect(content().string("80.0")); // (1 * 30 + 1 * 50) * 1.0 = 80
    }

    @Test
    public void testBadRequestForUnknownType() throws Exception {
        Item[] items = { new Item("SWEATSHIRT", 1) };
        Body body = new Body(items, "UNKNOWN_TYPE");

        mockMvc.perform(post("/shopping")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testPriceTooHighException() throws Exception {
        Item[] items = { new Item("JACKET", 3) }; // This should exceed the price limit for STANDARD_CUSTOMER
        Body body = new Body(items, "STANDARD_CUSTOMER");

        MvcResult result = mockMvc.perform(post("/shopping")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest())
            .andReturn();

        // Verify that the ResponseStatusException is correctly instantiated and contains the expected message
        assertTrue(result.getResolvedException() instanceof ResponseStatusException, "Exception should be of type ResponseStatusException");
        ResponseStatusException ex = (ResponseStatusException) result.getResolvedException();
        assertTrue(ex.getReason().contains("Price (300.0) is too high for standard customer"), "Error message should indicate that the price is too high");
    }
}
