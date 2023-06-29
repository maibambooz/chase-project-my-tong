package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TheaterTests {
    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer customer = new Customer("John Doe", UUID.randomUUID());
        Reservation reservation = theater.reserveShowing(customer, 2, 4);
        assertNotEquals(reservation.totalFee(), 50);
        assertEquals(reservation.totalFee(), 37.5);
    }

    @Test
    void checkCreatedMovieReservation() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer customer = new Customer("John Doe", UUID.randomUUID());

        Reservation testReservation = theater.reserveShowing(customer, 2, 2);
        assertTrue(testReservation.getCustomer().equals(customer));
        assertTrue(testReservation.getQuantity() == 2);
        assertEquals(2, testReservation.getShowing().getSequenceOfTheDay());
//        theater.printAllShowingReservations();
    }

    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }

    @Test
    void printScheduleJsonFormat(){
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printJson();
    }

    @Test
    void printAllSequenceReservations(){
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printAllSequenceReservations();
    }
}
