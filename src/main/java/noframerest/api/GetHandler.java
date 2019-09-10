package noframerest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import noframerest.model.User;

/**
 *
 * @author jnap
 */
public class GetHandler implements HttpHandler {

    private final File json = new File("/Volumes/flobmusic/_archives/code/Java/JavaExamples/indie/noFrameRestAPI/user.json");
    
    // data binder for jackson:
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();

        try {
            if ("GET".equals(requestMethod)) {

                /*
                    Json file to Java Object. Also we use the [] Array because 
                    Jackson expects an object not an array, as it is given in 
                    our json file:
                 */
                User[] user = mapper.readValue(json, User[].class);

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
        }
        exchange.close();

    }
}
