package am.jsl.personalfinances.service.user;

import am.jsl.personalfinances.domain.user.Role;
import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.domain.user.VerificationToken;
import am.jsl.personalfinances.domain.user.VerificationTokenType;
import am.jsl.personalfinances.dto.user.PasswordResetDTO;
import am.jsl.personalfinances.search.ListPaginatedResult;
import am.jsl.personalfinances.search.user.UserSearchQuery;
import am.jsl.personalfinances.service.BaseTest;
import am.jsl.personalfinances.util.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains UserService tests.
 */
public class UserServiceTest extends BaseTest {

    /**
     * The password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Executed before all UserServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
    }

    @Test
    @DisplayName("Create User Test")
    public void testCreateUser() throws Exception {
        log.info("Starting test for create user");
        User user = new User();
        user.setLogin(RandomStringUtils.randomAlphabetic(8));
        user.setPassword("testpassword");
        user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@gmail.com");
        user.setFirstName("First Name");
        user.setLastName("Last Name");
        user.setCreatedAt(LocalDateTime.now());
        user.setChangedBy(DEFAULT_USER_ID);
        user.setChangedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setEnabled(true);
        userService.create(user);

        assertTrue(user.getId() > 0);

        log.info("Finished test for create user");
    }

    @Test
    @DisplayName("Register User Test")
    public void testRegisterUser() throws Exception {
        log.info("Starting test for register user");
        User user = new User();
        user.setLogin(RandomStringUtils.randomAlphabetic(8));
        user.setPassword("testpassword");
        user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@gmail.com");
        user.setFirstName("First Name");
        user.setLastName("Last Name");
        user.setChangedBy(DEFAULT_USER_ID);
        userService.register(user, Locale.getDefault(), "");

        // validate user
        user = userService.getUser(user.getId());
        assertTrue(user.getId() > 0);
        assertFalse(user.isEnabled());
        assertEquals(Role.USER, user.getRole());

        // confirm registration
        VerificationToken verificationToken = userService.getToken(user.getId(), VerificationTokenType.NEW_ACCOUNT);
        assertFalse(verificationToken.isExpired());
        userService.confirmRegistration(user.getId(), verificationToken.getToken());

        // validate user
        user = userService.getUser(user.getId());
        assertTrue(user.isEnabled());

        verificationToken = userService.getToken(user.getId(), VerificationTokenType.NEW_ACCOUNT);
        assertTrue(verificationToken.isExpired());

        log.info("Finished test for register user");
    }

    @Test
    @DisplayName("Update User Test")
    public void testUpdateUser() throws Exception {
        log.info("Starting test for update user");
        String login = RandomStringUtils.randomAlphabetic(8);
        String email = RandomStringUtils.randomAlphabetic(8) + "@gmail.com";
        String firstName = "firstname updated";
        String lastName = "lastname updated";
        String phone = "phone updated";
        boolean status = false;
        Role role = Role.ADMIN;

        User user = createUser();

        // update user
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setEnabled(status);
        user.setRole(role);
        userService.update(user);

        // validate user
        user = userService.getUser(login);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(phone, user.getPhone());
        assertEquals(status, user.isEnabled());
        assertEquals(role, user.getRole());
        log.info("Finished test for update user");
    }

    @Test
    @DisplayName("Update User Profile Test")
    public void testUpdateUserProfile() throws Exception {
        log.info("Starting test for update user profile");
        String login = RandomStringUtils.randomAlphabetic(8);
        String email = RandomStringUtils.randomAlphabetic(8) + "@gmail.com";
        String firstName = "firstname updated";
        String lastName = "lastname updated";
        String phone = "phone updated";

        User user = createUser();

        // update user
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        userService.updateProfile(user);

        // validate user
        user = userService.getUser(login);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(phone, user.getPhone());
        assertTrue(user.isEnabled());
        assertEquals(Role.USER, user.getRole());

        log.info("Finished test for update user profile");
    }

    @Test
    @DisplayName("Reset Password Test")
    public void testResetPassword() throws Exception {
        log.info("Starting test for reset password");
        User user = createUser();
        String email = user.getEmail();
        String newPassword = "newpassword";

        userService.sendPasswordResetMail("", email, Locale.getDefault());

        VerificationToken verificationToken = userService.getToken(user.getId(), VerificationTokenType.PASSWORD_RESET);
        assertFalse(verificationToken.isExpired());

        // reset password
        PasswordResetDTO passwordResetDTO = new PasswordResetDTO();
        passwordResetDTO.setUserId(user.getId());
        passwordResetDTO.setLogin(user.getLogin());
        passwordResetDTO.setToken(verificationToken.getToken());
        passwordResetDTO.setNewPassword(newPassword);
        passwordResetDTO.setReNewPassword(newPassword);
        userService.resetPassword(passwordResetDTO);

        // validate user
        verificationToken = userService.getToken(user.getId(), VerificationTokenType.PASSWORD_RESET);
        assertTrue(verificationToken.isExpired());
        user = (User) userService.loadUserByUsername(user.getLogin());

        assertTrue( passwordEncoder.matches(newPassword, user.getPassword()));

        log.info("Finished test for reset password");
    }

    @Test
    @DisplayName("Delete User Test")
    public void testDeleteUser() throws Exception {
        log.info("Starting test for delete user");

        User user = createUser();
        long userId = user.getId();

        userService.deleteUser(user);

        // validate user
        user = userService.get(userId, userId);
        assertNull(user);

        log.info("Finished test for delete user");
    }

    @Test
    @DisplayName("Search Users Test")
    public void testSearchUsers() {
        log.info("Starting test for search users");

        UserSearchQuery query = new UserSearchQuery(1, Constants.PAGE_SIZE);
        ListPaginatedResult<User> result = userService.search(query);

        assertTrue(result.getTotal() > 0);
        log.info("Finished test for search users");
    }

    /**
     * Executed after all UserServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}