package com.nick.inv.defs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  Manages the inventory of Parts and Products.
 *
 *  This class hands important data to the application.
 *
 * @author Nick Popelnukh
 */

public class Inventory {

    /**
     * A list for all Parts and all Products that are currently in the inventory.
     */
    static ObservableList<Part> partsList = FXCollections.observableArrayList();
    static ObservableList<ProductInfo> productsList = FXCollections.observableArrayList();

    /**
     * Gets a list of all of the Products that are currently in the inventory.
     * @return returns a list of the Products.
     */
    public static ObservableList<ProductInfo> getAllProducts() {
        return productsList;
    }
    /**
     * Gets a list of all of the Parts that are currently in the inventory.
     * @return returns a list of the Parts.
     */
    public static ObservableList<Part> getAllParts() {
        return partsList;
    }

    /**
     * Adds a new part to the list of Parts.
     * @param newPart Information about the new part.
     */

    public static void addPart(Part newPart) {
        partsList.add(newPart);
    }

    /**
     * Updates a part in the part list.
     * @param index The index of the part.
     * @param selectedPart Information about the selected part.
     */

    public static void updatePart(int index, Part selectedPart) {
        partsList.set(index, selectedPart);
    }

    /**
     * Updates a product in the product list.
     * @param index The index of the product.
     * @param selectedProduct Information about the selected product.
     */

    public static void updateProduct(int index, ProductInfo selectedProduct) {
        productsList.set(index, selectedProduct);
    }

    /**
     * Removes a part from the part list.
     * @param removePart Information about which part to remove.
     */
    public static void removePart(Part removePart) {
        partsList.remove(removePart);
    }

    /**
     * Generates an id for a newly added part.
     * @return Returns the calculated new part ID.
     */

    public static int genID() {
        return Inventory.getAllParts().size() + 1;
    }

    /**
     * Generates an id for a newly added product.
     * @return Returns the calculated new product ID.
     */
    public static int genProdID() {
        return Inventory.getAllParts().size() + 1;
    }

    /**
     * Adds a product to the list of products.
     * @param newProduct Information about the new product.
     */

    public static void addProduct(ProductInfo newProduct) {
        productsList.add(newProduct);
    }

    /**
     * Searches the part by part ID.
     * @param id Which partID the lookup is for.
     * @return Returns information about the part.
     */
    public static Part lookupPartID(int id) {
        Part temp = null;
        for (Part parts : partsList){
            if (id == parts.getId()){
                temp = parts;
            }
        }
        return temp;
    }

    /**
     * Searches a product by the product ID.
     * @param productID Which productID the lookup is for.
     * @return Returns information about the product.
     */

    public static ProductInfo lookupProductID(int productID) {
        ProductInfo temp = null;
        for (ProductInfo products : productsList){
            if (productID == products.getProductID()){
                temp = products;
            }
        }
        return temp;
    }

    /**
     * Searches for a part by the partName.
     * @param searchPartName The given partName in the search.
     * @return Returns entries of the Part.
     */
    public static ObservableList<Part> lookupPart(String searchPartName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        if(searchPartName.length() == 0) {
            foundParts = partsList;
        }
        else {
            for (Part partInfo : partsList) {
                if (partInfo.getName().toLowerCase().contains(searchPartName.toLowerCase())) {
                    foundParts.add(partInfo);
                }
            }
        }

        return foundParts;
    }
    /**
     * Searches for a part by the productName.
     * @param searchProductName The given productName in the search.
     * @return Returns entries of the Product.
     */

    public static ObservableList<ProductInfo> lookupProduct(String searchProductName){
        ObservableList<ProductInfo> foundProducts = FXCollections.observableArrayList();

        if(searchProductName.length() == 0) {
            foundProducts = productsList;
        }
        else {
            for (ProductInfo productInfo : productsList) {
                if (productInfo.getProductName().toLowerCase().contains(searchProductName.toLowerCase())) {
                    foundProducts.add(productInfo);
                }
            }
        }

        return foundProducts;
    }

}
