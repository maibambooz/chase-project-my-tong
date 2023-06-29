package com.jpmc.theater;

import java.util.UUID;
import java.util.Objects;

public class Customer {

    private String name;
    private UUID userId;

    /**
     * @param name customer name
     * @param userId customer id
     */
    public Customer(String name, UUID userId) {
        this.userId = userId;
        this.name = name;
    }

    /**
     * @return Retrieve name of Customer
     */
    public String getName(){
        return name;
    }

    /**
     * @return Retrieve unique id of Customer
     */
    public UUID getUserId(){
        return userId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Customer)) return false;
        Customer customer = (Customer) object;
        return Objects.equals(name, customer.name) && Objects.equals(userId, customer.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userId);
    }

    @Override
    public String toString() {
        return "Customer Id: " + getUserId() + ", Customer name: " + getName();
    }
}