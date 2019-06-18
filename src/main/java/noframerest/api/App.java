package noframerest.api;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 *
 * @author jnap
 */
public class App {
    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/users/all", (exchange -> {
            
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "these are all the users";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1); // HTTP Response Method not Allowed
            }
            exchange.close();
    }));
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
