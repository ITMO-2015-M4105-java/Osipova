import java.io.*;
import java.util.*;

/**
 * Created by Надюша on 02.10.2015.
 */
public class ProductsReader {
    private String directory;
    private Map<String, PriceCounter> productMap= new TreeMap<String, PriceCounter>();
    ProductsReader(String directory) {
        this.directory = directory;
    }

    public void readFiles() throws IOException {
        File directoryFile = new File(directory);
        File[] files = directoryFile.listFiles();
        for (File file : files) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                StringTokenizer stringTokenizer=new StringTokenizer(line);
                String productName = stringTokenizer.nextToken();
                String productPriceString=stringTokenizer.nextToken();
                Integer productPrice = Integer.parseInt(productPriceString);
                if (productMap.containsKey(productName)){
                    PriceCounter priceCounter = productMap.get(productName);
                    Integer currentPrice = priceCounter.Price;
                    Integer currentCounter=priceCounter.Counter;
                    currentPrice=currentPrice+productPrice;
                    currentCounter=currentCounter+1;
                    priceCounter.Counter=currentCounter;
                    priceCounter.Price=currentPrice;
                    productMap.put(productName, priceCounter);
                }
                else {
                    PriceCounter priceCounter = new PriceCounter(productPrice,1);
                    productMap.put(productName,priceCounter);
                }
            }
        }
    }
    public void makeUpResult() throws IOException {
        FileWriter fileWriter=new FileWriter("result.txt");
       for (Map.Entry<String, PriceCounter> entry: productMap.entrySet()){
           String productName = entry.getKey();
           PriceCounter priceCounter = entry.getValue();
           String result = entry.getKey() +"_colour " + (priceCounter.Price/priceCounter.Counter);
           fileWriter.write(result+"\n");
       }
        fileWriter.close();
    }
}
