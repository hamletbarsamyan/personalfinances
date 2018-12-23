package am.jsl.personalfinances.dao.account;


import am.jsl.personalfinances.dao.BaseDao;
import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.dto.account.AccountListDTO;
import am.jsl.personalfinances.dto.account.AdjustBalanceDTO;

import java.util.List;

/**
 * The Dao interface for accessing {@link Account} domain object.
 * @author hamlet
 */
public interface AccountDao extends BaseDao<Account> {

    /**
     * Returns all accounts for the given user.
     * @param userId the user id
     * @return the list of accounts
     */
    List<AccountListDTO> getAccounts(long userId);

    /**
     * Returns active accounts for the given user.
     * @param userId the user id associated with account
     * @return the list of active accounts
     */
    List<AccountListDTO> getActiveAccounts(long userId);

    /**
     * Updates account's balance based on the given AdjustBalanceDTO object.
     * @param adjustBalance the AdjustBalanceDTO object
     * @see AdjustBalanceDTO
     */
    void updateBalance(AdjustBalanceDTO adjustBalance);

    /**
     * Decreases the balance of the given account.
     * @param id the account id
     * @param userId the user id associated with account
     * @param amount the amount to subtract from balance
     */
    void decreaseBalance(long id, long userId, double amount);

    /**
     * Increases the balance of the given account.
     * @param id  the account id
     * @param userId the user id associated with account
     * @param amount the amount to add to balance
     */
    void increaseBalance(long id, long userId, double amount);
}
