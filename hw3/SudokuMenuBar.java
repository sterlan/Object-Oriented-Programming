/* Author: Lantzos  
                Stergios
*/

package ece326.hw3;
import java.awt.*;

//THE SUDOKU MENU
public class SudokuMenuBar extends MenuBar {
    
    //MENU
    Menu SudokuMenu;
    
    //The levels of the menu
    MenuItem easyMode;
    MenuItem intermediateMode;
    MenuItem expertMode;
    
    
    //Constructor
    public SudokuMenuBar() {
    
        //Creating the menu
        SudokuMenu = new Menu("New Game");
        
        //The levels
        easyMode = new MenuItem("Easy");
        
        intermediateMode = new MenuItem("Intermediate");
        
        expertMode = new MenuItem("Expert");
       
        //Add the levels in the menu
        SudokuMenu.add(easyMode);
        SudokuMenu.add(intermediateMode);
        SudokuMenu.add(expertMode);
        
        //Add this menu into the main menu
        add(SudokuMenu);   
    }
}

