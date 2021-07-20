package Inventory_System.Model;

import javafx.beans.property.SimpleIntegerProperty;

//class
public class InHouse extends Part{
    /**************************************ATTRIBUTES*******************************************/
    private final SimpleIntegerProperty partMachineID;

    /**********************************METHODS*************************************/
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);

        this.partMachineID = new SimpleIntegerProperty(machineID);
    }

    //Setter
    public void setPartMachineID(int partMachineID){
    this.partMachineID.set(partMachineID);
    }

    //Getter
    public int getPartMachineID() {
        return this.partMachineID.get();
    }

}





