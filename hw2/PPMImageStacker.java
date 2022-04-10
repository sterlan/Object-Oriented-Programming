/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;



/**
 *
 * @author dawg
 */
public class PPMImageStacker {
    
    //variables
    PPMImage PPMImageStacked;
    //My list of images
    List<File> ppmImagesList = new ArrayList<>();
    int totalImgs = 0;
    //Constructor
    public PPMImageStacker(java.io.File dir) throws UnsupportedFileFormatException, FileNotFoundException{
        
        //variables
        
        
        //Check if file exists
        if (!dir.exists()) {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException ex) {
                System.out.println("[ERROR] Directory " +dir.getName()+ " does not exist!");
            }
        }
        
        //If we reach here file exists
        //Check if its dir
        if (!dir.isDirectory()) {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException Ex) {
                System.out.println("[ERROR] " +dir.getName() +" is not a directory!");
            }
        }
        
        //If we reach here, file exists and its directory
        //last session: we need to store the PPMImages into a List
        //This for doing this
        for (File fp : dir.listFiles()) { //Running the files inside the Dir
            try {
                Scanner sc = new Scanner(fp);
                //PPM format
                if (!sc.next().equals("P3")) {
                    throw new UnsupportedFileFormatException();
                }
                
                ppmImagesList.add(fp); //Store it
                totalImgs += 1; //Count += 1
            } catch (UnsupportedFileFormatException U) {}
           
        }

    }
    
    //Methods
    
    //Stacks the images
    public void stack() throws FileNotFoundException, UnsupportedFileFormatException{
        
        
        //Variables
        PPMImage imgCurr; //For running through
        RGBImage[] imgArray = new RGBImage[totalImgs]; //Saving the images of directory here
        RGBImage RGBImageStacked;
        int i, rows, columns;
        short Red = 0, Green = 0, Blue = 0;
        int width = 0, height = 0, colordepth = 0;
        
        //Checking if list is Empty
        if (ppmImagesList.isEmpty()) {
            return;
        }
       
        //We want to run through all the PPMimages in the list
        for (i = 0; i < totalImgs; i++) {
            //Now, we want to check for all the images the pixels
            try {
                
                //Set the Image Current
                imgCurr = new PPMImage(ppmImagesList.get(i));
                
                if ((width == 0) && (height == 0)) { //1st time
                    width = imgCurr.width;
                    height = imgCurr.height;
                    colordepth = imgCurr.colordepth;
                }
                //next times, the widht and height must equals to imgCurr.width, height (same photos)
                if ((width != imgCurr.width) || (height != imgCurr.height)) {
                    System.out.println("Diff photos to scan!");
                } else {
                    //Save it to our new imgArray: an array of RGBpixels[][] of each image
                    imgArray[i] = new RGBImage(imgCurr);
                }
                
            } catch (FileNotFoundException ex) {
                return;
            }
        }
        
        //The Creation
        RGBImageStacked = new RGBImage(width, height, RGBImage.MAX_COLORDEPTH);
        
        for (rows = 0; rows < height; rows++) {
            for (columns = 0; columns < width; columns++) {
            
                //Running the PPMimages List
                for (i = 0; i < totalImgs; i++) {
                    
                    //Take R G B the same - sit values of each photo, then devided by total images
                    Red += imgArray[i].RGBPixelImg[rows][columns].getRed();
                    Blue +=  imgArray[i].RGBPixelImg[rows][columns].getBlue();
                    Green += imgArray[i].RGBPixelImg[rows][columns].getGreen();
                }
                //New RGBPixel
                RGBImageStacked.RGBPixelImg[rows][columns] = new RGBPixel((short)(Red/totalImgs), (short)(Green/totalImgs), (short)(Blue/totalImgs));
                Red = Green = Blue = 0; //Set R G B = 0 for the next Pixel
            }
        }
       
        //the new PPMImage
        PPMImageStacked = new PPMImage(RGBImageStacked);
        
    }
        
    public PPMImage getStackedImage() {
        //return the new Object
        return (PPMImageStacked);
    }
}
