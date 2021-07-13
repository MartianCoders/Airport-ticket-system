package service;
import model.Ticket;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IObserver extends Remote {
     void addedTicket(Ticket ticket) throws Exception, RemoteException;
}
