package com.khalex7.currencyconverter.controller.currencyparser;

public class Valute {
    private Long numCode;
    private String charCode;
    private Long nominal;
    private String name;
    private String value;

    public Valute (Long numCode, String charCode, Long nominal, String name, String value) {
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public Long getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public Long getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
