/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Class that contains the main method to start the application.
 * @author Austin Fellows
 * <p>FUTURE ENHANCEMENT: In the future I would like to change the algorithm I
 * used to implement the search functionality of this application. It currently
 * iterates sequentially through a list one item at a time. This is a very
 * inefficient way of searching for items in a large list. I would like to
 * implement a binary search algorithm in the future to enhance this feature.</p>
 * </b>
 * The javadocs files are located at AustinFellowsC482/javadoc
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
    */
    public static void main(String[] args) {
        // starts the JavaFX application
        launch(args);
    }

    /**
     * Starts the main screen.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }
    
}
