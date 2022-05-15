/* Author: Lantzos  
                Stergios
*/

package ece326.hw3;

//The solver of the SudokuGame
public final class SudokuSolver {
    
    private final int GRID_SIZE = 9;
    
    //The solution
    int [][]SudokuBoard = new int[GRID_SIZE][GRID_SIZE];
    
    //The starter board
    public SudokuSolver(int [][]BoardToSolve) {
    
        int i,j;
        this.SudokuBoard = BoardToSolve;
        
        //Solver
        Solve();

        //Display
        //Display(); UNMARK THIS COMMENT FOR PRINT THE SOLUTION
    }
    
    //To Check if Data exists in this Row
    public boolean IsCorrectRow(int Row, int Data) {
        boolean IsCorrect = true;
        
        int i;
        
        for (i = 0; i < GRID_SIZE; i++) {
            if (SudokuBoard[Row][i] == Data) {
                IsCorrect = false;
                break;
            }
        }
    return (IsCorrect);
    }
    
    //To Check if Data exists in this Column
    public boolean IsCorrectCol(int Column, int Data) {
        boolean IsCorrect = true;
        
        int i;
        
        for (i = 0; i < GRID_SIZE; i++) {
            if (SudokuBoard[i][Column] == Data) {
                IsCorrect = false;
                break;
            }
        }
    return (IsCorrect);
    }
    
    //To Check if Data exists in this Box
    public boolean IsCorrectBox(int Row, int Column, int Data) {
        boolean IsCorrect = true;
        
        int StartRow = 0, EndRow = 0, StartCol = 0, EndCol = 0;
        int i,j;

        StartRow = (Row - Row % 3); //Finds the correct Row to start
	StartCol = (Column - Column % 3); //Finds the correct Column to start
        //Now the "finish-lines"
        EndRow = StartRow + 3;
        EndCol = StartCol + 3;
		
        for (i = StartRow; i < EndRow; i++) {
                for (j = StartCol; j < EndCol; j++) {
                        if (SudokuBoard[i][j] == Data) {
                                IsCorrect = false;
                                return (IsCorrect);
                        }
                }
        }
        
    return (IsCorrect);
    }
    
    //Sets the Data
    public void SetData(int Row, int Col, int Data) {
        SudokuBoard[Row][Col] = new Integer(Data);
    }
    
    //The solver. Needs to be recursive!!
    public boolean Solve() {
        
        int i,j;
        int Data;
    
        //Section
        for (i = 0; i < GRID_SIZE; i++) {
                for (j = 0; j < GRID_SIZE; j++) {
                    
                    //If found a starter
                    if (SudokuBoard[i][j] == -1) {
                        for (Data = 1; Data <= 9; Data++) { //Data has the values of a possible SingleBoxText value(1-9)
                            if ((IsCorrectRow(i, Data)) && (IsCorrectCol(j, Data)) && (IsCorrectBox(i,j,Data))) {
                                SetData(i,j,Data);
                                if (Solve()) { //Recursive cause sometimes the SetData sets the 
                                               //wrong data
                                    return true;
                                } else {
                                    SudokuBoard[i][j] = -1;
                                }
                            }
                        }
                        //If we reach here, return false(This sit has already the correct Data)
                        return false;
                    } 
                }

            }
        return true;
    }
    
    //PRINTS THE SOLUTION OF SUDOKU in the terminal
    public void Display() {
        
        System.out.println("SOLVED");
        int i,j;
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                if (j == GRID_SIZE -1) {
                    System.out.println(" "+SudokuBoard[i][j]);
                } else {
                    System.out.print(" "+SudokuBoard[i][j]);
                }
            }
        }
    }
    
}
