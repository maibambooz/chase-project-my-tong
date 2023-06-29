package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Movie {
    private String title;
    private Duration runningTime;
    private double ticketPrice;
    private boolean specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, boolean specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing);
    }

    // Helper method that checks if the current time is within slots 11:00am to 4:00pm
    private boolean isBetweenSlots(LocalTime time, LocalTime start, LocalTime end){
        return time.compareTo(start) >= 0 && time.compareTo(end) < 0;
    }

    // Helper method to compare two best prices of discounts using ternary operations
    private double bestValue(double value1, double value2){
        return value1 > value2 ? value1 : value2;
    }

    private double getDiscount(Showing showing) {
        double specialDiscount = 0;
        double tempDiscount = 0;
        double sequenceDiscount = 0;

        if(specialCode){
            specialDiscount = getTicketPrice() * DISCOUNTS.SPECIAL.percent_off;
        }

        // Grabbing the local time to check to see if it is between 11am to 4pm to apply discounts
        LocalTime localTime = showing.getStartTime().toLocalTime();
        if(isBetweenSlots(localTime, LocalTime.parse("11:00"), LocalTime.parse("16:00"))){
            tempDiscount = getTicketPrice() * DISCOUNTS.SPECIAL_TIME.percent_off;
            specialDiscount = bestValue(specialDiscount, tempDiscount);
        }

        // Handling discount rate for 7th day of the month
        int currentDayOfMonth = showing.getStartTime().getDayOfMonth();
        if(currentDayOfMonth == 7){
            tempDiscount = DISCOUNTS.SEVENTH_DAY.amount;
            specialDiscount = bestValue(specialDiscount, tempDiscount);
        }

        int showSequence = showing.getSequenceOfTheDay();
        if (showSequence == 1) {
            sequenceDiscount = DISCOUNTS.SHOWING_FIRST.amount;
        } else if (showSequence == 2) {
            sequenceDiscount = DISCOUNTS.SHOWING_SECOND.amount;
        } else {
            throw new IllegalArgumentException("Showing sequence does not have applicable discount");
        }

        // biggest discount wins
        return bestValue(specialDiscount, sequenceDiscount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
}