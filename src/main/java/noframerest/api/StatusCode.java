package noframerest.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author jnap
 */
@Getter
@AllArgsConstructor
public enum StatusCode {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    
    BAD_REQUEST(400),
    METHOD_NOT_ALLOWED(405);
    
    private int code;
}
