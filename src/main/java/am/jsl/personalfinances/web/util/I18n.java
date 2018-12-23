package am.jsl.personalfinances.web.util;

import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * The I18n wraps Springs {@link MessageSource} and used in java classes.
 * Contains additional methods and message key constants.
 *
 * @author hamlet
 */
public class I18n {
	public static final String MESSAGE = "message";
	public static final String ERROR = "error";

    public static final String KEY_ERROR_NOT_FOUND = "error.not.found";
	public static final String KEY_ERROR_ENTER_REQUIRED_FIELDS = "error.enter.required.fields";
	public static final String KEY_ERROR_DUPLICATE = "error.duplicate";
	public static final String KEY_ERROR_DUPLICATE_EMAIL = "error.duplicate.email";
	public static final String KEY_ERROR_DUPLICATE_LOGIN = "error.duplicate.login";

	public static final String KEY_MESSAGE_SUCCESS_ADD = "message.success.add";
	public static final String KEY_MESSAGE_SUCCESS_UPDATE = "message.success.update";
	public static final String KEY_MESSAGE_SUCCESS_DELETE = "message.success.delete";
	public static final String KEY_ERROR_ROLE_DELETE = "role.delete.error";
	public static final String KEY_ERROR_CATEGORY_DELETE = "category.delete.error";
	public static final String KEY_ERROR_ACCOUNT_DELETE = "account.delete.error";
	public static final String KEY_ERROR_CONTACT_DELETE = "contact.delete.error";
	public static final String KEY_ERROR_INVALID_EMAIL = "error.enter.valid.email";
	public static final String KEY_MSG_RESET_PASSWORD_MAIL_SENT = "user.reset.password.mail.sent";
	public static final String KEY_ERROR_PASSWORDS_DONT_MATCH = "user.password.doesnt.match.confirm.password";

	private MessageSource messageSource;

	public String msg(String code) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = sra.getRequest();
		Locale locale = RequestContextUtils.getLocale(req);
		return messageSource.getMessage(code, new Object[]{}, locale);
	}

	public String msg(HttpServletRequest req, String code) {
		Locale locale = RequestContextUtils.getLocale(req);
		return msg(locale, code);
	}

	public String msg(Locale locale, String code) {
		return messageSource.getMessage(code, new Object[]{}, locale);
	}

	public String msg(String code, Object[] args) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = sra.getRequest();
		Locale locale = RequestContextUtils.getLocale(req);
		return messageSource.getMessage(code, args, locale);
	}

	public String msg(HttpServletRequest req, String code, Object[] args) {
		Locale locale = RequestContextUtils.getLocale(req);
		return msg(locale, code, args);
	}

	public String msg(Locale locale, String code, Object[] args) {
		return messageSource.getMessage(code, args, locale);
	}

	public void addNotFoundError(HttpServletRequest req, Model model, Object[] args) {
		Locale locale = RequestContextUtils.getLocale(req);
		String message = msg(locale, KEY_ERROR_NOT_FOUND, args);
		model.addAttribute(ERROR, message);
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
