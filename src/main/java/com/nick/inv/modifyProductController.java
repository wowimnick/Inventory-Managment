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
 * Manages the window to modify parts.
 * Checks the text-fields for valid input.
 * Manages adding and removing additional parts and initializes the
 * parts and associated parts tables.
 * Saves and applies the edited values.
 *
 * @author Nick Popelnukh
 */

public class modifyProductController implements Initializable {
    private ProductInfo selectedProduct;
    @FXML
    private TableView<Part> partCopy;
    @FXML public TableColumn<Part, Integer> partIDCopy, partStockCopy;
    @FXML public TableColumn<Part, String> partNameCopy;
    @FXML public TableColumn<Part, Double> partPriceCopy;

    @FXML private TableView<Part> AssociatedPartsTableView;
    @FXML private TableColumn<Part, Integer> AssociatedPartsIDColumn, AssociatedPartsStockColumn;
    @FXML private TableColumn<Part, String> AssociatedPartsNameColumn;
    @FXML private TableColumn<Part, Double> AssociatedPartsCostColumn;

    @FXML private TextField productName;
    @FXML private TextField productStock;
    @FXML private TextField productPrice;
    @FXML private TextField productMin;
    @FXML private TextField productMax;
    @FXML private TextField selPartID;
    @FXML private TextField searchedPart;
    @FXML private Button done;

    @FXML private Label productNameError;
    @FXML private Label productStockError;
    @FXML private Label productPriceError;
    @FXML private Label productMinMaxError;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Gets and applies the text-fields for the selected product for editing.
     * @param selectedProduct Gets the selected product in the table.
     */
    public void getProduct(ProductInfo selectedProduct) {
        this.selectedProduct = selectedProduct;

        selPartID.setText(Integer.toString(selectedProduct.getProductID()));
        productName.setText(selectedProduct.getProductName());
        productStock.setText(Integer.toString(selectedProduct.getProductStock()));
        productPrice.setText(Double.toString(selectedProduct.getProductPrice()));
        productMin.setText(Integer.toString(selectedProduct.getProductMin()));
        productMax.setText(Integer.toString(selectedProduct.getProductMax()));
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());
    }

    /**
     * Adds an associated part to the product.
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
     * Removes an associated part from the product.
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
            System.out.println("no");
        }
    }

    public void searchForPart() {
        String textPart = searchedPart.getText();
        ObservableList<Part> getSearchedPart = Inventory.lookupPart(textPart);
        partCopy.setItems(getSearchedPart);
    }

    /**
     * Saves the product on save button click.
     * Checks for invalid text-fields and displays an error if one is found.
     * Checks for valid stock, min, and max values.
     */
    public void saveProductButtonController() {
        int productID = Inventory.getAllProducts().indexOf(selectedProduct);
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
                    selectedProduct.setProductID(Inventory.genProdID());
                    selectedProduct.setProductName(productName.getText());
                    selectedProduct.setProductStock(Integer.parseInt(productStock.getText()));
                    selectedProduct.setProductPrice(Double.parseDouble(productPrice.getText()));
                    selectedProduct.setProductMin(Integer.parseInt(productMin.getText()));
                    selectedProduct.setProductMax(Integer.parseInt(productMax.getText()));
                    selectedProduct.getAllAssociatedParts().clear();
                    selectedProduct.addAssociatedPart(associatedParts);
                    Inventory.updateProduct(productID, selectedProduct);
                    stage.close();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error");
        }
    }

    /**
     * Initializes the parts and associated parts table.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIDCopy.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCopy.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCopy.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCopy.setCellValueFactory(new PropertyValueFactory<>("price"));
        partCopy.setItems(Inventory.getAllParts());

        AssociatedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartsStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartsTableView.setItems(associatedParts);
    }
}
