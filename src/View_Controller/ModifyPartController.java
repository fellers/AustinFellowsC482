/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import static Model.Inventory.getAllParts;
import static Model.Inventory.updatePart;
import Model.Outsourced;
import Model.Part;
import static View_Controller.MainScreenController.getSelectedPart;
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
 * Controller class for modifying an existing part in inventory.
 * @author indya
 */
public class ModifyPartController implements Initializable {
    /**
     * Label for Company Name/Machine ID text field.
     */
    @FXML
    private Label companyLabel;
    /**
     * Text field for part price.
     */
    @FXML
    private TextField price;
    /**
     * Text field for Company Name/Machine ID.
     */
    @FXML
    private TextField company;
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
     * Text field for part id.
     */
    @FXML
    private TextField id;
    /**
     * Text field for minimum part inventory level.
     */
    @FXML
    private TextField min;
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
     * Text field for maximum part inventory level.
     */
    @FXML
    private TextField max;
    /**
     * Radio button for outsourced parts.
     */
    @FXML
    private RadioButton outsourcedRadioButton;
    
    
    /**
     * Gets the index of the part to be modified. 
     */
    private int getIndex(Part selectedPart) {
        int index = -1;
        for (Part p: getAllParts()) {
            index++;
            if (p.getId() == selectedPart.getId()) {
                return index;
            }
        }
        return -1;
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
     * Checks that the value entered for inventory is in the valid range. 
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
     * Used for displaying error messages for invalid user input/actions. 
     */
    private void alerts (int alertCase) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        switch (alertCase) {
            case 1:
                alert.setTitle("Error modifying the part");
                alert.setHeaderText("The part could not be modified.");
                alert.setContentText("The minimum value cannot be greater than or equal to the maximum value. The minimum value cannot be less than zero.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error modifying part");
                alert.setHeaderText("The part could not be modified.");
                alert.setContentText("Inventory level cannot be less than the minimum value or greater than the maximum value.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error modifying part");
                alert.setHeaderText("Invalid input value for Machine ID");
                alert.setContentText("Machine ID must be an integer value.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error modifying part");
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
   
        Part selectedPart = getSelectedPart();
        int selectedPartId = selectedPart.getId();
        id.setText(Integer.toString(selectedPartId));
        name.setText(selectedPart.getName());
        inventory.setText(Integer.toString(selectedPart.getStock()));
        price.setText(Double.toString(selectedPart.getPrice()));
        max.setText(Integer.toString(selectedPart.getMax()));
        min.setText(Integer.toString(selectedPart.getMin()));
        if (selectedPart instanceof InHouse) {
            radioButtons.selectToggle(inHouseRadioButton);
            company.setText(Integer.toString(((InHouse) selectedPart).getMachineId()));
            companyLabel.setText("Machine ID");
        } else {
            radioButtons.selectToggle(outsourcedRadioButton);
            company.setText(((Outsourced) selectedPart).getCompanyName());
            companyLabel.setText("Company Name");
        }
    }    
    
    /**
     * Sets the label of the company text field to Company Name when the radio button is selected. 
     */
    @FXML
    private void inHouseButtonHandler(ActionEvent event) {
        companyLabel.setText("Machine ID");
    }
    
    /**
     * Sets the label of the company text field to Machine ID when the radio button is selected. 
     */
    @FXML
    private void outsourcedButtonHandler(ActionEvent event) {
        companyLabel.setText("Company Name");
    }
    
    /**
     * Saves a new part to inventory.
     */
    @FXML
    private void saveNewPartHandler (ActionEvent event) throws IOException {
        try {
        Part selectedPart = getSelectedPart();
        int index = getIndex(selectedPart);
        String partName = name.getText();
        int partInv = Integer.parseInt(inventory.getText());
        double partPrice = Double.parseDouble(price.getText());
        int partMin = Integer.parseInt(min.getText());
        int partMax = Integer.parseInt(max.getText());
        boolean modifiedPart = false;
        
        if (minMax(partMin, partMax) && inventoryInput(partMin, partMax, partInv)) {
        if (selectedPart instanceof InHouse) {
            try {
            selectedPart.setName(partName);
            selectedPart.setPrice(partPrice);
            selectedPart.setStock(partInv);
            selectedPart.setMin(partMin);
            selectedPart.setMax(partMax);
            ((InHouse) selectedPart).setMachineId(Integer.parseInt(company.getText()));
            updatePart(index, selectedPart);
            modifiedPart = true;
            } catch (Exception e) {
                alerts(3);
            }
        } else if (selectedPart instanceof Outsourced) {
            selectedPart.setName(partName);
            selectedPart.setPrice(partPrice);
            selectedPart.setStock(partInv);
            selectedPart.setMin(partMin);
            selectedPart.setMax(partMax);
            ((Outsourced) selectedPart).setCompanyName(company.getText());
            updatePart(index, selectedPart);
            modifiedPart = true;
        }
        
        Inventory.updatePart(index, selectedPart);
        
        if (modifiedPart) {
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
        
            stage.setScene(scene);
        
            stage.show();
    }
        }
        } catch (Exception e) {
            alerts(4);
        }
    }
    
    /**
     * Returns the user to the main screen when cancel button is pressed. 
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
