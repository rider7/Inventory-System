package Inventory_System.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//class
public class Inventory {

    /**************************************ATTRIBUTES*******************************************/

    //Global IDs used to create new parts and products...starting at five because of sample data
    private static int partGlobalID = 4;
    private static int productGlobalID = 4;

    // - allParts:ObservableList<Part>
    public static ObservableList<Part> allPartsList = FXCollections.observableArrayList(
            new InHouse(1,"Monitor",30.00, 34, 1, 10, 1),
            new InHouse(2,"Keyboard",10.00, 38, 1, 30, 2),
            new InHouse(3,"Speaker",23.00, 78, 1, 50, 3),
            new Outsourced(4,"Mouse",12.00, 12, 1, 70,"test")
    );

    // - allProducts:ObservableList<Product>
    public static ObservableList<Product> allProductsList = FXCollections.observableArrayList(
            new Product(1,"Gaming Desktop System",1200.00, 34, 1, 5),
            new Product(2,"Home Desktop System",800.00, 24, 1, 3),
            new Product(3,"Enterprise Desktop System",1200.00, 34, 1, 5),
            new Product(4,"Designer Desktop System",1600.00, 64, 1, 10)
    );

    /**********************************METHODS*************************************/
    //add a new Part to allPartList
    public static void addPart(Part newPart){
        allPartsList.add(newPart);
    }

    //add a new Product to the allProductsList
    public static void addProduct(Product newProduct){
        allProductsList.add(newProduct);
    }

    public static Part lookupPart(int partId){
        return allPartsList.get(partId);
    }

    public static Product lookupProduct(int productId){
        return (Product)allProductsList.get(productId);
    }

    public ObservableList<Part> lookupPart(String partName){
        return allPartsList;
    }

    public ObservableList<Product> lookupProduct(String productName){
        return allProductsList;
    }

    public static void updatePart(Part selectedPart){
        allPartsList.set(selectedPart.getId()-1, selectedPart);
    }

    public static void updateProduct(Product selectedProduct){
        allProductsList.set(selectedProduct.getId()-1, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart){
        allPartsList.remove(selectedPart);
        return true;
    }

    public static boolean deleteProduct(Product selectedProduct){
        allProductsList.remove(selectedProduct);
        return true;
    }

    public static ObservableList<Part> getAllParts(){
        return allPartsList;
    }

    public static ObservableList<Product> getAllProducts(){ return allProductsList; }

    //Method used to get the part global ID
    public static int getPartGlobalID(){return partGlobalID;};

    //Method used to set new global ID count after incrementing in part controller
    public static void setPartGlobalID(int newCount) {
        partGlobalID = newCount;
    }

    //Method used to get the product global ID
    public static int getProductGlobalID(){return productGlobalID;}

    //Method used to set new global ID count after incrementing in product controller
    public static void setProductGlobalID(int newCount) {
        productGlobalID = newCount;
    }

}



