/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

/**
 *
 * @author dawg
 */
public class RGBImage implements Image{
    
    //variables
    public static int MAX_COLORDEPTH = 255;
    int width = 0;
    int height = 0;
    int colordepth = 0;
    
    //Our image. A 2-D array of RGBPixels(our private int)
    RGBPixel [][]RGBPixelImg;
    
    //Constructors
    
    //Default Constructor
    public RGBImage() {}
    
    //Constructor when width, height, colorDepth are given
    public RGBImage(int width, int height, int colordepth) {
        this.width = width;
        this.height = height;
        this.colordepth = colordepth;
        this.RGBPixelImg = new RGBPixel[this.height][this.width];
    }
    
    //The copy Constructor
    public RGBImage(RGBImage copyImg) {
        
        //variables
        int i, j;
        
        //First check: if copyImg == null
        if ((copyImg == null) || (copyImg.RGBPixelImg == null)){
            return;
        }
        
        //else
        //Exchange the variables
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.colordepth = copyImg.colordepth;
        
        //Create the new pixelImage
        this.RGBPixelImg = new RGBPixel[this.height][this.width];
        
        //copy the pixels from copyImg to new pixelImage
        for (i = 0; i < this.height; i++) { //Running the lines
            for (j = 0; j < this.width; j++) { //Running the collumns
                
                //Creating new pixels for my pixelImage -> fill them with 
                //the pixels from copyImg
                this.RGBPixelImg[i][j] = new RGBPixel(copyImg.RGBPixelImg[i][j]);
            }
        }
    }
    
    //When YUVImage is given as input
    public RGBImage(YUVImage YUVImg) {
        
        //Variables
        int i, j;
        
        //first we must check if copyImg == null
        if ((YUVImg == null) || (YUVImg.YUVPixelImg == null)){
            return;
        }
    
        //Exchange the variables
        this.height = YUVImg.height;
        this.width = YUVImg.width;
        this.colordepth = MAX_COLORDEPTH;
        this.RGBPixelImg = new RGBPixel[this.height][this.width];
        
        //Now for all YUVpixels
        for (i = 0; i < this.height; i++) { //Running the lines
            for (j = 0; j < this.width; j++) { //Running the columns
                
                //Creating new pixels for my YUVPixelImg -> fill them with 
                //the pixels from copyImg
                this.RGBPixelImg[i][j] = new RGBPixel(YUVImg.YUVPixelImg[i][j]);
            }
        }
    }
    
    
    //Methods
    
    //ImageWidth
    int getWidth() {
        return(this.width);
    }
    
    //ImageHeight
    int getHeight() {
        return(this.height);
    }
    
    //ImageColordepth
    int getColorDepth() {
        return(this.colordepth);
    }
    
    //Return pixel #the private int
    RGBPixel getPixel(int row, int col) {
        return(this.RGBPixelImg[row][col]);
    }
    
    //Set pixel #the private int
    void setPixel(int row, int col, RGBPixel pixel) {
        this.RGBPixelImg[row][col] = pixel;
    }
    
    @Override
    public void grayscale() {
        //Variables
        int i,j;
        short gray;
        short red,green,blue;
        
        //Need to check if my Img is null
        if (this.RGBPixelImg == null) {
            //no Img to grayscale it
            return;
        }
        
        for (i = 0; i < this.height; i++) { //Running the lines
            for (j = 0; j < this.width; j++) { //The columns
                //Formula
                red = this.RGBPixelImg[i][j].getRed();
                green = this.RGBPixelImg[i][j].getGreen();
                blue = this.RGBPixelImg[i][j].getBlue();
                gray = (short)(red*0.3 +green*0.59 + blue*0.11);
                this.RGBPixelImg[i][j].setRGB(gray, gray, gray);
            }
        }
    }
    
