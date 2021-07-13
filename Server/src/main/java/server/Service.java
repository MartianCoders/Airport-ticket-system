package server;


import model.Flight;
import model.Ticket;
import model.User;
import repository.*;
import service.Exception;
import service.IObserver;
import service.IServices;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Service implements IServices, Serializable {

    private IRepositoryUser repositoryUser;
    private IRepositoryFlight repositoryFlights;
    private IRepositoryTicket repositoryTicket;
    private Map<User, IObserver> loggedClients;
    private final int defaultThreadsNo = 5;

    public Service(RepositoryUser uRepo, RepositoryFlight fRepo, RepositoryTicket tRepo) {
        this.repositoryUser = uRepo;
        this.repositoryFlights = fRepo;
        this.repositoryTicket = tRepo;
        this.loggedClients = new ConcurrentHashMap<>();
    }


    public synchronized void login(User user, IObserver client) throws Exception {
        User userR = this.repositoryUser.findEntityUser(user.getUser());
        if (userR!=null){
            if(loggedClients.get(user)!=null)
                throw new Exception("User already logged in.");
            loggedClients.put(user, client);
        }else
            throw new Exception("Authentication failed.s");
    }

    @Override
    public void logout(User user, IObserver client) throws Exception {
        IObserver localClient=loggedClients.remove(user);
        if (localClient==null)
            throw new Exception("User "+user.getUser()+" is not logged in.");
    }

    @Override
    public void addTicket(Ticket ticket) throws Exception {
        String nameClient = ticket.getNameClient();
        String otherPeople = ticket.getOtherPeople();
        String adressCLient = ticket.getAdressClient();
        Integer seats = ticket.getSeats();
        Integer flightID = ticket.getFlightID();

        Ticket ticket1 = new Ticket(nameClient, otherPeople, adressCLient, seats, flightID);
        this.repositoryTicket.add(ticket1);
        notifyAddTicket(ticket);
    }

    private void notifyAddTicket(Ticket ticket) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(defaultThreadsNo);

        for(IObserver observer: loggedClients.values()) {
            if(observer != null)
                executorService.execute(() -> {
                    try {
                        observer.addedTicket(ticket);
                    } catch (Exception | RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }
    }

    @Override
    public List<Flight> searchFlightDestinationAndData(String destination, String data) throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime;
        if(!data.equals("")) {
            System.out.println("Da");
            String destinationFormat = data+"T00:00";
            dateTime = LocalDateTime.parse(destinationFormat, formatter);
            return this.repositoryFlights.searchAfterDestinationDate(destination, dateTime);
        }
        else
            return this.repositoryFlights.searchAfterDestinationDate(destination, null);
    }

    @Override
    public Flight[] getFlightsDB() {
        Collection<Flight> flights = this.repositoryFlights.getAll();
        int size = 0;
        if (flights != null)
            size = flights.size();
        return flights.toArray(new Flight[size]);

    }

    @Override
    public List<Ticket> getTicketsDB() {
        return this.repositoryTicket.getAll();
    }
}
