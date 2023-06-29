package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.rmi.server.UID;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterTests {
//    @Test
//    void totalFeeForCustomer() {
//        Theater theater = new Theater(LocalDateProvider.singleton());
//        Customer john = new Customer("John Doe", UUID.randomUUID());
//        Reservation reservation = theater.reserveShowing(john, 2, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
//        assertEquals(reservation.totalFee(), 50);
//    }

    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }
}
