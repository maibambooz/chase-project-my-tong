package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public class Movie {
    private String title;
    private Duration runningTime;
    private double ticketPrice;
    private boolean specialCode;

    /**
     * @param title String title of movie
     * @param runningTime Duration object of duration of movie
     * @param ticketPrice Price of ticket
     * @param specialCode Special movie code or not
     */
    public Movie(String title, Duration runningTime, double ticketPrice, boolean specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    /**
     * @return Retrieve title of movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Retrieve the duration of movie
     */
    public Duration getRunningTime(){
        return runningTime;
    }

    /**
     * @return Retrieve the price of a ticket
     */
    public double getTicketPrice(){
        return ticketPrice;
    }

    /**
     * @param showing Showing object of Movie containing times, info
     * @return double value of the final price of a ticket after discounts applied
     */
    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing);
    }

    /**
     * Helper method that checks if the current time is within slots 11:00am to 4:00pm
     * @param time current time
     * @param start start time of slot
     * @param end end time of slot
     * @return boolean value if time is within the slot
     */
    private boolean isBetweenSlots(LocalTime time, LocalTime start, LocalTime end) {
        return time.compareTo(start) >= 0 && time.compareTo(end) < 0;
    }

    /**
     * Helper method to compare two best prices of discounts using ternary operations
     * @return the value with the best discount
     */
    private double bestValue(double value1, double value2) {
        return value1 > value2 ? value1 : value2;
    }

    /**
     * Method to return final value after comparison of applicable discounts
     * @param showing Showing Object containing movie information
     * @return price of ticket
     */
    private double getDiscount(Showing showing){
        double specialDiscount = 0;
        double tempDiscount = 0;
        double sequenceDiscount = 0;

        if (specialCode) {
            specialDiscount = getTicketPrice() * DISCOUNTS.SPECIAL.percent_off;
        }

        // Grabbing the local time to check to see if it is between 11am to 4pm to apply discounts
        LocalTime localTime = showing.getStartTime().toLocalTime();
        if (isBetweenSlots(localTime, LocalTime.parse("11:00"), LocalTime.parse("16:00"))) {
            tempDiscount = getTicketPrice() * DISCOUNTS.SPECIAL_TIME.percent_off;
            specialDiscount = bestValue(specialDiscount, tempDiscount);
        }

        // Handling discount rate for 7th day of the month
        int currentDayOfMonth = showing.getStartTime().getDayOfMonth();
        if (currentDayOfMonth == 7) {
            tempDiscount = DISCOUNTS.SEVENTH_DAY.amount;
            specialDiscount = bestValue(specialDiscount, tempDiscount);
        }

        int showSequence = showing.getSequenceOfTheDay();
        if (showSequence == 1) {
            sequenceDiscount = DISCOUNTS.SHOWING_FIRST.amount;
        } else if (showSequence == 2) {
            sequenceDiscount = DISCOUNTS.SHOWING_SECOND.amount;
        }

        // biggest discount wins
        return bestValue(specialDiscount, sequenceDiscount);
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, runningTime, ticketPrice, specialCode);
    }

    @Override
    public String toString(){
        return "Movie Title: " + getTitle() + ", Duration: " + getRunningTime() + ", Ticket price: " + getTicketPrice();
    }
}