package repository;

import model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class RepositoryTicket implements IRepositoryTicket {
    private final repository.JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public RepositoryTicket(Properties properties) {
        System.out.println();
        logger.info("\u001B[34m> Initializing repository.RepositoryTicket <\u001B[0m");
        this.dbUtils = new repository.JdbcUtils(properties);
    }

    @Override
    public void add(Ticket entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO Ticket (nameClient, otherPeople, adressClient, seats, flightID) VALUES (?, ?, ?, ?, ?)");
            try {
                preparedStatement.setString(1, entity.getNameClient());
                preparedStatement.setString(2, entity.getOtherPeople());
                preparedStatement.setString(3, entity.getAdressClient());
                preparedStatement.setInt(4, entity.getSeats());
                preparedStatement.setInt(5, entity.getFlightID());

                int result = preparedStatement.executeUpdate();
                logger.trace("New INSERT {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error add TicketDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error add TicketDB " + throwable);
        }
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM Ticket WHERE ticketID=(?)");
            try {
                preparedStatement.setInt(1, integer);
                int result = preparedStatement.executeUpdate();
                logger.trace("New DELETE {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error delete TicketDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error delete TicketDB " + throwable);
        }
    }

    @Override
    public void deleteAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM Ticket");
            try {
                int result = preparedStatement.executeUpdate();
                logger.trace("New DELETEALL {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error deleteAll TicketDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error deleteAll TicketDB " + throwable);
        }
    }

    @Override
    public void update(Integer integer, Ticket entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE Ticket SET ticketID=(?), nameClient=(?), otherPeople=(?), adressClient=(?), seats=(?), flightID=(?) WHERE ticketID=(?)");
            try {
                preparedStatement.setInt(1, integer);
                preparedStatement.setString(2, entity.getNameClient());
                preparedStatement.setString(3, entity.getOtherPeople());
                preparedStatement.setString(4, entity.getAdressClient());
                preparedStatement.setInt(5, entity.getSeats());
                preparedStatement.setInt(6, entity.getFlightID());
                preparedStatement.setInt(7, integer);

                int result = preparedStatement.executeUpdate();
                logger.trace("New UPDATE {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error update TicketID " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error update TicketID " + throwable);
        }
    }

    @Override
    public Ticket findEntity(Integer integer) {
        Ticket finalTicket = null;
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT * FROM Ticket WHERE ticketID=(?)");
            try {
                preparedStatement.setInt(1, integer);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int ticketID = resultSet.getInt("ticketID");
                    String nameClient = resultSet.getString("nameClient");
                    String otherPeople = resultSet.getString("otherPeople");
                    String adressClient = resultSet.getString("adressClient");
                    int seats = resultSet.getInt("seats");
                    int flightID = resultSet.getInt("flightID");

                    Ticket ticket = new Ticket(nameClient, otherPeople, adressClient, seats, flightID);
                    ticket.setID(ticketID);
                    finalTicket = ticket;
                    logger.trace("Found TICKET {}", resultSet);
                }
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error findEntity TicketID " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error findEntity TicketID " + throwable);
        }

        return finalTicket;
    }

    @Override
    public List<Ticket> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Ticket> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT * FROM Ticket");
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int ticketID = resultSet.getInt("ticketID");
                    String nameClient = resultSet.getString("nameClient");
                    String otherPeople = resultSet.getString("otherPeople");
                    String adressClient = resultSet.getString("adressClient");
                    int seats = resultSet.getInt("seats");
                    int flightID = resultSet.getInt("flightID");

                    Ticket ticket = new Ticket(nameClient, otherPeople, adressClient, seats, flightID);
                    ticket.setID(ticketID);
                    list.add(ticket);
                }
                logger.trace("New getALL {}", resultSet);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error getALL TicketDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error getALL TicketDB " + throwable);
        }
        logger.traceExit(list);
        return list;
    }
}
