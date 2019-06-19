package com.example.bpwcurrencyModel;

public class Currency {
    private String currency_name;
    private double currency_value_sell;
    private double currency_value_buy;

    public Currency(String name, double sell_value, double buy_value){
        currency_name = name;
        currency_value_sell = sell_value;
        currency_value_buy = buy_value;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public double getCurrency_value_sell() {
        return currency_value_sell;
    }

    public void setCurrency_value_sell(double currency_value_sell) {
        this.currency_value_sell = currency_value_sell;
    }

    public double getCurrency_value_buy() {
        return currency_value_buy;
    }

    public void setCurrency_value_buy(double currency_value_buy) {
        this.currency_value_buy = currency_value_buy;
    }
}
