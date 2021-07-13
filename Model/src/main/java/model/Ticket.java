package model;

import java.io.Serializable;

public class Ticket implements EntityID<Integer>, Serializable {
    private Integer ticketID;
    private String nameClient;
    private String otherPeople;
    private String adressClient;
    private Integer seats;

    private Integer flightID;

    public Ticket(String nameClient, String otherPeople, String adressClient, Integer seats, Integer flightID) {
        this.nameClient = nameClient;
        this.adressClient = adressClient;
        this.otherPeople = otherPeople;
        this.seats = seats;
        this.flightID = flightID;
    }

    public Integer getTicketID() {
        return ticketID;
    }

    public Integer getSeats() {
        return seats;
    }

    public String getAdressClient() {
        return adressClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    public String getOtherPeople() {
        return otherPeople;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public void setAdressClient(String adressClient) {
        this.adressClient = adressClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public void setOtherPeople(String otherPeople) {
        this.otherPeople = otherPeople;
    }

    @Override
    public Integer getID() {
        return ticketID;
    }

    @Override
    public void setID(Integer integer) {
        ticketID = integer;
    }

    public void setFlightID(Integer flightID) {
        this.flightID = flightID;
    }

    public Integer getFlightID() {
        return flightID;
    }

    @Override
    public String toString() {
        return "model.Ticket{" +
                "ticketID=" + ticketID +
                ", nameClient='" + nameClient + '\'' +
                ", otherPeople='" + otherPeople + '\'' +
                ", adressClient='" + adressClient + '\'' +
                ", seats=" + seats +
                '}';
    }
}
