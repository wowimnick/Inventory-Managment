package com.nick.inv;

import com.nick.inv.defs.Inventory;
import com.nick.inv.defs.Part;
import com.nick.inv.defs.ProductInfo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Manages the main window for the application.
 * Defines all button presses and searches on the main window.
 * Initializes the parts and products tables.
 *
 * @author Nick Popelnukh
 */

public class MyController implements Initializable {
    // Below are the partTable definitions
    @FXML private TableView<Part> partTable;
    @FXML public TableColumn<Part, Integer> id, stock;
    @FXML public TableColumn<Part, String> name;
    @FXML public TableColumn<Part, Double> price;

    // Below are the productTable definitions
    @FXML private TableView<ProductInfo> productTable;
    @FXML public TableColumn<ProductInfo, Integer> productID, productStock;
    @FXML public TableColumn<ProductInfo, String> productName;
    @FXML public TableColumn<ProductInfo, Double> productPrice;
    @FXML private Label associatedPartError;
    @FXML private TextField searchedPart;
    @FXML private TextField searchedProduct;

    /**
     * Initializes parts and products tables and sets their contents.
     * @param location pass
     * @param resourceBundle pass
     */
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTable.setItems(Inventory.getAllParts());

        productID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        productStock.setCellValueFactory(new PropertyValueFactory<>("ProductStock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("ProductPrice"));
        productTable.setItems(Inventory.getAllProducts());

    }

    /**
     * Opens the addParts menu and calls the addPartController class.
     */

    public void openAddPart() throws IOException {
        FXMLLoader fxmlAddPart = new FXMLLoader(getClass().getResource("addPart.fxml"));
        Parent root = fxmlAddPart.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Opens the modifyPart menu and calls the modifyPartController class.
     */
    public void openModifyPart() throws IOException {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        FXMLLoader fxmlModifyPart = new FXMLLoader(getClass().getResource("modifyPart.fxml"));
        Parent root1 = fxmlModifyPart.load();
        modifyPartController controller = fxmlModifyPart.getController();
        controller.getParts(selectedPart);
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root1));
        stage1.show();
    }

    /**
     * Defines what happens when a part is deleted/removed from the parts table on the main window.
     */
    public void onPartDelete() {
        Part selectedPart2 = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart2 == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Once you delete '" + selectedPart2.getName() + "', it will be gone forever.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty() || result.get() == ButtonType.OK) {
            Inventory.removePart(selectedPart2);
        } else {
            System.out.println("no");
        }

    }

    /**
     * Opens the addProduct windoww and calls the addProductController class.
     */
    public void openAddProduct() throws IOException {
        FXMLLoader fxmlAddProduct = new FXMLLoader(getClass().getResource("addProduct.fxml"));
        Parent root = fxmlAddProduct.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Opens the modifyProduct window and calls the modifyProductController class.
     */
    public void openModifyProduct() throws IOException {
        ProductInfo selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        }
        FXMLLoader fxmlModifyProduct = new FXMLLoader(getClass().getResource("modifyProduct.fxml"));
        Parent root1 = fxmlModifyProduct.load();
        modifyProductController controller = fxmlModifyProduct.getController();
        controller.getProduct(selectedProduct);
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root1));
        stage1.show();
    }

    /**
     * Defines what happens when a product is deleted/removed from the products table on the main window.
     */
    public void onProductDelete() {
        ProductInfo selectedPart = productTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Once you delete '" + selectedPart.getProductName() + "', it will be gone forever.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty() || result.get() == ButtonType.OK) {
            if (!(selectedPart.getAllAssociatedParts().isEmpty())) {
                associatedPartError.setText("This product has associated parts!");
            } else {
                productTable.getItems().remove(selectedPart);
                associatedPartError.setText("");
            }
        }
    }

    /**
     * Gets the selectedPart from the table and calls the lookupPart method from the Inventory class.
     */
    public void onPartSearch() {
            String textPart = searchedPart.getText();
            ObservableList<Part> getSearchedPart = Inventory.lookupPart(textPart);
            partTable.setItems(getSearchedPart);
        }
    /**
     * Gets the selectedProduct from the table and calls the lookupProduct method from the Inventory class.
     */
    public void onProductSearch() {
        String textProduct = searchedProduct.getText();
        ObservableList<ProductInfo> getSearchedProduct = Inventory.lookupProduct(textProduct);

        productTable.setItems(getSearchedProduct);
    }
}


