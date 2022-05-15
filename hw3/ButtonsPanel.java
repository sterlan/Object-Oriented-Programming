/* Author: Lantzos  
                Stergios
*/

package ece326.hw3;
import java.awt.*;
import javax.swing.*;

//A PANEL FOR THE BUTTONS
public class ButtonsPanel extends JPanel{

    String Button; //Each time we click a button -> Save the choice into this String
    
    JButton [] buttonsGrid; //An array foy our Buttons, to keep them together
    
    JCheckBox ChBox; //The CheckBox "Verify Against Solution"
    
    boolean ChBoxIsClicked = false; //If CheckBox is Clicked
    
    //Cunstructor
    public ButtonsPanel() {
        
        //Variables
        Image img;
        ImageIcon imgIcon;
        String label; //Each label of the buttons
        int width, height, i;
        
        //New JPanel for the buttons
        //options
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(300, 50));
        
        //Creates the Buttons
        buttonsGrid = new JButton[13];
        
        //Section
        for (i = 0; i < 13; i++) {
            switch(i) {
           
                //Eraser
                case 9: width = 60;
                        height = 30;
                        
                        imgIcon = new ImageIcon(("src/main/java/ece326/hw3/Images/eraser.png"));
                        img = imgIcon.getImage().getScaledInstance(width/2, height/2, Image.SCALE_DEFAULT);
                        imgIcon = new ImageIcon(img);
                        buttonsGrid[i] = new JButton("", imgIcon);
                        buttonsGrid[i].setActionCommand("eraser");
                        break;
                        
                //Undo
                case 10:width = 60;
                        height = 30;
                    
                        imgIcon = new ImageIcon("src/main/java/ece326/hw3/Images/undo.png");
                        img = imgIcon.getImage().getScaledInstance(width/2, height/2, Image.SCALE_DEFAULT);
                        imgIcon = new ImageIcon(img);
                        buttonsGrid[i] = new JButton("", imgIcon);
                        buttonsGrid[i].setActionCommand("undo");
                        break;
                
                //Verify Against Solution
                case 11:width = 240;
                        height = 30;
                        label = "Verify Against Solution";

                        buttonsGrid[i] = new JButton("");
                        buttonsGrid[i].setActionCommand(label);
                        //But we add in the panel the checkbox, so we know when
                        //we have action command "Verify Against Solution", we 
                        //gonna work with checkBox only
                        ChBox = new JCheckBox(label);
                        ChBox.setActionCommand(label);
                        break;
                        
                //Rubik   
                case 12:width = 60;
                        height = 30;
                    
                        imgIcon = new ImageIcon("src/main/java/ece326/hw3/Images/rubik.png");
                        img = imgIcon.getImage().getScaledInstance(width/2, height/2, Image.SCALE_DEFAULT);
                        imgIcon = new ImageIcon(img);
                        buttonsGrid[i] = new JButton("", imgIcon);
                        buttonsGrid[i].setActionCommand("rubik");
                        break;
                        
                //Default Button 1-9    
                default:
                        label = (new Integer(i + 1)).toString();
                        buttonsGrid[i] = new JButton(label);
                        break;
            }
            
            //Add each button to the panel
            if (i == 11) {
                add(ChBox);
            } else {
                add(buttonsGrid[i]); 
            }  
        }
    }
}
    
