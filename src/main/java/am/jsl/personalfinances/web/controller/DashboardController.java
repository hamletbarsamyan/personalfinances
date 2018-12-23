package am.jsl.personalfinances.web.controller;

import am.jsl.personalfinances.dto.account.AccountListDTO;
import am.jsl.personalfinances.dto.transaction.TransactionByCategoryResultDTO;
import am.jsl.personalfinances.search.transaction.TransactionByCategorySearchQuery;
import am.jsl.personalfinances.service.account.AccountService;
import am.jsl.personalfinances.service.transaction.TransactionService;
import am.jsl.personalfinances.util.Constants;
import am.jsl.personalfinances.util.DateUtils;
import am.jsl.personalfinances.web.form.PieChartForm;
import am.jsl.personalfinances.web.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Defines methods for the dashboard page functionality.
 *
 * @author hamlet
 */
@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController extends BaseController {
    /**
     * The TransactionService
     */
    @Autowired
    private transient TransactionService transactionService;

    /**
     * The AccountService
     */
    @Autowired
    private transient AccountService accountService;

    /**
     * Searches the transactions for current user based on the given PieChartForm data
     * and returns json.
     *
     * @param searchForm the PieChartForm
     * @return the json containing pie chart data
     */
    @RequestMapping(value = {"/pieChartData"}, method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse pieChartData(@ModelAttribute PieChartForm searchForm) {
        Date start = null;
        Date end = null;
        String[] str = searchForm.getDaterange().split("-");

        try {
            start = DateUtils.toDate(str[0].trim(), Constants.DATE_FORMAT_L);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        try {
            end = DateUtils.toDate(str[1].trim(), Constants.DATE_FORMAT_L);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        JsonResponse response = new JsonResponse();

        // init search query
        TransactionByCategorySearchQuery query = new TransactionByCategorySearchQuery();
        query.setUserId(getUser().getId());
        query.setTransactionType(searchForm.getType());
        query.setAccountId(searchForm.getAccount());
        query.setStartDate(start);
        query.setEndDate(end);

        TransactionByCategoryResultDTO result = transactionService.search(query);
        response.setResult(result);

        return response;
    }

    /**
     * Returns list of active accounts fro current user.
     * @return list of active accounts
     */
    @RequestMapping(value = {"/accountsOverview"}, method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse accountsOverview() {
        JsonResponse response = new JsonResponse();
        List<AccountListDTO> accounts = accountService.getActiveAccounts(getUser().getId());
        response.setResult(accounts);

        return response;
    }
}
