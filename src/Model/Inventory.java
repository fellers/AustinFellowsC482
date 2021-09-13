/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author indya
 */
public class Inventory {
       
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    
    
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partId) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if(allParts.get(i).getId() == partId) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }
    
    public static Product lookupProduct(int productId) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if(allProducts.get(i).getId() == productId) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }
    
    public static ObservableList<Part> lookupPart(String partName) {
        if (!allParts.isEmpty()) {
            ObservableList searchParts = FXCollections.observableArrayList();
                for (Part p : getAllParts()) {
                    if(p.getName().contains(partName)) {
                        searchParts.add(p);
                    }
                }
                return searchParts;
        }
        return null;
    }
                
    
    public static ObservableList<Product> lookupProduct(String productName) {
        if (!allProducts.isEmpty()) {
            ObservableList searchProducts = FXCollections.observableArrayList();
                for (Product p : getAllProducts()) {
                    if(p.getName().contains(productName)) {
                        searchProducts.add(p);
                }
            }
                return searchProducts;
        }
        return null;
    }
    
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }
    
    public static boolean deletePart(Part selectedPart) {
        int i;
        for (i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == selectedPart.getId()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public static boolean deleteProduct(Product selectedProduct) {
        int i;
        for (i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == selectedProduct.getId()) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
