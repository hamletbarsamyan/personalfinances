package am.jsl.personalfinances.search.user;

import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.search.Query;

/**
 * Custom {@link Query} for searching of {@link User} items.
 *
 * @author hamlet
 */
public class UserSearchQuery extends Query<User> {

    /**
     * Instantiates a new User search query.
     *
     * @param page     the page
     * @param pageSize the page size
     */
    public UserSearchQuery(int page, int pageSize) {
        super(page, pageSize);
    }
}
