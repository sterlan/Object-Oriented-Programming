/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
/**
 *
 * @author dawg
 */
public class PPMImage extends RGBImage {
    
    //Variables
    
    //Constructors
    public PPMImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException  {
        //Variables
        int i,j;
        short red, green, blue;
        Scanner sc;
        String filetype;
        
        //if file does not exist or cant be read
        if ((!file.exists()) || (!file.canRead())) {
            throw new FileNotFoundException();
        }
        
        //if file is not type PPM
        try {
            sc = new Scanner(file);
            filetype = sc.next(); //taking the filetype name
            if (!filetype.equals("P3")) {
                //If the file is not a PPM 
                
                throw new UnsupportedFileFormatException();
            } 
            //Scanning now the new width, height
            width = sc.nextInt(); //taking the width
            height = sc.nextInt(); //taking the height
            colordepth = sc.nextInt(); //New maxColordepth
            
            //New pixelMatrix
            RGBPixelImg = new RGBPixel[super.height][super.width];
            
            for (i = 0; i < height; i++) {
                for (j = 0; j < width; j++) {
                   
                    //taking R G B 
                    red = sc.nextShort();
                    green = sc.nextShort();
                    blue = sc.nextShort();
                    
                    //Set the R G B values -> pixel
                    RGBPixelImg[i][j] = new RGBPixel(red, green, blue);
                }
            }
            
            //checking if file has more data
            if (sc.hasNext()) {
                throw new UnsupportedFileFormatException();
            }
        } catch (UnsupportedFileFormatException U) {
            System.out.println("Not a PPM file\n");
        }
    }
    
    public PPMImage(RGBImage img) {
        
        //Version 1
        //Variables
       
        /*
        int i, j;
        short red, green, blue;
        
        //Check if valid input
        if (img.RGBPixelImg == null) {
            return;
        }
        
        //New values
        super.height = img.height;
        super.width = img.width;
        super.colordepth = img.colordepth;
        
        super.RGBPixelImg = new RGBPixel[this.height][this.width];
        
        //The creation
        for (i = 0; i < this.height; i++) {
            for (j = 0; j < this.width; j++) {
                red = img.RGBPixelImg[i][j].getRed();
                green = img.RGBPixelImg[i][j].getGreen();
                blue = img.RGBPixelImg[i][j].getBlue();
                super.RGBPixelImg[i][j] = new RGBPixel(red, green, blue);
            }
        }
        */
        
        //Version 2 
        //The constructor for RGBImage
        super(img);
        
    }
    
    public PPMImage(YUVImage img) {
        //The constructor for YUVImage
        super(img);
    }
    
    
    //Methods
    
    //We write the content from PPMImage to a string,
    //then with method "toFIle" we copy this string into a file
    @Override
    public String toString() {
        //Variables
        int i,j;
        StringBuilder PPMContent;
        
        //the append - format
        PPMContent = new StringBuilder("");
        PPMContent.append("P3\n").append(super.width).append(" ").append(super.height).append("\n").append(super.colordepth).append("\n");
        for (i = 0; i < super.height; i++) {
            for (j = 0; j < super.width; j++) {
                
                //if im last, print \n
                
                if (j == super.width - 1) {
                    PPMContent.append(super.RGBPixelImg[i][j].getRed()).append(" ").append(super.RGBPixelImg[i][j].getGreen()).append(" ").append(super.RGBPixelImg[i][j].getBlue()).append("\n");
                } else {
                    PPMContent.append(super.RGBPixelImg[i][j].getRed()).append(" ").append(super.RGBPixelImg[i][j].getGreen()).append(" ").append(super.RGBPixelImg[i][j].getBlue()).append(" ");
                }
            }
        }
        
        //return String
        return PPMContent.toString();
    
    }
    
    //Writes the string created by toString() to a file
    void toFile(java.io.File file) {
        //Variables
    
        if (file.exists()) {
            //File exists, delete it 
            file.delete();
        } else {
            //File not exists, we gonna create it
        }
    
        try {
            if (file.createNewFile()) {
                //file created succesfully
            } else {
                //file not created succesfully
                return;
            }
            
            try ( //Writes the content
                    FileWriter fw = new FileWriter(file)) {
                    fw.write(toString());
            }
        } catch (IOException e) {}
    }
}
