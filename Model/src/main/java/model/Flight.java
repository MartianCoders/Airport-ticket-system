package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Flight implements EntityID<Integer>, Serializable {
    private int flightID;
    private String destination;
    private LocalDateTime timeOfTheFlight;
    private String airport;
    private Integer seats;

    public Flight(String destination, LocalDateTime localDateTime, String airport, Integer seats) {
        this.destination = destination;
        this.timeOfTheFlight = localDateTime;
        this.airport = airport;
        this.seats = seats;
    }

    public int getFlightID() {
        return flightID;
    }

    public Integer getSeats() {
        return seats;
    }

    public String getAirport() {
        return airport;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getTimeOfTheFlight() {
        return timeOfTheFlight;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public void setTimeOfTheFlight(LocalDateTime timeOfTheFlight) {
        this.timeOfTheFlight = timeOfTheFlight;
    }

    @Override
    public String toString() {
        return "model.Flight{" +
                "ID= " + flightID + '\'' +
                "destination='" + destination + '\'' +
                ", localDateTime=" + timeOfTheFlight +
                ", airport='" + airport + '\'' +
                ", seats=" + seats +
                '}';
    }

    @Override
    public Integer getID() {
        return this.flightID;
    }

    @Override
    public void setID(Integer integer) {
        this.flightID = integer;
    }
}
