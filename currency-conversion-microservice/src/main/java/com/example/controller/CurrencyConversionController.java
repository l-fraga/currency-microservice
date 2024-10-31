package com.example.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.example.domain.CurrencyConversion;
import com.example.exception.ResourceNotFoundException;
import com.example.interfaces.CurrencyExchangeProxy;

public class CurrencyConversionController {

    private CurrencyExchangeProxy proxy;

    public CurrencyConversionController(CurrencyExchangeProxy proxy) {
        this.proxy = proxy;
    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            CurrencyConversion response = responseEntity.getBody();
            return new CurrencyConversion(response.getId(), from, to, quantity, response.getConversionMultiple(), quantity.multiply(response.getConversionMultiple()), response.getEnvironment() + " " + "rest template");
        }
        throw new ResourceNotFoundException("From" + from + "To" + to + "not found");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversion response = proxy.retrieveExchangeValue(from, to);
        return new CurrencyConversion(response.getId(), from, to, quantity, response.getConversionMultiple(), quantity.multiply(response.getConversionMultiple()), response.getEnvironment() + " " + "feign");
    }
}
