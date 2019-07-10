package noframerest.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
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
    private ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

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
    }

    ;

    @SuppressWarnings("unchecked")
    private String addUser(String requestBody) {


        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();

        try {

            JsonNode objectNode = mapper.readTree(requestBody);
            User toValue = mapper.treeToValue(objectNode, User.class);

            ((ObjectNode) objectNode).put("id", toValue.getId());
            ((ObjectNode) objectNode).put("username", toValue.getUsername());
            ((ObjectNode) objectNode).put("password", toValue.getPassword());

            arrayNode.addPOJO(toValue);//.add(objectNode);
            //arrayNode.add(objectNode);
            
            mapper.writerWithDefaultPrettyPrinter().writeValue(json, arrayNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestBody;
    }
}
