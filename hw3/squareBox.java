/* Author: Lantzos  
                Stergios
*/

package ece326.hw3;
import java.awt.Color;
import java.awt.*;
import javax.swing.*;

//Class for a (3x3) squareBox
public class squareBox extends JPanel{
    
    //Variables
    private static final int GRID_SIZE = 9;
    SingleBoxText []squareGrid;
    
    //Constructor
    public squareBox() {
        //variables
        int i;
   
        //Options for each squareBox
        setBackground(Color.BLACK);
        setLayout(new GridLayout(GRID_SIZE/3, GRID_SIZE/3));
        
        //A textField array[], Layout is GridLayout so you see (3x3), for each squareBox
        squareGrid = new SingleBoxText[GRID_SIZE];
        
        //The options of the (3x3) TextFields of a single squareBox
        for (i = 0; i < GRID_SIZE; i++) {
            squareGrid[i] = new SingleBoxText("");
            add(squareGrid[i]);
        }
    }
}
