package repository;

import model.User;
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


public class RepositoryUser implements IRepositoryUser {

    private final repository.JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();


    public RepositoryUser(Properties prop) {
        System.err.println();
        logger.info("\u001B[34m> Initializing repository.RepositoryUser <\u001B[0m");

        this.dbUtils = new repository.JdbcUtils(prop);
    }

    @Override
    public void add(User entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO User (user, password, firstLogin) VALUES (?, ?, ?)");
            try {
                preparedStatement.setString(1, entity.getUser());
                preparedStatement.setString(2, entity.getPassword());
                preparedStatement.setString(3, entity.getFirstLogin().toString());

                int result = preparedStatement.executeUpdate();
                logger.trace("New INSERT {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error add UserDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error add UserDB " + throwable);
        }
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM User WHERE userID=(?)");
            preparedStatement.setString(1, integer.toString());
            try {
                int result = preparedStatement.executeUpdate();
                logger.trace("New DELETE {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error deleteID userDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error deleteID userDB " + throwable);
        }

    }

    @Override
    public void deleteAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM User");
            try {
                int result = preparedStatement.executeUpdate();
                logger.trace("new DELETE " + result);
            }catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error deleteAll userDB " + throwable);
            }
        }catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error deleteAll userDB " + throwable);
        }

    }

    @Override
    public void update(Integer integer, User entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE User SET userID=(?), user=(?), password=(?), firstLogin=(?) WHERE userID=(?)");
            try {
                preparedStatement.setString(5, integer.toString());

                preparedStatement.setInt(1, integer);
                preparedStatement.setString(2, entity.getUser());
                preparedStatement.setString(3, entity.getPassword());
                preparedStatement.setString(4, entity.getFirstLogin().toString());

                int result = preparedStatement.executeUpdate();
                logger.trace("New UPDATE {}", result);
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error update userDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error update userDB " + throwable);
        }
    }

    @Override
    public User findEntity(Integer integer) {
        return null;
    }

    @Override
    public List<User> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<User> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT * FROM User");
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    int userID = resultSet.getInt("userID");
                    String user = resultSet.getString("user");
                    String password = resultSet.getString("password");
                    String localDateTime = resultSet.getString("firstLogin");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    LocalDateTime firstLogin = LocalDateTime.parse(localDateTime, formatter);

                    User user1 = new User(user, password, firstLogin);
                    user1.setID(userID);

                    list.add(user1);
                }
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error getAll UserDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error getAll UserDB " + throwable);
        }

        logger.traceExit(list);
        return list;
    }

    @Override
    public User findEntityUser(String user) {
        User finalUser = null;
        System.out.println("search" + user);
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT * FROM User WHERE user=(?)");
            preparedStatement.setString(1, user);
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                logger.trace("Found User " + resultSet);
                while(resultSet.next()) {
                    int userID = resultSet.getInt("userID");
                    String password = resultSet.getString("password");
                    String userD = resultSet.getString("user");
                    String localDateTime = resultSet.getString("firstLogin");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    LocalDateTime firstLogin = LocalDateTime.parse(localDateTime, formatter);

                    User user1 = new User(userD, password, firstLogin);
                    user1.setID(userID);
                    finalUser = user1;
                }
            } catch (SQLException throwable) {
                logger.error(throwable);
                System.err.println("Error findEntity userDB " + throwable);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error findEntity userDB " + throwable);
        }

        return finalUser;
    }
}
