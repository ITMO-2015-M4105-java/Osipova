import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Надюша on 19.12.2015.
 */
public class HelloUDPClient {
    private static DatagramSocket ourSocket;
    private static String hostName;
    private static int port;
    private static String prefix;
    private static int threadsNumber, packagesNumber;
    private static InetSocketAddress address;
    private static byte[] buffer;
    private static DatagramPacket in, out;

    public static void main(String[] args){
        hostName=args[0];
        port = Integer.parseInt(args[1]);
        prefix = args[2];
        threadsNumber = Integer.parseInt(args[3]);
        packagesNumber = Integer.parseInt(args[4]);

        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadsNumber; i++){
            executor.execute(new UDPClient(hostName, port, prefix, packagesNumber, i));

        }
        executor.shutdown();
    }
    public static class UDPClient implements Runnable{
        private String host;
        private int port;
        private String prefix;
        private int packagesNumber, threadID;
        public UDPClient(String host, int port, String prefix, int packagesNumber, int threadID){
            this.host = host;
            this.port = port;
            this.prefix = prefix;
            this.packagesNumber = packagesNumber;
            this.threadID = threadID;
        }
        @Override
        public void run() {
            Integer counter=0;
            while (true) {
                try {

                    ourSocket = new DatagramSocket();
                    String outMessage = prefix + threadID + "_" + counter;
                    address = new InetSocketAddress(host, port);

                    DatagramPacket in = new DatagramPacket(new byte[9999], 9999);
                    DatagramPacket out = new DatagramPacket(outMessage.getBytes(), outMessage.length(), address);
                    ourSocket.send(out);
                    ourSocket.setSoTimeout(500);
                    ourSocket.receive(in);
                    String response = new String(in.getData(), in.getOffset(), in.getLength());
                    System.out.println("Response: " + response);


                }
                catch (SocketTimeoutException e){
                    counter--;

                }
                catch (SocketException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                finally{
                    counter++;
                    if (counter==packagesNumber) break;
                }

            }

        }
    }
}
