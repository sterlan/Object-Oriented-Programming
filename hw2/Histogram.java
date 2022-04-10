/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

/**
 *
 * @author dawg
 */
public class Histogram {
    
    //Variables
    int []histogramArray;
    final int MAX_LUMINOCITY = 236;
    private int totalPix;
    
    //Constructor
    public Histogram(YUVImage img) {
        
        //Variables
        int rows, columns;
        
    
        //Check if input is NULL
        if (img.YUVPixelImg == null) {
            return;
        }
        
        //Creation of the HistogramArray
        histogramArray = new int[MAX_LUMINOCITY];
        
        //Initialize the Array
        for (columns = 0; columns < MAX_LUMINOCITY; columns++) {
            histogramArray[columns] = 0;
        }
        
        for (rows = 0; rows < img.height; rows++) {
            for (columns = 0; columns < img.width; columns++) {
                histogramArray[img.YUVPixelImg[rows][columns].getY()] += 1;
            }
        }
        
        //Set the number of Total Pixels
        totalPix = img.height*img.width;
    }
    
    //Methods
    @Override
    //Our toString() method
    public String toString() {
        
        //Variables
        StringBuilder histogram = new StringBuilder();
        int i, j;
        int thousands, hundreds, tens , ones;
        
        //Check if histogram not-exists
        if (histogramArray == null) {
            return null;
        }
        
        //Run MAX_LUMINOCITY times
        for (i = 0; i < MAX_LUMINOCITY; i++) {
            
            //a,b sessions of the project
            histogram.append(String.format("\n%3d.(%4d)\t",i, histogramArray[i]));
            thousands = hundreds = tens = ones = 0;
            
            
            //Finding the thousands
            for (j = 0; j < histogramArray[i]/1000; j++) {
                thousands += 1;
            }
            
            //Finding the hundreds
            for (j = 0; j < ((histogramArray[i] - thousands*1000)/100 ); j++) {
                hundreds += 1;
            }
            
            //Finding the tens
            for (j = 0; j < ((histogramArray[i] - thousands*1000 - hundreds*100)/10); j++) {
                tens += 1;
            }
            
            //Finding the ones
            for (j = 0; j < ((histogramArray[i] - thousands*1000 - hundreds*100 - tens*10)/1); j++) {
                ones += 1;
            }
            
            //The append - session
            
            //Append # for each thousand
            for (j = 0; j < thousands; j++) {
                histogram.append("#");
            }
            //Append $ for each hundred
            for (j = 0; j < hundreds; j++) {
                histogram.append("$");
            }
            //Append @ for each ten
            for (j = 0; j < tens; j++) {
                histogram.append("@");
            }
            //Append * for each one
            for (j = 0; j < ones; j++) {
                histogram.append("*");
            }

        }
        
        //Append \n
    histogram.append("\n");
    return histogram.toString();
        
    }
    
    //method to file
    public void toFile (File file) {
        
        
        //Check if file exists
        if (file.exists()) {
            file.delete();
        } 
        
        try {
            //Try create the file
            if (!file.createNewFile()) {
                return; 
            }
            try ( //After successfull creation, need to write into the file
                    FileWriter flWriter = new FileWriter(file)) {
                    flWriter.write(toString());
                    //close the fileWriter when job's finished
            }
        } catch (IOException e) {}
     }
    
    //Method getEqualizedLuminoctiy
    //Returns the new luminocity which corresponds to old luminocity
    public short getEqualizedLuminocity(int luminocity) {
        return (short) histogramArray[luminocity];
    }
    
    
    //Method equalize
    public void equalize() {
        //Variables
        int i;
        //cumulative probability distribution array
        double []cumulativeProbArray;
        int [] newHistogramArray;
        
        //Initialize
        cumulativeProbArray = new double[MAX_LUMINOCITY];
        newHistogramArray = new int[MAX_LUMINOCITY];
        
        //calculate the probs for each sit of histogrammArray
        //Step 1
        for (i = 0; i < MAX_LUMINOCITY; i++) {
            cumulativeProbArray[i] = (double) (histogramArray[i]) / (double) (totalPix);
        }
        
        //Step 2
        for (i = 1; i < MAX_LUMINOCITY; i++) {
            cumulativeProbArray[i] += cumulativeProbArray[i - 1];
        }
        
        //Step 3: We want MAX_LUMINOCITY - 1 = 235
        //Initialize the new HistogramArray
        for (i = 0; i < MAX_LUMINOCITY; i++) {
            newHistogramArray[i] = 0;
        }
        
        //Step 4: Multiply the cumulativeProbArray by (MAX_LUMINOCITY - 1)
        //Store it to newHistogramArray
        for (i = 0; i < MAX_LUMINOCITY; i++) {
            newHistogramArray[i] = (int) (cumulativeProbArray[i] * (MAX_LUMINOCITY - 1));
        }
        
        //Set the new array
        //Step 5
        histogramArray = newHistogramArray;
    
    }
    
    
}
