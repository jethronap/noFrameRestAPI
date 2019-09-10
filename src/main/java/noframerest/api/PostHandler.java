package noframerest.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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
    private final ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

    @Override
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

                System.out.println("POST--------done");
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        } catch (IOException e) {
        }
        exchange.close();
    }

    ;

    @SuppressWarnings("unchecked")
    private String addUser(String requestBody) {

        try {
            FileWriter output = new FileWriter(json);

            /* object node that is created from the requestBody,
               so that we can have access to its value parameters.
             */
            JsonNode objectNode = mapper.readTree(requestBody);
            // convert the JsonNode to POJO:
            User newUser = mapper.convertValue(objectNode, User.class);

            ((ObjectNode) objectNode).put("id", newUser.getId());
            ((ObjectNode) objectNode).put("username", newUser.getUsername());
            ((ObjectNode) objectNode).put("password", newUser.getPassword());

            SequenceWriter seqWriter = mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false)
                    .writerWithDefaultPrettyPrinter().writeValuesAsArray(output);
            seqWriter.write(objectNode);

            seqWriter.close();
        } catch (IOException | IllegalArgumentException e) {
        }
        return requestBody;
    }
}
