package am.jsl.personalfinances.service.account;

import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.domain.account.AccountType;
import am.jsl.personalfinances.dto.account.AccountListDTO;
import am.jsl.personalfinances.dto.account.AdjustBalanceDTO;
import am.jsl.personalfinances.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains AccountService tests.
 */
public class AccountServiceTest extends BaseTest {

    /**
     * Executed before all AccountServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
    }

    @Test
    @DisplayName("Create Account Test")
    public void testCreateAccount() throws Exception {
        log.info("Starting test for create account");
        Account account = new Account();
        account.setName("test account");
        account.setAccountType(AccountType.CASH.getValue());
        account.setCurrency(CURRENCY_USD);
        account.setActive(true);
        account.setSortOrder(0);

        account.setCreatedAt(LocalDateTime.now());
        account.setUserId(user.getId());
        accountService.create(account);

        assertTrue(account.getId() > 0);
        log.info("Finished test for create account");
    }

    @Test
    @DisplayName("Update Account Test")
    public void testUpdateAccount() throws Exception {
        log.info("Starting test for update account");
        String accountName = "name updated";
        AccountType accountType = AccountType.CREDIT_CARD;
        double balance = 1000;
        String currency = "AMD";
        int sortOrder = 10;
        boolean active = false;
        String icon = "icon updated";
        String color = "#1111";
        String description = "description updated";

        Account account = createAccount();

        // update account
        account.setName(accountName);
        account.setAccountType(accountType.getValue());
        account.setBalance(balance);
        account.setCurrency(currency);
        account.setSortOrder(sortOrder);
        account.setActive(active);

        account.setIcon(icon);
        account.setColor(color);
        account.setDescription(description);

        accountService.update(account);

        // validate account
        account = accountService.get(account.getId(), account.getUserId());

        assertEquals(accountName, account.getName());
        assertEquals(accountType.getValue(), account.getAccountType());
        assertEquals(balance, account.getBalance());
        assertEquals(currency, account.getCurrency());
        assertEquals(sortOrder, account.getSortOrder());
        assertEquals(active, account.isActive());
        assertEquals(icon, account.getIcon());
        assertEquals(color, account.getColor());
        assertEquals(description, account.getDescription());

        log.info("Finished test for update account");
    }

    @Test
    @DisplayName("Delete Account Test")
    public void testDeleteAccount() throws Exception {
        log.info("Starting test for delete account");

        Account account = createAccount();
        long accountId = account.getId();
        long userId = account.getUserId();

        accountService.delete(account.getId(), account.getUserId());

        // validate account
        account = accountService.get(accountId, userId);
        assertNull(account);

        log.info("Finished test for delete account");
    }

    @Test
    @DisplayName("Account updateBalance Test")
    public void testAdjustBalance() throws Exception {
        log.info("Starting test for account updateBalance");

        Account account = createAccount();

        long accountId = account.getId();
        double balance = 1000;
        long userId = account.getUserId();

        AdjustBalanceDTO adjustBalanceDTO = new AdjustBalanceDTO();
        adjustBalanceDTO.setId(accountId);
        adjustBalanceDTO.setBalance(balance);
        adjustBalanceDTO.setUserId(userId);

        accountService.updateBalance(adjustBalanceDTO);

        // validate account
        account = accountService.get(account.getId(), account.getUserId());

        assertEquals(balance, account.getBalance());

        log.info("Finished test for account updateBalance");
    }

    @Test
    @DisplayName("Get Accounts Test")
    public void testGetAccounts() throws Exception {
        log.info("Starting test for getAccounts");
        Account account = new Account();
        account.setName("test account");
        account.setAccountType(AccountType.CASH.getValue());
        account.setCurrency(CURRENCY_USD);
        account.setActive(true);
        account.setSortOrder(0);

        account.setCreatedAt(LocalDateTime.now());
        account.setUserId(user.getId());
        accountService.create(account);

        List<AccountListDTO> accounts = accountService.getAccounts(user.getId());

        assertEquals(1, accounts.size());
        log.info("Finished test for getAccounts");
    }

    @Test
    @DisplayName("Get Active Accounts Test")
    public void testGetActiveAccounts() throws Exception {
        log.info("Starting test for getActiveAccounts");
        Account account = createAccount();
        account.setActive(false);
        accountService.update(account);

        List<AccountListDTO> accounts = accountService.getActiveAccounts(user.getId());

        assertEquals(0, accounts.size());
        log.info("Finished test for getActiveAccounts");
    }

    @Test
    @DisplayName("Lookup Accounts Test")
    public void testLookupAccounts() throws Exception {
        log.info("Starting test for lookup accounts");
        Account account = new Account();
        account.setName("test account");
        account.setAccountType(AccountType.CASH.getValue());
        account.setCurrency(CURRENCY_USD);
        account.setActive(true);
        account.setSortOrder(0);

        account.setCreatedAt(LocalDateTime.now());
        account.setUserId(user.getId());
        accountService.create(account);

        List<Account> accounts = accountService.lookup(user.getId());

        assertEquals(1, accounts.size());
        log.info("Finished test for lookup accounts");
    }

    /**
     * Executed after all AccountServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
