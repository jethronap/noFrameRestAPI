package noframerest.dao;

import noframerest.model.NewUser;

/**
 *
 * @author jnap
 */
public interface UserDao {
    String create(NewUser user);
}
