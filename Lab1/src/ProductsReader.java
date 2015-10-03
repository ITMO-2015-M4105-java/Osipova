import java.io.*;

/**
 * Created by Надюша on 02.10.2015.
 */
public class ProductsReader {
    private String directory;

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
                System.out.println(line);
            }
        }
    }
}
