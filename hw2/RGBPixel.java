/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

/**
 *
 * @author dawg
 */
public class RGBPixel {
    
    //Variables
    private int RGBpix = 0;
    //constructors
    public RGBPixel(short red, short green, short blue) {
        setRGB(red, green, blue);
    }
    
    //RGB constructor when RGBpixel is given
    public RGBPixel(RGBPixel pixel) {
        RGBpix = pixel.getRGB();
    }
    
    //RGB constructor when YUVpixel is given
    public RGBPixel(YUVPixel pixel) {
        short R, G, B, C, D, E;
        
        
        //for YUVPixel
        C = (short) (pixel.getY() - 16);
        D = (short) (pixel.getU() - 128);
        E = (short) (pixel.getV() - 128);
        
        //for the new RGBPixel
        R = clip ((short)((298*C + 409*E +128)>>8));
        G = clip ((short)(( 298 * C - 100 * D - 208 * E + 128) >> 8));
        B = clip ((short)(( 298 * C + 516 * D           + 128) >> 8));
        
        setRGB(R,G,B);
    }
    
    //Methods
    
    //GetRed Method
    public short getRed() {
        //Shifting by 16 bits(2 bytes) to getRed
        return (short) ((RGBpix >> 16));
    }
    
    //GetGreen method
    public short getGreen() {
        //Shifting by 8 bits(1 bytes) to getGreen
        return (short) ((RGBpix >> 8) & (0xff));
    }
    
    //getBlue method
    public short getBlue() {
        //Since blue position is the last 8 bits, no shift needed
        return (short) ((RGBpix) & (0xff));
    }
    
    
    //for every R G B color , we need to save the other two
    //colors before the shift
    //SetRed method
    public void setRed(short red) {
        short blue, green;
        blue = getBlue();
        green = getGreen();
        RGBpix = (((red << 8)|green) << 8)|blue;
    }
    
    //SetGreen method
    public void setGreen(short green) {
        short blue, red;
        blue = getBlue();
        red = getRed();
        RGBpix = (((red << 8)|green) << 8)|blue;
    }
    
    //SetBlue method
    public void setBlue(short blue) {
        short red, green;
        red = getRed();
        green = getGreen();
        RGBpix = (((red << 8)|green) << 8)|blue;
    
    }
    
    //getRBG pixel #the private int
    public int getRGB() {
        return(RGBpix);
    }
    
    //setRGB pixel #the private int
    public void setRGB(int value) {
        RGBpix = value;
    }
    
    //setRGB pixel when red, gree, blue given
    public final void setRGB(short red, short green, short blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }
    
    //Method for my String output
    @Override
    public String toString() {
        return String.format("%d %d %d\n", getRed(), getGreen(), getBlue());
    }
    
    //method clip
    private short clip(short value) {
        if (value < 0) {
            value = 0;
        } else if (value > 255) {
            value = 255;
        }
    return value;
    }
}
