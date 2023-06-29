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
    }

    @Test
    void noExistingSequence(){
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer customer = new Customer("Jane Doe", UUID.randomUUID());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            theater.reserveShowing(customer, 10, 1);
        });

        String expectedMessage = "Unable to find showing with given sequence: " + 10;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }

    @Test
    void printAllSequenceReservations(){
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printAllSequenceReservations();
    }
}
