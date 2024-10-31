package com.example.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.example.domain.CurrencyExchange;

public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Long> {

    CurrencyExchange findByFromAndTo(String from, String to);

}
