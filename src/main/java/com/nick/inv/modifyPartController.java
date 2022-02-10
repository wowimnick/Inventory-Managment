package com.nick.inv;

import com.nick.inv.defs.InHouse;
import com.nick.inv.defs.Inventory;
import com.nick.inv.defs.OutSourced;
import com.nick.inv.defs.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Manages the window to modify parts.
 * Checks the text-fields for valid input.
 * Saves and applies the edited values.
 *
 * @author Nick Popelnukh
 */
public class modifyPartController implements Initializable {

    @FXML
    private Button done;
    @FXML
    private RadioButton outSourced;
    @FXML
    private RadioButton inHouse;
    @FXML
    private Label machineidORCompany;
    @FXML
    private TextField selPartID;
    @FXML
    private TextField partTypeText;
    @FXML
    private TextField partName;
    @FXML
    private TextField partStock;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMin;
    @FXML
    private TextField partMax;
    @FXML
    private Label partNameError;
    @FXML
    private Label partStockError;
    @FXML
    private Label partPriceError;
    @FXML
    private Label partMinMaxError;
    @FXML
    private Label partError;

    public Part selectedPart;
    private int partID;

    /**
     * Changes the machineID or Company label when the radio button is
     * changed.
     */

    public void partType() {
        if (outSourced.isSelected()) {
            this.machineidORCompany.setText("Company Name");
        } else if (inHouse.isSelected()) {
            this.machineidORCompany.setText("Machine ID");
        }
    }

    /**
     * Gets and applies the text-fields for the selected part for editing.
     * @param selectedPart Gets the selected part in the table.
     */

    public void getParts(Part selectedPart) {
        this.selectedPart = selectedPart;

        partID = Inventory.getAllParts().indexOf(selectedPart);
        selPartID.setText(Integer.toString(selectedPart.getId()));
        partName.setText(selectedPart.getName());
        partStock.setText(Integer.toString(selectedPart.getStock()));
        partPrice.setText(Double.toString(selectedPart.getPrice()));
        partMin.setText(Integer.toString(selectedPart.getMin()));
        partMax.setText(Integer.toString(selectedPart.getMax()));

        if (selectedPart instanceof InHouse ih) {
            inHouse.setSelected(true);
            this.machineidORCompany.setText("Machine ID");
            partTypeText.setText(Integer.toString(ih.getMachineId()));
        } else if (selectedPart instanceof OutSourced os) {
            outSourced.setSelected(true);
            this.machineidORCompany.setText("Company");
            partTypeText.setText(os.getCompany());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    /**
     * Saves the part and checks for errors such as empty fields and min,
     * max, and stock values.
     */
    public void savePart() {
        Stage stage = (Stage) done.getScene().getWindow();
        try {
            if (partName.getText() == null || partName.getText().trim().isEmpty()) {
                partNameError.setText("Name cannot be empty!");
            }
            if (partStock.getText() == null || partStock.getText().trim().isEmpty()) {
                partStockError.setText("Part stock cannot be empty!");
            }
            if (partPrice.getText() == null || partPrice.getText().trim().isEmpty()) {
                partPriceError.setText("Price cannot be empty!");
            }
            if (partMin.getText() == null || partMax.getText() == null || partMax.getText().trim().isEmpty() || partMin.getText().trim().isEmpty()) {
                partMinMaxError.setText("Min/Max cannot be empty!");
            }
            if (!(partMin.getText().trim().isEmpty() && partMax.getText().trim().isEmpty() && partStock.getText().trim().isEmpty())) {
                if (!(Integer.parseInt(partStock.getText()) > Integer.parseInt(partMin.getText()) &&
                        Integer.parseInt(partStock.getText()) < Integer.parseInt(partMax.getText()))) {
                    partMinMaxError.setText("Max must be greater than min!");
                    partStockError.setText("Stock must be between min and max!");
                } else {
                    String newPartName = partName.getText();
                    partID = Inventory.getAllParts().indexOf(selectedPart);
                    int id = Integer.parseInt(selPartID.getText());
                    int newPartStock = Integer.parseInt(partStock.getText());
                    double newPriceCost = Double.parseDouble(partPrice.getText());
                    int newPartMin = Integer.parseInt(partMin.getText());
                    int newPartMax = Integer.parseInt(partMax.getText());

                    if (inHouse.isSelected()) {
                        int newMachineID = Integer.parseInt(partTypeText.getText());
                        InHouse modify = new InHouse(id, newPartName, newPriceCost, newPartStock, newPartMin, newPartMax, newMachineID);
                        Inventory.updatePart(partID, modify);
                    } else if (outSourced.isSelected()) {
                        String newCompany = partTypeText.getText();
                        OutSourced modify = new OutSourced(id, newPartName, newPriceCost, newPartStock, newPartMin, newPartMax, newCompany);
                        Inventory.updatePart(partID, modify);
                    }
                }
            }
            stage.close();
    } catch(
    NumberFormatException e)

    {
        partError.setText("Error: Invalid Input");
    }
}
}
