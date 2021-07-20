package Inventory_System.View_Controller;

import Inventory_System.Model.Inventory;
import Inventory_System.Model.Part;
import Inventory_System.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class Add_Product_Controller implements Initializable {
    /**************************************ATTRIBUTES*******************************************/
    //FXML TextFields
    @FXML
    private TextField productAddName;
    @FXML
    private TextField productAddInventory;
    @FXML
    private TextField productAddPrice;
    @FXML
    private TextField productAddMax;
    @FXML
    private TextField productAddMin;
    @FXML
    private TextField partsSearchField;

    //FXML Buttons
    @FXML
    private Button productCancelButton;
    @FXML
    private Button productSaveButton;
    @FXML
    private Button partsSearchFieldButton;

    //FXML table 1
    @FXML
    private TableView<Part> productPartsTableView;
    @FXML
    private TableColumn<Part, Integer> productPartIDColumn;
    @FXML
    private TableColumn<Part, String> productPartNameColumn;
    @FXML
    private TableColumn<Part, Double> productPriceColumn;
    @FXML
    private TableColumn<Part, Integer> productInventoryLevelColumn;

    //FXML table 2
    @FXML
    private TableView<Part> productPartsTableView2;
    @FXML
    private TableColumn<Part, Integer> productPartIDColumn2;
    @FXML
    private TableColumn<Part, String> productPartNameColumn2;
    @FXML
    private TableColumn<Part, Double> productPriceColumn2;
    @FXML
    private TableColumn<Part, Integer> productInventoryLevelColumn2;

    //Product Object product
    private Product product;

    /**********************************METHODS*************************************/
    //Method to initialize the table data
    public void initialize(URL location, ResourceBundle resources) {
        //create new instance of Product with default values
        product = new Product(0,"Product Name Here",0.00,0,0,0);
        //sets the columns parts
        productPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        //set the items on the table from the observable list for parts
        productPartsTableView.setItems(Inventory.getAllParts());

        //sets the columns parts
        productPartIDColumn2.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productPartNameColumn2.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productPriceColumn2.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        productInventoryLevelColumn2.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        //set the items on the table from the observable list for parts
        productPartsTableView2.setItems(product.getAllAssociatedParts());
    }
    @FXML
    private void productBackButtonHandler(ActionEvent event) throws IOException {
        // Creating Alert window and dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel Adding Product");
        alert.setContentText("Are you sure you want to cancel before adding the selected Product?");

        //Delete confirm button options
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage;
            Parent root;
            stage=(Stage) productCancelButton.getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader=new FXMLLoader();
            root = loader.load(getClass().getResource("Main_Screen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // If they click Cancel they return to the application
        }

    }

    @FXML
    private void productSaveButtonHandler(ActionEvent event) throws IOException{
        // Grabs the last product ID used from the static variable productGlobalID and increments it by 1
        int incrementedProductID = Inventory.getProductGlobalID() + 1;

        // Setting incremented productGlobalID
        Inventory.setProductGlobalID(incrementedProductID);

        //Setting local variables to data in text fields based on FXML IDs
        String productName = productAddName.getText();
        int productStock = Integer.parseInt(productAddInventory.getText());
        double productPrice = Double.parseDouble(productAddPrice.getText());
        int productMax = Integer.parseInt(productAddMax.getText());
        int productMin = Integer.parseInt(productAddMin.getText());

        //Create new instance of the Product object based on data above
        Product productNew = new Product(incrementedProductID, productName, productPrice, productStock, productMin, productMax);
        Inventory.addProduct(productNew);

        //Check for errors and allow them to fix errors if Min value less than Max value
        if(productNew.checkForErrors() == 0){
            Stage stage;
            Parent root;
            stage = (Stage) productSaveButton.getScene().getWindow();
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

    //main search functionality
    public void getResultsHandlerParts(ActionEvent actionEvent) {
        //get text user has entered in textfield
        // System.out.println("Search button event worked!");
        String searchStringParts = partsSearchField.getText();

        //create new observable list and set it to the output of searchByPart method after passing in searchStringParts through loops
        ObservableList<Part> filteredPartsList = searchByPartName(searchStringParts);

        //If search does not return part name then look for part id
        if(filteredPartsList.size()==0){
            try {
                int id = Integer.parseInt(searchStringParts);
                Part searchPart = searchByPartID(id);
                if (searchPart!= null) {
                    filteredPartsList.add(searchPart);
                }
            }
            catch(NumberFormatException e)
            {
                //ignore
            }

        }
        productPartsTableView.setItems(filteredPartsList);

    }
    //search by part name
    private ObservableList<Part> searchByPartName(String partialPart){
        //System.out.println("Search method ran!");
        //ObservableList to return with filtered Parts
        ObservableList<Part> allPartsTempList = FXCollections.observableArrayList();

        //List from Inventory to walk through finding filtered Parts
        ObservableList<Part> allPartsList = Part.getAllParts();

        //Enhanced loop through allPartsList using temporary variable searchPart
        for(Part searchPart : allPartsList){
            if(searchPart.getName().toLowerCase().contains(partialPart)){
                allPartsTempList.add(searchPart);
                //System.out.println("If statement worked!");
            }
        }

        return allPartsTempList;
    }
    //search by part ID
    private Part searchByPartID(int id){
        //List from Inventory to walk through finding filtered Parts
        ObservableList<Part> allPartsList = Part.getAllParts();
        //Loop through list as long as less than the list size
        for(int i=0; i < allPartsList.size(); i++){
            Part searchPart = allPartsList.get(i);
            //if the id is equal than return
            if(searchPart.getId() == id) {
                return searchPart;
            }
        }
        return null;
    }

    //add a part to the associated array for a specific product
    @FXML
    private void addAssociatedPartHandler(ActionEvent event)    {
        //Get the selected item for the associated part
        Part selectedItem = productPartsTableView.getSelectionModel().getSelectedItem();
        //Add the part to the part associated array for this instance
        this.product.addAssociatedPart(selectedItem);
    }

    //delete products handler
    @FXML
    private void productsPartsDeleteButtonHandler(ActionEvent event){
        // Creating Alert window and dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Part From Product");
        alert.setContentText("Are you sure you want to delete the Part associated with the selected Product?");

        //Delete confirm button options
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // Select the product
            Part deleteSelectedAssociatedPart = productPartsTableView2.getSelectionModel().getSelectedItem();
            //Delete the part
            this.product.deleteAssociatedPart(deleteSelectedAssociatedPart);
        } else {
            // If they click Cancel they return to the application
        }

    }

}
