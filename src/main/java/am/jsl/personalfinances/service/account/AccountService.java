package am.jsl.personalfinances.service.account;

import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.dto.account.AccountListDTO;
import am.jsl.personalfinances.dto.account.AdjustBalanceDTO;
import am.jsl.personalfinances.service.BaseService;

import java.util.List;

/**
 * Service interface which defines all the methods for working with {@link Account} domain object.
 * @author hamlet
 */
public interface AccountService extends BaseService<Account> {
    /**
     * Returns all accounts for the given user.
     * @param userId the user id
     * @return the list of accounts
     */
    List<AccountListDTO> getAccounts(long userId);

    /**
     * Updates account's balance based on the given AdjustBalanceDTO object.
     * @param adjustBalance the AdjustBalanceDTO object
     * @see AdjustBalanceDTO
     */
    void updateBalance(AdjustBalanceDTO adjustBalance);

    /**
     * Returns active accounts for the given user.
     * @param userId the user id
     * @return the list of active accounts
     */
    List<AccountListDTO> getActiveAccounts(long userId);
}
