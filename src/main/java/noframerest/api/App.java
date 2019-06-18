package noframerest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import noframerest.model.User;

/**
 *
 * @author jnap
 */
public class App {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;

        ObjectMapper mapper = new ObjectMapper();

        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/users/all", (exchange -> {
            try {
                if ("GET".equals(exchange.getRequestMethod())) {

                    /*
                    Json file to Java Object. Also we use the [] Array because 
                    Jackson expects an object not an array, as it is given in 
                    our json file:
                     */
                    User[] user = mapper.readValue(new File("/Volumes/flobmusic/_archives/code/Java/JavaExamples/indie/noFrameRestAPI/user.json"), User[].class);

                    //Pretty print user from json file:
                    String prettyUser = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);

                    exchange.sendResponseHeaders(200, prettyUser.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(prettyUser.getBytes());
                    output.flush();
                } else {
                    exchange.sendResponseHeaders(405, -1); // HTTP Response Method not Allowed
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            exchange.close();
        }));
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
