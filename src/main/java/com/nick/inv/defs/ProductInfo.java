package com.nick.inv.defs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Retains all the product information for the individual products.
 *
 * @author Nick Popelnukh
 */

public class ProductInfo {
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int productMax;
    private int productMin;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public ProductInfo(int productID, String productName, double productPrice, int productStock, int productMin, int productMax) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productMax = productMax;
        this.productMin = productMin;
    }
    public ProductInfo() {
        this(0, null, 0.00, 0, 0, 0);
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public int getProductMax() {
        return productMax;
    }

    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    public int getProductMin() {
        return productMin;
    }

    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    public void addAssociatedPart(ObservableList<Part> part){
        this.associatedParts.addAll(part);
    }

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
