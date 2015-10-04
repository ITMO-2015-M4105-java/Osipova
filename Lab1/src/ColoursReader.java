import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Надюша on 04.10.2015.
 */
public class ColoursReader {
    private String coloursFilename;
    private Map<String, Integer> colourMap = new LinkedHashMap<String, Integer>();

    ColoursReader(String coloursFilename) {
        this.coloursFilename = coloursFilename;
    }

    public void readColours() throws IOException {
        try {
            File coloursFile = new File(coloursFilename);

            BufferedReader br = new BufferedReader(new FileReader(coloursFile));
        String line = null;
        while ((line = br.readLine()) != null) {
            StringTokenizer colourStringTokenizer = new StringTokenizer(line);
            String colourName = colourStringTokenizer.nextToken();
            String colourPriceString=colourStringTokenizer.nextToken();
            if (colourStringTokenizer.hasMoreTokens()){
                continue;
            }
            Integer colourPrice = Integer.parseInt(colourPriceString);
            colourMap.put("_"+colourName, colourPrice);
        }
        br.close();
            if (colourMap.isEmpty()){
                colourMap.put("",0);
            }
        }
        catch(FileNotFoundException e){
            colourMap.put("",0);
        }
    }
    public Map<String, Integer> getColourMap() throws IOException {
        readColours();
        return this.colourMap;
    }
}
