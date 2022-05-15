/* Author: Lantzos  
                Stergios
*/

package ece326.hw3;

//A manager containts the Data, the BoxID and the column where data is located
public class Manager {

    public int BoxID;
    public int Column;
    public String Data;
    
    //Constructor
    public Manager(int BoxID, int Column, String Data) {
        
        this.BoxID = BoxID;
        this.Column = Column;
        this.Data = Data;
    }
    
    
    public int getBoxID() {
        return this.BoxID;
    }
    public int getColumn() {
        return this.Column;
    }
    public String GetData() {
        return this.Data;
    }

}

