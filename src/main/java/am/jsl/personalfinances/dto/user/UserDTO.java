package am.jsl.personalfinances.dto.user;

import am.jsl.personalfinances.domain.user.Role;
import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.util.Constants;
import am.jsl.personalfinances.util.TextUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The type User dto.
 *
 * @author hamlet
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 8416849345869102122L;

    /**
     * The internal identifer
     */
    private long id;

    /**
     * The user login
     */
    private String login;

    /**
     * The user first name
     */
    private String firstName;

    /**
     * The user last name
     */
    private String lastName;

    /**
     * The user email
     */
    private String email;

    /**
     * The user phone
     */
    private String phone;

    /**
     * The user icon
     */
    private String icon = null;

    /**
     * The user password
     */
    private String password;

    /**
     * The user confirm password
     */
    private String confirmPassword;

    /**
     * Indicates whether this user is enabled
     */
    private boolean enabled;

    /**
     * The role of this user
     */
    private Role role;

    /**
     * The last logged in date
     */
    private LocalDateTime lastLogin = null;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets icon.
     *
     * @param icon the icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets confirm password.
     *
     * @return the confirm password
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets confirm password.
     *
     * @param confirmPassword the confirm password
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Is enabled boolean.
     *
     * @return the boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets last login.
     *
     * @return the last login
     */
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets last login.
     *
     * @param lastLogin the last login
     */
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Return the user icon full path.
     *
     * @return the icon path
     */
    public String getIconPath() {
        if (!TextUtils.isEmpty(icon)) {
            StringBuilder path = new StringBuilder();
            path.append(Constants.USER_IMG_PATH).append(id);
            path.append(Constants.SLASH).append(icon);
            return path.toString();
        }
        return Constants.USER_PROFILE_DEFAULT_IMG;
    }

    /**
     * Creates a User DTO object from User domain object.
     *
     * @param user the User
     * @return the UserDTO
     */
    public static UserDTO from(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setIcon(user.getIcon());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    /**
     * Converts this user dto to User domain object.
     *
     * @return the User domain object
     */
    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setIcon(icon);
        user.setEnabled(enabled);
        user.setRole(role);
        return user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.login, other.login)
                && Objects.equals(this.email, other.email);
    }
}
