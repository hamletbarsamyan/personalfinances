package am.jsl.personalfinances.service;

import am.jsl.personalfinances.domain.Category;
import am.jsl.personalfinances.domain.Contact;
import am.jsl.personalfinances.domain.Currency;
import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.domain.account.AccountType;
import am.jsl.personalfinances.domain.user.Role;
import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.log.AppLogger;
import am.jsl.personalfinances.service.account.AccountService;
import am.jsl.personalfinances.service.category.CategoryService;
import am.jsl.personalfinances.service.contact.ContactService;
import am.jsl.personalfinances.service.currency.CurrencyService;
import am.jsl.personalfinances.service.transaction.TransactionService;
import am.jsl.personalfinances.service.user.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Base test with common fields and methods.
 * 
 * @author hamlet
 */
@SpringBootTest(classes = { TestApplicationContext.class })
@ActiveProfiles("test")
@Rollback
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
	protected final AppLogger log = new AppLogger(this.getClass());

	protected final long DEFAULT_USER_ID = 1L;
	protected final String CURRENCY_USD = "USD";
	protected final String CURRENCY_EUR = "EUR";

	protected User user;
	protected Account account;
	protected Category category;
	protected Contact contact;
	protected Currency currency;

	@Autowired
	protected UserService userService;

	@Autowired
	protected AccountService accountService;

	@Autowired
	protected CategoryService categoryService;
	
	@Autowired
	protected ContactService contactService;

	@Autowired
	protected CurrencyService currencyService;

	@Autowired
	protected TransactionService transactionService;

	/**
	 * Creates a new user.
	 * 
	 * @return the User.
	 */
	protected User createUser() throws Exception {
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

		return user;
	}

	/**
	 * Creates a new account.
	 * @return the Account
	 */
	protected Account createAccount() throws Exception {
		Account account = new Account();
		account.setName("test account");
		account.setAccountType(AccountType.CASH.getValue());
		account.setBalance(100);
		account.setCurrency(CURRENCY_USD);
		account.setSortOrder(0);
		account.setActive(true);

		account.setIcon("icon");
		account.setColor("color");
		account.setDescription("description");

		account.setCreatedAt(LocalDateTime.now());
		account.setUserId(user.getId());
		accountService.create(account);

		return account;
	}

	/**
	 * Creates a new Category.
	 * @return the Category
	 */
	protected Category createCategory() throws Exception {
		Category category = new Category();
		category.setName("test category");
		category.setIcon("icon");
		category.setColor("color");
		category.setDescription("description");
		category.setParentId(0);

		category.setCreatedAt(LocalDateTime.now());
		category.setUserId(user.getId());
		categoryService.create(category);

		return category;
	}

	/**
	 * Creates a new Contact.
	 * @return the Contact
	 */
	protected Contact createContact() throws Exception {
		Contact contact = new Contact();
		contact.setName("test contact");
		contact.setEmail("email");
		contact.setPhone("phone");
		contact.setDescription("description");

		contact.setCreatedAt(LocalDateTime.now());
		contact.setUserId(user.getId());
		contactService.create(contact);

		return contact;
	}

	/**
	 * Creates a new Currency.
	 * @return the Currency
	 */
	protected Currency createCurrency() throws Exception {
		Currency currency = new Currency();
		currency.setCode("TST");
		currency.setName("Test Currency");
		currency.setSymbol("t");
		currencyService.create(currency);

		return currency;
	}

	/**
	 * Deletes all temporary objects.
	 * @throws Exception if failed
	 */
	public void cleanUp() throws Exception {
		if (account != null) {
			accountService.delete(account.getId(), account.getUserId());
		}

		if (category != null) {
			categoryService.delete(category.getId(), category.getUserId());
		}

		if (contact != null) {
			contactService.delete(contact.getId(), contact.getUserId());
		}

		if (currency != null) {
			currencyService.delete(currency.getCode());
		}

		if (user != null) {
			userService.deleteUser(user);
		}
	}
}
