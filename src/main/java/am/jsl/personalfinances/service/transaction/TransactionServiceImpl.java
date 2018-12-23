package am.jsl.personalfinances.service.transaction;

import am.jsl.personalfinances.dao.account.AccountDao;
import am.jsl.personalfinances.dao.transaction.TransactionDao;
import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.domain.transaction.Transaction;
import am.jsl.personalfinances.domain.transaction.TransactionStatus;
import am.jsl.personalfinances.domain.transaction.TransactionType;
import am.jsl.personalfinances.domain.transaction.Transfer;
import am.jsl.personalfinances.dto.transaction.*;
import am.jsl.personalfinances.ex.CannotDeleteException;
import am.jsl.personalfinances.search.transaction.TransactionByCategorySearchQuery;
import am.jsl.personalfinances.search.transaction.TransactionSearchQuery;
import am.jsl.personalfinances.service.BaseServiceImpl;
import am.jsl.personalfinances.util.DateUtils;
import am.jsl.personalfinances.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The service implementation of the {@link TransactionService}.
 * @author hamlet
 */
@Service("transactionService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class TransactionServiceImpl extends BaseServiceImpl<Transaction> implements TransactionService {

    /**
     * The cache keys of transactions.
     */
    private static final String CACHE_TRANSACTION_SEARCH = "transactionSearch";
    private static final String CACHE_KEY_SEARCH_BY_CATEGORY_QUERY = "ct";
    private static final String CACHE_KEY_SEARCH_QUERY = "s";
    private static final String CACHE_KEY_SEARCH_TOTALS = "t";

    /**
     * The TransactionDao
     */
    private TransactionDao transactionDao;

    /**
     * the AccountDao
     */
    private AccountDao accountDao;

    /**
     * The CacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    @Override
    public TransactionSearchResult search(TransactionSearchQuery searchQuery) {
        long userId = searchQuery.getUserId();
        Cache cache = cacheManager.getCache(CACHE_TRANSACTION_SEARCH);
        SimpleValueWrapper cachedSearchQuery = (SimpleValueWrapper) cache.get(CACHE_KEY_SEARCH_QUERY + userId);

        if (cachedSearchQuery == null || !cachedSearchQuery.get().equals(searchQuery)) {
            searchQuery.setCalculateTotals(true);
            cache.put(CACHE_KEY_SEARCH_QUERY + userId, searchQuery);
        } else {
            searchQuery.setCalculateTotals(false);
        }
        TransactionSearchResult result = transactionDao.search(searchQuery);

        if (searchQuery.isCalculateTotals()) {
            cache.put(CACHE_KEY_SEARCH_TOTALS + userId, result.getTotals());
        } else {
            result.setTotals((List<TransactionListTotalDTO>) cache.get(CACHE_KEY_SEARCH_TOTALS + userId).get());
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'s' + #transaction.userId"),
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'t' + #transaction.userId"),
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'ct' + #transaction.userId")}
    )
    @Override
    public void create(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            return;
        }
        transaction.setStatus(TransactionStatus.DONE.getValue());

        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDateTime.now());
        }

        transactionDao.create(transaction);

        // update account balance
        updateAccountBalance(transaction);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'s' + #transaction.userId"),
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'t' + #transaction.userId"),
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'ct' + #transaction.userId")})
    @Override
    public void update(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            return;
        }

        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDateTime.now());
        }

        // reset account balance
        resetAccountBalance(transaction.getId(), transaction.getUserId());

        transactionDao.update(transaction);

        // update account balance
        updateAccountBalance(transaction);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'s' + #userId"),
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'t' + #userId"),
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'ct' + #userId")})
    @Override
    public void delete(long id, long userId) throws CannotDeleteException {
        if (!transactionDao.canDelete(id, userId)) {
            throw new CannotDeleteException();
        }

        // reset account balance
        resetAccountBalance(id, userId);

        transactionDao.delete(id, userId);
    }

    /**
     * Updates account balance based on transaction details.
     * @param transaction the Transaction
     */
    private void updateAccountBalance(Transaction transaction) {
        double amount = transaction.getAmount();

        if (amount == 0) {
            return;
        }

        TransactionType type = TransactionType.get(transaction.getTransactionType());
        long accountId = transaction.getAccountId();
        long userId = transaction.getUserId();

        switch (type) {
            case EXPENSE:
                accountDao.decreaseBalance(accountId, userId, amount);
            break;
            case INCOME:
                accountDao.increaseBalance(accountId, userId, amount);
                break;
            case TRANSFER:
                accountDao.decreaseBalance(accountId, userId, amount);
                Transfer transfer = transaction.getTransfer();
                accountDao.increaseBalance(transfer.getTargetAccountId(), userId, transfer.getConvertedAmount());
                break;
        }
    }

    /**
     * Resets account balance based on transaction id.
     * @param id the transaction id
     * @param userId the user id
     */
    private void resetAccountBalance(long id, long userId) {
        Transaction transaction = get(id, userId);
        double amount = transaction.getAmount();

        TransactionType type = TransactionType.get(transaction.getTransactionType());
        long accountId = transaction.getAccountId();

        switch (type) {
            case EXPENSE:
                accountDao.increaseBalance(accountId, transaction.getUserId(), amount);
                break;
            case INCOME:
                accountDao.decreaseBalance(accountId, transaction.getUserId(), amount);
                break;
            case TRANSFER:
                accountDao.increaseBalance(accountId, transaction.getUserId(), amount);
                Transfer transfer = transaction.getTransfer();
                accountDao.decreaseBalance(transfer.getTargetAccountId(), userId, transfer.getConvertedAmount());
                break;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'s' + #addTransactionsDTO.userId"),
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'t' + #addTransactionsDTO.userId"),
            @CacheEvict(value = CACHE_TRANSACTION_SEARCH, key = "'ct' + #addTransactionsDTO.userId")})
    @Override
    public void createBatch(AddTransactionsDTO addTransactionsDTO) {
        LocalDateTime transactionDate = null;
        String transactionDateStr = addTransactionsDTO.getTransactionDate();
        transactionDate = DateUtils.parse(transactionDateStr);

        if (transactionDate == null) {
            transactionDate = LocalDateTime.now();
        }

        List<Transaction> transactions = addTransactionsDTO.getTransactions();
        List<Transaction> result = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() <= 0) {
                continue;
            }
            transaction.setAccountId(addTransactionsDTO.getAccountId());
            transaction.setTransactionType(addTransactionsDTO.getTransactionType());
            transaction.setUserId(addTransactionsDTO.getUserId());
            transaction.setTransactionDate(transactionDate);
            transaction.setContactId(addTransactionsDTO.getContactId());
            transaction.setDescription(addTransactionsDTO.getDescription());

            // update account balance
            updateAccountBalance(transaction);
            result.add(transaction);
        }

        transactionDao.createBatch(result);
    }

    @Override
    public TransactionDetailsDTO getDetails(long id, long userId) {
        TransactionDetailsDTO detailsDTO = transactionDao.getDetails(id, userId);

        if (detailsDTO.isTransfer()) {
            if (detailsDTO.getTargetAccountId() != 0) {
                Account account = accountDao.get(detailsDTO.getTargetAccountId(), userId);
                detailsDTO.setTargetAccount(account.getName());
                detailsDTO.setTargetAccountIcon(account.getIcon());
                detailsDTO.setTargetAccountColor(account.getColor());
                detailsDTO.setTargetAccountSymbol(account.getSymbol());
            }
        }
        return detailsDTO;
    }

    @Override
    public List<TransactionListDTO> getReminderTransactions(long reminderId, long userId) {
        return transactionDao.getReminderTransactions(reminderId, userId);
    }

    @Override
    public TransactionByCategoryResultDTO search(TransactionByCategorySearchQuery query) {
        long userId = query.getUserId();
        int queryHashCode = query.hashCode();

        // try load query result from cache by userId, query hashcode
        Cache cache = cacheManager.getCache(CACHE_TRANSACTION_SEARCH);
        String cacheKey = CACHE_KEY_SEARCH_BY_CATEGORY_QUERY + userId;
        SimpleValueWrapper cachedSearchQuery = (SimpleValueWrapper) cache.get(cacheKey);
        Map<Integer, TransactionByCategoryResultDTO> searchCategoryMap = null;

        if (cachedSearchQuery != null && cachedSearchQuery.get() != null) {
            searchCategoryMap = (Map<Integer, TransactionByCategoryResultDTO>) cache.get(cacheKey).get();

            if (searchCategoryMap != null) {
                TransactionByCategoryResultDTO searchResult = searchCategoryMap.get(queryHashCode);

                if (searchResult != null) {
                    return searchResult;
                }
            }
        }

        TransactionByCategoryResultDTO result = new TransactionByCategoryResultDTO();
        List<TransactionByCategoryDTO> list = transactionDao.search(query);

        double total = list.stream().mapToDouble(TransactionByCategoryDTO::getTotal).sum();

        double percent = 0;
        String symbol = null;

        for (TransactionByCategoryDTO dto : list) {
            percent = (dto.getTotal() * 100.0) / total;
            dto.setPercent(NumberUtils.formatPercent(percent));
            dto.setTotalStr(NumberUtils.formatAmount(dto.getTotal()));
            symbol = dto.getSymbol();
        }
        result.setList(list);
        result.setTotalStr(NumberUtils.formatAmount(total) + (symbol != null ? symbol : ""));

        // put the result into cache
        if (searchCategoryMap == null) {
            searchCategoryMap = new HashMap<>();
        }

        searchCategoryMap.put(queryHashCode, result);
        cache.put(cacheKey, searchCategoryMap);

        return result;
    }

    @Override
    public double getAmount(long id, long userId) {
        return transactionDao.getAmount(id, userId);
    }

    @Autowired
    public void setDaos(@Qualifier("transactionDao") TransactionDao transactionDao,
                        @Qualifier("accountDao") AccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
        setBaseDao(transactionDao);
    }
}
