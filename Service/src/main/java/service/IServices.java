package service;

import model.Flight;
import model.Ticket;
import model.User;

import java.util.List;


public interface IServices {
     void login(User user, IObserver client) throws Exception;
     void logout(User user, IObserver client) throws Exception;
     void addTicket(Ticket ticket) throws Exception;
     List<Flight> searchFlightDestinationAndData(String destination, String data) throws Exception;
     Flight[] getFlightsDB() throws Exception;
     List<Ticket> getTicketsDB() throws Exception;
}
