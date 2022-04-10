/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

/**
 *
 * @author dawg
 */
public class YUVPixel {

    //Variables
    private int YUVpix = 0;
    
    //Constructors
    public YUVPixel(short Y, short U, short V) {
        setYUV(Y, U, V);
    }
    
    //When YUVpixel given
    public YUVPixel(YUVPixel pixel) {
        YUVpix = pixel.getYUV();
    }
    
    //When RGBPixel given, we need to "translate" it into YUVpixel
    public YUVPixel(RGBPixel pixel) {
        short red, green, blue, Y, U, V;
        red = pixel.getRed();
        green = pixel.getGreen();
        blue = pixel.getBlue();
        
        Y = (short)(((66 * red + 129 * green +  25 * blue + 128) >> 8) +  16);
        U = (short)(((-38 * red -  74 * green + 112 * blue + 128) >> 8) + 128);
        V = (short)(((112 * red -  94 * green -  18 * blue + 128) >> 8) + 128);
        
        setYUV(Y,U,V);

    }
    //Methods
    
    //getY method
    public short getY() {
        return (short) (YUVpix >> 16);
    }
    
    //getU method
    public short getU() {
        return (short) ((YUVpix >> 8) & (0xff));
    }
    
    //getV method
    public short getV() {
        return (short) ((YUVpix) & (0xff));
    }
    
    //setY method
    public void setY(short Y) {
        short U, V;
        U = getU();
        V = getV();
        YUVpix = (((Y << 8)|U) << 8)|V;
    }
    
    //setY method
    public void setU(short U) {
        short Y, V;
        Y = getY();
        V = getV();
        YUVpix = (((Y << 8)|U) << 8)|V;
    }
    
    
    //setV method
    public void setV(short V) {
        short Y, U;
        Y = getY();
        U = getU();
        YUVpix = (((Y << 8)|U) << 8)|V;
    }
    
    //setYUV pixel #the private int
    public void setYUV(int value) {
        YUVpix = value;
    }
    
    //setYUV pixel when Y, U, V given
    public final void setYUV(short Y, short U, short V) {
        setY(Y);
        setU(U);
        setV(V);
    }
    
    //return YUV #the private int
    public int getYUV() {
        return(YUVpix);
    }
}
