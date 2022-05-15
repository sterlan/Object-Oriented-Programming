/* Author: Lantzos  
                Stergios
*/

package ece326.hw3;
import java.awt.*;
import javax.swing.*;


//SUDOKU PANEL, CONTAINS BUTTONS, MENU, AND THE SINGLE BOXES
public final class Sudoku extends JFrame{
    
    //Variables
    private static final int frameHeight = 400;
    private static final int frameWidth = 500;
    
    SudokuPanel SudokuFrame; //The panel which contains the SingleBoxes
    ButtonsPanel ButtonsFrame; //The panel which contains The Buttons
    SudokuMenuBar SudokuMenu; //The menu
    
    //The constructor
    public Sudoku(){
            
        //THE ENTIRE FRAME
        super("S U D O K U    P U Z Z L E");
        
        //Options 
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(frameWidth,frameHeight));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //The menu
        SudokuMenu = new SudokuMenuBar();
        setMenuBar(SudokuMenu);

        //The SudokuPanel
        SudokuFrame = new SudokuPanel();
        getContentPane().add(SudokuFrame); 

        //The buttons
        ButtonsFrame = new ButtonsPanel();
        getContentPane().add(ButtonsFrame);
    }
}