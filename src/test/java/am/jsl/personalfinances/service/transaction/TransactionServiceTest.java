package am.jsl.personalfinances.service.transaction;

import am.jsl.personalfinances.domain.Category;
import am.jsl.personalfinances.domain.Contact;
import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.domain.transaction.Transaction;
import am.jsl.personalfinances.domain.transaction.TransactionSource;
import am.jsl.personalfinances.domain.transaction.TransactionType;
import am.jsl.personalfinances.domain.transaction.Transfer;
import am.jsl.personalfinances.dto.transaction.AddTransactionsDTO;
import am.jsl.personalfinances.dto.transaction.TransactionByCategoryResultDTO;
import am.jsl.personalfinances.dto.transaction.TransactionListDTO;
import am.jsl.personalfinances.dto.transaction.TransactionSearchResult;
import am.jsl.personalfinances.search.ListPaginatedResult;
import am.jsl.personalfinances.search.transaction.TransactionByCategorySearchQuery;
import am.jsl.personalfinances.search.transaction.TransactionSearchQuery;
import am.jsl.personalfinances.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains TransactionService tests.
 */
public class TransactionServiceTest extends BaseTest {

    /**
     * Executed before all TransactionServiceTest tests.
     *
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
        category = createCategory();
        contact = createContact();
    }

    @Test
    @DisplayName("Create Income Transaction Test")
    public void testCreateIncomeTransaction() throws Exception {
        log.info("Starting test for create income transaction");
        Account account = createAccount();

        double income = 10;
        double accountInitialBalance = account.getBalance();

        // create income transaction
        Transaction transaction = createTransaction(account, null, TransactionType.INCOME, income);

        assertTrue(transaction.getId() > 0);

        // check if account balance is increased
        account = accountService.get(account.getId(), account.getUserId());
        assertEquals(
                accountInitialBalance + income, account.getBalance(),  "Account balance is not increased");

        log.info("Finished test for create income transaction");
    }

    @Test
    @DisplayName("Create Expense Transaction Test")
    public void testCreateExpenseTransaction() throws Exception {
        log.info("Starting test for create expense transaction");
        double expense = 10;

        Account account = createAccount();
        double accountInitialBalance = account.getBalance();

        // create expense transaction
        Transaction transaction = createTransaction(account, null, TransactionType.EXPENSE, expense);

        assertTrue(transaction.getId() > 0);

        // check if account balance is decreased
        account = accountService.get(account.getId(), account.getUserId());
        assertEquals(
                accountInitialBalance - expense, account.getBalance(), "Account balance is not decreased");

        log.info("Finished test for create expense transaction");
    }

    @Test
    @DisplayName("Create Transfer Transaction Test")
    public void testCreateTransferTransaction() throws Exception {
        log.info("Starting test for create transfer transaction");

        Account sourceAccount = createAccount();
        Account targetAccount = createAccount();
        double transferAmount = 10;
        double sourceAccInitBalance = sourceAccount.getBalance();
        double targetAccInitBalance = targetAccount.getBalance();

        // create transfer transaction
        Transaction transaction = createTransaction(sourceAccount, targetAccount,
                TransactionType.TRANSFER, transferAmount);

        // validate transaction and accounts
        assertTrue(transaction.getId() > 0);

        Transfer transfer = transaction.getTransfer();

        assertNotNull(transfer, "Transfer was not created");

        // check if source account balance is decreased
        sourceAccount = accountService.get(sourceAccount.getId(), sourceAccount.getUserId());
        assertEquals(
                sourceAccInitBalance - transferAmount, sourceAccount.getBalance(), "Source account balance is not decreased");

        // check if target account balance is increased
        targetAccount = accountService.get(targetAccount.getId(), targetAccount.getUserId());
        assertEquals(
                targetAccInitBalance + transfer.getConvertedAmount(), targetAccount.getBalance(), "Target account balance is not increased");

        log.info("Finished test for create transfer transaction");
    }

    @Test
    @DisplayName("Create batch Transactions Test")
    public void testCreateBatchTransactions() throws Exception {
        log.info("Starting test for create batch transactions");
        Account account = createAccount();

        double income = 10;
        double accountInitialBalance = account.getBalance();

        AddTransactionsDTO addTransactionsDTO = new AddTransactionsDTO();
        addTransactionsDTO.setAccountId(account.getId());
        addTransactionsDTO.setTransactionType(TransactionType.INCOME.getValue());
        addTransactionsDTO.setContactId(contact.getId());
        addTransactionsDTO.setUserId(user.getId());

        // create income transaction
        Transaction transaction = new Transaction();
        transaction.setCategoryId(category.getId());
        transaction.setAmount(income);
        addTransactionsDTO.addTransaction(transaction);

        transactionService.createBatch(addTransactionsDTO);

        // check if account balance is increased
        account = accountService.get(account.getId(), account.getUserId());
        assertEquals(
                accountInitialBalance + income, account.getBalance(), "Account balance is not increased");

        log.info("Finished test for create batch transactions");
    }

    @Test
    @DisplayName("Update Income Transaction Test")
    public void testUpdateIncomeTransaction() throws Exception {
        log.info("Starting test for update income transaction");
        Account account = createAccount();
        Account newAccount = createAccount();

        double income = 10;
        double accountInitialBalance = account.getBalance();
        double newAccountInitialBalance = newAccount.getBalance();

        // create income transaction
        Transaction transaction = createTransaction(account, null, TransactionType.INCOME, income);

        assertTrue(transaction.getId() > 0);

        // check if account balance is increased
        account = accountService.get(account.getId(), account.getUserId());
        assertEquals(
                accountInitialBalance + income, account.getBalance(), "Account balance is not increased");

        // update transaction
        Category newCategory = createCategory();
        Contact newContact = createContact();
        double newAmount = 15;

        transaction.setAccountId(newAccount.getId());
        transaction.setCategoryId(newCategory.getId());
        transaction.setAmount(newAmount);
        transaction.setContactId(newContact.getId());
        transactionService.update(transaction);

        // validate transaction
        transaction = transactionService.get(transaction.getId(), transaction.getUserId());

        assertEquals(newAccount.getId(), transaction.getAccountId());
        assertEquals(newCategory.getId(), transaction.getCategoryId());
        assertEquals(newAmount, transaction.getAmount());
        assertEquals(newContact.getId(), transaction.getContactId());

        // check if account balance is rolled back, because account was changed
        account = accountService.get(account.getId(), account.getUserId());
        assertEquals(
                accountInitialBalance, account.getBalance(), "Account balance is not decreased");

        // check if new account balance is increased
        newAccount = accountService.get(newAccount.getId(), account.getUserId());
        assertEquals(
                newAccountInitialBalance + newAmount, newAccount.getBalance(), "Account balance is not increased");

        log.info("Finished test for update income transaction");
    }

    @Test
    @DisplayName("Update Expense Transaction Test")
    public void testUpdateExpenseTransaction() throws Exception {
        log.info("Starting test for update expense transaction");
        Account account = createAccount();
        Account newAccount = createAccount();

        double expense = 10;
        double accountInitialBalance = account.getBalance();
        double newAccountInitialBalance = newAccount.getBalance();

        // create expense transaction
        Transaction transaction = createTransaction(account, null, TransactionType.EXPENSE, expense);

        assertTrue(transaction.getId() > 0);

        // check if account balance is decreased
        account = accountService.get(account.getId(), account.getUserId());
        assertEquals(
                accountInitialBalance - expense, account.getBalance(), "Account balance is not decreased");

        // update transaction
        Category newCategory = createCategory();
        Contact newContact = createContact();
        double newAmount = 15;

        transaction.setAccountId(newAccount.getId());
        transaction.setCategoryId(newCategory.getId());
        transaction.setAmount(newAmount);
        transaction.setContactId(newContact.getId());
        transactionService.update(transaction);

        // validate transaction
        transaction = transactionService.get(transaction.getId(), transaction.getUserId());

        assertEquals(newAccount.getId(), transaction.getAccountId());
        assertEquals(newCategory.getId(), transaction.getCategoryId());
        assertEquals(newAmount, transaction.getAmount());
        assertEquals(newContact.getId(), transaction.getContactId());

        // check if account balance is rolled back, because account was changed
        account = accountService.get(account.getId(), account.getUserId());
        assertEquals(
                accountInitialBalance, account.getBalance(), "Account balance is not increased");

        // check if new account balance is decreased
        newAccount = accountService.get(newAccount.getId(), account.getUserId());
        assertEquals(
                newAccountInitialBalance - newAmount, newAccount.getBalance(), "Account balance is not decreased");

        log.info("Finished test for update expense transaction");
    }

    @Test
    @DisplayName("Update Transfer Transaction Test")
    public void testUpdateTransferTransaction() throws Exception {
        log.info("Starting test for update transfer transaction");

        Account sourceAccount = createAccount();
        Account newAccount = createAccount();
        Account targetAccount = createAccount();
        Account newTargetAccount = createAccount();

        double transferAmount = 10;
        double sourceAccInitBalance = sourceAccount.getBalance();
        double newAccInitialBalance = newAccount.getBalance();
        double targetAccInitBalance = targetAccount.getBalance();
        double newTargetAccInitBalance = newTargetAccount.getBalance();

        // create transfer transaction
        Transaction transaction = createTransaction(sourceAccount, targetAccount,
                TransactionType.TRANSFER, transferAmount);

        // validate transaction and accounts
        assertTrue(transaction.getId() > 0);

        Transfer transfer = transaction.getTransfer();

        assertNotNull(transfer, "Transfer was not created");

        // check if source account balance is decreased
        sourceAccount = accountService.get(sourceAccount.getId(), sourceAccount.getUserId());
        assertEquals(
                sourceAccInitBalance - transferAmount, sourceAccount.getBalance(), "Source account balance is not decreased");

        // check if target account balance is increased
        targetAccount = accountService.get(targetAccount.getId(), targetAccount.getUserId());
        assertEquals(
                targetAccInitBalance + transfer.getConvertedAmount(), targetAccount.getBalance(), "Target account balance is not increased");


        // update transaction, change source and target accounts, category, amount contact
        transaction = transactionService.get(transaction.getId(), transaction.getUserId());
        Category newCategory = createCategory();
        Contact newContact = createContact();
        double newTransferAmount = 15;

        transaction.setAccountId(newAccount.getId());
        transaction.setCategoryId(newCategory.getId());
        transaction.setAmount(newTransferAmount);
        transaction.setContactId(newContact.getId());

        double rate = currencyService.getRate(newAccount.getCurrency(), newTargetAccount.getCurrency());
        double convertedAmount = newTransferAmount * rate;

        transfer = transaction.getTransfer();
        transfer.setTargetAccountId(newTargetAccount.getId());
        transfer.setConvertedAmount(convertedAmount);
        transfer.setRate(rate);
        transactionService.update(transaction);

        // validate transaction
        transaction = transactionService.get(transaction.getId(), transaction.getUserId());

        assertEquals(newAccount.getId(), transaction.getAccountId());
        assertEquals(newCategory.getId(), transaction.getCategoryId());
        assertEquals(newTransferAmount, transaction.getAmount());
        assertEquals(newContact.getId(), transaction.getContactId());

        // check if old account balance is rolled back, because account was changed
        sourceAccount = accountService.get(sourceAccount.getId(), sourceAccount.getUserId());
        assertEquals(
                sourceAccInitBalance, sourceAccount.getBalance(), "Old Account balance is not increased");

        // check if new account balance is decreased
        newAccount = accountService.get(newAccount.getId(), newAccount.getUserId());
        assertEquals(
                newAccInitialBalance - newTransferAmount, newAccount.getBalance(), "New Account balance is not decreased");

        // check if old target account balance is rolled back, because target account was changed
        targetAccount = accountService.get(targetAccount.getId(), targetAccount.getUserId());
        assertEquals(
                targetAccInitBalance, targetAccount.getBalance(), "Old Target Account balance is not increased");

        // check if new target account balance is decreased
        newTargetAccount = accountService.get(newTargetAccount.getId(), newTargetAccount.getUserId());
        assertEquals(
                newTargetAccInitBalance + newTransferAmount, newTargetAccount.getBalance(), "New Target Account balance is not increased");

        log.info("Finished test for update transfer transaction");
    }

    @Test
    @DisplayName("Delete Expense Transaction Test")
    public void testDeleteExpenseTransaction() throws Exception {
        log.info("Starting test for delete expense transaction");
        Account account = createAccount();
        double accountInitialBalance = account.getBalance();

        // create expense transaction
        Transaction transaction = createTransaction(account, null, TransactionType.EXPENSE, 10);

        long transactionId = transaction.getId();
        long userId = transaction.getUserId();

        transactionService.delete(transaction.getId(), transaction.getUserId());

        // validate transaction
        transaction = transactionService.get(transactionId, userId);
        assertNull(transaction);

        // check if account balance is rolled back
        account = accountService.get(account.getId(), account.getUserId());
        assertEquals(
                accountInitialBalance, account.getBalance(), "Account balance is not rolled back");

        log.info("Finished test for delete expense transaction");
    }

    @Test
    @DisplayName("Delete Income Transaction Test")
    public void testDeleteIncomeTransaction() throws Exception {
        log.info("Starting test for delete income transaction");
        Account account = createAccount();
        double accountInitialBalance = account.getBalance();

        // create income transaction
        Transaction transaction = createTransaction(account, null, TransactionType.INCOME, 10);

        long transactionId = transaction.getId();
        long userId = transaction.getUserId();

        transactionService.delete(transaction.getId(), transaction.getUserId());

        // validate transaction
        transaction = transactionService.get(transactionId, userId);
        assertNull(transaction);

        // check if account balance is rolled back
        account = accountService.get(account.getId(), account.getUserId());
        assertEquals(
                accountInitialBalance, account.getBalance(), "Account balance is not rolled back");

        log.info("Finished test for delete income transaction");
    }

    @Test
    @DisplayName("Delete Transfer Transaction Test")
    public void testDeleteTransferTransaction() throws Exception {
        log.info("Starting test for delete transfer transaction");

        Account sourceAccount = createAccount();
        Account targetAccount = createAccount();
        double transferAmount = 10;
        double sourceAccInitBalance = sourceAccount.getBalance();
        double targetAccInitBalance = targetAccount.getBalance();

        // create transfer transaction
        Transaction transaction = createTransaction(sourceAccount, targetAccount, TransactionType.TRANSFER, transferAmount);


        // validate transaction and accounts
        assertTrue(transaction.getId() > 0);

        Transfer transfer = transaction.getTransfer();

        assertNotNull(transfer, "Transfer was not created");

        // check if source account balance is decreased
        sourceAccount = accountService.get(sourceAccount.getId(), sourceAccount.getUserId());
        assertEquals(
                sourceAccInitBalance - transferAmount, sourceAccount.getBalance(), "Source account balance is not decreased");

        // check if target account balance is increased
        targetAccount = accountService.get(targetAccount.getId(), targetAccount.getUserId());
        assertEquals(
                targetAccInitBalance + transfer.getConvertedAmount(), targetAccount.getBalance(), "Target account balance is not increased");

        transactionService.delete(transaction.getId(), transaction.getUserId());

        // validate transaction
        // check if the balance of source account is rolled back
        sourceAccount = accountService.get(sourceAccount.getId(), sourceAccount.getUserId());
        assertEquals(
                sourceAccInitBalance, sourceAccount.getBalance(), "Source Account balance is not rolled back");

        // check if the balance of target account is rolled back
        targetAccount = accountService.get(targetAccount.getId(), targetAccount.getUserId());
        assertEquals(
                targetAccInitBalance, targetAccount.getBalance(), "Target Account balance is not rolled back");

        log.info("Finished test for delete transfer transaction");
    }

    @Test
    @DisplayName("Search Transactions Test")
    public void testSearchTransactions() throws Exception {
        log.info("Starting test for search transactions");
        Account account = createAccount();
        Account account1 = createAccount();

        // create transactions
        createTransaction(account, null, TransactionType.INCOME, 10);
        createTransaction(account1, null, TransactionType.EXPENSE, 10);

        TransactionSearchQuery query = new TransactionSearchQuery(1, 10);
        query.setUserId(user.getId());
        query.setTransactionType(TransactionType.INCOME.getValue());
        query.setCategoryId(category.getId());
        query.setContact(contact.getId());

        TransactionSearchResult result = transactionService.search(query);
        ListPaginatedResult<TransactionListDTO> paginatedResult = result.getListPaginatedResult();
        assertEquals(1, paginatedResult.getTotal(), "Incorrect transaction search result");

        query.setAccountId(account1.getId());
        query.setTransactionType(TransactionType.EXPENSE.getValue());

        result = transactionService.search(query);
        paginatedResult = result.getListPaginatedResult();
        assertEquals(1, paginatedResult.getTotal(), "Incorrect transaction search result");

        log.info("Finished test for search transactions");
    }

    @Test
    @DisplayName("Search Transactions By Category Test")
    public void testSearchTransactionsByCategory() throws Exception {
        log.info("Starting test for search transactions by category");
        Account account = createAccount();
        Account account1 = createAccount();

        // create transactions
        createTransaction(account, null, TransactionType.INCOME, 10);
        createTransaction(account1, null, TransactionType.EXPENSE, 10);

        TransactionByCategorySearchQuery query = new TransactionByCategorySearchQuery();
        query.setUserId(user.getId());
        query.setAccountId(account.getId());
        query.setTransactionType(TransactionType.INCOME.getValue());

        TransactionByCategoryResultDTO result = transactionService.search(query);
        assertEquals(1, result.getList().size(), "Incorrect transaction search result");

        query.setAccountId(account1.getId());
        query.setTransactionType(TransactionType.EXPENSE.getValue());

        result = transactionService.search(query);
        assertEquals(1, result.getList().size(), "Incorrect transaction search result");

        log.info("Finished test for search transactions by category");
    }

    /**
     * Creates a new Transaction with the given fields.
     *
     * @param account         the account
     * @param targetAccount   the target account is provider for transfer type
     * @param transactionType the transaction type
     * @param amount          the transaction amount
     * @return the Transaction
     * @throws Exception if error occurs
     */
    private Transaction createTransaction(Account account, Account targetAccount, TransactionType transactionType, double amount)
            throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setCategoryId(category.getId());
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType.getValue());
        transaction.setTransactionSource(TransactionSource.MANUAL.getValue());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setContactId(contact.getId());
        transaction.setDescription("description");
        transaction.setUserId(user.getId());

        // create transfer with exchanged amount
        if (transaction.isTransferType()) {
            double rate = currencyService.getRate(account.getCurrency(), targetAccount.getCurrency());
            double convertedAmount = amount * rate;
            Transfer transfer = new Transfer();
            transfer.setTargetAccountId(targetAccount.getId());
            transfer.setConvertedAmount(convertedAmount);
            transfer.setRate(rate);
            transaction.setTransfer(transfer);
        }

        transactionService.create(transaction);

        return transaction;
    }

    /**
     * Executed after all TransactionServiceTest tests.
     *
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
