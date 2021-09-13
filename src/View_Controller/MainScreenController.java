/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;


import Model.Inventory;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import Model.Part;
import Model.Product;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * This is the controller class for the main screen.
 * @author indya
 */
public class MainScreenController implements Initializable {
    /**
     * Text field where user will search for parts.
     */
    @FXML
    private TextField partSearch;
    /**
     * Table view that displays all parts.
     */
    @FXML
    private TableView<Part> partsTable;
    /**
     * Text field that collects input to search for products.
     */
    @FXML
    private TextField productSearch;
    /**
     * Table that displays all products
     */
    @FXML
    private TableView<Product> productsTable;
    /**
     * ID column in the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partId;
    /**
     * Name column in the parts table.
     */
    @FXML
    private TableColumn<Part, String> partName;
    /**
     * Inventory level column in the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryLevel;
    /**
     * Price column in the parts table.
     */
    @FXML
    private TableColumn<Part, Double> partPrice;
    /**
     * ID column in the products table.
     */
    @FXML
    private TableColumn<Product, Integer> productId;
    /**
     * Name column in the products table.
     */
    @FXML
    private TableColumn<Product, String> productName;
    /**
     * Inventory level column in the products table.
     */
    @FXML
    private TableColumn<Product, Integer> productInventory;
    /**
     * Price column in the products table.
     */
    @FXML
    private TableColumn<Product, Double> productPrice;
    
    
    /**
     * Reference for the part selected in the parts table.
     */
    private static Part selectedPart;
    /**
     * Holds value of the index for the part selected in the parts table.
     */
    private static int selectedPartIndex;
    /**
     * Reference for the product selected in the products table. 
     */
    private static Product selectedProduct;
    /**
     * Holds value of the index for the product selected in the products table.
     */
    private static int selectedProductIndex;
    
    /**
     * Used by modify part controller to get the part that was selected.
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }
    
    /**
     * Used by modify part controller to get the index of the selected part.
     */
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }
    
    /**
     * Used by modify product controller to get the product that was selected. 
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }
    
    /**
     * Used by modify product controller to get the index of the selected product. 
     */
    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partsTable.setItems(Inventory.getAllParts());
        
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsTable.setItems(Inventory.getAllProducts());
        
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    /**
     * Searches for parts based on text input. 
     */
    @FXML
    private void partSearchHandler(ActionEvent event) {
        String searchId = partSearch.getText();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        
        if (searchId.equals("")) {
            partsTable.setItems(getAllParts());
        }
        else {
            boolean searchResult = false;
            try {
            Part part = Inventory.lookupPart(Integer.parseInt(searchId));
            if (part != null) {
                partsFound.add(part);
                partsTable.setItems(partsFound);
            }
            else {
                partsTable.setItems(getAllParts());
            }
        }
        catch (NumberFormatException e) {
            for (Part p : getAllParts()) {
            if (p.getName().contains(searchId)) {
            searchResult = true;
            partsFound.add(p);
                }
            partsTable.setItems(partsFound);
            }
            if (searchResult == false) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No parts found");
                alert.setContentText("Please try searching again");
                alert.showAndWait();
            }    
        }
    }
}
    
    /**
     * Loads the add part screen. 
     */
    @FXML
    private void addPartHandler(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        
        stage.setScene(scene);
        
        stage.show();
    }
    
    /**
     * Loads the modify part screen. 
     */
    @FXML
    private void modifyPartHandler(ActionEvent event) throws IOException {
        
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        selectedPartIndex = getAllParts().indexOf(selectedPart);
        
        if(selectedPart == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Modifying Part");
            alert.setHeaderText("The part cannot be updated");
            alert.setContentText("You have not selected a part");
            alert.showAndWait();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
        
            stage.setScene(scene);
        
            stage.show();
        }
    }
    
    /**
     * Deletes a selected part from inventory. 
     */
    @FXML
    private void deletePartHandler(ActionEvent event) {
        Part p = partsTable.getSelectionModel().getSelectedItem();
        
        if (p == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Cannot delete part");
            nullAlert.setHeaderText("No part selected");
            nullAlert.setContentText("You must select a part in order to delete it");
            nullAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you would like to delete this part? This action cannot be undone.");
            alert.setContentText("Press OK if you would like to delete this part, otherwise press cancel");
            alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            Inventory.deletePart(p);
        }
        else {
            alert.close();
        }
    }
    }
    
    /**
     * Exits the application. 
     */
    @FXML
    private void exitHandler(ActionEvent event) {
        System.exit(0);
    }
    
    /**
     * Searches for a product based on text input. 
     */
    @FXML
    private void productSearchHandler(ActionEvent event) {
        String searchId = productSearch.getText();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        
        if (searchId.equals("")) {
            productsTable.setItems(getAllProducts());
        }
        else {
            boolean searchResult = false;
            try {
            Product product = Inventory.lookupProduct(Integer.parseInt(searchId));
            if (product != null) {
                productsFound.add(product);
                productsTable.setItems(productsFound);
            }
            else {
                productsTable.setItems(getAllProducts());
            }
        }
        catch (NumberFormatException e) {
            for (Product p : getAllProducts()) {
            if (p.getName().contains(searchId)) {
            searchResult = true;
            productsFound.add(p);
                }
            productsTable.setItems(productsFound);
            }
            if (searchResult == false) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No products found");
                alert.setContentText("Please try searching again");
                alert.showAndWait();
            }    
        }
    }
    }
    
    /**
     * Opens the add product screen. 
     */
    @FXML
    private void addProductHandler(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        
        stage.setScene(scene);
        
        stage.show();
    }
    
    /**
     * Opens the modify product screen.
     */
    @FXML
    private void modifyProductHandler(ActionEvent event) throws IOException {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        selectedProductIndex = getAllProducts().indexOf(selectedProduct);
        
        if(selectedProduct == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Modifying Product");
            alert.setHeaderText("The product cannot be updated");
            alert.setContentText("You have not selected a product");
            alert.showAndWait();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
            Scene scene = new Scene(root);
            stage.setTitle("Modify Product");
        
            stage.setScene(scene);
        
            stage.show();
        }
    }
    
    /**
     * Deletes a product from inventory. 
     */
    @FXML
    private void deleteProductHandler(ActionEvent event) {
        boolean hasAssociatedParts = false;
        Product p = productsTable.getSelectionModel().getSelectedItem();
        
        if (p == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Cannot delete product");
            nullAlert.setHeaderText("No product selected");
            nullAlert.setContentText("You must select a product in order to delete it");
            nullAlert.showAndWait();
        } else {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you would like to delete this product? This action cannot be undone.");
        alert.setContentText("Press OK if you would like to delete this product, otherwise press cancel");
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            //Product p = productsTable.getSelectionModel().getSelectedItem();
            if (p.getAllAssociatedParts().isEmpty()) {
            Inventory.deleteProduct(p);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error deleting product");
                a.setHeaderText("Products with associated parts cannot be deleted");
                a.setContentText("Please remove this product's associated parts to delete this product.");
                a.showAndWait();
            }
        }
        else {
            alert.close();
        }
    }
    }
}

