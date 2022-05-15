/* Author: Lantzos  
                Stergios
*/

package ece326.hw3;
import java.util.LinkedList;

//Undo Class
public class Edit_Function {
    
    LinkedList<Manager> ll; //LinkedList for the Managers
    
    Manager ReturnManager;  //The Return Value
    
    
    //Constructor
    public Edit_Function(LinkedList<Manager> inputll) {
        this.ll = inputll;
    }
    
    //Method "UNDO"
    public Manager undo() {
        
        //Checks if List is Empty
        if (ll.isEmpty()) {
            ReturnManager = null;
        } else {
            
            
            //takes each time the head
            ReturnManager = new Manager(ll.getFirst().getBoxID(),ll.getFirst().getColumn(), ll.getFirst().GetData());
            
            //Removes him from the list
            ll.removeFirst();
        }
        
        return (ReturnManager);
    }
}
