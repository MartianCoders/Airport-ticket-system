package repository;
import model.Flight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepositoryFlight implements IRepositoryFlight {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();




    public RepositoryFlight(Properties prop) {
        System.out.println();
        logger.info("\u001B[34m> Initializing repository.RepositoryFlight <\u001B[0m");
        this.dbUtils = new JdbcUtils(prop);
    }

    @Override
    public void add(Flight entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO Flight (destination, dateTime, airport, seats) VALUES (?, ?, ?, ?)");
            try {
                preparedStatement.setString(1, entity.getDestination());
                preparedStatement.setString(2, entity.getTimeOfTheFlight().toString());
                preparedStatement.setString(3, entity.getAirport());
                preparedStatement.setInt(4, entity.getSeats());

                int result = preparedStatement.executeUpdate();
                logger.trace("New INSERT {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error add FlightDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error add FlightDB " + throwable);
        }
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM Flight WHERE flightID=(?)");
            try {
                preparedStatement.setString(1, integer.toString());

                int result = preparedStatement.executeUpdate();
                logger.trace("New DELETE {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error delete FlightDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error delete FlightDB " + throwable);
        }
    }

    @Override
    public void deleteAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM Flight");
            try {
                int result = preparedStatement.executeUpdate();
                logger.trace("New DELETE {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error delete FlightDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error delete FlightDB " + throwable);
        }
    }

    @Override
    public void update(Integer integer, Flight entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE Flight SET flightID=(?), destination=(?), dateTime=(?), airport=(?), seats=(?) WHERE flightID=(?)");
            try {
                preparedStatement.setString(6, integer.toString());

                preparedStatement.setInt(1, integer);
                preparedStatement.setString(2, entity.getDestination());
                preparedStatement.setString(3, entity.getTimeOfTheFlight().toString());
                preparedStatement.setString(4, entity.getAirport());
                preparedStatement.setInt(5, entity.getSeats());
                int result = preparedStatement.executeUpdate();
                logger.trace("New INSERT {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error add FlightDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error add FlightDB " + throwable);
        }
    }

    @Override
    public Flight findEntity(Integer integer) {
        Flight finalFlight = null;
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT * FROM Flight WHERE flightID=(?)");
            preparedStatement.setString(1, integer.toString());
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    int flightID = resultSet.getInt("flightID");
                    String destination = resultSet.getString("destination");
                    String localDateTime = resultSet.getString("dateTime");
                    String airport = resultSet.getString("airport");
                    Integer seats = resultSet.getInt("seats");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    LocalDateTime timeOfFlight = LocalDateTime.parse(localDateTime, formatter);

                    Flight flight = new Flight(destination, timeOfFlight, airport, seats);
                    flight.setID(flightID);
                    finalFlight = flight;
                }
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error findID FlightDB " + throwable);
            }

        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error findID FlightDB " + throwable);
        }
        logger.traceExit(finalFlight);
        return finalFlight;
    }

    @Override
    public List<Flight> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Flight> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT * FROM Flight");
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    int flightID = resultSet.getInt("flightID");
                    String destination = resultSet.getString("destination");
                    String localDateTime = resultSet.getString("dateTime");
                    String airport = resultSet.getString("airport");
                    Integer seats = resultSet.getInt("seats");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    LocalDateTime timeOfFlight = LocalDateTime.parse(localDateTime, formatter);

                    Flight flight = new Flight(destination, timeOfFlight, airport, seats);
                    flight.setID(flightID);
                    list.add(flight);
                }
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error FlightDB " + throwable);
            }

        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error FlightDB " + throwable);
        }

        logger.traceExit(list);
        return list;
    }

    @Override
    public List<Flight> searchAfterDestinationDate(String destination, LocalDateTime data) {
        List<Flight> list = this.getAll();
        List<Flight> finalList = new ArrayList<>();

        for(Flight flight: list) {
            if(data != null) {
                if (flight.getDestination().equals(destination) && flight.getTimeOfTheFlight().getYear() == data.getYear() && flight.getTimeOfTheFlight().getMonth() == data.getMonth() && flight.getTimeOfTheFlight().getDayOfMonth() == data.getDayOfMonth())
                    finalList.add(flight);
            }
            else {
                if (flight.getDestination().equals(destination))
                    finalList.add(flight);
            }

        }
        return finalList;
    }
}
