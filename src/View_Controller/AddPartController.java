/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controller class for adding new parts to inventory.
 * @author indya
 */
public class AddPartController implements Initializable {
    /**
     * Text field for part ID.
     */
    @FXML
    private TextField partId;  
    /**
     * Text field for part price.
     */
    @FXML
    private TextField price;
    /**
     * Text field for part inventory level.
     */
    @FXML
    private TextField inventory;
    /**
     * Text field for part name.
     */
    @FXML
    private TextField name;
    /**
     * Text field for part minimum inventory level.
     */
    @FXML
    private TextField min;
    /**
     * Label for the Company Name/Machine ID text field.
     */
    @FXML
    private Label companyLabel;
    /**
     * Text field for Company Name/Machine ID input.
     */
    @FXML
    private TextField company;
    /**
     * Text field for part maximum inventory level.
     */
    @FXML
    private TextField max;
    /**
     * Radio button for in house parts.
     */
    @FXML
    private RadioButton inHouseRadioButton;
    /**
     * Toggle group for in house/outsourced radio buttons.
     */
    @FXML
    private ToggleGroup radioButtons;
    /**
     * Radio button for outsourced parts.
     */
    @FXML
    private RadioButton outsourcedRadioButton;
    
    
    /**
     * Used to assign unique IDs to parts.
     */
    private static int id;
    
    
    /**
     * Used to generate unique IDs for parts. 
     */
    public static int generatePartId() {
    return ++id;
    }
    
    
    /**
     * Checks that the minimum and maximum values are valid inputs. 
     */
    private boolean minMax (int min, int max) {
        boolean inputValid = true;
        if (min < 0 || min >= max) {
            inputValid = false;
            alerts(1); 
        }
        return inputValid;
    }
    
    
    /**
     * Checks that the value entered for inventory is within the valid range. 
     */
    private boolean inventoryInput (int min, int max, int inv) {
        boolean inputValid = true;
        if (inv < min || inv > max) {
            inputValid = false;
            alerts(2);
        }
        return inputValid;
    }
    
    
    /**
     *  Used for displaying error messages for invalid user input/actions.
     */
    private void alerts (int alertCase) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        switch (alertCase) {
            case 1:
                alert.setTitle("Error adding the part");
                alert.setHeaderText("The part could not be added.");
                alert.setContentText("The minimum value cannot be greater than or equal to the maximum value. The minimum value cannot be less than zero.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error adding part");
                alert.setHeaderText("The part could not be added.");
                alert.setContentText("Inventory level cannot be less than the minimum value or greater than the maximum value.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error adding part");
                alert.setHeaderText("Invalid input value for Machine ID");
                alert.setContentText("Machine ID must be an integer value.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error adding part");
                alert.setHeaderText("One or more fields have been left empty");
                alert.setContentText("All fields must have a valid input value.");
                alert.showAndWait();
                break;
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        generatePartId();
    }    
    
    /**
     * Sets the label of company text field to Machine ID when the radio button is selected. 
     */
    @FXML
    private void inHouseButtonHandler(ActionEvent event) {
        companyLabel.setText("Machine ID");
    }
    
    /**
     * Sets the label of company text field to company name when the radio button is selected.
     */
    @FXML
    private void outsourcedButtonHandler(ActionEvent event) {
        companyLabel.setText("Company Name");
    }
    
    /**
     * Saves new part to inventory. 
     */
    @FXML
    private void saveNewPartHandler(ActionEvent event) throws IOException {
        try {
        String partName = name.getText();
        double partPrice = Double.parseDouble(price.getText());
        int partInventory = Integer.parseInt(inventory.getText());
        int minimum = Integer.parseInt(min.getText());
        int maximum = Integer.parseInt(max.getText());
        boolean addedPart = false;
        
        if (minMax(minimum, maximum) && inventoryInput(minimum, maximum, partInventory)) {
        if (radioButtons.getSelectedToggle() == inHouseRadioButton) {
            try {
            Inventory.addPart(new InHouse(id, partName, partPrice, partInventory, minimum, maximum, Integer.parseInt(company.getText())));
            addedPart = true;
            } catch (Exception e) {
                alerts(3);
            }
        } else {
            Inventory.addPart(new Outsourced(id, partName, partPrice, partInventory, minimum, maximum, company.getText()));
            addedPart = true;
        }
        if (addedPart) {
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
        
            stage.setScene(scene);
        
            stage.show();
        }
        } 
                }catch (Exception e) {
                alerts(4);
        }
    }
    
    /**
     * Returns user to the home screen when cancel button is pressed. 
     */
    @FXML
    private void cancelHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        
        stage.setScene(scene);
        
        stage.show();
    }
    
}
