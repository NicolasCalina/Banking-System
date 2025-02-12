package org.poo.main;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.ExchangeInput;

@Getter
@Setter
public class ExchangeRates {
    private String from;
    private String to;
    private double rate;
    private int timestamp;

    public ExchangeRates(final ExchangeInput exchangeRate) {
        this.from = exchangeRate.getFrom();
        this.to = exchangeRate.getTo();
        this.rate = exchangeRate.getRate();
        this.timestamp = exchangeRate.getTimestamp();
    }
}
