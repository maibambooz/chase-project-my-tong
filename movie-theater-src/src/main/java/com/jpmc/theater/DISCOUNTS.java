package com.jpmc.theater;

/**
 * @author My Tong
 * DISCOUNT enums representing of each unique discount rules by the theater.
 * Be it a percentage or a fix dollar amount.
 */
public enum DISCOUNTS {
    SPECIAL("Special", 0.2, 0),
    SHOWING_FIRST("ShowOne", 0, 3),
    SHOWING_SECOND("ShowTwo", 0, 2),
    SPECIAL_TIME("SpecialTime", 0.25, 0),
    SEVENTH_DAY("Seven", 0, 1);

    String discount;
    Double percent_off;
    Double amount;

    DISCOUNTS(String discount, double percent_off, double amount) {
        this.discount = discount;
        this.percent_off = percent_off;
        this.amount = amount;
    }
}
