package com.sipios.refactoring.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ShoppingControllerITTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    public void getPrice_validationError() throws Exception {


        var result = mockMvc.perform(MockMvcRequestBuilders.post("/shopping")
            .contentType(MediaType.APPLICATION_JSON)
            // use java 15 to use literal text blocks
            .content("{\"type\": \"PREMIUM_CUSTOME\", \"items\": [{\"type\": \"TSHIRT\" , \"nb\" : 4}]}")).andReturn();

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertTrue(result.getResponse().getErrorMessage()
            .contains("CustomerType value is not correct"));


    }


    @Test
    public void getPrice_validationError_with_language_fr() throws Exception {


        var result = mockMvc.perform(MockMvcRequestBuilders.post("/shopping")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT_LANGUAGE, "fr")
            // use java 15 to use literal text blocks
            .content("{\"type\": \"PREMIUM_CUSTOME\", \"items\": [{\"type\": \"TSHIRT\" , \"nb\" : 4}]}")).andReturn();

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertTrue(result.getResponse().getErrorMessage()
            .contains("la valeur de CustomerType est invalide"));
    }

}
