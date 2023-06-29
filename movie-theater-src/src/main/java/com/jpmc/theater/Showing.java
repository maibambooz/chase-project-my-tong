package com.jpmc.theater;

import java.time.LocalDateTime;

public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    /**
     * Showing Constructor
     * @param movie Movie Object
     * @param sequenceOfTheDay Number of which showing
     * @param showStartTime LocalDateTime object with the showStartTime
     */
    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    /**
     * @return Retrieve Movie Object
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @return Retrieve start time of showing
     */
    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    /**
     * @return Retrieve the current showing sequence of the movie
     */
    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    /**
     * @param sequence sequence showing of movie
     * @return boolean if sequence number matches
     */
    public boolean isSequence(int sequence) {
        return sequenceOfTheDay == sequence;
    }

    /**
     * @return Retrieve movie fees after price adjustments
     */
    public double getMovieFee() {
        return getMovie().calculateTicketPrice(this);
    }

    @Override
    public String toString(){
        return getMovie().toString();
    }

}
