/* Author: Lantzos  
                Stergios
*/

//ONE EXAMPLE
//easyMODE: 6..94...193.........1.6...2.....1.533.....7..4.5.........3......927.5...7..61....
//intermediateMODE:...6..73.62.73.5.9...2...6.59...2....7.91......856..7.9.....3.......8..........18
//expertMODE: ...2..6.55.3........2...3....1.7.93...9.3..8.7.....1...9.12.7..........6.8.6.....

package ece326.hw3;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

///The class of SUDOKU IMPLEMENTATION///
public final class SudokuPuzzle extends JFrame implements ActionListener{
    
    //Variables
    private static final int GRID_SIZE = 9;
    
    int [][]StarterBoard = new int[GRID_SIZE][GRID_SIZE]; //The sudoku board starter
    int [][]Board = new int[GRID_SIZE][GRID_SIZE]; //The board
    int [][]BoardSolved = new int[GRID_SIZE][GRID_SIZE]; //The Solved one
    
    int BoardRow, BoardColumn;
    
    String Choice = null; //MenuChoice
    
    String ButtonChoice = null; //ButtonChoice
    
    Boolean GameFinished; //When game is finished
    
    URL URLmodeLevel = null; //The URL mode -> opens only with VPN
    String DataURLTransfer = ""; //The string we save tha data from URL
    
    Sudoku SudokuGame; //Our SudokuPanel
    
    boolean GameStarted = false; //If game started
    
    LinkedList<Manager> ll; //My LinkedList for undo
    Edit_Function edit; //Undo Function
    
    SudokuSolver Solver; //The Solver
    
    Manager TempManager; 
    
    //The last message when game is Completed
    JDialog IsFinished;
    
    //Input from keyBoard
    String KeyboardIn;
    boolean NewCellAdd = false;
    int BoxID;
    int CellID;
    //For saving the BoxText when we do BackSpace or Delete
    String ManagerData;
    //Constructor
    public SudokuPuzzle() {

        //Variables
        int i,j;
        
        //"Builds" the entire Frame of Sudoku(Contains ButtonsPanel, SudokuPanel, MenuBar)
        SudokuGame = new Sudoku(); 
        SudokuGame.pack();
        SudokuGame.setVisible(true);
        
        //Options for the menu buttons
        SudokuGame.SudokuMenu.easyMode.addActionListener(this);
        SudokuGame.SudokuMenu.intermediateMode.addActionListener(this);
        SudokuGame.SudokuMenu.expertMode.addActionListener(this);
        
        //ACTION LISTENER FOR ALL THE BUTTONS
        for (i = 0; i < 13; i++) {
            
            //IF ITS CHECKBOX
            if (i == 11) {
                SudokuGame.ButtonsFrame.ChBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        //Only if GameStarted and its not finished
                        if ((GameStarted) && (!GameFinished)) {
                            ButtonChoice = ae.getActionCommand();
                        }
                    }
                });
                
