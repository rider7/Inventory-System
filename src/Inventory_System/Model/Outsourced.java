package Inventory_System.Model;

import javafx.beans.property.SimpleStringProperty;

//class
public class Outsourced extends Part{
    /**************************************ATTRIBUTES*******************************************/

    private final SimpleStringProperty partCompanyName;

    /**********************************METHODS*************************************/
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.partCompanyName = new SimpleStringProperty(companyName);
    }

    //Setter
    public void setPartCompanyName(String partCompanyName){
    this.partCompanyName.set(partCompanyName);
    }

    //Getter
    public String getPartCompanyName(){
    return this.partCompanyName.get();
    }

}


