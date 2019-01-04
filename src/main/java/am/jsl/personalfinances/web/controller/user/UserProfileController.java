package am.jsl.personalfinances.web.controller.user;

import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.dto.user.PasswordChangeDTO;
import am.jsl.personalfinances.dto.user.UserDTO;
import am.jsl.personalfinances.ex.DuplicateEmailException;
import am.jsl.personalfinances.ex.DuplicateUserException;
import am.jsl.personalfinances.ex.UserNotFoundException;
import am.jsl.personalfinances.util.*;
import am.jsl.personalfinances.web.controller.BaseController;
import am.jsl.personalfinances.web.form.validator.UserValidator;
import am.jsl.personalfinances.web.util.I18n;
import am.jsl.personalfinances.web.util.WebUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static am.jsl.personalfinances.web.util.WebUtils.USER;

/**
 * The UserProfileController defines methods for managing user profile:
 * viewing / editing profile data, changing password, attaching / detaching profile images.
 *
 * @author hamlet
 */
@Controller
@RequestMapping(value = "/profile")
public class UserProfileController extends BaseController {

    /**
     * The user profile templates
     */
    public static final String FORWARD_PROFILE_VIEW = "profile/profile-view";
    public static final String REDIRECT_PROFILE_VIEW = "redirect:view";
    public static final String FORWARD_PASSWORD_CHANGE = "profile/password-change";
    public static final String REDIRECT_PASSWORD_CHANGE = "redirect:profile/passwordchange?id=";
    public static final String PASSWORD_CHANGE_DTO = "passwordChangeDTO";

    /**
     * The directory where user images are uploaded.
     */
    @Value("${personalfinances.user.img.dir}")
    private String userImgDir;

    /**
     * The password encoder
     */
    @Autowired
    private transient PasswordEncoder passwordEncoder;

    /**
     * The user validator
     */
    @Autowired
    private transient UserValidator userFormValidator;

    /**
     * Registers the user validator.
     *
     * @param binder the WebDataBinder
     */
    @InitBinder("userDTO")
    public void initUserBinder(WebDataBinder binder) {
        binder.addValidators(userFormValidator);
    }

    /**
     * Called when user opens user profile page.
     *
     * @param model the Model
     * @return the user profile page
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(Model model) {
        try {
            if (!model.containsAttribute(PASSWORD_CHANGE_DTO)) {
                User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = userService.getUser(userDetails.getId());
                model.addAttribute(USER, UserDTO.from(user));
            }
            return FORWARD_PROFILE_VIEW;
        } catch (UserNotFoundException e) {
            log.error(e.getMessage(), e);
            return WebUtils.PAGE_HOME;
        }
    }

    /**
     * Called when user clicked on password change link from profile page.
     *
     * @param model the Model
     * @return the password change page
     */
    @RequestMapping(value = "/passwordchange", method = RequestMethod.GET)
    public String passwordChangePage(Model model) {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PasswordChangeDTO passwordChangeDTO = new PasswordChangeDTO();
        passwordChangeDTO.setId(userDetails.getId());
        model.addAttribute(PASSWORD_CHANGE_DTO, passwordChangeDTO);
        return FORWARD_PASSWORD_CHANGE;
    }

    /**
     * Called when users clicks on change button from password change page.
     *
     * @param request           the HttpServletRequest
     * @param passwordChangeDTO the PasswordChangeDTO
     * @param redirectAttrs     the RedirectAttributes
     * @return the user profile page
     */
    @RequestMapping(value = "/passwordchange", method = RequestMethod.POST)
    public String passwordChange(HttpServletRequest request,
                                 @ModelAttribute PasswordChangeDTO passwordChangeDTO,
                                 RedirectAttributes redirectAttrs) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean error = false;
        String message = null;
        String oldPassword = passwordChangeDTO.getOldPassword();
        String newPassword = passwordChangeDTO.getNewPassword();
        String rePassword = passwordChangeDTO.getRePassword();

