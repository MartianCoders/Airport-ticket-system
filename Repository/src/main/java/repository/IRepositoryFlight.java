package repository;

import model.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface IRepositoryFlight extends IRepository<Integer, Flight> {
    List<Flight> searchAfterDestinationDate(String destination, LocalDateTime data);
}
