package noframerest.api;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author jnap
 */
public class App {


    public static void main(String[] args) throws IOException {
        int serverPort = 8000;

        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/users/all", new GetHandler());
        server.createContext("/users/add", new PostHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port" + serverPort);
    }
}
