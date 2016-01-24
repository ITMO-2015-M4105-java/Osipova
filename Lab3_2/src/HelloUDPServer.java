import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Надюша on 19.12.2015.
 */
public class HelloUDPServer {
    public static int port;
    public static int threadsNumber;
    public static DatagramSocket ourSocket;
    public static DatagramPacket in,out;
    public static InetSocketAddress address;

    static class UDPServer implements Runnable{
        public static DatagramSocket serverSocket;
        public UDPServer(DatagramSocket serverSocket){
            this.serverSocket = serverSocket;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    serverSocket.receive(in);
                    String incomingMessage = new String(in.getData(), in.getOffset(), in.getLength());
                    String response = "Hello," + incomingMessage;
                    out = new DatagramPacket(response.getBytes(), response.getBytes().length, in.getAddress(), in.getPort());
                    serverSocket.send(out);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }

        }
    }
    public static void main(String[] args){
        port = Integer.parseInt(args[0]);
        threadsNumber = Integer.parseInt(args[1]);
        InetSocketAddress address = new InetSocketAddress("localhost", port);
        in = new DatagramPacket(new byte[9999], 9999);
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            ourSocket= new DatagramSocket(address);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < threadsNumber; i++){
            executor.execute(new UDPServer(ourSocket));

        }
        executor.shutdown();

    }

}
