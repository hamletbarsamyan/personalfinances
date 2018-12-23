package am.jsl.personalfinances.service.user;

import am.jsl.personalfinances.dao.user.UserDao;
import am.jsl.personalfinances.domain.user.Role;
import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.domain.user.VerificationToken;
import am.jsl.personalfinances.domain.user.VerificationTokenType;
import am.jsl.personalfinances.dto.user.PasswordResetDTO;
import am.jsl.personalfinances.ex.DuplicateEmailException;
import am.jsl.personalfinances.ex.DuplicateUserException;
import am.jsl.personalfinances.ex.InvalidTokenException;
import am.jsl.personalfinances.ex.UserNotFoundException;
import am.jsl.personalfinances.search.ListPaginatedResult;
import am.jsl.personalfinances.search.user.UserSearchQuery;
import am.jsl.personalfinances.service.BaseServiceImpl;
import am.jsl.personalfinances.service.EmailService;
import am.jsl.personalfinances.util.GenerateShortUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * The service implementation of the {@link UserService}.
 * @author hamlet
 */
@Service("userService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    /**
     * The user dao.
     */
    private UserDao userDao;

    /**
     * The password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * The email service.
     */
    @Autowired
    private EmailService emailService;

    @Override
    public User getUser(String name) throws UserNotFoundException {
        return userDao.getUser(name);
    }

    @Override
    public ListPaginatedResult<User> search(UserSearchQuery userSearchQuery) {
        try {
            return userDao.search(userSearchQuery);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ListPaginatedResult<>();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void login(long userId){
        userDao.login(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#user.login")})
    public void deleteUser(User user) {
        userDao.deleteUser(user.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void sendPasswordResetMail(String contextPath, String email, Locale locale) throws MessagingException {
        User user = userDao.getUserByEmail(email);

        if (user != null) {
            VerificationToken verificationToken = userDao.getToken(user.getId(), VerificationTokenType.PASSWORD_RESET);

            if (verificationToken == null) {
                verificationToken = new VerificationToken();
            }
            verificationToken.setUserId(user.getId());

            String token = GenerateShortUUID.next();
            verificationToken.updateToken(token);
            verificationToken.setTokenType(VerificationTokenType.PASSWORD_RESET.getValue());

            if (verificationToken.getId() == 0) {
                userDao.createVerificationToken(verificationToken);
            } else {
                userDao.updateVerificationToken(verificationToken);
            }

            final String resetPasswordLink = contextPath + "/user-public/reset-password?id=" + user.getId() + "&token=" + token;
            emailService.sendPasswordResetMail(email, resetPasswordLink, locale);
        } else {
            log.debug("Email not found {}", email);
        }
    }

    @Override
    public VerificationToken checkToken(long userId, String token, VerificationTokenType tokenType) throws InvalidTokenException {
        VerificationToken verificationToken = userDao.getToken(userId, tokenType);

        if (verificationToken == null
                || !verificationToken.getToken().equals(token)
                || verificationToken.isExpired()) {
            throw new InvalidTokenException();
        }

        return verificationToken;
    }

    @Override
    public VerificationToken getToken(long userId, VerificationTokenType tokenType) {
        return userDao.getToken(userId, tokenType);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#passwordResetDTO.login")})
    @Override
    public void resetPassword(PasswordResetDTO passwordResetDTO) throws InvalidTokenException {
        String token = passwordResetDTO.getToken();
        long userId = passwordResetDTO.getUserId();

        // check, change token to expired
        VerificationToken verificationToken = checkToken(userId, token, VerificationTokenType.PASSWORD_RESET);
        verificationToken.setExpired(true);
        userDao.updateVerificationToken(verificationToken);

        // change password
        String encryptedPassword = passwordEncoder.encode(passwordResetDTO.getNewPassword());
        userDao.changePassword(encryptedPassword, userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void register(User user, Locale locale, String contextPath) throws Exception {

        if (userDao.loginExists(user.getLogin(), user.getId())) {
            throw new DuplicateUserException();
        }

        String email = user.getEmail();

        if (userDao.emailExists(email, user.getId())) {
            throw new DuplicateEmailException();
        }

        // create user
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setChangedAt(LocalDateTime.now());
        user.setEnabled(false);
        userDao.create(user);

        // create verification token
        VerificationToken verificationToken = userDao.getToken(user.getId(), VerificationTokenType.NEW_ACCOUNT);

        if (verificationToken == null) {
            verificationToken = new VerificationToken();
        }
        verificationToken.setUserId(user.getId());

        String token = GenerateShortUUID.next();
        verificationToken.updateToken(token);
        verificationToken.setTokenType(VerificationTokenType.NEW_ACCOUNT.getValue());

        if (verificationToken.getId() == 0) {
            userDao.createVerificationToken(verificationToken);
        } else {
            userDao.updateVerificationToken(verificationToken);
        }

        final String registrationConfirmLink = contextPath + "/user-public/confirm-registration?id=" + user.getId() + "&token=" + token;
        emailService.sendRegistrationMail(email, registrationConfirmLink, locale);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void confirmRegistration(Long userId, String token) throws InvalidTokenException, UserNotFoundException {
        VerificationToken verificationToken = userDao.getToken(userId, VerificationTokenType.NEW_ACCOUNT);

        if (verificationToken == null
                || !verificationToken.getToken().equals(token)
                || verificationToken.isExpired()) {
            throw new InvalidTokenException();
        }

        // enable user
        User user = userDao.getUser(userId);
        user.setEnabled(true);
        user.setChangedBy(user.getId());
        user.setChangedAt(LocalDateTime.now());
        userDao.update(user);

        // update verification
        verificationToken.setExpired(true);
        userDao.updateVerificationToken(verificationToken);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#user.login")})
    public void create(User user) throws Exception {
        if (user.getId() == 0) {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
        }
        if (userDao.loginExists(user.getLogin(), user.getId())) {
            throw new DuplicateUserException();
        }

        if (userDao.emailExists(user.getEmail(), user.getId())) {
            throw new DuplicateEmailException();
        }
        userDao.create(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#user.login")})
    public void update(User user) throws Exception {
        if (userDao.loginExists(user.getLogin(), user.getId())) {
            throw new DuplicateUserException();
        }

        if (userDao.emailExists(user.getEmail(), user.getId())) {
            throw new DuplicateEmailException();
        }
        userDao.update(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#user.login")})
    public void updateProfile(User user) throws Exception {
        if (userDao.loginExists(user.getLogin(), user.getId())) {
            throw new DuplicateUserException();
        }

        if (userDao.emailExists(user.getEmail(), user.getId())) {
            throw new DuplicateEmailException();
        }
        userDao.updateProfile(user);
    }

    @Override
    public User getUser(long userId) throws UserNotFoundException {
        return userDao.getUser(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void changePassword(String newPassword, long userId) {
        String encryptedPassword = passwordEncoder.encode(newPassword);
        userDao.changePassword(encryptedPassword, userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#user.login")})
    @Override
    public void updateIcon(User user) {
        userDao.updateIcon(user);
    }

    @Override
    @Cacheable(value = "userByName", key = "#username")
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userDao.getUser(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    /**
     * Setter for property 'userDao'.
     *
     * @param userDao Value to set for property 'userDao'.
     */
    @Autowired
    public void setUserDao(@Qualifier("userDao") UserDao userDao) {
        this.userDao = userDao;
        setBaseDao(userDao);
    }
}