                SudokuGame.ButtonsFrame.ChBox.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        //Only if GameStarted and its not finished
                        if ((GameStarted) && (!GameFinished)) {
                            //Verify against Solution is checked
                            if (SudokuGame.ButtonsFrame.ChBoxIsClicked) {
                                CheckAgainstSolutionDisplay(BoardSolved, Board, StarterBoard);
                            } else {
                                SudokuGame.SudokuFrame.InitializeColors(Board, SudokuGame.SudokuFrame.Boxes);
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
                        if ((GameStarted) && (!GameFinished)) {
                            //Each time we change the state of CheckBox
                            
                            //If it was clicked Before
                            if (SudokuGame.ButtonsFrame.ChBoxIsClicked) {
                                //Now, we clicked, so its un-clicked
                                SudokuGame.ButtonsFrame.ChBoxIsClicked = false;
                            } else {
                                //else, it was un-clicked, so now its clicked
                                SudokuGame.ButtonsFrame.ChBoxIsClicked = true;
                            }
                        }
                    }
                    @Override
                    public void mouseReleased(MouseEvent me) {}

                    @Override
                    public void mouseEntered(MouseEvent me) {}

                    @Override
                    public void mouseExited(MouseEvent me) {}
                });
                
            } 

            //ELSE ITS A SINGLE BUTTON
            else {
                SudokuGame.ButtonsFrame.buttonsGrid[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if ((GameStarted) && (!GameFinished)) {
                            ButtonChoice = ae.getActionCommand();
                        }
                    }
                });
                
                SudokuGame.ButtonsFrame.buttonsGrid[i].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        int i,j;
                        loop:
                        if ((GameStarted) && (!GameFinished)){ //ONLY IF GAME IS STARTED
                            
                            //RUBIK BUTTON
                            if (ButtonChoice.equals("rubik")) {
                                //Copy the Solved Board into JTextField's of Sudoku's Panel
                                SudokuArrayData(SudokuGame.SudokuFrame.Boxes, BoardSolved);
                                CopyBoardData(Board, BoardSolved);
                                SudokuGame.SudokuFrame.InitializeColors(Board, SudokuGame.SudokuFrame.Boxes);
                                
                                //Disable the Buttons and all the Grids
                                DisableButtons(); 
                                DisableGrids();
                                return;
                                
                            } 
                            //UNDO BUTTON
                            else if (ButtonChoice.equals("undo")) {
                                    
                                    //The undo Section
                                    TempManager = edit.undo();
                                    if (TempManager == null) {
                                        //Undo cant be done
                                    } else {
                                        //Else, undo is done
                                        RestartValues(SudokuGame.SudokuFrame.Boxes);
                                        SudokuGame.SudokuFrame.Boxes[TempManager.getBoxID()].squareGrid[TempManager.Column].BoxText.setText(TempManager.GetData());

                                        //Check for Collisions
                                        for (i = 0; i < GRID_SIZE; i++) {
                                            for (j = 0; j < GRID_SIZE; j++) {
                                                if (CollisionsCheck(SudokuGame.SudokuFrame.Boxes)) {
                                                    //We have collisions
                                                } else {
                                                    //We do not have collisions,
                                                    //Every SingleBoxText which was "IsRed = true",
                                                    //turns now false
                                                    RestartValues(SudokuGame.SudokuFrame.Boxes);
                                                        
                                                } 
                                                
                                                //We also need to check if CheckBox is Clicked
                                                if (SudokuGame.ButtonsFrame.ChBoxIsClicked) {
                                                    CheckAgainstSolutionDisplay(BoardSolved, Board, StarterBoard);
                                                } else {
                                                    SudokuGame.SudokuFrame.InitializeColors(Board, SudokuGame.SudokuFrame.Boxes);
                                                }
                                                //Paint the entire Frame(Cyan, Reds, Yellows)
                                                SudokuGame.SudokuFrame.SudokuPaint();  
                                            }
                                        }
                                    }
                                break loop;
                            } 

                        //FOR ALL THE OTHERS
                        for (i = 0; i < GRID_SIZE; i++) {
                            for (j = 0; j < GRID_SIZE; j++) { 

                                //CHECK WHICH GRID IS CLICKED
                                if (SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].IsClicked && (!SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].Starter)) {

                                    //ERASER BUTTON
                                    if (ButtonChoice.equals("eraser")) {
                                        String Data = new String(SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].BoxText.getText().toString());
                                        SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].BoxText.setText("");//deletes the content

                                        //add the old value to LinkedList for Undo
                                        TempManager = new Manager(i,j,Data);
                                        ll.addFirst(TempManager);

                                        TextFieldsToBoard(); //copy the JTextfields in to current board
                                        
                                        //Restart the Values in case we had collisions
                                        RestartValues(SudokuGame.SudokuFrame.Boxes);
                                        
                                        //Checks if CheckBox is clicked
                                        if (SudokuGame.ButtonsFrame.ChBoxIsClicked) {
                                            CheckAgainstSolutionDisplay(BoardSolved, Board, StarterBoard);
                                        } else {
                                            SudokuGame.SudokuFrame.InitializeColors(Board, SudokuGame.SudokuFrame.Boxes);
                                        }
                                        SudokuGame.SudokuFrame.SudokuPaint();
                                    }

                                    //ELSE IS A SINGLE BUTTON FROM 1-9
                                    else {
                                        if (SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].IsEditable) {
                                            //If we can edit the SingleBoxText we clicked on
                                            if (new Integer(ButtonChoice) >= 1 && new Integer(ButtonChoice) <= 9) { //For 1-9 buttons
                                                
                                                //Takes the old Value
                                                TempManager = new Manager(i,j,SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].BoxText.getText());
                                                //Add it to the undo LinkedList
                                                ll.addFirst(TempManager); 
                                                
                                                //Restarts the values if we had collisions before
                                                RestartValues(SudokuGame.SudokuFrame.Boxes);
                                                
                                                //Sets the new Value
                                                SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].BoxText.setText(ButtonChoice); //Set the button

                                                //Copy the SudokuFrame's values to the board
                                                TextFieldsToBoard(); 
                                                
                                                //Collision Check
                                                if (CollisionsCheck(SudokuGame.SudokuFrame.Boxes)) {
                                                    //We have collision here
                                                } else {
                                                    //No collisions
                                                    RestartValues(SudokuGame.SudokuFrame.Boxes);
                                                    
                                                    //Need to check if game is FInished
                                                    if (IsGameFinished()) {
                                                        DisableGrids();
                                                        DisableButtons();
                                                        showDialog();
                                                    }
                                                }
                                                if (SudokuGame.ButtonsFrame.ChBoxIsClicked) {
                                                    CheckAgainstSolutionDisplay(BoardSolved, Board, StarterBoard); 
                                                } else {
                                                    SudokuGame.SudokuFrame.InitializeColors(Board, SudokuGame.SudokuFrame.Boxes);
                                                    }
                                                SudokuGame.SudokuFrame.SudokuPaint();
                                            } else {
                                                System.out.println("Non acceptable move!"); 
                                            }
                                        return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                @Override
                public void mousePressed(MouseEvent me) {}

                @Override
                public void mouseReleased(MouseEvent me) {}

                @Override
                public void mouseEntered(MouseEvent me) {}

                @Override
                public void mouseExited(MouseEvent me) {}
                });      
            }
        }
        
        //KeyListener for each BoxText 
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                SingleBoxText temp = SudokuGame.SudokuFrame.Boxes[i].squareGrid[j];
                temp.BoxText.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent evt) {}


                    @Override
                    public void keyPressed(KeyEvent evt) {
                        //Add the old value in the UNDOLinkedList
                        
                        if ((GameStarted) && (!GameFinished)){
                            if (!temp.Starter) {
                             //ONLY IF GAME IS STARTED 
                           
                            
                             //BACKSPASE OR DELETE KEY
                            if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_DELETE) {
                                KeyboardIn = "Delete";
                                //WE TAKE THE STRING FOR DELETE, BEFORE A KEY IS PRESSED and we lose the content
                                ManagerData = temp.BoxText.getText();
                            }
                            else {
                                //CHECK FOR KEYS 1-9
                                if (evt.getKeyCode() == KeyEvent.VK_1) {
                                    KeyboardIn = "1"; 
                                    NewCellAdd = true;
                                } else if (evt.getKeyCode() == KeyEvent.VK_2) {
                                    KeyboardIn = "2";
                                    NewCellAdd = true;
                                } else if (evt.getKeyCode() == KeyEvent.VK_3) {
                                    KeyboardIn = "3";  
                                    NewCellAdd = true;
                                } else if (evt.getKeyCode() == KeyEvent.VK_4) {
                                    KeyboardIn = "4";   
                                    NewCellAdd = true;
                                } else if (evt.getKeyCode() == KeyEvent.VK_5) {
                                    KeyboardIn = "5"; 
                                    NewCellAdd = true;
                                } else if (evt.getKeyCode() == KeyEvent.VK_6) {
                                    KeyboardIn = "6";
                                    NewCellAdd = true;
                                } else if (evt.getKeyCode() == KeyEvent.VK_7) {
                                    KeyboardIn = "7";
                                    NewCellAdd = true;
                                } else if (evt.getKeyCode() == KeyEvent.VK_8) {
                                    KeyboardIn = "8"; 
                                    NewCellAdd = true;
                                } else if (evt.getKeyCode() == KeyEvent.VK_9) {
                                    KeyboardIn = "9";
                                    NewCellAdd = true;
                                } else {
                                    //Nothing to do
                                    //NO VALID KEY
                                    KeyboardIn = "";
                                    NewCellAdd = false;
                                    }
                                }   

                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent ke) {
                        
                        //THE ID'S
                        BoxID = temp.MyBoxID;
                        CellID = temp.myGridID;
                        
                        if (temp.IsEditable) { //IF IM EDITABLE (WHEN COLLISIONS et.c)
                            if (NewCellAdd) { //AND IF I GOT MESSAGE FOR A NEW CELL ADD
                                
                                BoxToBoard(BoxID, CellID);
                                
                                //DELETE OR BACKSPACE
                                if (KeyboardIn.equals("Delete")) {
                                    if (Board[BoardRow][BoardColumn] == -1) {
                                    } else {
                                        //Add the manager with the string we took in "KeyPressed"
                                        TempManager = new Manager(BoxID,CellID, ManagerData);
                                        ll.addFirst(TempManager);

                                        //Deletes the content
                                        temp.BoxText.setText("");

                                        //Updates the board
                                        TextFieldsToBoard(); 
                                        
                                        //Restart the Values in case we had collisions
                                        RestartValues(SudokuGame.SudokuFrame.Boxes);

                                        //Checks if CheckBox is clicked
                                        if (SudokuGame.ButtonsFrame.ChBoxIsClicked) {
                                            CheckAgainstSolutionDisplay(BoardSolved, Board, StarterBoard);
                                        } else {
                                            SudokuGame.SudokuFrame.InitializeColors(Board, SudokuGame.SudokuFrame.Boxes);
                                        }
                                        //Paint the Sudoku
                                        SudokuGame.SudokuFrame.SudokuPaint();
                                    }
                                    
                                //ELSE ITS SINGLE KEY 1-9
                                } else {
                                    if (KeyboardIn.equals("1") || KeyboardIn.equals("2") || KeyboardIn.equals("3") || KeyboardIn.equals("4") || 
                                        KeyboardIn.equals("5") || KeyboardIn.equals("6") || KeyboardIn.equals("7") || KeyboardIn.equals("8") ||
                                        KeyboardIn.equals("9")) {
                                        
                                        //RESTART THE VALUES IN CASE WE HAD COLLISIONS BEFORE
                                        RestartValues(SudokuGame.SudokuFrame.Boxes);

                                        //STORES THE OLD VALUE OF THIS CELL
                                        ManagerData = String.valueOf(Board[BoardRow][BoardColumn]);
                                        if (ManagerData.equals("-1")) { //IF THIS CELL WAS EMPTY
                                            ManagerData = "";
                                        }
                                        TempManager = new Manager(BoxID,CellID, ManagerData);
                                        ll.addFirst(TempManager); 

                                        //New value
                                        temp.BoxText.setText("");
                                        temp.BoxText.setText(KeyboardIn);

                                        //THE UPDATE OF BOARD
                                        TextFieldsToBoard(); 
                                        
                                        //COLLISIONS CHECK
                                        if (CollisionsCheck(SudokuGame.SudokuFrame.Boxes)) {
                                        //We have collision here
                                        } else {
                                            //No collisions
                                            //Need to check if game is FInished
                                            if (IsGameFinished()) {
                                                DisableGrids();
                                                DisableButtons();
                                                showDialog();
                                            }
                                        }

                                        if (SudokuGame.ButtonsFrame.ChBoxIsClicked) {
                                            CheckAgainstSolutionDisplay(BoardSolved, Board, StarterBoard); 
                                        } else {
                                            SudokuGame.SudokuFrame.InitializeColors(Board, SudokuGame.SudokuFrame.Boxes);
                                            }
                                        SudokuGame.SudokuFrame.SudokuPaint();
                                    }
                                }
                            } else {
                                temp.BoxText.setText("");
                            }
                            
                        }
                    }
                });

                //MouseListener for each BoxText   
                temp.BoxText.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent me) {}

                    @Override
                    public void mousePressed(MouseEvent me) {
                        
                        //Make all others "IsClicked" to false
                        int i,j;
                            for (i = 0; i < GRID_SIZE; i++) {
                                for (j = 0; j < GRID_SIZE; j++) {
                                    if (SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].IsClicked) {
                                        SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].IsClicked = false; 
                                    }
                                }
                            }
                            
                            //Make me "IsClicked"  true
                            temp.IsClicked = true;
                            
                            if (GameStarted) {
                                if (SudokuGame.ButtonsFrame.ChBoxIsClicked) {
                                    CheckAgainstSolutionDisplay(BoardSolved, Board, StarterBoard);
                                } else {
                                    SudokuGame.SudokuFrame.InitializeColors(StarterBoard, SudokuGame.SudokuFrame.Boxes);
                                    }
                                //If i'm red, i want to be painted Red and not Yellow
                                if (temp.IsRed) {
                                    temp.BoxText.setBackground(Color.red);
                                } else {
                                    temp.BoxText.setBackground(Color.YELLOW);
                                }
                            }
                        }
                        
                    @Override
                    public void mouseReleased(MouseEvent me) {}

                    @Override
                    public void mouseEntered(MouseEvent me) {}

                    @Override
                    public void mouseExited(MouseEvent me) {}
                });
            
            }
        } 
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        //Choice of the level
        Choice = ae.getActionCommand(); 
        
        //Section of Modes and URL links
        try {
            if (Choice.equals("Easy")) {

                //EasyMode
                URLmodeLevel = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=easy");
                System.out.println("MODE: easy");

            } else if (Choice.equals("Intermediate")) {

                //InterMediateMode
                URLmodeLevel = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=intermediate");
                System.out.println("MODE: intermediate");

            } else if (Choice.equals("Expert")) {
                
                //ExpertMode
                URLmodeLevel = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=expert");
                System.out.println("MODE: expert");
            }
            
        } catch (MalformedURLException ex) {
            System.out.println("ERROR opening URL: " +ex);
        }
        
        //Now we need to read what we get from URL
        try {
            
            //initialize my variables
            ll = null; 
            IsFinished = null;
            
            URLreader(); ////Transfer the data, ONLY WORKS WITH VPN
            
            BoardTransferingData(); //Transfering the data URL string into
                                    //the board[][]
                    
            //Copy the StarterBoard to BoardSolved, so we can solve him
            CopyBoardData(BoardSolved, StarterBoard);
            
            //Copy the StarterBoard. 
            //Now we gonna work with Board[][] only
            CopyBoardData(Board, StarterBoard);
            CopyBoardData(SudokuGame.SudokuFrame.board, StarterBoard);
            
            //Enable the Grids
            EnableGrids();
            //Enable the Buttons
            EnableButtons();
            //Restart the Values
            RestartValues(SudokuGame.SudokuFrame.Boxes);
            
            SudokuArrayData(SudokuGame.SudokuFrame.Boxes, StarterBoard); //Transfering the data from the 
                                                                         //board[][] into the Sudoku's Panel JTextFields
            
            ll = new LinkedList<>(); //Each time we create new LinkedList for undo actions
            //Solve the Sudoku -> save the values to BoardSolved
            Solver = new SudokuSolver(BoardSolved);             
            //New edit Function for the LinkedList of undo actions
            edit = new Edit_Function(ll);
            
            //Game already started :)
            GameStarted = true;       
            GameFinished = false; 
            
            //Verify Against Solution must be un-clicked when a new game starts
            SudokuGame.ButtonsFrame.ChBoxIsClicked = false;
            
            SudokuGame.SudokuFrame.GameStarted = true; //For the SudokuPanel
            SetIDs();
            StarterValues(SudokuGame.SudokuFrame.Boxes); //Sets the startedValues into NO-editable
            
            System.out.println("Data: "+DataURLTransfer);        
        } catch (IOException e) {
            System.out.println("IOException: "+e);
        }
       
    }
    
    //Transfering the data from URL to a string
    public final void URLreader() throws IOException {
        
        //Variables
        String tempReader;
        
        //Initialize empty String
        DataURLTransfer = "";
        
        //data Trasnfering
        try {
            BufferedReader DataIn = new BufferedReader(new InputStreamReader(this.URLmodeLevel.openStream()));
            while ((tempReader = DataIn.readLine()) != null) {
                DataURLTransfer += tempReader;
            }   
        } catch (IOException e) {}
    }
    
    //Trasnfering the data from URLstring into a int[][] array
    public void BoardTransferingData() {
    
        int i,j; 
        int pos = 0;

        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                if (DataURLTransfer.charAt(pos) == '.') {
                    StarterBoard[i][j] = -1;
                } else {
                    StarterBoard[i][j] = (DataURLTransfer.charAt(pos) - '0');
                }
                pos+=1;
            }
        }
    }
    
    //Transfering data into the SUDOKU JTextFields, Box by Box
    public void SudokuArrayData(squareBox []Boxes, int [][]Board) {
        //Variables
        int countBoxes,i,j;

        countBoxes = 0;

        for (i = 0; i < GRID_SIZE; i+=3) {
            for (j = 0; j < GRID_SIZE; j+=3) {
                squareBoxDataCopy(Boxes[countBoxes], Board, i, j);
                countBoxes+=1;
            }
        }
        
    }
    
    //Copy the data from the SUDOKU-TABLE(=board[][]), into the sudokuPanel TextFields
    public void squareBoxDataCopy(squareBox Box, int [][]Board, int BoardRowStart, int ColStart) {
        //Variables
        int Pos;
        int j, k;
        
        Pos = 0;
        
        for (j = BoardRowStart; j < BoardRowStart + 3; j++) {
            for (k = ColStart; k < ColStart + 3; k++) {
                setData(Box.squareGrid[Pos], Board[j][k]);
                Pos++;
            }
        }
    }
    
    //Set the correct Data in a TextField
    public void setData(SingleBoxText squareGrid, int DataValue) {
        
        if (DataValue == -1) {
            squareGrid.BoxText.setText(""); 
            squareGrid.BoxText.setBackground(Color.WHITE);
        } else {
            squareGrid.BoxText.setText(""+DataValue);
            squareGrid.BoxText.setBackground(Color.LIGHT_GRAY);
        }
    }
    
    //Set the StarterValues from URL string into NON-EDITABLE
    public void StarterValues(squareBox Boxes[]) {
        //Variables
        int i, j;
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                if (Boxes[i].squareGrid[j].BoxText.getText().equals("")) {
                    Boxes[i].squareGrid[j].BoxText.setEditable(true);
                    Boxes[i].squareGrid[j].Starter = false;
                } else {
                    Boxes[i].squareGrid[j].Starter = true;
                    Boxes[i].squareGrid[j].BoxText.setEditable(false);
                }
            }
        }
    }
    
    //Check for Collisions(Same value inside a Box || same value in the same Row || same value in the same Column)
    public boolean CollisionsCheck(squareBox Boxes[]) {
        
        int i, j;
        SingleBoxText CollisionOne;
        int SelfPos = 0;
        boolean GridNeedToBeChanged = false;
        int meAsBox = 0;

        //Update the Board
        TextFieldsToBoard();
        
        loop:
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                //Check if its not starter
                if (SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].Starter == false) { 
                        CollisionOne = SudokuGame.SudokuFrame.Boxes[i].squareGrid[j];
                        SelfPos = j;

                        //Call first to check collisions inside the Box
                        if (CollisionBoxCheck(CollisionOne, Boxes[i], SelfPos)){
                            GridNeedToBeChanged = true;
                        }

                        //Now, its time to Check for collisions in Rows, Columns
                        if (CollisionBoardRowColCheck(SudokuGame.SudokuFrame.Boxes, i, CollisionOne, j)) {
                            GridNeedToBeChanged = true;
                        }
                        //Paints the Sudoku
                        SudokuGame.SudokuFrame.SudokuPaint();
                        if (GridNeedToBeChanged) {
                            break loop;
                        }
                    }                     
                }
            }
        
        //Save my ID
        meAsBox = i;
        
        if (GridNeedToBeChanged) {
            //Gonna set all Grid's Value "IsEditable" to false, except me
            for (i = 0; i < GRID_SIZE; i++) {
                for (j = 0; j < GRID_SIZE; j++) {
                    if (i == meAsBox && j == SelfPos) {
                        //its me, im the onlye one who is Editable
                        //System.out.println("Me ekana kokkino!");
                        Boxes[meAsBox].squareGrid[SelfPos].BoxText.setEditable(true);
                        Boxes[meAsBox].squareGrid[SelfPos].IsEditable = true;
                        Boxes[meAsBox].squareGrid[SelfPos].IsRed = true;
                        Boxes[meAsBox].squareGrid[SelfPos].BoxText.setBackground(Color.red);
                    } else {
                        Boxes[i].squareGrid[j].BoxText.setEditable(false);
                        Boxes[i].squareGrid[j].IsEditable = false;
                    }
                }
            }
            
        } 
        
    return GridNeedToBeChanged;
    }
    
    //Freeze all the gridTextes except the one causes collision, so the user will need to change only this
    public void CollisionFreeze(squareBox []Boxes, squareBox Me, int CollisionPos) {
    
        int i, j;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                    Boxes[i].squareGrid[j].BoxText.setEditable(false); 
                    Boxes[i].squareGrid[j].IsEditable = false;
                    if (Boxes[i] == Me) {
                        if (j == CollisionPos) {
                            Boxes[i].squareGrid[j].BoxText.setEditable(true); 
                            Boxes[i].squareGrid[j].IsEditable = true;
                        }
                    }
                }
            }
        }
    
    //Turns red the collisions inside a Box with red
    public boolean CollisionBoxCheck(SingleBoxText GridToCheck, squareBox Box, int MySelf) {
        int i, j;
        boolean NeedToBeChanged = false;
        
        if (GridToCheck.BoxText.getText().equals("")) {
            return false;
        }
        for (i = 0; i < GRID_SIZE; i++) {
            if (GridToCheck.BoxText.getText().equals(Box.squareGrid[i].BoxText.getText())) {
                if (i == MySelf) {
                    
                } else {
                    Box.squareGrid[i].IsRed = true;
                    NeedToBeChanged = true;
                    break;
                }
            }
        }
        //After loops ends, we must see if we have collisions
        if (NeedToBeChanged) {
            GridToCheck.IsRed = true;
        } else {
            GridToCheck.IsRed = false; //No collisions, remains yellow
        }

        return(NeedToBeChanged);
    }
    
    //SquareBoxes are JTextFields[9]. We must find the correct row to compare
    public boolean CollisionBoardRowColCheck(squareBox Boxes[], int MyBoxID, SingleBoxText CollisionGrid, int BoardColumn) {
        int StartCol = 0, EndCol = 0;
        int Col = 0;
        int i, j;
        boolean NeedToBeChanged = false;
       
        //9x9 of Boxes, we need to check for collisions the Box we are plus others 4(its neighbors)
        int []BoxesBoardRowToCheck = new int[2];
        int []BoxesColToCheck = new int[2];
        
        if (CollisionGrid.BoxText.getText().equals("")) {
            return false;
        }
        
        //Section
        switch(MyBoxID) {
        
            case 0:
                BoxesBoardRowToCheck[0] = 1;
                BoxesBoardRowToCheck[1] = 2;
                BoxesColToCheck[0] = 3;
                BoxesColToCheck[1] = 6;
                break;
            case 1:
                BoxesBoardRowToCheck[0] = 0;
                BoxesBoardRowToCheck[1] = 2;
                BoxesColToCheck[0] = 4;
                BoxesColToCheck[1] = 7;
                break;
            case 2:
                BoxesBoardRowToCheck[0] = 0;
                BoxesBoardRowToCheck[1] = 1;
                BoxesColToCheck[0] = 5;
                BoxesColToCheck[1] = 8;
                break;
            case 3:
                BoxesBoardRowToCheck[0] = 4;
                BoxesBoardRowToCheck[1] = 5;
                BoxesColToCheck[0] = 0;
                BoxesColToCheck[1] = 6;
                break;
            case 4:
                BoxesBoardRowToCheck[0] = 3;
                BoxesBoardRowToCheck[1] = 5;
                BoxesColToCheck[0] = 1;
                BoxesColToCheck[1] = 7;
                break;
            case 5:
                BoxesBoardRowToCheck[0] = 3;
                BoxesBoardRowToCheck[1] = 4;
                BoxesColToCheck[0] = 2;
                BoxesColToCheck[1] = 8;
                break;
            case 6:
                BoxesBoardRowToCheck[0] = 7;
                BoxesBoardRowToCheck[1] = 8;
                BoxesColToCheck[0] = 0;
                BoxesColToCheck[1] = 3;
                break;
            case 7:
                BoxesBoardRowToCheck[0] = 6;
                BoxesBoardRowToCheck[1] = 8;
                BoxesColToCheck[0] = 1;
                BoxesColToCheck[1] = 4;
                break;
            case 8:
                BoxesBoardRowToCheck[0] = 6;
                BoxesBoardRowToCheck[1] = 7;
                BoxesColToCheck[0] = 2;
                BoxesColToCheck[1] = 5;
                break;
            default: System.out.println("Error here!");
        }
        
        //We gonna check only the 4 -side by side- boxes, we must find the right spot to check of each box
        if (BoardColumn >= 0 && BoardColumn <= 2) {
            StartCol = 0;
            EndCol = 2;
            Col = BoardColumn;
        } else if (BoardColumn >= 3 && BoardColumn <= 5) {
            StartCol = 3;
            EndCol = 5;
            Col = BoardColumn - 3;
        } else if (BoardColumn >= 6 && BoardColumn <= 8) {
            StartCol = 6;
            EndCol = 8;
            Col = BoardColumn - 6;
        } 
        
        //For the BoardColumn-neightbor boxes
        for (i = 0; i < 2; i++) {
            for (j = Col; j < GRID_SIZE; j+=3) {
                if (Boxes[BoxesColToCheck[i]].squareGrid[j].BoxText.getText().equals(CollisionGrid.BoxText.getText())) {
                    Boxes[BoxesColToCheck[i]].squareGrid[j].IsRed = true;
                    NeedToBeChanged = true;
                }
            }
        }
        
        //For the BoardRow-neighbor boxes
        for (i = 0; i < 2; i++) {
            for (j = StartCol; j <= EndCol; j++) {
                if (Boxes[BoxesBoardRowToCheck[i]].squareGrid[j].BoxText.getText().equals(CollisionGrid.BoxText.getText())) {
                    Boxes[BoxesBoardRowToCheck[i]].squareGrid[j].IsRed = true;
                    NeedToBeChanged = true;
                }
            }
        }
        if (NeedToBeChanged) {
            CollisionGrid.IsRed = true;
        } else {
            CollisionGrid.IsRed = false;
        }
        return(NeedToBeChanged);
    }
   

    //Turns the "IsRed" boolean values of all Grids to false
    //turns the "IsEdited" boolean values of all Grids to false
    public void RestartValues(squareBox []Boxes) {
        int i, j;
        
        ResetAgainstSolutionDisplay(); //initialitation of the colors
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                Boxes[i].squareGrid[j].IsRed = false;
                Boxes[i].squareGrid[j].IsEditable = true;
                Boxes[i].squareGrid[j].BoxText.setEditable(true);
            }
        }
    }
    
    
    //Copy the data from GetsCopied array to ToCopy array
    public void CopyBoardData(int [][]ToCopy, int [][]GetsCopied) {
        int i,j;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                ToCopy[i][j] = GetsCopied[i][j];
            }
        }
        
    }
    
    //When Verify Against Solution is checked!
    public void CheckAgainstSolutionDisplay(int [][]SolvedBoard, int [][]BoardSudoku, int [][]BoardStarter) {
        int i,j;
        int BoxID;
        int Column;
        
        SudokuGame.SudokuFrame.InitializeColors(Board, SudokuGame.SudokuFrame.Boxes);
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                if (BoardSudoku[i][j] == -1) { //Means has no value
                
                } else {
                    if (BoardSudoku[i][j] != SolvedBoard[i][j] && BoardStarter[i][j] == -1) {
                        BoxID = SudokuGame.SudokuFrame.FindBox(i, j);
                        Column = SudokuGame.SudokuFrame.TranslateCordinates(SudokuGame.SudokuFrame.BoxRow, SudokuGame.SudokuFrame.BoxCol);
                        SudokuGame.SudokuFrame.Boxes[BoxID].squareGrid[Column].BoxText.setBackground(Color.CYAN);
                    }
                }
            }
        }
        
    }
    
    //Initliazations of the colors if checkBox is checked!
    public void InitializeColorsWhenCheckBox(int [][]board, squareBox []Boxes) {
        int i, j;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                if (Boxes[i].squareGrid[j].Starter) {
                    Boxes[i].squareGrid[j].setLightGray();
                } else {
                    if (SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].BoxText.getBackground().equals(Color.CYAN)) {
                        
                    } else {
                        Boxes[i].squareGrid[j].setWhite();
                    }
                }
            }
        }
    }
    
    //Initialization, of the colors
    public void ResetAgainstSolutionDisplay() {
        
        if (SudokuGame.ButtonsFrame.ChBoxIsClicked) {
            //System.out.println("GIORGOS");
            InitializeColorsWhenCheckBox(Board, SudokuGame.SudokuFrame.Boxes);
        } else {
            //System.out.println("MAKHS");
            SudokuGame.SudokuFrame.InitializeColors(Board, SudokuGame.SudokuFrame.Boxes);
        }
    }
    
    //Translates the coordinates of Box to Find the BoardRow, BoardColumn
    public void BoxToBoard(int BoxID, int squareGridID) {
        
        switch (BoxID) {
        
            case 0:
                   //Finds the BoardRow
                   if (squareGridID >= 0 && squareGridID <= 2) {
                       BoardRow = 0;
                   } else if (squareGridID >= 3 && squareGridID <= 5) {
                       BoardRow = 1;
                   } else if (squareGridID >= 6 && squareGridID <= 8) {
                       BoardRow = 2;
                   }
                   //Finds the BoardColumn
                   if (squareGridID == 0 || squareGridID == 3 || squareGridID == 6) {
                       BoardColumn = 0;
                   } else if (squareGridID == 1 || squareGridID == 4 || squareGridID == 7) {
                       BoardColumn = 1;
                   } else if (squareGridID == 2 || squareGridID == 5 || squareGridID == 8) {
                       BoardColumn = 2;
                   }
                   break;
            case 1:
                   if (squareGridID >= 0 && squareGridID <= 2) {
                       BoardRow = 0;
                   } else if (squareGridID >= 3 && squareGridID <= 5) {
                       BoardRow = 1;
                   } else if (squareGridID >= 6 && squareGridID <= 8) {
                       BoardRow = 2;
                   }
                   if (squareGridID == 0 || squareGridID == 3 || squareGridID == 6) {
                       BoardColumn = 3;
                   } else if (squareGridID == 1 || squareGridID == 4 || squareGridID == 7) {
                       BoardColumn = 4;
                   } else if (squareGridID == 2 || squareGridID == 5 || squareGridID == 8) {
                       BoardColumn = 5;
                   }
                   break;
            case 2:
                   if (squareGridID >= 0 && squareGridID <= 2) {
                       BoardRow = 0;
                   } else if (squareGridID >= 3 && squareGridID <= 5) {
                       BoardRow = 1;
                   } else if (squareGridID >= 6 && squareGridID <= 8) {
                       BoardRow = 2;
                   }
                   if (squareGridID == 0 || squareGridID == 3 || squareGridID == 6) {
                       BoardColumn = 6;
                   } else if (squareGridID == 1 || squareGridID == 4 || squareGridID == 7) {
                       BoardColumn = 7;
                   } else if (squareGridID == 2 || squareGridID == 5 || squareGridID == 8) {
                       BoardColumn = 8;
                   }
                   break;
            case 3:
                   if (squareGridID >= 0 && squareGridID <= 2) {
                       BoardRow = 3;
                   } else if (squareGridID >= 3 && squareGridID <= 5) {
                       BoardRow = 4;
                   } else if (squareGridID >= 6 && squareGridID <= 8) {
                       BoardRow = 5;
                   }
                   if (squareGridID == 0 || squareGridID == 3 || squareGridID == 6) {
                       BoardColumn = 0;
                   } else if (squareGridID == 1 || squareGridID == 4 || squareGridID == 7) {
                       BoardColumn = 1;
                   } else if (squareGridID == 2 || squareGridID == 5 || squareGridID == 8) {
                       BoardColumn = 2;
                   }
                   break;
            case 4:
                   if (squareGridID >= 0 && squareGridID <= 2) {
                       BoardRow = 3;
                   } else if (squareGridID >= 3 && squareGridID <= 5) {
                       BoardRow = 4;
                   } else if (squareGridID >= 6 && squareGridID <= 8) {
                       BoardRow = 5;
                   }
                   if (squareGridID == 0 || squareGridID == 3 || squareGridID == 6) {
                       BoardColumn = 3;
                   } else if (squareGridID == 1 || squareGridID == 4 || squareGridID == 7) {
                       BoardColumn = 4;
                   } else if (squareGridID == 2 || squareGridID == 5 || squareGridID == 8) {
                       BoardColumn = 5;
                   }
                   break;
            case 5:
                   if (squareGridID >= 0 && squareGridID <= 2) {
                       BoardRow = 3;
                   } else if (squareGridID >= 3 && squareGridID <= 5) {
                       BoardRow = 4;
                   } else if (squareGridID >= 6 && squareGridID <= 8) {
                       BoardRow = 5;
                   }
                   if (squareGridID == 0 || squareGridID == 3 || squareGridID == 6) {
                       BoardColumn = 6;
                   } else if (squareGridID == 1 || squareGridID == 4 || squareGridID == 7) {
                       BoardColumn = 7;
                   } else if (squareGridID == 2 || squareGridID == 5 || squareGridID == 8) {
                       BoardColumn = 8;
                   }
                   break;
            case 6:
                   if (squareGridID >= 0 && squareGridID <= 2) {
                       BoardRow = 6;
                   } else if (squareGridID >= 3 && squareGridID <= 5) {
                       BoardRow = 7;
                   } else if (squareGridID >= 6 && squareGridID <= 8) {
                       BoardRow = 8;
                   }
                   if (squareGridID == 0 || squareGridID == 3 || squareGridID == 6) {
                       BoardColumn = 0;
                   } else if (squareGridID == 1 || squareGridID == 4 || squareGridID == 7) {
                       BoardColumn = 1;
                   } else if (squareGridID == 2 || squareGridID == 5 || squareGridID == 8) {
                       BoardColumn = 2;
                   }
                   break;
            case 7:
                   if (squareGridID >= 0 && squareGridID <= 2) {
                       BoardRow = 6;
                   } else if (squareGridID >= 3 && squareGridID <= 5) {
                       BoardRow = 7;
                   } else if (squareGridID >= 6 && squareGridID <= 8) {
                       BoardRow = 8;
                   }
                   if (squareGridID == 0 || squareGridID == 3 || squareGridID == 6) {
                       BoardColumn = 3;
                   } else if (squareGridID == 1 || squareGridID == 4 || squareGridID == 7) {
                       BoardColumn = 4;
                   } else if (squareGridID == 2 || squareGridID == 5 || squareGridID == 8) {
                       BoardColumn = 5;
                   }
                   break;
            case 8:
                   if (squareGridID >= 0 && squareGridID <= 2) {
                       BoardRow = 6;
                   } else if (squareGridID >= 3 && squareGridID <= 5) {
                       BoardRow = 7;
                   } else if (squareGridID >= 6 && squareGridID <= 8) {
                       BoardRow = 8;
                   }
                   if (squareGridID == 0 || squareGridID == 3 || squareGridID == 6) {
                       BoardColumn = 6;
                   } else if (squareGridID == 1 || squareGridID == 4 || squareGridID == 7) {
                       BoardColumn = 7;
                   } else if (squareGridID == 2 || squareGridID == 5 || squareGridID == 8) {
                       BoardColumn = 8;
                   }
                   break;
            default: 
                   break;
        }
    }
    
    //Prints the Board we build
    public void BoardDisplay() {
        int i,j;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                if (j == GRID_SIZE - 1) {
                    System.out.println(" "+Board[i][j]);
                } else {
                    System.out.print(" "+Board[i][j]);
                } 
            }
        }
    }
    
    //The solver of Sudoku
    public void SolverJoined(int [][]Board, squareBox []Boxes) {
        int i,j;
        int BoxID, col;

        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                System.out.println(" "+Board[i][j]);
                BoxID = SudokuGame.SudokuFrame.FindBox(i, j);
                col = SudokuGame.SudokuFrame.TranslateCordinates(i, j);
                Boxes[BoxID].squareGrid[col].BoxText.setText(""+Board[i][j]);
            }
        }
    }
    
    //Copy the JTextFIelds from the panel into the SudokuBoard
    public void TextFieldsToBoard() {
    
        int i,j;
        int BoxID,Column;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                BoxID = SudokuGame.SudokuFrame.FindBox(i, j);
                Column = SudokuGame.SudokuFrame.TranslateCordinates(SudokuGame.SudokuFrame.BoxRow, SudokuGame.SudokuFrame.BoxCol);
                if (SudokuGame.SudokuFrame.Boxes[BoxID].squareGrid[Column].BoxText.getText().equals("")) {
                    this.Board[i][j] = -1;
                } else {
                    this.Board[i][j] = new Integer(SudokuGame.SudokuFrame.Boxes[BoxID].squareGrid[Column].BoxText.getText());
                }
            }
        }
    }
    
    //Disables all the Buttons
    public void DisableButtons() {
        int i;
        
        for (i = 0; i < 13; i++) {
            if (i == 11) {
                SudokuGame.ButtonsFrame.ChBoxIsClicked = false;
                SudokuGame.ButtonsFrame.ChBox.setEnabled(false);
            } else {
                SudokuGame.ButtonsFrame.buttonsGrid[i].setEnabled(false);
            }
        }
    }
    
    //Enables all the buttons
    public void EnableButtons() {
        int i;
        
        for (i = 0; i < 13; i++) {
            if (i == 11) {
                if (SudokuGame.ButtonsFrame.ChBox.isSelected()) {
                    SudokuGame.ButtonsFrame.ChBox.setSelected(false);
                } 
                SudokuGame.ButtonsFrame.ChBox.setEnabled(true);
            } else {
                SudokuGame.ButtonsFrame.buttonsGrid[i].setEnabled(true);
            }
        }
    }
    
    //Checks if the game is Finished by the user
    public boolean IsGameFinished() {
    
        int i,j;
        boolean IsFinished = true;
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                if (Board[i][j] == BoardSolved[i][j]) {
                    
                } else {
                    //Game is not finished
                    IsFinished = false;
                    
                    break; 
                }
            }
        }
        return IsFinished;
    }
    
    //Shows the dialog after game ends
    private void showDialog() {
        
        
        JTextField TextSection = new JTextField("Congratulatios!You finished the game succesfully!");
        IsFinished = new JDialog(this.SudokuGame, "SUDOKU ENDGAME MESSAGE",Dialog.ModalityType.APPLICATION_MODAL);
        TextSection.setEditable(false);
        
        IsFinished.setBounds(SudokuGame.getBounds().x + SudokuGame.getBounds().height/4, SudokuGame.getBounds().y + SudokuGame.getBounds().width/4, SudokuGame.getBounds().width/2, SudokuGame.getBounds().height/2);
        
        IsFinished.getContentPane().add(TextSection);
        IsFinished.pack();
        IsFinished.setVisible(true);
        IsFinished.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        GameFinished = true;
        
    
    }
    
    //Disables all the Grids
    public void DisableGrids() {
        int i,j;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].BoxText.setEditable(false);
            }
        }
    }
    
    //Enable the Grids
    public void EnableGrids() {
        int i,j;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].BoxText.setEditable(true);
            }
        }
    }
    
    //Set the ID's (BoxID, squareID), of each SingleBoxText inside squareID->inside BoxID
    public void SetIDs() {
        int i,j;
        
        for (i = 0; i < GRID_SIZE; i++) {
            for (j = 0; j < GRID_SIZE; j++) {
                SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].MyBoxID = i;
                SudokuGame.SudokuFrame.Boxes[i].squareGrid[j].myGridID = j;
            }
        }
    }
  //end of the Class
}