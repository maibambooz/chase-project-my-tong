package com.jpmc.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Theater {

    private LocalDateProvider provider;
    private List<Showing> schedule;
    private HashMap<Integer, ArrayList<Reservation>> showingReservations;

    /**
     * Theater Constructor
     * @param provider LocalDateProvider object
     */
    public Theater(LocalDateProvider provider) {
        this.provider = provider;
        this.showingReservations = new HashMap<>();

        generateSchedule();
        createInitialShowingReservations();
    }

    /**
     * Generate showing schedule
     * List of schedule showtimes are generated for three movies
     */
    private void generateSchedule(){
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, true);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, false);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, false);

        schedule = List.of(
                new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
    }

    /**
     * @return Retrieves list of Showings
     */
    public List<Showing> getSchedule() {
        return schedule;
    }

    /**
     * @param customer customer information for identification
     * @param sequence show number for the day
     * @param quantity number of tickets requested for showing
     */
    public Reservation reserveShowing(Customer customer, int sequence, int quantity) {

        Showing showing = null;
        for(Showing show : schedule){
            if(show.getSequenceOfTheDay() != sequence){
                throw new IllegalArgumentException("Unable to find showing with given sequence: " + sequence);
            }else{
                showing = show;
                break;
            }
        }

        // This is currently not used, but once a reservation has been made it is added onto a hashmap
        Reservation reservation = new Reservation(customer, showing, quantity);
        ArrayList<Reservation> temp = showingReservations.get(sequence);
        temp.add(reservation);
        showingReservations.put(sequence, temp);

        return reservation;
    }

    /**
     * @return Retrieves HashMap of all show reservations
     */
    public HashMap<Integer, ArrayList<Reservation>> getAllShowingReservations() {
        return showingReservations;
    }

    /**
     * Method to print all reservations in a showing
     */
    public void printAllSequenceReservations(){
        getAllShowingReservations().forEach((key, value) -> {
            if(!value.isEmpty()){
                System.out.println("Sequence " + key + ": ");
                value.forEach( reservation -> System.out.println(reservation.displayReservationInfo()));
            }
        });
    }

    /**
     * Display current day's movie schedule simple text and json format
     */
    public void printSchedule(){
        System.out.println("===================================================");
        System.out.println("Movie schedule for: " + formatLocalDateTime(provider.currentDate()));
        System.out.println("===================================================");
        getSchedule().forEach(s -> {
                    System.out.println(s.getSequenceOfTheDay() + ": " + formatLocalDateTime(s.getStartTime()) + " - " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + ", Price: $" + s.getMovieFee());
        });
        System.out.println("===================================================");
        printJson();
    }

    /**
     * @param date LocalDate object
     * @return String that displays formatted current date and day of the week
     */
    private String formatLocalDateTime(LocalDate date){
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

    /**
     * @param date LocalDateTime object
     * @return String that displays formatted showing date and time to be human-readable
     */
    private String formatLocalDateTime(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
    }

    /**
     * @param duration object of showing time
     * @return String of converted movie duration to hours & minutes
     */
    private String humanReadableFormat(Duration duration){
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    /**
     * @param value Reminder of minutes to determine plural or not
     * @return empty string if no leftover minutes, otherwise "s"
     */
    private String handlePlural(long value){
        if(value == 1) {
            return "";
        }else {
            return "s";
        }
    }

    /**
     * Method simple iterates through schedule showing objects and prints in json format
     */
    private void printJson() {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("===================================================");
        System.out.println("Json text of Movie Showing");
        System.out.println("===================================================");
        getSchedule().forEach(s -> {
            try {
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(s));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println("===================================================");
        });
    }

    /**
     * Method to initialize key and value of reservation hashmap
     */
    private void createInitialShowingReservations() {
        schedule.forEach(s -> showingReservations.put(s.getSequenceOfTheDay(), new ArrayList<Reservation>()));
    }

    public static void main(String[] args) {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();    }
}
