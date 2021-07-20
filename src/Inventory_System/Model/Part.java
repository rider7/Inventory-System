package Inventory_System.Model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import static Inventory_System.Model.Inventory.allPartsList;

//class
public abstract class Part {
    /**************************************ATTRIBUTES*******************************************/

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleIntegerProperty stock;
    private SimpleIntegerProperty min;
    private SimpleIntegerProperty max;


    /**********************************METHODS*************************************/
    //constructor
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }


    public void setId(SimpleIntegerProperty id){
        this.id = id;
    }

    public void setName(SimpleStringProperty name){
        this.name = name;
    }

    public void setPrice(SimpleDoubleProperty price){
        this.price = price;
    }

    public void setStock(SimpleIntegerProperty stock){
        this.stock = stock;
    }

    public void setMin(SimpleIntegerProperty min){
        this.min = min;
    }

    public void setMax(SimpleIntegerProperty max){
        this.max = max;
    }

    public  int getId(){
        return id.get();
    }

    public String getName(){
        return name.get();
    }

    public double getPrice(){
        return price.get();
    }

    public int getStock(){
        return stock.get();
    }

    public int getMin(){
        return min.get();
    }

    public int getMax(){
        return max.get();
    }

    public static ObservableList<Part> getAllParts(){
        return allPartsList;
    }

    public IntegerProperty getIdProp() {
        return this.id;
    }

    public IntegerProperty getStockProp() {
        return this.stock;
    }

    public IntegerProperty getMinProp() {
        return this.min;
    }

    public IntegerProperty getMaxProp() {
        return this.max;
    }

    public DoubleProperty getPriceProp() {
        return this.price;
    }

    public StringProperty getNameProp() { return this.name; }

    public int checkForErrors(){
        int errorNumber;
        if(this.getMin() >= this.getMax()){
            //System.out.println("Min value should be less than Max value. Please update values appropriately and click Save.");
            errorNumber = 1;
        }else if(this.getMin() <= this.getMax()){
            //System.out.println("Max value should be more than Min value. Please update values appropriately and click Save.");
            errorNumber = 2;
        } else{
            errorNumber = 0;
        }
        return errorNumber;

    }

}
