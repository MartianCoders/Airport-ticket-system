package client.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Flight;
import model.Ticket;
import service.Exception;
import service.IObserver;
import service.IServices;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerMain extends UnicastRemoteObject implements Initializable, IObserver, Serializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonFlight;

    @FXML
    private Button buttonTickets;

    @FXML
    private Button buttonSearchFlight;

    @FXML
    private AnchorPane paneFlights;

    @FXML
    private AnchorPane paneTickets;

    @FXML
    private TextField inputDestinatie;

    @FXML
    private TextField inputData;

    @FXML
    private TableView<Flight> listFlights;

    @FXML TableColumn<Flight, Integer> flightID;
    @FXML TableColumn<Flight, String> destination;
    @FXML TableColumn<Flight, LocalDateTime> date;
    @FXML TableColumn<Flight, String> airport;
    @FXML TableColumn<Flight, Integer> seats;

    @FXML
    private TableView<Ticket> listTickets;

    @FXML TableColumn<Flight, Integer> ticketID;
    @FXML TableColumn<Flight, String> name;
    @FXML TableColumn<Flight, String> people;
    @FXML TableColumn<Flight, String> adress;
    @FXML TableColumn<Flight, Integer> seatsTicket;
    @FXML TableColumn<Flight, Integer> flightIDTickets;
    @FXML Button buttonAddTicket;
    @FXML TextField flightIDTextLabel;
    @FXML
    private TextField inputNameTicket;

    @FXML
    private TextField inputPeopleTicket;

    @FXML
    private TextField inputAdressTicket;

    @FXML
    private TextField inputSeatsTicket;

    private IServices server;

    public ControllerMain() throws RemoteException {

    }

    public ControllerMain(IServices server) throws RemoteException {
        super();
        System.out.println("ControllerMain build");
        this.server = server;
    }

    @FXML
    void mouseClickRow(MouseEvent event) {
        int flightID = listFlights.getSelectionModel().getSelectedItem().getFlightID();

        flightIDTextLabel.setText(String.valueOf(flightID));
    }

    @FXML
    void mouseClickAddTicket(ActionEvent event) throws Exception {
        String nameTicket = inputNameTicket.getText();
        String peopleTicket = inputPeopleTicket.getText();
        String adressTicket = inputAdressTicket.getText();
        int seats = Integer.parseInt(inputSeatsTicket.getText());
        int flightID = listFlights.getSelectionModel().getSelectedItem().getFlightID();
        this.server.addTicket(new Ticket(nameTicket, peopleTicket, adressTicket, seats, flightID));

        inputNameTicket.clear();
        inputPeopleTicket.clear();
        inputAdressTicket.clear();
        inputSeatsTicket.clear();


    }

    public void setFlights() throws Exception {
        Flight[] flights = server.getFlightsDB();
        flightID.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightID"));
        destination.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
        date.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("timeOfTheFlight"));
        airport.setCellValueFactory(new PropertyValueFactory<Flight, String>("airport"));
        seats.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));

        listFlights.getItems().addAll(flights);
    }

    public void setTickets() throws java.lang.Exception {
        ticketID.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("ticketID"));
        name.setCellValueFactory(new PropertyValueFactory<Flight, String>("nameClient"));
        people.setCellValueFactory(new PropertyValueFactory<Flight, String>("otherPeople"));
        adress.setCellValueFactory(new PropertyValueFactory<Flight, String>("adressClient"));
        seatsTicket.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));
        flightIDTickets.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightID"));

        listTickets.getItems().clear();
        listTickets.getItems().addAll(server.getTicketsDB());
    }


    @FXML
    void mouseClickFlight(ActionEvent event) throws Exception {
        this.setVisibilityPanes(true, false);

        flightID.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightID"));
        destination.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
        date.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("timeOfTheFlight"));
        airport.setCellValueFactory(new PropertyValueFactory<Flight, String>("airport"));
        seats.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));

        listFlights.getItems().clear();
        listFlights.getItems().addAll(server.getFlightsDB());
    }

    @FXML
    void mouseExitFlight(MouseEvent event) {
        buttonFlight.setStyle("-fx-background-color: transparent; ");
    }

    @FXML
    void mouseHoverFlight(MouseEvent event) {
        buttonFlight.setStyle("-fx-background-color: #14248A; ");
    }

    @FXML
    void mouseClickTickets(ActionEvent event) throws Exception {
        this.setVisibilityPanes(false, true);

        ticketID.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("ticketID"));
        name.setCellValueFactory(new PropertyValueFactory<Flight, String>("nameClient"));
        people.setCellValueFactory(new PropertyValueFactory<Flight, String>("otherPeople"));
        adress.setCellValueFactory(new PropertyValueFactory<Flight, String>("adressClient"));
        seatsTicket.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));
        flightIDTickets.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightID"));

        listTickets.getItems().clear();
        listTickets.getItems().addAll(server.getTicketsDB());
    }

    @FXML
    void mouseExitTickets(MouseEvent event) {
        buttonTickets.setStyle("-fx-background-color: transparent; ");
    }

    @FXML
    void mouseHoverTickets(MouseEvent event) {
        buttonTickets.setStyle("-fx-background-color: #14248A; ");
    }

    void setVisibilityPanes(boolean p1, boolean p2) {
        if(p1) {
            paneFlights.setVisible(true);
            paneTickets.setVisible(false);
        }
        else if(p2){
            paneFlights.setVisible(false);
            paneTickets.setVisible(true);
        }
        else {
            paneFlights.setVisible(false);
            paneTickets.setVisible(false);
        }
    }

    @FXML

    void mouseClickSearchFlight(ActionEvent event) throws Exception {

        String inputDestinatieText = inputDestinatie.getText();
        String inputDataText = inputData.getText();
        List<Flight> list = server.searchFlightDestinationAndData(inputDestinatieText, inputDataText);

        if(list != null) {
            flightID.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightID"));
            destination.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
            date.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("timeOfTheFlight"));
            airport.setCellValueFactory(new PropertyValueFactory<Flight, String>("airport"));
            seats.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));

            listFlights.getItems().clear();
            listFlights.getItems().addAll(list);
        }
        inputDestinatie.clear();
        inputData.clear();


    }



    @FXML
    void mouseHoverSearchFlight(MouseEvent event) {

    }

    @FXML
    void mouseExitSearchFlight(MouseEvent event) {

    }

    public void setServer(IServices server) {
        this.server = server;
    }

    @FXML
    void initialize() {
        assert buttonFlight != null : "fx:id=\"buttonFlight\" was not injected: check your FXML file 'guiconfig.fxml'.";
        assert buttonTickets != null : "fx:id=\"buttonTickets\" was not injected: check your FXML file 'guiconfig.fxml'.";
        this.setVisibilityPanes(false, false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void addedTicket(Ticket ticket) throws Exception {
        Platform.runLater(() -> listTickets.getItems().add(ticket));
    }
}
