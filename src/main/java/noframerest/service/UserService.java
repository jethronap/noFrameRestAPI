package noframerest.service;

import lombok.AllArgsConstructor;
import noframerest.dao.UserDao;
import noframerest.model.NewUser;

/**
 *
 * @author jnap
 */
@AllArgsConstructor
public class UserService {
    private final UserDao dao;
    
    public String create(NewUser user) {
        return dao.create(user);
    }
}
