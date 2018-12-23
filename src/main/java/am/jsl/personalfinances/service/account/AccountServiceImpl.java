package am.jsl.personalfinances.service.account;

import am.jsl.personalfinances.dao.account.AccountDao;
import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.dto.account.AccountListDTO;
import am.jsl.personalfinances.dto.account.AdjustBalanceDTO;
import am.jsl.personalfinances.ex.CannotDeleteException;
import am.jsl.personalfinances.service.BaseServiceImpl;
import am.jsl.personalfinances.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The service implementation of the {@link AccountService}.
 * @author hamlet
 */
@Service("accountService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AccountServiceImpl extends BaseServiceImpl<Account> implements AccountService {

    /**
     * The template file where is stored a html representation of accounts.
     */
    private static final String ACCOUNT_LOOKUP_HTML = "account-lookup.html";

    /**
     * The account dao.
     */
    private AccountDao accountDao;

    @Override
    public List<AccountListDTO> getAccounts(long userId) {
        return accountDao.getAccounts(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateBalance(AdjustBalanceDTO adjustBalance) {
        accountDao.updateBalance(adjustBalance);
    }

    @Override
    public List<AccountListDTO> getActiveAccounts(long userId) {
        return accountDao.getActiveAccounts(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(long id, long userId) throws CannotDeleteException {
        super.delete(id, userId);
        publish(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(Account account) throws Exception {
        super.create(account);
        publish(account.getUserId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Account account) throws Exception {
        super.update(account);
        publish(account.getUserId());
    }

    /**
     * Setter for property 'accountDao'.
     *
     * @param accountDao Value to set for property 'accountDao'.
     */
    @Autowired
    public void setAccountDao(@Qualifier("accountDao") AccountDao accountDao) {
        this.accountDao = accountDao;
        setBaseDao(accountDao);
    }

    /**
     * Generates a html representation of accounts for the given user id.
     * @param userId the user id
     */
    private void publish(long userId) {
        if (!publishHtml) {
            log.info("Publish html is disabled");
            return;
        }

        List<Account> accounts = lookup(userId);

        StringBuilder html = new StringBuilder();
        html.append("<option value='0'></option>");
        long id;
        String name;
        String icon;

        for (Account account : accounts) {
            id = account.getId();
            icon = account.getIcon();
            icon = icon == null ? "" : icon;
            name = account.getName();
            name = name == null ? "" : name;

            if (!TextUtils.isEmpty(account.getSymbol())) {
                name += " (" + account.getSymbol() + ")";
            }

            html.append("<option value='").append(id).append("' ");
            html.append("currency='").append(account.getCurrency()).append("'>");
            html.append("<span>").append(icon).append("</span>").append("&nbsp;");
            html.append("<span>").append(name);
            html.append("</span></option>");
        }

        publish(userId, ACCOUNT_LOOKUP_HTML, html.toString());
    }
}
