package Inventory_System.View_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import Inventory_System.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;

public class Main_Screen_Controller implements Initializable {

    /**************************************ATTRIBUTES*******************************************/
    //FXML Table 1
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partsIDColumn;
    @FXML
    private TableColumn<Part, String> partsNameColumn;
    @FXML
    private TableColumn<Part, Integer> partsInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partsPriceColumn;

    //FXML Table 2
    @FXML
    private TextField partsSearchField;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productsIDColumn;
    @FXML
    private TableColumn<Product, String> productsNameColumn;
    @FXML
    private TableColumn<Product, Integer> productsInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productsPriceColumn;

    //FXML Buttons
    @FXML
    private Button addPartsButton;
    @FXML
    private Button modifyPartsButton;
    @FXML
    private Button partsDeleteButton;
    @FXML
    private Button partsSearchButton;
    @FXML
    private Button addProductsButton;
    @FXML
    private Button productsDeleteButton;
    @FXML
    private Button modifyProductsButton;
    @FXML
    private Button productsSearchButton;
    @FXML
    private Button exitMainButton;

    //FXML TextFields
    @FXML
    private TextField productsSearchField;

    //Variables
    private static int selPart;
    private static int selProduct;


    /**********************************METHODS*************************************/

    //Method to initialize and setup the table with data
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //sets the columns parts
        partsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partsInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        //set the items on the table from the observable list for parts
        partsTableView.setItems(Inventory.getAllParts());

        //sets the columns parts
        productsIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productsPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productsInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));

        //set the items on the table from the observable list for parts
        productsTableView.setItems(Inventory.getAllProducts());
    }

    //Method to change scene to the Add_Part.xml
    @FXML
    private void addPartSceneHandler(ActionEvent event) throws IOException {
        //System.out.println("addPartButton Click Worked");
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "Add_Part.fxml"));
        Stage stage = (Stage) addPartsButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Method to change scene to Modify_Part.fmxl with populated data that is selected to Add Part
    @FXML
    private void modifyPartSceneHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) modifyPartsButton.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "Modify_Part.fxml"));
        root =loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        //if statement to determine if it is a InHouse or Outsourced part using instanceof
        if(partsTableView.getSelectionModel().getSelectedItem() instanceof InHouse){
            Modify_Part_Controller controller = loader.getController();
            InHouse inhouse= (InHouse) partsTableView.getSelectionModel().getSelectedItem();
            controller.setInHousePart(inhouse);

        }
        else{
            Modify_Part_Controller controller = loader.getController();
            Outsourced outsourced= (Outsourced) partsTableView.getSelectionModel().getSelectedItem();
            controller.setOutsourcedPart(outsourced);
        }
    }

    //Method to change scene to add product scene
    @FXML
    private void addProductSceneHandler(ActionEvent event) throws IOException {
        //System.out.println("addProductButton Click Worked");
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "Add_Product.fxml"));
        Stage stage = (Stage) addProductsButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Method to change scene to Modify_Product.fxml scene
    @FXML
    private void modifyProductSceneHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) modifyProductsButton.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "Modify_Product.fxml"));
        root =loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Modify_Product_Controller controller = loader.getController();
        Product product = productsTableView.getSelectionModel().getSelectedItem();
        controller.setProduct(product);
    }

    //Method to search the list of Parts
    public void getResultsHandlerParts(ActionEvent actionEvent) {
        //Used to get the text entered by the user
        String searchStringParts = partsSearchField.getText();

        //Creates new observable list and set it to the output of searchByPart method after passing in searchStringParts through loops
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
                    //No catch needed
                }
        }
        partsTableView.setItems(filteredPartsList);
    }

    //Method used to search the list of Part by Name specifically
    private ObservableList<Part> searchByPartName(String partialPart){
        //ObservableList to return with filtered Parts
        ObservableList<Part> allPartsTempList =FXCollections.observableArrayList();

        //List from Inventory to walk through finding filtered Parts
        ObservableList<Part> allPartsList = Part.getAllParts();

        //Enhanced loop through allPartsList using temporary variable searchPart
        for(Part searchPart : allPartsList){
            if(searchPart.getName().toLowerCase().contains(partialPart)){
                allPartsTempList.add(searchPart);
            }
        }
        return allPartsTempList;
}

    //Method used to search the list of Parts by ID specifically
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

    //Method used to search Products
    public void getResultsHandlerProducts(ActionEvent actionEvent) {
        //Used to get the text entered by the user
        String searchStringProducts = productsSearchField.getText();

        //create new observable list and set it to the output of searchByProduct method after passing in searchString through loops
        ObservableList<Product> filteredProductsList = searchByProductName(searchStringProducts);

        //If search does not return part name then look for part id
        if(filteredProductsList.size()==0){
            try {
                int id = Integer.parseInt(searchStringProducts);
                Product searchProduct = searchByProductID(id);
                if (searchStringProducts!= null) {
                    filteredProductsList.add(searchProduct);
                }
            }
            catch(NumberFormatException e)
            {
                //Not needed
            }
        }
        productsTableView.setItems(filteredProductsList);
    }

    private ObservableList<Product> searchByProductName(String partialProduct){
        //ObservableList to return with filtered Products
        ObservableList<Product> allProductsTempList =FXCollections.observableArrayList();

        //List from Inventory to walk through finding filtered Products
        ObservableList<Product> allProductsList = Product.getAllProducts();

        //Enhanced loop through allPartsList using temporary variable searchPart
        for(Product searchStringProducts : allProductsList){
            if(searchStringProducts.getName().toLowerCase().contains(partialProduct)){
                allProductsTempList.add(searchStringProducts);
            }
        }
        return allProductsTempList;
    }

    //Method to search for a Product by ID specifically
    private Product searchByProductID(int id){
        //List from Inventory to walk through finding filtered Parts
        ObservableList<Product> allProductsList = Product.getAllProducts();
        //Loop through list as long as less than the list size
        for(int i=0; i < allProductsList.size(); i++){
            Product searchByProductID = allProductsList.get(i);
            //if the id is equal than return
            if(searchByProductID.getId() == id) {
                return searchByProductID;
            }
        }
        return null;
    }
    //Method to Exit
    @FXML
    private void exitButtonEventHandler(ActionEvent event) {
        // Creating Alert window and dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Exit Application");
        alert.setContentText("Are you sure you want to exit the application?");

        //Exit confirm button options
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) exitMainButton.getScene().getWindow();
            stage.close();
        } else {
            // If they click Cancel they return to the application
        }
    }

    //Method to delete Part
    @FXML
    private void partsDeleteButtonHandler(ActionEvent event) {
        // Creating Alert window and dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Are you sure you want to delete the selected part?");

        //Delete confirm button options
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        // Select the part
        Part deleteSelectedPart = partsTableView.getSelectionModel().getSelectedItem();
        //Delete the part
        //allPartsList.remove(deleteSelectedPart);
        Inventory.deletePart(deleteSelectedPart);
        } else {
            // If they click Cancel they return to the application
        }
        }

        //Method to delete Products
    @FXML
    private void productsDeleteButtonHandler(ActionEvent event) {
        // Creating Alert window and dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Are you sure you want to delete the selected product?");

        //Delete confirm button options
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        // Select the product
        Product deleteSelectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        //Delete the part
        //allProductsList.remove(deleteSelectedProduct);
        Inventory.deleteProduct(deleteSelectedProduct);
        } else {
            // If they click Cancel they return to the application
        }
    }

    public static int selectedPart(){return selPart;}
    public static int selectedProduct(){return selProduct;}
    }


