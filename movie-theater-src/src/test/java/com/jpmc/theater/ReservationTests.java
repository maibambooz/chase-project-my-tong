package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ReservationTests {

    @Test
    void checkCustomer() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Customer customer1 = new Customer("Jane Doe", id1);
        Customer customer2 = new Customer("John Smith", id2);

        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, true);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.now()));

        Reservation reservation = new Reservation(customer1, showing, 2);

        // Check if Reservation customer name is equal and if customer name is set
        assertEquals("Jane Doe", reservation.getCustomer().getName());
        assertEquals("Jane Doe", customer1.getName());
        assertEquals("John Smith", customer2.getName());

        // Check reservation user id and customer ids
        assertTrue(reservation.getCustomer().getUserId() == id1);
        assertTrue(id1 == customer1.getUserId());
        assertTrue(id2 == customer2.getUserId());

        assertFalse(customer1.equals(customer2));
    }

    @Test
    void checkShowing() {
        Customer customer = new Customer("Jane Doe", UUID.randomUUID());
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, true);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        Showing showing2 = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));
        Reservation reservation = new Reservation(customer, showing, 1);

        assertEquals(reservation.getShowing(), showing);
        assertTrue(reservation.getShowing().equals(showing));
        assertFalse(showing.equals(showing2));
    }

    @Test
    void checkTotalFees() {
        Customer customer = new Customer("John Doe", UUID.randomUUID());

        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85),5, false);
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12, false);

        // Create showing with no special time slot discount and non-special movie
        Showing showing1 = new Showing(turningRed, 7, LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 59)));

        // Create showing with special time slot discount and non-special movie
        Showing showing2 = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));
        Reservation reservation1 = new Reservation(customer, showing1, 2);
        Reservation reservation2 = new Reservation(customer, showing2, 3);

        assertTrue(reservation1.totalFee() == 10);
        assertEquals(27, reservation2.totalFee());
    }
}