    @Override
    public void doublesize() {
        //Variables
        int i, j;
        RGBPixel [][]doubleSizedImg;
        
        //Need to check if my Img is null
        if (this.RGBPixelImg == null) {
            //No Img to doubleSize
            return;
        }
        
        //Allocate propert space
        doubleSizedImg = new RGBPixel[2*this.height][2*this.width];
        
        for (i = 0; i < this.height; i++) { //For the rows
            for (j = 0; j < this.width; j++) { //For the columns
                doubleSizedImg[2*i][2*j] = this.RGBPixelImg[i][j];
                doubleSizedImg[2*i + 1][2*j] = this.RGBPixelImg[i][j];
                doubleSizedImg[2*i][2*j + 1] = this.RGBPixelImg[i][j];
                doubleSizedImg[2*i + 1][2*j + 1] = this.RGBPixelImg[i][j];
            }
        }
        
        //When copy ends
        //Our new values for height, width
        this.height *= 2;
        this.width *= 2;
        //Our new Img
        this.RGBPixelImg = doubleSizedImg;
    }
    
    
    @Override
    public void halfsize() {
        //Variables
        int row, col;
        RGBPixel [][]halfSizedImg;
        short redAvg, greenAvg, blueAvg;
        //Need to check if my Img is null
        if (this.RGBPixelImg == null) {
            //No Img to halfSize
            return;
        }
        
        //Our new values for height, width
        this.height /= 2;
        this.width /= 2;
        
        //Allocate propert space
        halfSizedImg = new RGBPixel[this.height][this.width];
        
        
        //Find Avg of each R G B
        for (row = 0; row < this.height; row++) { //for the rows
            for (col = 0; col < this.width; col++) { //for the columns
                redAvg = (short) (this.RGBPixelImg[2*row][2*col].getRed() +
                        this.RGBPixelImg[2*row + 1][2*col].getRed() +
                        this.RGBPixelImg[2*row][2*col + 1].getRed() +
                        this.RGBPixelImg[2*row + 1][2*col + 1].getRed());
                redAvg /= 4;
                
                greenAvg = (short) (this.RGBPixelImg[2*row][2*col].getGreen() +
                        this.RGBPixelImg[2*row + 1][2*col].getGreen() +
                        this.RGBPixelImg[2*row][2*col + 1].getGreen() +
                        this.RGBPixelImg[2*row + 1][2*col + 1].getGreen());
                greenAvg /= 4;
                
                blueAvg = (short) (this.RGBPixelImg[2*row][2*col].getBlue() +
                        this.RGBPixelImg[2*row + 1][2*col].getBlue() +
                        this.RGBPixelImg[2*row][2*col + 1].getBlue() +
                        this.RGBPixelImg[2*row + 1][2*col + 1].getBlue());
                blueAvg /= 4;
                
                //New R G B pixel
                halfSizedImg[row][col] = new RGBPixel(redAvg, greenAvg, blueAvg);
            }
        }
        
        
        //When copy ends
        //Our new Img
        this.RGBPixelImg = halfSizedImg;
        
    
    }
    @Override
    public void rotateClockwise() {
        //variables
        int i, j, row, column;
        int temp;
        RGBPixel [][]clockwisedImg;
        RGBPixel tmp;
        
        if (this.RGBPixelImg == null) {
            //Nothing to clockWise
            return;
        }
        
        //The new image(the rotated one)
        clockwisedImg = new RGBPixel[this.width][this.height];
        
        for (i = 0; i < this.height; i++) { //running the rows of our image we want to rotate
            for (j = 0; j < this.width; j++) { //the columns
                tmp = this.RGBPixelImg[i][j]; //Save the pixel #private int
                column = (this.height -i) - 1; //The column we want to copy this pixel
                row = j; //the row
                
                //Set the pixel
                clockwisedImg[row][column] = new RGBPixel(tmp.getRed(),tmp.getGreen(), tmp.getBlue());
            }
        }
        
        //Exchange the new values
        temp = this.height;
        this.height = this.width;
        this.width = temp;
    
        //Our new image 
        this.RGBPixelImg = clockwisedImg;
    
    }
    
    
    
}