        if (!TextUtils.hasText(oldPassword)
                || !TextUtils.hasText(newPassword)
                || !TextUtils.hasText(rePassword)) {
            message = i18n.msg(request, "error.enter.required.fields");
            error = true;
        } else {
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                message = i18n.msg(request, "user.old_password_incorrect");
                error = true;
            } else if (!rePassword.equals(newPassword)) {
                message = i18n.msg(request, "user.passwords_does_not_match");
                error = true;
            }
        }

        if (!error) {
            userService.changePassword(newPassword, user.getId());
            message = i18n.msg(request, "user.password.change_success.msg");
            redirectAttrs.addFlashAttribute(I18n.MESSAGE, message);

            return REDIRECT_PROFILE_VIEW;
        } else {
            redirectAttrs.addFlashAttribute(I18n.ERROR, message);
            return REDIRECT_PASSWORD_CHANGE;
        }
    }

    /**
     * Called from user profile page for attaching an image to the user profile.
     *
     * @param request       the HttpServletRequest
     * @param uploadedFile  the MultipartFile
     * @param redirectAttrs the RedirectAttributes
     * @return the user profile page
     * @throws IOException if could not upload image
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadImage(HttpServletRequest request, @RequestParam("file") MultipartFile uploadedFile,
                              RedirectAttributes redirectAttrs) throws IOException {

        if (uploadedFile.isEmpty()) {
            return REDIRECT_PROFILE_VIEW;
        }

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId = user.getId();
            String fileName = uploadedFile.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);

            if (!ImageFileFilter.isValidImageExtension(extension)) {
                String message = i18n.msg(request, "error.select.valid.image");
                redirectAttrs.addFlashAttribute(I18n.ERROR, message);
                return REDIRECT_PROFILE_VIEW;
            }

            String uploadPath = userImgDir + userId;
            File imageUploadDir = new File(uploadPath);

            if (!imageUploadDir.isDirectory()) {
                imageUploadDir.mkdir();
            }

            // scale image if needs
            BufferedImage image = ImageUtils.toBufferedImage(uploadedFile.getBytes());
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            if (imgWidth > Constants.PROFILE_IMG_WIDTH
                    || imgHeight > Constants.PROFILE_IMG_HEIGHT) {
                image = ImageUtils.resizeImage(image,
                        Constants.PROFILE_IMG_WIDTH,
                        Constants.PROFILE_IMG_HEIGHT, true);
            }

            User dbUser = userService.getUser(user.getId());
            String icon = dbUser.getIcon();
            File imageFile = null;

            // remove old icon
            if (!TextUtils.isEmpty(icon)) {
                imageFile = new File(imageUploadDir, icon);
                imageFile.delete();
            }

            icon = GenerateShortUUID.next() + "." + extension;

            imageFile = new File(imageUploadDir, icon);
            ImageIO.write(image, extension, imageFile);

            user.setIcon(icon);
            userService.updateIcon(user);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        return REDIRECT_PROFILE_VIEW;
    }

    /**
     * Called when user clicks on remove image link.
     *
     * @return the user profile pae
     */
    @RequestMapping(value = "/removeImage", method = RequestMethod.POST)
    public String removeImage() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User dbUser = userService.getUser(user.getId());
            String icon = dbUser.getIcon();

            if (TextUtils.isEmpty(icon)) {
                return REDIRECT_PROFILE_VIEW;
            }

            // remove icon
            String uploadPath = userImgDir + user.getId();
            File imageUploadDir = new File(uploadPath);

            if (imageUploadDir.isDirectory()) {
                File imageFile = new File(imageUploadDir, icon);
                imageFile.delete();
            }

            user.setIcon(null);
            userService.updateIcon(user);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        return REDIRECT_PROFILE_VIEW;
    }

    /**
     * Called when user clicks on save button from user profile page.
     *
     * @param request       the HttpServletRequest
     * @param userDTO       the UserDTO
     * @param result        the BindingResult
     * @param redirectAttrs the RedirectAttributes
     * @return the user profile page
     * @throws Exception if exception occurs
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, @Valid @ModelAttribute UserDTO userDTO,
                       BindingResult result, RedirectAttributes redirectAttrs) throws Exception {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttrs.addFlashAttribute(USER, userDTO);
            return REDIRECT_PROFILE_VIEW;
        }

        try {
            User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDTO.toUser();
            user.setId(userDetails.getId());
            user.setChangedAt(LocalDateTime.now());
            user.setChangedBy(userDetails.getId());
            userService.updateProfile(user);

            String message = i18n.msg(request, I18n.KEY_MESSAGE_SUCCESS_UPDATE,
                    new Object[]{USER, userDTO.getLogin()});
            redirectAttrs.addFlashAttribute(I18n.MESSAGE, message);

        } catch (DuplicateUserException unf) {
            String message = i18n.msg(request, I18n.KEY_ERROR_DUPLICATE,
                    new Object[]{USER, userDTO.getLogin()});
            redirectAttrs.addFlashAttribute(I18n.ERROR, message);
        } catch (DuplicateEmailException ex) {
            String message = i18n.msg(request, I18n.KEY_ERROR_DUPLICATE,
                    new Object[]{USER, userDTO.getEmail()});
            redirectAttrs.addFlashAttribute(I18n.ERROR, message);
        }

        return REDIRECT_PROFILE_VIEW;
    }
}
