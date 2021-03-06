import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by AnSick on 04.10.2015.
 *
 * Class for reading the colours from the colours file and adding them for future proccessing.
 */
public class ColoursReader {
    /**
     * String for storing the name of file with colour names in it.
     */
    private String coloursFilename;
    /**
     * Map for storing all the colours and their prices.
     */
    private Map<String, Integer> colourMap = new LinkedHashMap<String, Integer>();

    /**
     * Constructor for the class.
     * @param coloursFilename
     */
    ColoursReader(String coloursFilename) {
        this.coloursFilename = coloursFilename;
    }

    /**
     * Method for reading colours from the file and adding them to the map.
     * @throws IOException
     */
    public void readColours() throws IOException {
        try {
            File coloursFile = new File(coloursFilename);
            BufferedReader br = new BufferedReader(new FileReader(coloursFile));
        String line = null;
            while ((line = br.readLine()) != null) {
            StringTokenizer colourStringTokenizer = new StringTokenizer(line);
               //If the amount of tokens is not 2, we don't need to process this line.
                if (colourStringTokenizer.countTokens()!=2){
                    continue;
                }
            String colourName = colourStringTokenizer.nextToken();
            String colourPriceString=colourStringTokenizer.nextToken();


            Integer colourPrice = Integer.parseInt(colourPriceString);
            colourMap.put("_"+colourName, colourPrice);
        }
        br.close();
            //In order to not have the null pointer exception.
            if (colourMap.isEmpty()){
                colourMap.put("",0);
            }
        }
        catch(FileNotFoundException e){
            colourMap.put("",0);
        }
    }

    /**
     * Basic getter for the colours map.
     * @throws IOException
     */
    public Map<String, Integer> getColourMap() throws IOException {
        readColours();
        return this.colourMap;
    }
}
