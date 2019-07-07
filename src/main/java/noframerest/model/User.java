package noframerest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jnap
 */
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @JsonProperty("id")
    private String id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

}
