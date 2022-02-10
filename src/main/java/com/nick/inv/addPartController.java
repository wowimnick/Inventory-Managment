package com.nick.inv;

import com.nick.inv.defs.InHouse;
import com.nick.inv.defs.Inventory;
import com.nick.inv.defs.OutSourced;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Manages the part addition window.
 *
 * @author Nick Popelnukh
 */

public class addPartController {

    @FXML private Button done;
    @FXML private RadioButton outSourced;
    @FXML private RadioButton inHouse;
    @FXML private Label machineidORCompany;
    @FXML private TextField partTypeText;
    @FXML private TextField partName;
    @FXML private TextField partStock;
    @FXML private TextField partPrice;
    @FXML private TextField partMin;
    @FXML private TextField partMax;
    @FXML private Label partNameError;
    @FXML private Label partStockError;
    @FXML private Label partPriceError;
    @FXML private Label partMinMaxError;
    @FXML private Label partError;

    /**
     * Adds a new part and checks for errors such as empty fields and min,
     * max, and stock values
     */
    @FXML
    public void newPart() {
        Stage stage = (Stage) done.getScene().getWindow();
        try {
            if (partName.getText() == null || partName.getText().trim().isEmpty()) {
                partNameError.setText("Name cannot be empty!");
            }
            else if (partStock.getText() == null || partStock.getText().trim().isEmpty()) {
                partStockError.setText("Part stock cannot be empty!");
            }
            else if (partPrice.getText() == null || partPrice.getText().trim().isEmpty()) {
                partPriceError.setText("Price cannot be empty!");
            }
            else if (partMin.getText() == null || partMax.getText() == null || partMax.getText().trim().isEmpty() || partMin.getText().trim().isEmpty()) {
                partMinMaxError.setText("Min/Max cannot be empty!");
            } else if (!(partMin.getText().trim().isEmpty() && partMax.getText().trim().isEmpty() && partStock.getText().trim().isEmpty())) {
                if (!(Integer.parseInt(partStock.getText()) > Integer.parseInt(partMin.getText()) &&
                        Integer.parseInt(partStock.getText()) < Integer.parseInt(partMax.getText()))) {
                    partMinMaxError.setText("Max must be greater than min!");
                    partStockError.setText("Stock must be between min and max!");
                } else {
                    String newPartName = partName.getText();
                    int newPartStock = Integer.parseInt(partStock.getText());
                    double newPriceCost = Double.parseDouble(partPrice.getText());
                    int newPartMin = Integer.parseInt(partMin.getText());
                    int newPartMax = Integer.parseInt(partMax.getText());

                    if (outSourced.isSelected()) {
                        String newCompany = partTypeText.getText();
                        Inventory.addPart(new OutSourced(Inventory.genID(), newPartName, newPriceCost, newPartStock, newPartMin, newPartMax, newCompany));
                        System.out.println(Inventory.getAllParts());
                        stage.close();
                    } else if (inHouse.isSelected()) {
                        int machineID = Integer.parseInt(partTypeText.getText());
                        Inventory.addPart(new InHouse(Inventory.genID(), newPartName, newPriceCost, newPartStock, newPartMin, newPartMax, machineID));
                        System.out.println(Inventory.getAllParts());
                        stage.close();
                    }

                }
            }

        } catch (NumberFormatException ex) {
            partError.setText("Error: Invalid Input");
        }
    }

    /**
     * Changes the machineID or Company label when the radio button is
     * changed.
     */

    public void partType() {
        if (outSourced.isSelected()) {
            machineidORCompany.setText("Company Name");
        } else if (inHouse.isSelected()) {
            machineidORCompany.setText("Machine ID");
        }
    }

}
