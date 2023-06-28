package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MovieTests {

    @Test
    void checkMovieTitle() {
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95),15, false);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85),15, false);

        assertEquals("The Batman", theBatMan.getTitle());
        assertNotEquals("ThE BaTmAn", theBatMan.getTitle());
        assertNotEquals("Turning Blue", turningRed.getTitle());
    }

    @Test
    void checkTicketPrices() {
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95),10, false);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85),8.40, false);

        assertEquals(10, theBatMan.getTicketPrice());
        assertNotEquals(15, theBatMan.getTicketPrice());
        assertEquals(8.40, turningRed.getTicketPrice());
        assertNotEquals(8.39, turningRed.getTicketPrice());
    }

    @Test
    void specialMovieWith20PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, true);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        assertEquals(10, spiderMan.calculateTicketPrice(showing));

        System.out.println(Duration.ofMinutes(90));
    }

    @Test
    void specialMovieWithTimeSlotDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, true);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));
        assertEquals(9.375, spiderMan.calculateTicketPrice(showing));

    }

    @Test
    void movieWithTimeSlotDiscount() {
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85),5, false);
        Showing showing = new Showing(turningRed, 7, LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 59)));

        System.out.println(turningRed.calculateTicketPrice(showing));

        assertEquals(3.75, turningRed.calculateTicketPrice(showing));
    }

    @Test
    void movieWithSeventhDayDiscount() {
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85),15, false);
        Showing turningRedShowing = new Showing(turningRed, 7, LocalDateTime.of(LocalDate.parse("2023-06-07"), LocalTime.of(19, 30)));
        assertEquals(14, turningRed.calculateTicketPrice(turningRedShowing));

        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95),15, false);
        Showing batmanShowing = new Showing(theBatMan, 3, LocalDateTime.of(LocalDate.parse("2023-06-08"), LocalTime.of(17, 25)));

        System.out.println(theBatMan.calculateTicketPrice(batmanShowing));

        assertNotEquals(14.00, theBatMan.calculateTicketPrice(batmanShowing));
    }


}
