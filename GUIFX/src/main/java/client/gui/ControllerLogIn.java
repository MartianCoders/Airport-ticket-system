package client.gui;

import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import service.IObserver;
import service.IServices;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ControllerLogIn {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField inputUser;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Button buttonLogIn;

    @FXML
    private Text resultInput;

    private IServices service;

    private ControllerMain mainCtrl;

    Parent mainParent;

    public void setMainCtrl(ControllerMain mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void setMainParent(Parent mainParent) {
        this.mainParent = mainParent;
    }

    public void setService(IServices service) {
        this.service = service;
    }

    public IServices getService() {
        return this.service;
    }

    @FXML
    void clickButtonLogIn(ActionEvent event) {
        resultInput.setText("");
        String user = inputUser.getText();
        String password = inputPassword.getText();
        LocalDateTime localDateTime = LocalDateTime.now();
        User userCtrl = new User(user, password, localDateTime);

        inputUser.clear();
        inputPassword.clear();

        try {
            service.login(userCtrl, mainCtrl);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    // ADD
                    System.exit(0);
                }
            });

            stage.show();
            mainCtrl.setTickets();
            mainCtrl.setFlights();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /*
        if(this.service.checkLogInData(user, password)) {
            Stage stageLogIn = (Stage) buttonLogIn.getScene().getWindow();
            stageLogIn.close();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/guiconfig.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                ControllerMain controllerMain = fxmlLoader.getController();
                Service service = new Service();
                controllerMain.setService(service);
                Scene scene = new Scene(root1);

                Stage stage = new Stage();
                stage.setTitle("Flights Manager");

                stage.setScene(scene);
                stage.show();

                Node divider = scene.lookup(".split-pane-divider");
                if (divider != null) {
                    divider.setStyle("-fx-background-color: transparent;");
                    divider.setMouseTransparent(true);

                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            resultInput.setText("! User/Parola gresita !");
        }

         */

    }

    @FXML
    void initialize() {
        assert inputUser != null : "fx:id=\"inputUser\" was not injected: check your FXML file 'login.fxml'.";
        assert inputPassword != null : "fx:id=\"inputPassword\" was not injected: check your FXML file 'login.fxml'.";
        assert buttonLogIn != null : "fx:id=\"buttonLogIn\" was not injected: check your FXML file 'login.fxml'.";

    }
}
