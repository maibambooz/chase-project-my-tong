package com.jpmc.theater;

public class Reservation {
    private Customer customer;
    private Showing showing;
    private int quantity;

    public Reservation(Customer customer, Showing showing, int quantity){
        this.customer = customer;
        this.showing = showing;
        this.quantity = quantity;
    }

    public Customer getCustomer(){
        return customer;
    }

    public Showing getShowing(){
        return showing;
    }

    public int getQuantity(){
        return quantity;
    }

    public double totalFee(){
        return showing.getMovieFee() * getQuantity();
    }
}