package am.jsl.personalfinances.dao.transaction;

import am.jsl.personalfinances.dao.BaseDao;
import am.jsl.personalfinances.domain.transaction.Transaction;
import am.jsl.personalfinances.dto.transaction.TransactionByCategoryDTO;
import am.jsl.personalfinances.dto.transaction.TransactionDetailsDTO;
import am.jsl.personalfinances.dto.transaction.TransactionListDTO;
import am.jsl.personalfinances.dto.transaction.TransactionSearchResult;
import am.jsl.personalfinances.search.transaction.TransactionByCategorySearchQuery;
import am.jsl.personalfinances.search.transaction.TransactionSearchQuery;

import java.util.List;

/**
 * The Dao interface for accessing {@link Transaction} domain object.
 * @author hamlet
 */
public interface TransactionDao extends BaseDao<Transaction> {
    /**
     * Retrieves paginated result of transactions for the given search query.
     * @param searchQuery the {@link TransactionSearchQuery} containing query options
     * @return the {@link TransactionSearchResult} containing paged result
     */
    TransactionSearchResult search(TransactionSearchQuery searchQuery);

    /**
     * Creates the given list of transactions.
     * @param transactions the list of transactions
     */
    void createBatch(List<Transaction> transactions);

    /**
     * Returns a transaction details with the given id and user id.
     * @param id the transaction id
     * @param userId the user id
     * @return the {@link TransactionDetailsDTO} containing transaction details
     */
    TransactionDetailsDTO getDetails(long id, long userId);

    /**
     * Returns transactions with the given reminder id and user id.
     * @param reminderId the reminder id
     * @param userId the user id
     * @return the list of {@link TransactionListDTO} items
     */
    List<TransactionListDTO> getReminderTransactions(long reminderId, long userId);

    /**
     * Retrieves paginated result of transactions for the given search query.
     * @param query the {@link TransactionByCategorySearchQuery} query containing search options
     * @return the list of {@link TransactionByCategoryDTO} items
     */
    List<TransactionByCategoryDTO> search(TransactionByCategorySearchQuery query);

    /**
     * Returns transaction amount with the given transaction id and user id
     * @param id the transaction id
     * @param userId the user id
     * @return the amount
     */
    double getAmount(long id, long userId);
}
