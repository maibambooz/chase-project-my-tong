package com.jpmc.theater;

import java.util.Objects;

public class Reservation {
    private Customer customer;
    private Showing showing;
    private int quantity;

    /**
     * Reservation Constructor
     * @param customer Customer object
     * @param showing Showing object
     * @param quantity Number of tickets reserved for customer
     */
    public Reservation(Customer customer, Showing showing, int quantity) {
        this.customer = customer;
        this.showing = showing;
        this.quantity = quantity;
    }

    /**
     * @return Retrieve Customer name
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return Retrieve showing information within the reservation
     */
    public Showing getShowing(){
        return showing;
    }

    /**
     * @return Retrieve quantity of tickets of showing
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * @return Retrieve string of all fields of Reservation
     */
    public String displayReservationInfo() {
        return getCustomer().toString() + "\n" + getShowing().toString() + "\nQuantity: " + getQuantity() + "\nTotal: " + totalFee();
    }

    /**
     * @return Retrieve total amount after discounts and quantity applied
     */
    public double totalFee(){
        return showing.getMovieFee() * getQuantity();
    }
}