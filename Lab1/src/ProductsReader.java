import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.*;

/**
 *  Created by Надюша on 02.10.2015.
 *  Класс, предназначенный для считывания строк из файлов с ассортиментом
 *  и последующей обработки оных.
 */

public class ProductsReader {
    private String directory;
    private Map<String, PriceCounter> productMap= new TreeMap<String, PriceCounter>();
    private Map<String, Integer> coloursMap = new LinkedHashMap<String, Integer>();
    ProductsReader(String directory, Map<String, Integer> coloursMap) {
        this.directory = directory;
        this.coloursMap = coloursMap;
    }
    public Map<String, PriceCounter> getProductMap(){
        return productMap;
    }
    public void readFiles() throws IOException {
            File directoryFile = new File(directory);
        if(directoryFile.listFiles().length==0){
            System.out.println("SORRY, THE DIRECTORY YOU ENTERED IS EMPTY. CHECK THE DIRECTORY PATH AND TRY AGAIN.");
            System.exit(0);
        }
            File[] files = directoryFile.listFiles();
        for (File file : files) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList tempArray = new ArrayList();
            String line = null;
            while ((line = br.readLine()) != null) {
                StringTokenizer stringTokenizer=new StringTokenizer(line);
                if (stringTokenizer.countTokens()!=2){
                    continue;
                }
                String productName = stringTokenizer.nextToken();
                String productPriceString=stringTokenizer.nextToken();
                Integer productPrice=0;

                if (tempArray.contains(productName)){
                    continue;
                }
                else{
                    tempArray.add(productName);
                }

                if(!(productName.matches("^[a-zA-Zа-яА-Я]+$")))
                {
                    continue;
                }

                if(productPriceString.matches("^[0-9]+$"))
                {
                    productPrice = Integer.parseInt(productPriceString);
                }
                else{
                    continue;
                }
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
            tempArray.clear();
        }

    }
    public void makeUpResult() throws IOException {
        FileWriter fileWriter=new FileWriter("result.txt");
       for (Map.Entry<String, PriceCounter> entry: productMap.entrySet()){
           String productName = entry.getKey();
           PriceCounter priceCounter = entry.getValue();
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
