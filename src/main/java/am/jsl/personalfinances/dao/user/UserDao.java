package am.jsl.personalfinances.dao.user;

import am.jsl.personalfinances.dao.BaseDao;
import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.domain.user.VerificationToken;
import am.jsl.personalfinances.domain.user.VerificationTokenType;
import am.jsl.personalfinances.ex.UserNotFoundException;
import am.jsl.personalfinances.search.ListPaginatedResult;
import am.jsl.personalfinances.search.user.UserSearchQuery;

/**
 * The Dao interface for accessing {@link User} domain object.
 * @author hamlet
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Retrieves paginated result of users for the given search query.
     * @param userSearchQuery the {@link UserSearchQuery} containing query options
     * @return the {@link ListPaginatedResult} object containing paged result
     */
    ListPaginatedResult<User> search(UserSearchQuery userSearchQuery);

    /**
     * Sets last_login field with current date for the given user id.
     * @param userId the user id
     */
    void login(long userId);

    /**
     * Deletes an user with the given user id
     * @param id the user id
     */
    void deleteUser(long id);

    /**
     * Checks whether an user with the given login and id exists.
     * @param login the login
     * @param id user id
     * @return true if user login exists
     */
    boolean loginExists(String login, long id);

    /**
     * Checks whether an user with the given email and id exists.
     * @param email the emails
     * @param id the user id
     * @return true if user email exists
     */
    boolean emailExists(String email, long id);

    /**
     * Creates an user with the given user.
     * @param user the user
     */
    void create(User user);

    /**
     * Updates an user with the given user.
     * @param user the user
     */
    void update(User user);

    /**
     * Returns an user with the given id.
     * Will throw {@link UserNotFoundException} if user not found.
     * @param userId the user id
     * @return the user
     * @throws UserNotFoundException if user not found
     */
    User getUser(long userId) throws UserNotFoundException;

    /**
     * Changes user password with the given encryptedPassword.
     * @param encryptedPassword the encrypted password
     * @param userId the user id
     */
    void changePassword(String encryptedPassword, long userId);

    /**
     * Returns an user with the given user name.
     * Will throw {@link UserNotFoundException} if user not found.
     * @param username the user name
     * @return the user
     * @throws UserNotFoundException if user not found
     */
    User getUser(String username) throws UserNotFoundException;

    /**
     * Updates the icon for the given user.
     * @param user the user
     */
    void updateIcon(User user);

    /**
     * Updates the profile of the given user.
     * @param user the user
     */
    void updateProfile(User user) throws Exception;

    /**
     * Returns an user with the given email.
     * @param email the emails
     * @return the user
     */
    User getUserByEmail(String email);

    /**
     * Creates the given {@link VerificationToken}
     * @param verificationToken the VerificationToken
     */
    void createVerificationToken(VerificationToken verificationToken);

    /**
     * Updates the given {@link VerificationToken}
     * @param verificationToken the VerificationToken
     */
    void updateVerificationToken(VerificationToken verificationToken);

    /**
     * Returns the VerificationToken with the user id and tokenType.
     * @param userId the user id
     * @param tokenType the token type
     * @return the VerificationToken
     */
    VerificationToken getToken(long userId, VerificationTokenType tokenType);
}
