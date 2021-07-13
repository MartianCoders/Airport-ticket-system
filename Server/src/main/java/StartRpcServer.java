import repository.RepositoryFlight;
import repository.RepositoryTicket;
import repository.RepositoryUser;
import server.Service;
import service.IServices;


import java.io.IOException;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {

        System.out.println("Start server");
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");

        /*
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        RepositoryUser userRepo=new RepositoryUser(serverProps);
        RepositoryFlight flightRepo = new RepositoryFlight(serverProps);
        RepositoryTicket ticketRepo = new RepositoryTicket(serverProps);
        IServices serverImpl=new Service(userRepo, flightRepo, ticketRepo);
        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new RPCConcurrentServer(serverPort, serverImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }

         */
    }
}
