package Inventory_System.View_Controller;

import Inventory_System.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class Add_Part_Controller implements Initializable {
    /**************************************ATTRIBUTES*******************************************/

    //FXML Text Fields
    @FXML
    private TextField partAddName;
    @FXML
    private TextField partAddInventory;
    @FXML
    private TextField partAddPrice;
    @FXML
    private TextField partAddID;
    @FXML
    private TextField partAddMachineID;
    @FXML
    private TextField partAddMax;
    @FXML
    private TextField partAddMin;

    //FXML ToggleGroups
    @FXML
    private ToggleGroup partSourceOptions;

    //FXML Labels
    @FXML
    private Label machineIDLabel;

    //FXML Buttons
    @FXML
    private Button partCancelButton;
    @FXML
    private Button partSaveButton;
    @FXML
    private RadioButton partInHouseOption;
    @FXML
    private RadioButton partOutsourceOption;

    //Object part
    Part part;

    /**********************************METHODS*************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Nothing to initialize
    }

    //method to go back from the Add Product screen to the Main Screen
    @FXML
    private void partBackButtonHandler(ActionEvent event) throws IOException {
        // Creating Alert window and dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel Adding Part");
        alert.setContentText("Are you sure you want to cancel before adding the selected Part?");

        //Delete confirm button options
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage;
            Parent root;
            stage = (Stage) partCancelButton.getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("Main_Screen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // If they click Cancel they return to the application
        }
    }

    //Method to set the part
    public void setPart(Part part) {
        this.part = part;

        //partsIDColumn.setText(new Integer(part.getId()).toString());
        partAddName.setText(part.getName());
    }

    //Method to determine if it is an InHouse or Outsourced Part and to set the text/label accordingly
    @FXML
    private void partInHouseOptionHandler(ActionEvent event) {
        partOutsourceOption.setSelected(false);
        partAddMachineID.clear();
        machineIDLabel.setText("Machine ID");
        partAddMachineID.setPromptText("Machine ID");
    }

    //Method to determine if it is an InHouse or Outsourced Part and to set the text/label accordingly
    @FXML
    private void partOutsourceOptionHandler(ActionEvent event) {
        partInHouseOption.setSelected(false);
        partAddMachineID.clear();
        //System.out.println("OutsourcedOptionHandler");
        machineIDLabel.setText("Company Name");
        partAddMachineID.setPromptText("Company Name");
    }

    //Method to save the new Part to the allPartsList after determining if it is an InHouse or Outsourced Part and then redirects to Main Screen
    @FXML
    private void partSaveButtonHandler (ActionEvent event) throws IOException {
        // Grabs the last part ID used from the static variable partGlobalID and increments it by 1
        int incrementedPartID = Inventory.getPartGlobalID() + 1;

        // Setting incremented partGlobalID
        Inventory.setPartGlobalID(incrementedPartID);

        String partName = partAddName.getText();
        int partStock = Integer.parseInt(partAddInventory.getText());
        double partPrice = Double.parseDouble(partAddPrice.getText());
        int partMax = Integer.parseInt(partAddMax.getText());
        int partMin = Integer.parseInt(partAddMin.getText());

        //used for Company Name and Machine ID
        String partCompanyName = partAddMachineID.getText();

        // Gathers which radio button was selected at the time of save
        Toggle optionSelected = partSourceOptions.getSelectedToggle();

        // In House radio button selected
        if (optionSelected == partInHouseOption) {
            //parse companyName String to partMachineID int
            int partMachineID = Integer.parseInt(partCompanyName);
            InHouse partNew = new InHouse(incrementedPartID, partName, partPrice, partStock, partMin, partMax, partMachineID);
            Inventory.addPart(partNew);
            //Check for errors and allow them to fix errors if Min value less than Max value
            if(partNew.checkForErrors() == 0){
                Stage stage;
                Parent root;
                stage = (Stage) partSaveButton.getScene().getWindow();
                //load up OTHER FXML document
                FXMLLoader loader = new FXMLLoader();
                root = loader.load(getClass().getResource("Main_Screen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else{
                //User alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Data Entry Error");
                alert.setHeaderText(null);
                alert.setContentText("Min value should be less than Max value. Please update values appropriately and click Save.");
                alert.showAndWait();
            }
        //Outsourced Part creation and error logic
        } else {
            Outsourced partNew = new Outsourced(incrementedPartID, partName, partPrice, partStock, partMin, partMax, partCompanyName);
            Inventory.addPart(partNew);
            //Check for errors and allow them to fix errors if Min value less than Max value
            if(partNew.checkForErrors() == 0){
                Stage stage;
                Parent root;
                stage = (Stage) partSaveButton.getScene().getWindow();
                //load up OTHER FXML document
                FXMLLoader loader = new FXMLLoader();
                root = loader.load(getClass().getResource("Main_Screen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Data Entry Error");
                alert.setHeaderText(null);
                alert.setContentText("Max value should be greater than Min value. Please update values appropriately and click Save.");
                alert.showAndWait();
            }
        }
    }
}
