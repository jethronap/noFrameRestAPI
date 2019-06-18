package noframerest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jnap
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private String id;
    private String username;
    private String password;
    
}
