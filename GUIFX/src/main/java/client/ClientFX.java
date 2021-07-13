package client;

import client.gui.ControllerLogIn;
import client.gui.ControllerMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.IServices;


public class ClientFX extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("START");
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IServices server=(IServices)factory.getBean("service");
        System.out.println("Obtained a reference to remote chat server");

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("/client/login.fxml"));
        loader.setLocation(getClass().getResource("/client/login.fxml"));
        System.out.println(loader.getLocation());
        Parent root=loader.load();


        ControllerLogIn ctrl =
                loader.<ControllerLogIn>getController();
        ctrl.setService(server);


        FXMLLoader mloader = new FXMLLoader(
                getClass().getClassLoader().getResource("/client/guiconfig.fxml"));
        mloader.setLocation(getClass().getResource("/client/guiconfig.fxml"));
        System.out.println(mloader.getLocation());
        Parent mroot=mloader.load();





        ControllerMain mainCtrl =
                mloader.<ControllerMain>getController();
        mainCtrl.setServer(server);

        ctrl.setMainCtrl(mainCtrl);
        ctrl.setMainParent(mroot);

        primaryStage.setTitle("TEST");
        primaryStage.setScene(new Scene(root, 350.0, 250.0));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
