/* Author: Lantzos  
                Stergios
*/

package ece326.hw3;
import java.awt.*;
import javax.swing.*;


//A class for the text inside a single Box of Sudoku
public class SingleBoxText extends JPanel{
    
    
    JTextField BoxText; //The text of each Box
    
    boolean Starter = false; //Boolean if Its Starter (From URL Links)
    
    boolean IsEditable = true; //If We can edit this Box
    
    boolean IsRed = false; //If its a Collision Box
    
    boolean IsClicked = false; //If its Clicked
    
    int MyBoxID; //in which BoxID and GridID(=CellID)
    int myGridID;                       //im located
    
    //Constructor
    public SingleBoxText(String Data) {
        
        //Options for the panel's Text
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        //Options for each Text
        BoxText = new JTextField(Data);
        BoxText.setHorizontalAlignment(JTextField.CENTER);
        BoxText.setEditable(false); //Initialize all of them as non-editable 
        
        //Add BoxText in this SingleBoxText
        add(BoxText);
        
    }
    
    public void setWhite() {
        BoxText.setBackground(Color.WHITE);
    }
    
    public void setLightGray() {
        BoxText.setBackground(Color.LIGHT_GRAY);
    }

    
}
