package noframerest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;
import noframerest.model.User;

/**
 *
 * @author jnap
 */
public class PostHandler implements HttpHandler {

    private final File json = new File("/Volumes/flobmusic/_archives/code/Java/JavaExamples/indie/noFrameRestAPI/user.json");
    // data binder for jackson:
    private ObjectMapper mapper = new ObjectMapper();

    public void handle(HttpExchange exchange) throws IOException {

        try {
            if ("POST".equals(exchange.getRequestMethod())) {

                String body = new BufferedReader(
                        new InputStreamReader(exchange.getRequestBody()
                        )
                ).lines().collect(Collectors.joining());

                exchange.getResponseHeaders().set(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
                exchange.sendResponseHeaders(200, 0);

                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(addUser(body).getBytes("UTF-8"));
                responseBody.close();

                System.out.println("--------done");
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        exchange.close();
    };

    @SuppressWarnings("unchecked")
    private String addUser(String requestBody) {

        // created instance for new user:
        User newUser = new User();
        
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode = mapper.createObjectNode();
        try {

            objectNode.put("id", newUser.getId());
            objectNode.put("username", newUser.getUsername());
            objectNode.put("password", newUser.getPassword());
            arrayNode.add(objectNode);
            mapper.writerWithDefaultPrettyPrinter().writeValue(json, arrayNode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestBody;//arrayNode.toString();
    }
}
