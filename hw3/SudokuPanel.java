/* Author: Lantzos  
                Stergios
*/

package ece326.hw3;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

//THE 3X3 PANEL SQUARES
public class SudokuPanel extends JPanel implements MouseListener{
    
    private static final int GRID_SIZE = 9;
    private static final int PanelWidth = 100;
    private static final int PanelHeight = 180;
    
    boolean GameStarted = false; //If the game is started
    
    SingleBoxText temp; 
    
    //The entire Panel has 9 squareBoxes
    final squareBox []Boxes = new squareBox[GRID_SIZE];
    
    int board[][] = new int[GRID_SIZE][GRID_SIZE]; //THE STARTER BOARD
    
    //Helpfull variables for finds the corect Row and Col of a Box 
    int BoxRow = 0;
    int BoxCol = 0;
    
    //Constructor
    public SudokuPanel() {
        
        //Variables
        int i;
        
        //Panel options
        setBackground(Color.LIGHT_GRAY);
        setLayout(new GridLayout(0, GRID_SIZE/3));
        setPreferredSize(new Dimension(PanelWidth, PanelHeight));
        
        //Creating the (9x9) entire panel
        for (i = 0; i < GRID_SIZE; i++) {
            Boxes[i] = new squareBox();
            for (int j = 0; j < GRID_SIZE; j++) {
                Boxes[i].squareGrid[j].BoxText.addMouseListener(this);
            }
            add(Boxes[i]); //add each (3x3)square into this            
        }
    } 

    @Override
    public void mouseClicked(MouseEvent me) {
        //Only if Game started
        if (GameStarted) {
            SudokuPaint();
        }
    }
    @Override
    public void mousePressed(MouseEvent me) {
        //Only if Game started
        if (GameStarted) {
            InitializeColors(board, Boxes);
        }
    }
    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
    
    //Each time Colors the Boxes with same Value into Yellow
    public void DataCheck(squareBox Boxes[], SingleBoxText BoxToCheck) {
        //Variables
        int i,j;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                if (Boxes[i].squareGrid[j].BoxText.getText().equals(BoxToCheck.BoxText.getText())) {
                    Boxes[i].squareGrid[j].BoxText.setBackground(Color.YELLOW); 
                }
            }
        }
    }

    //Given a [][]array of integers, this method finds
    //the right Box of an array of Boxes[]
    public int FindBox(int Row, int Col) {
        int BoxID = 0;
        
        if (Row >= 0 && Row < 3 && Col >= 0 && Col < 3) {
            //First Box
            BoxID = 0; 
            BoxRow = Row;
            BoxCol = Col;
        } else if (Row >= 0 && Row < 3 && Col >= 3 && Col < 6) {
            //Second Box
            BoxID = 1; 
            BoxRow = Row;
            BoxCol = Col - 3;
        } else if (Row >= 0 && Row < 3 && Col >= 6 && Col < 9) {
            //Third Box
            BoxID = 2; 
            BoxRow = Row;
            BoxCol = Col - 6;
        } else if (Row >= 3 && Row < 6 && Col >= 0 && Col < 3) {
            //Fourth Box
            BoxID = 3; 
            BoxRow = Row - 3;
            BoxCol = Col;
        } else if (Row >= 3 && Row < 6 && Col >= 3 && Col < 6) {
            //Fifth Box
            BoxID = 4; 
            BoxRow = Row - 3;
            BoxCol = Col - 3;
        } else if (Row >= 3 && Row < 6 && Col >= 6 && Col < 9) {
            //Sixth Box
            BoxID = 5; 
            BoxRow = Row - 3;
            BoxCol = Col - 6;
        } else if (Row >= 6 && Row < 9 && Col >= 0 && Col < 3) {
            //Seventh Box
            BoxID = 6;  
            BoxRow = Row - 6;
            BoxCol = Col;
        } else if (Row >= 6 && Row < 9 && Col >= 3 && Col < 6) {
            //Eigth Box
            BoxID = 7; 
            BoxRow = Row - 6;
            BoxCol = Col - 3;
        } else if (Row >= 6 && Row < 9 && Col >= 6 && Col < 9) {
            //Ninth Box
            BoxID = 8; 
            BoxRow = Row - 6;
            BoxCol = Col - 6;
        }
        return BoxID;
    }
    
    //Method for Initialize the Colors of the SudokuPanel each time (White, Light_Gray)
    public void InitializeColors(int [][]board, squareBox []Boxes) {
        int i, j;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                if (Boxes[i].squareGrid[j].Starter) { //If im starter (given value from URL link)
                    Boxes[i].squareGrid[j].setLightGray(); //Paint me with Gray
                } else {
                    Boxes[i].squareGrid[j].setWhite(); //Paint me with White
                }
            }
        }
    }
    
    //Given Row and Col of a 3x3 array,
    //finding the correct Column of a 1x9 array
    public int TranslateCordinates(int Row, int Col) {
        //Variables
        int ReturnCol = 0;
        
        if (Row == 0 && Col == 0) {
            ReturnCol = 0;
        } else if (Row == 0 && Col == 1) {
            ReturnCol = 1;
        } else if (Row == 0 && Col == 2) {
            ReturnCol = 2;
        } else if (Row == 1 && Col == 0) {
            ReturnCol = 3;
        } else if (Row == 1 && Col == 1) {
            ReturnCol = 4;
        } else if (Row == 1 && Col == 2) {
            ReturnCol = 5;
        } else if (Row == 2 && Col == 0) {
            ReturnCol = 6;
        } else if (Row == 2 && Col == 1) {
            ReturnCol = 7;
        } else if (Row == 2 && Col == 2) {
            ReturnCol = 8;
        }
    return(ReturnCol);
    }
   
    //This method paints YELLOW all the grids with same value 
    //as the Grid we "click"
    public void SudokuPaint() {
        SingleBoxText tempBoxText;
            int i,j;

            //Section for the YELLOW marks
            loop:
            for (i = 0; i < GRID_SIZE; i++) {
                for (j = 0; j < GRID_SIZE; j++) {
                    
                        if (Boxes[i].squareGrid[j].IsClicked) {
                            tempBoxText = Boxes[i].squareGrid[j];
                            if (Boxes[i].squareGrid[j].BoxText.getText().equals("")) {
                            } else {
                                //Not empty sit. Need to mark the other grids
                                //of SudokuPanel
                                DataCheck(Boxes, tempBoxText);
                                break loop;
                            }
                        } 
                    }
                }
            
            //For the RED CollisionBoxes
            for (i = 0; i < GRID_SIZE; i++) {
                for (j = 0; j < GRID_SIZE; j++) {
                    if (Boxes[i].squareGrid[j].IsRed) {
                        Boxes[i].squareGrid[j].BoxText.setBackground(Color.red);
                    }
                }
            }
            
        }
    
    //End of the class
}

