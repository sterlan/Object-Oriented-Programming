/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

/**
 *
 * @author dawg
 */
public class YUVImage {
    
    //variables
    int width = 0;
    int height = 0;
    YUVPixel [][]YUVPixelImg;
    
    //Constructors
    public YUVImage(int width, int height) {
        
        //Variables
        int i, j;
        
        
        this.width = width;
        this.height = height;
        this.YUVPixelImg = new YUVPixel[this.height][this.width];
        
        //Now for all YUVpixels
        for (i = 0; i < this.height; i++) { //Running the lines
            for (j = 0; j < this.width; j++) { //Running the columns
                this.YUVPixelImg[i][j] = new YUVPixel((short)16, (short)128, (short)128);
            }
        }
    }
    
    //The copy Constructor
    //When YUV image given as input
    public YUVImage(YUVImage copyImg) {
        //Variables
        int i, j;
        
        //first we must check if copyImg == null
        if ((copyImg == null) || (copyImg.YUVPixelImg == null)){
            return;
        }
        
        //Exchange the variables
        this.height = copyImg.height;
        this.width = copyImg.width;
        this.YUVPixelImg = new YUVPixel[this.height][this.width];
        
        //Now for all YUVpixels
        for (i = 0; i < this.height; i++) { //Running the lines
            for (j = 0; j < this.width; j++) { //Running the columns
                
                //Creating new pixels for my YUVPixelImg -> fill them with 
                //the pixels from copyImg
                this.YUVPixelImg[i][j] = new YUVPixel(copyImg.YUVPixelImg[i][j]);
            }
        }
        
    }
    
    //When RGB image given as input
    public YUVImage(RGBImage RGBImg) {
        //Variables
        int i, j;
        
        //first we must check if copyImg == null
        if ((RGBImg == null) || (RGBImg.RGBPixelImg == null)){
            return;
        }
        
        //Exchange the variables
        this.height = RGBImg.height;
        this.width = RGBImg.width;
        this.YUVPixelImg = new YUVPixel[this.height][this.width];
        
        //Now for all YUVpixels
        for (i = 0; i < this.height; i++) { //Running the lines
            for (j = 0; j < this.width; j++) { //Running the columns
                
                //Creating new pixels for my YUVPixelImg -> fill them with 
                //the pixels from copyImg
                this.YUVPixelImg[i][j] = new YUVPixel(RGBImg.RGBPixelImg[i][j]);
            }
        }
            
    }
    
    //
    public YUVImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {
        
        //variables
        String fileType;
        YUVPixel [][]FilePixelImg;
        int i,j;
        short Y,U,V;
        
        //first we need to check if this file exists
        if (file.canRead() && file.exists()) {
        } else {
            //This file cant be read or does not exist
            //Throw the error
           throw new FileNotFoundException();
        }
        
        //The file exists, can read it
        //We must check if it is .yuv
        try (Scanner sc = new Scanner(file)) {
            fileType = sc.next(); //taking the 1st string 
            
            //Compare it to YUV3
            if (fileType.compareTo("YUV3") != 0) {
                //this file is not a YUV3 file
                throw new UnsupportedFileFormatException("Not a YUV3 file!");
            }
            
            //else, the file is type .yuv
            
            this.width = sc.nextInt(); //taking the width
            this.height = sc.nextInt(); //taking the height
            
            
            //Create the new YUV image
            FilePixelImg = new YUVPixel[this.height][this.width];
            
            //For the Y U V values of a pixel
            for (i = 0; i < this.height; i++) {
                for (j = 0; j < this.width; j++) {
                    Y = sc.nextShort();
                    U = sc.nextShort();
                    V = sc.nextShort();
                    
                    //New Pixels 
                    FilePixelImg[i][j] = new YUVPixel(Y,U,V);
                }
            }
            //New img
            this.YUVPixelImg = FilePixelImg; 
        }
    }
    
    
    //Methods
    @Override
    public String toString() {
        //Variables
        int i, j;
        StringBuilder myStrBuilder = new StringBuilder("");
        
        //first we need to write the type of the file
        //YUV3
        myStrBuilder = myStrBuilder.append("YUV3\n");
        
        //Now the width, height
        myStrBuilder = myStrBuilder.append(this.width);
        myStrBuilder = myStrBuilder.append(" ");
        myStrBuilder = myStrBuilder.append(this.height);
        myStrBuilder = myStrBuilder.append("\n");
        
        //the info for each pixel follows
        for (i = 0; i < this.height; i++) {
            for (j = 0; j < this.width; j++) {
                myStrBuilder = myStrBuilder.append(this.YUVPixelImg[i][j].getY());
                myStrBuilder = myStrBuilder.append(" ");
                myStrBuilder = myStrBuilder.append(this.YUVPixelImg[i][j].getU());
                myStrBuilder = myStrBuilder.append(" ");
                myStrBuilder = myStrBuilder.append(this.YUVPixelImg[i][j].getV());
                myStrBuilder = myStrBuilder.append("\n");
            }
        }
        //Our String is ready
        return myStrBuilder.toString();
        
    }
    
    //Writes our image in a file as a YUV file
    void toFile(java.io.File file) {
        
        //variables
        String fileName;
        
        fileName = file.getName();
    
        if (file.exists()) {
            //File exists, delete it 
            file.delete();
        } else {
            //File not exists, we gonna create it
        }
        
        try {
            
            //Our new file
            File ImgFile = new File(fileName);
            if (ImgFile.createNewFile()) {
                //File created succesfully
            } else {
                //File not created succesfully
                return;
            }
        
            //We need to copy the info
            FileWriter flWriter = new FileWriter(file);
            //So we call our toString() 
            flWriter.write(toString());
            flWriter.close();
        } catch (IOException e) {}
        //
    }
    
    void equalize() {
        //Variables
        Histogram newHistogram;
        int row, column;
        
        //Check if image not-exists
        if (this.YUVPixelImg == null) {
            return;
        }
        
        //newHistogram takes input the YUVimg
        newHistogram = new Histogram(this); 
        //Initialize the newHistogram.cumulativeProbArray and the new histogramArray
        newHistogram.equalize(); 
        
        for (row= 0; row < this.height; row++) {
            for (column = 0; column < this.width; column++) {
                //Set the new Equalized value Y
                this.YUVPixelImg[row][column].setY(newHistogram.getEqualizedLuminocity(this.YUVPixelImg[row][column].getY()));
            }
        }
    }
    
}
