package Inventory_System.View_Controller;

import Inventory_System.Model.InHouse;
import Inventory_System.Model.Inventory;
import Inventory_System.Model.Outsourced;
import Inventory_System.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Modify_Part_Controller {
    /**************************************ATTRIBUTES*******************************************/
    //FXML TextFields
    @FXML
    private TextField partModifyID;
    @FXML
    private TextField partModifyName;
    @FXML
    private TextField partModifyStock;
    @FXML
    private TextField partModifyPrice;
    @FXML
    private TextField partModifyMax;
    @FXML
    private TextField partModifyMin;
    @FXML
    private TextField partModifyCompany;

    //FXML Labels
    @FXML
    private Label machineIDLabel;


    //FXML Buttons
    @FXML
    private Button partCancelButton2;
    @FXML
    private Button partSaveButton2;
    @FXML
    private RadioButton partInHouseOption;
    @FXML
    private RadioButton partOutsourceOption;

    //Initialized Objects
    Part part;
    InHouse inhouse;
    Outsourced outsourced;

    /**********************************METHODS*************************************/
    public void initialize(URL url, ResourceBundle rb) {

    }

    //Method to go back to Main_Screen.fxml scene
    @FXML
    private void partBackButtonHandler2(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) partCancelButton2.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader();
        root = loader.load(getClass().getResource("Main_Screen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Method to set a Part
    public void setPart(Part part) {
        this.part = part;

        partModifyName.setText(part.getName());
        partModifyStock.setText(new Integer(part.getStock()).toString());
        partModifyPrice.setText(new Double(part.getPrice()).toString());
        partModifyMin.setText(new Integer(part.getMin()).toString());
        partModifyMax.setText(new Integer(part.getMax()).toString());

    }

    //Method to set a InHouse Part
    public void setInHousePart(InHouse inhouse) {
        this.inhouse = inhouse;

        partModifyID.setText(new Integer(inhouse.getId()).toString());
        partModifyName.setText(inhouse.getName());
        partModifyStock.setText(new Integer(inhouse.getStock()).toString());
        partModifyPrice.setText(new Double(inhouse.getPrice()).toString());
        partModifyMin.setText(new Integer(inhouse.getMin()).toString());
        partModifyMax.setText(new Integer(inhouse.getMax()).toString());
        partModifyCompany.setText(String.valueOf(inhouse.getPartMachineID()));

        machineIDLabel.setText("Machine ID");
        partInHouseOption.setSelected(true);
    }

    //Method to set a Outsourced Part
    public void setOutsourcedPart(Outsourced outsourced) {
        this.outsourced = outsourced;

        partModifyID.setText(new Integer(outsourced.getId()).toString());
        partModifyName.setText(outsourced.getName());
        partModifyStock.setText(new Integer(outsourced.getStock()).toString());
        partModifyPrice.setText(new Double(outsourced.getPrice()).toString());
        partModifyMin.setText(new Integer(outsourced.getMin()).toString());
        partModifyMax.setText(new Integer(outsourced.getMax()).toString());
        partModifyCompany.setText(String.valueOf(outsourced.getPartCompanyName()));

        machineIDLabel.setText("Company Name");
        partOutsourceOption.setSelected(true);
    }

    public void partSaveHandler(ActionEvent event) throws IOException{
//if statement to check for inhouse or outsourced

        //use add part as example
        int partID = Integer.parseInt(partModifyID.getText());
        String partName = partModifyName.getText();
        int partStock = Integer.parseInt(partModifyStock.getText());
        double partPrice = Double.parseDouble(partModifyPrice.getText());
        int partMax = Integer.parseInt(partModifyMax.getText());
        int partMin = Integer.parseInt(partModifyMin.getText());
        //used for Company Name and Machine ID
        String partCompanyName = partModifyCompany.getText();

        //outsourced modify part using updatePart method in Inventory
        if (this.machineIDLabel.getText().equals("Company Name")) {
            //System.out.println("outsourced modify part if statement");
            Outsourced partOutsourced = new Outsourced(partID, partName, partPrice, partStock, partMin, partMax, partCompanyName);
            Inventory.updatePart(partOutsourced);
            //Check for errors and allow them to fix errors if Min value less than Max value
            if(partOutsourced.checkForErrors() == 0){
                Stage stage;
                Parent root;
                stage = (Stage) partSaveButton2.getScene().getWindow();
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

            //inhouse modify part using updatePart method in Inventory
        } else{
            //System.out.println("inhouse modify part if statement");
            int partMachineID = Integer.parseInt(partCompanyName);
            InHouse partInHouse = new InHouse(partID, partName, partPrice, partStock, partMin, partMax, partMachineID);
            Inventory.updatePart(partInHouse);
            if(partInHouse.checkForErrors() == 0){
                Stage stage;
                Parent root;
                stage = (Stage) partSaveButton2.getScene().getWindow();
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
        }

    }
}
