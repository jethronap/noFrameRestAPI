package noframerest.model;

import lombok.Builder;
import lombok.Value;

/**
 *
 * @author jnap
 */

@Value
@Builder
public class User {

    String id;
    String username;
    String password;
}
