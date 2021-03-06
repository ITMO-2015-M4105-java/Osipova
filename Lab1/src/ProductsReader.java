import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.*;

/**
 *  Created by AnSick on 02.10.2015.
 *
 *  Class for reading information from files, validating the usability of this information,
 *  processing information and creation of file with results of this process.
 */

public class ProductsReader {
    /**
     * String for the address of directory where the product files are stored.
     */
    private String directory;
    /**
     * TreeMap for collecting all of the products.
     * TreeMap is used because we need to have a sorted list of products.
     */
    private Map<String, PriceCounter> productMap= new TreeMap<String, PriceCounter>();
    /**
     * LinkedHashMap for collecting all of the colours.
     */
    private Map<String, Integer> coloursMap = new LinkedHashMap<String, Integer>();
    /**
     *Constructor for the class.
     */
    ProductsReader(String directory, Map<String, Integer> coloursMap) {
        this.directory = directory;
        this.coloursMap = coloursMap;
    }

    /**
     * Getter for the map with products.
     */
    public Map<String, PriceCounter> getProductMap(){
        return productMap;
    }

    /**
     * Method for reading the products from the files and arranging them.
     * @throws IOException
     */
    public void readFiles() throws IOException {
            File directoryFile = new File(directory);
        //We need to make sure that there are files in the directory.
        if(directoryFile.listFiles().length==0){
            System.out.println("SORRY, THE DIRECTORY YOU ENTERED IS EMPTY. CHECK THE DIRECTORY PATH AND TRY AGAIN.");
            System.exit(0);
        }
        //Getting the list of files in the directory.
            File[] files = directoryFile.listFiles();
        //Reading products info from each file and validating it, then adding them to the map.
        for (File file : files) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            //tempArray is needed to know whether we had such a product in the current file or not.
            ArrayList tempArray = new ArrayList();
            String line = null;
            while ((line = br.readLine()) != null) {
                StringTokenizer stringTokenizer=new StringTokenizer(line);
               //If the amount of string tokens is not 2, we don't need to process this line.
                if (stringTokenizer.countTokens()!=2){
                    continue;
                }
                String productName = stringTokenizer.nextToken();
                String productPriceString=stringTokenizer.nextToken();
                Integer productPrice=0;
                //If we already had such product in current file, we don't need to add it.
                if (tempArray.contains(productName)){
                    continue;
                }
                //If we never had this product in current file, we need to add it.
                else{
                    tempArray.add(productName);
                }
                //If the first token is not a string with words, we don't need to process the line.
                if(!(productName.matches("^[a-zA-Zа-яА-Я]+$")))
                {
                    continue;
                }
                //If the second token is not a number, we don't need to process the line.
                if(productPriceString.matches("^[0-9]+$"))
                {
                    productPrice = Integer.parseInt(productPriceString);
                }
                else{
                    continue;
                }

                if (productMap.containsKey(productName)){
                    /**
                     * If we already have such product in the collection of products, we
                     * need to summ up the price that we already have stored with the new price
                     * and then change the counter so that we will be able to count the
                     * average price later.
                     */
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
            tempArray.clear();
        }

    }

    /**
     * Method for mixing products and colours and then writing them into the result file.
     * @throws IOException
     */
    public void makeUpResult() throws IOException {
        FileWriter fileWriter=new FileWriter("result.txt");
       for (Map.Entry<String, PriceCounter> entry: productMap.entrySet()){
           String productName = entry.getKey();
           PriceCounter priceCounter = entry.getValue();
           //If there were more than 1 product entries we need to count the average.
           if (priceCounter.Counter>1) {
               for (Map.Entry<String, Integer> entry1 : coloursMap.entrySet()) {
                   Double resultPrice = priceCounter.Price.doubleValue() / priceCounter.Counter;
                   String result = entry.getKey() + entry1.getKey() + " " + ((resultPrice) + entry1.getValue());
                   fileWriter.write(result + "\n");
               }
           }
           else{
               for (Map.Entry<String, Integer> entry1 : coloursMap.entrySet()) {
                   String result = entry.getKey() + entry1.getKey() + " " + ((priceCounter.Price/priceCounter.Counter) + entry1.getValue());
                   fileWriter.write(result + "\n");
               }
           }
       }
        fileWriter.close();
    }
}
