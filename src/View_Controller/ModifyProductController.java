/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import static Model.Inventory.updateProduct;
import Model.Part;
import Model.Product;
import static View_Controller.MainScreenController.getSelectedProduct;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controller class for modifying an existing product in inventory.
 * @author indya
 */
public class ModifyProductController implements Initializable {
    /**
     * Text field for product ID.
     */
    @FXML
    private TextField id;
    /**
     * Text field for product name.
     */
    @FXML
    private TextField name;
    /**
     * Text field for product inventory level.
     */
    @FXML
    private TextField inventory;
    /**
     * Text field for product price.
     */
    @FXML
    private TextField price;
    /**
     * Text field for product's maximum allowed inventory level.
     */
    @FXML
    private TextField max;
    /**
     * Text field for product's minimum allowed inventory level.
     */
    @FXML
    private TextField min;
    /**
     * Text field for searching the all parts table.
     */
    @FXML
    private TextField search;
    /**
     * Table view that contains all parts in inventory.
     */
    @FXML
    private TableView<Part> allPartsTable;
    /**
     * Part ID column in the all parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partId;
    /**
     * Part name column in the all parts table.
     */
    @FXML
    private TableColumn<Part, String> partName;
    /**
     * Part inventory level column in the all parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partInventory;
    /**
     * Part price column in the all parts table.
     */
    @FXML
    private TableColumn<Part, Double> partPrice;
    /**
     * Table view that contains a product's associated parts.
     */
    @FXML
    private TableView<Part> associatedPartsTable;
    /**
     * Part ID column in the associated parts table.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartId;
    /**
     * Part name column in the associated parts table.
     */
    @FXML
    private TableColumn<Part, String> associatedPartName;
    /**
     * Part inventory level column in the associated parts table.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInventory;
    /**
     * Part price column in the associated parts table.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPrice;
    
    
    /**
     * Contains associated parts before they are saved to a product object.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    
    /**
     * Gets the index of the product that was selected for modification. 
     */
    private int getIndex(Product selectedProduct) {
        int index = -1;
        for (Product p: getAllProducts()) {
            index++;
            if (p.getId() == selectedProduct.getId()) {
                return index;
            }
        }
        return -1;
    }
    
    
    /**
     * Checks that the values for min and max are valid inputs. 
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
     * Checks that the value for inventory level is a valid input. 
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
                alert.setTitle("Error modifying the product");
                alert.setHeaderText("The product could not be modified.");
                alert.setContentText("The minimum value cannot be greater than or equal to the maximum value. The minimum value cannot be less than zero.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error modifying product");
                alert.setHeaderText("The product could not be modified.");
                alert.setContentText("Inventory level cannot be less than the minimum value or greater than the maximum value.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error modifying product");
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
      
        Product selectedProduct = getSelectedProduct();
        int selectedProductId = selectedProduct.getId();
        id.setText(Integer.toString(selectedProductId));
        name.setText(selectedProduct.getName());
        inventory.setText(Integer.toString(selectedProduct.getStock()));
        price.setText(Double.toString(selectedProduct.getPrice()));
        max.setText(Integer.toString(selectedProduct.getMax()));
        min.setText(Integer.toString(selectedProduct.getMin()));
        for (Part p : selectedProduct.getAllAssociatedParts()) {
            associatedParts.add(p);
        }
        allPartsTable.setItems(Inventory.getAllParts());
        associatedPartsTable.setItems(associatedParts);
        
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        associatedPartsTable.setItems(associatedParts);
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    /**
     * Returns user to the main screen when cancel button is pressed. 
     */
    @FXML
    private void cancelButtonHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        
        stage.setScene(scene);
        
        stage.show();
    }
    
    /**
     * Searches for parts in the all parts table. 
     */
    @FXML
    private void searchHandler(ActionEvent event) {
        String searchId = search.getText();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        if (searchId.equals("")) {
            allPartsTable.setItems(getAllParts());
        }
        else {
            boolean searchResult = false;
            try {
            Part part = Inventory.lookupPart(Integer.parseInt(searchId));
            if (part != null) {
                partsFound.add(part);
                allPartsTable.setItems(partsFound);
            }
            else {
                allPartsTable.setItems(getAllParts());
            }
        }
        catch (NumberFormatException e) {
            for (Part p : getAllParts()) {
            if (p.getName().contains(searchId)) {
            searchResult = true;
            partsFound.add(p);
                }
            allPartsTable.setItems(partsFound);
            }
            if (searchResult == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No parts found");
                alert.setContentText("Please try searching again");
                alert.showAndWait();
            }    
        }
    }
    }
    
    /**
     * Adds an associated part to the associated parts observable list. 
     */
    @FXML
    private void addAssociatedPartHandler(ActionEvent event) {
        Part selectedAssociatedPart = allPartsTable.getSelectionModel().getSelectedItem();
        if (selectedAssociatedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding associated part");
            alert.setHeaderText("No part was selected.");
            alert.showAndWait();
        } else {
        associatedParts.add(selectedAssociatedPart);
        
        }
    }
    /**
     * Removes an associated part from the associated parts observable list. 
     */
    @FXML
    private void removeAssociatedPartHandler(ActionEvent event) {
        Part p = associatedPartsTable.getSelectionModel().getSelectedItem();
        
        if (p == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Cannot remove part");
            nullAlert.setHeaderText("No part selected");
            nullAlert.setContentText("You must select a part in order to remove it");
            nullAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Associated Part Confirmation");
            alert.setHeaderText("Are you sure you would like to remove this associated part? This action cannot be undone.");
            alert.setContentText("Press OK if you would like to remove this associated part, otherwise press cancel");
            alert.showAndWait();
        
            if (alert.getResult() == ButtonType.OK) {
                associatedParts.remove(p);
            } else {
                alert.close();
            }
        }
    }
    
    /**
     * Saves a product and its associated parts list to inventory. 
     */
    @FXML
    private void saveProductHandler(ActionEvent event) throws IOException {
        try {
            Product selectedProduct = getSelectedProduct();
            int index = getIndex(selectedProduct);
            String productName = name.getText();
            int productInv = Integer.parseInt(inventory.getText());
            double productPrice = Double.parseDouble(price.getText());
            int productMin = Integer.parseInt(min.getText());
            int productMax = Integer.parseInt(max.getText());
            boolean modifiedProduct = false;
            
            if (minMax(productMin, productMax) && inventoryInput(productMin, productMax, productInv)) {
                selectedProduct.setName(productName);
                selectedProduct.setPrice(productPrice);
                selectedProduct.setStock(productInv);
                selectedProduct.setMin(productMin);
                selectedProduct.setMax(productMax);
            for (Part p : selectedProduct.getAllAssociatedParts()) {
                selectedProduct.deleteAssociatedPart(p);
            }
            for (Part p : associatedParts) {
                selectedProduct.addAssociatedPart(p);
            }
            updateProduct(index, selectedProduct);
            
            modifiedProduct = true;
            }
            if (modifiedProduct) {
                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
                Scene scene = new Scene(root);
                stage.setTitle("Inventory Management System");
        
                stage.setScene(scene);
        
            stage.show();
    }
        } catch (Exception e) {
            alerts(3);
        }
    }
}
