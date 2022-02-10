package com.nick.inv;

import com.nick.inv.defs.Inventory;
import com.nick.inv.defs.Part;
import com.nick.inv.defs.ProductInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Manages the window to add products.
 * Checks the text-fields for valid input.
 * Manages adding and removing additional parts and initializes the
 * parts and associated parts tables.
 *
 * @author Nick Popelnukh
 */

public class addProductController implements Initializable {

    @FXML
    private TableView<Part> partCopy;
    @FXML
    public TableColumn<Part, Integer> partIDCopy, partStockCopy;
    @FXML
    public TableColumn<Part, String> partNameCopy;
    @FXML
    public TableColumn<Part, Double> partPriceCopy;

    @FXML
    private TableView<Part> AssociatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> AssociatedPartsIDColumn, AssociatedPartsStockColumn;
    @FXML
    private TableColumn<Part, String> AssociatedPartsNameColumn;
    @FXML
    private TableColumn<Part, Double> AssociatedPartsCostColumn;

    @FXML
    private TextField productName;
    @FXML
    private TextField productStock;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productMin;
    @FXML
    private TextField productMax;

    @FXML
    private Label productNameError;
    @FXML
    private Label productStockError;
    @FXML
    private Label productPriceError;
    @FXML
    private Label productMinMaxError;
    @FXML
    private Button done;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public void addProductButtonController() {
        ProductInfo newProduct = new ProductInfo();
        Stage stage = (Stage) done.getScene().getWindow();
        try {
            if (productName.getText() == null || productName.getText().trim().isEmpty()) {
                productNameError.setText("Name cannot be empty!");
            }
            if (productStock.getText() == null || productStock.getText().trim().isEmpty()) {
                productStockError.setText("Part stock cannot be empty!");
            }
            if (productPrice.getText() == null || productPrice.getText().trim().isEmpty()) {
                productPriceError.setText("Price cannot be empty!");
            }
            if (productMin.getText() == null || productMax.getText() == null || productMax.getText().trim().isEmpty() || productMin.getText().trim().isEmpty()) {
                productMinMaxError.setText("Min/Max cannot be empty!");
            }
            if (!(productMin.getText().trim().isEmpty() && productMax.getText().trim().isEmpty() && productStock.getText().trim().isEmpty())) {
                if (!(Integer.parseInt(productStock.getText()) > Integer.parseInt(productMin.getText()) &&
                        Integer.parseInt(productStock.getText()) < Integer.parseInt(productMax.getText()))) {
                    productMinMaxError.setText("Max must be greater than min!");
                    productStockError.setText("Stock must be between min and max!");
                } else {
                    newProduct.setProductID(Inventory.genProdID());
                    newProduct.setProductName(productName.getText());
                    newProduct.setProductStock(Integer.parseInt(productStock.getText()));
                    newProduct.setProductPrice(Double.parseDouble(productPrice.getText()));
                    newProduct.setProductMin(Integer.parseInt(productMin.getText()));
                    newProduct.setProductMax(Integer.parseInt(productMax.getText()));
                    newProduct.addAssociatedPart(associatedParts);
                    Inventory.addProduct(newProduct);
                    stage.close();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("test");
        }
    }

    /**
     * Adds associated parts to the product.
     *
     */
    public void addAssociatedPart() {
        Part selectedPartCopy = partCopy.getSelectionModel().getSelectedItem();

        if (selectedPartCopy == null) {
            return;
        }

        associatedParts.add(selectedPartCopy);
        System.out.println(associatedParts.size());
    }

    /**
     * Removes associated parts from the product.
     */
    public void removeAssociatedPart() {
        Part removeSelectedPart = AssociatedPartsTableView.getSelectionModel().getSelectedItem();
        if (removeSelectedPart == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Once you delete '" + removeSelectedPart.getName() + "', it will be gone forever.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty() || result.get() == ButtonType.OK) {
            associatedParts.remove(removeSelectedPart);
        } else {
            System.out.println("Cancelled");
        }
    }

    /**
     * Initializes the parts and associated parts table.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Part> originalParts = Inventory.getAllParts();

        partIDCopy.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCopy.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCopy.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCopy.setCellValueFactory(new PropertyValueFactory<>("price"));
        partCopy.setItems(originalParts);

        AssociatedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartsStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartsTableView.setItems(associatedParts);

    }
}
