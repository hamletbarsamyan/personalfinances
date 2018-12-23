package am.jsl.personalfinances.web.controller.reminder;

import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.domain.reminder.Reminder;
import am.jsl.personalfinances.domain.transaction.TransactionType;
import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.dto.reminder.ReminderDTO;
import am.jsl.personalfinances.dto.reminder.ReminderDetailsDTO;
import am.jsl.personalfinances.dto.reminder.ReminderListDTO;
import am.jsl.personalfinances.dto.transaction.TransactionListDTO;
import am.jsl.personalfinances.ex.CannotDeleteException;
import am.jsl.personalfinances.search.ListPaginatedResult;
import am.jsl.personalfinances.search.PageWrapper;
import am.jsl.personalfinances.search.reminder.ReminderSearchQuery;
import am.jsl.personalfinances.service.account.AccountService;
import am.jsl.personalfinances.service.reminder.ReminderService;
import am.jsl.personalfinances.service.transaction.TransactionService;
import am.jsl.personalfinances.util.Constants;
import am.jsl.personalfinances.util.DateUtils;
import am.jsl.personalfinances.web.controller.BaseController;
import am.jsl.personalfinances.web.form.ReminderSearchForm;
import am.jsl.personalfinances.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static am.jsl.personalfinances.web.util.WebUtils.ACCOUNTS;
import static am.jsl.personalfinances.web.util.WebUtils.TRANSACTIONS;

/**
 * The ReminderController defines methods for reminder pages functionality
 * such as search, view, add, edit reminders.
 *
 * @author hamlet
 */
@Controller
@RequestMapping(value = "/reminder")
@Lazy
public class ReminderController extends BaseController {
    /**
     * The reminder template paths
     */
    public static final String REDIRECT_REMINDER_LIST = "redirect:list";
    public static final String FORWARD_REMINDER_LIST = "reminder/reminder-list";
    public static final String FORWARD_REMINDER_RESULT_LIST = "reminder/reminder-list-result :: reminderResultsList";
    public static final String FORWARD_TRANSACTION_RESULT_LIST = "reminder/reminder-transactions :: transactionResultsList";
    public static final String FORWARD_REMINDER_VIEW = "reminder/reminder-view :: reminderFormDiv";
    public static final String FORWARD_REMINDER_MANAGE = "reminder/reminder-manage :: reminderFormDiv";

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
     * The ReminderService
     */
    @Autowired
    private transient ReminderService reminderService;

    /**
     * Called when opened reminder list.
     *
     * @return the reminder list
     */
    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list() {
        return FORWARD_REMINDER_LIST;
    }

    /**
     * Called via ajax methods for searchin reminders with the given ReminderSearchForm data.
     *
     * @param model      the Model
     * @param searchForm the ReminderSearchForm
     * @return tje reminder list template
     */
    @RequestMapping(value = {"/loadReminders"}, method = RequestMethod.POST)
    public String loadReminders(Model model,
                                @ModelAttribute ReminderSearchForm searchForm) {

        int page = searchForm.getPage();
        int size = searchForm.getSize();
        Date start = null;
        Date end = null;
        String[] str = searchForm.getDaterange().split("-");

        if (str.length == 2) {
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
        }

        if (size < Constants.PAGE_SIZE || size > 100) {
            size = Constants.PAGE_SIZE;
        }

        // init search query
        ReminderSearchQuery query = new ReminderSearchQuery(page, size);
        query.setId(searchForm.getId());
        query.setUserId(getUser().getId());
        query.setTransactionType(searchForm.getType());
        query.setAccountId(searchForm.getAccount());
        query.setCategoryId(searchForm.getCategory());
        query.setContact(searchForm.getContact());
        query.setStatus(searchForm.getStatus());
        query.setSortBy(searchForm.getSortBy());
        query.setAsc(searchForm.isAsc());
        query.setStartDate(start);
        query.setEndDate(end);

        ListPaginatedResult<ReminderListDTO> result = reminderService.search(query);

        PageWrapper<ReminderListDTO> pageWrapper = new PageWrapper<>(result, page, size);
        model.addAttribute(Constants.PAGE, pageWrapper);

        return FORWARD_REMINDER_RESULT_LIST;
    }

    /**
     * Called when users clicks on add link from remidner list page.
     *
     * @param model the Model
     * @return the reminder manage template
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage(Model model) {
        if (!model.containsAttribute(WebUtils.REMINDER)) {
            User user = getUser();
            long userId = user.getId();
            List<Account> accounts = accountService.lookup(userId);
            long accountId = !accounts.isEmpty() ? accounts.get(0).getId() : 0;

            ReminderDTO reminderDTO = new ReminderDTO(accountId, TransactionType.EXPENSE.getValue(), LocalDateTime.now(), userId);
            reminderDTO.setTargetAccountId(accountId);
            model.addAttribute(WebUtils.REMINDER, reminderDTO);
            model.addAttribute(ACCOUNTS, accounts);
        }
        return FORWARD_REMINDER_MANAGE;
    }

    /**
     * Called when user clicks on edit link from reminder list page.
     *
     * @param id    the reminder id
     * @param model the Model
     * @return the manage reminder template
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(@RequestParam(value = "id", required = true)
                                   long id, Model model) {
        if (!model.containsAttribute(WebUtils.REMINDER)) {
            User user = getUser();
            long userId = user.getId();
            Reminder reminder = reminderService.get(id, userId);
            List<Account> accounts = accountService.lookup(userId);

            model.addAttribute(WebUtils.REMINDER, ReminderDTO.from(reminder));
            model.addAttribute(ACCOUNTS, accounts);
        }
        return FORWARD_REMINDER_MANAGE;
    }

    /**
     * Called when user clicks on save link from manage reminder window.
     *
     * @param reminderDTO the ReminderDTO
     * @return the reminder list
     * @throws Exception if exception occurs
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute ReminderDTO reminderDTO) throws Exception {
        Reminder reminder = reminderDTO.toReminder();
        reminder.setUserId(getUser().getId());

        if (reminder.getId() == 0) {
            reminderService.create(reminder);
        } else {
            reminderService.update(reminder);
        }
        return REDIRECT_REMINDER_LIST;
    }

    /**
     * Called when user clicks on view link from reminder list page.
     *
     * @param id    the reminder id
     * @param model the Model
     * @return the reminder view template
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(@RequestParam(value = "id", required = true)
                               long id, Model model) {
        if (!model.containsAttribute(WebUtils.REMINDER)) {
            User user = getUser();
            long userId = user.getId();
            ReminderDetailsDTO reminder = reminderService.getDetails(id, userId);

            model.addAttribute(WebUtils.REMINDER, reminder);
        }
        return FORWARD_REMINDER_VIEW;
    }

    /**
     * Called when user clicks on reminder link.
     *
     * @param id the reminder id
     * @return the reminder list page
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(value = "id") long id) {

        try {
            if (id != 0) {
                reminderService.delete(id, getUser().getId());
            }
        } catch (CannotDeleteException e) {
            log.error(e.getMessage(), e);
        }

        return REDIRECT_REMINDER_LIST;
    }

    /**
     * Called via ajax method for loading transactions with the given reminder id.
     *
     * @param reminderId the remidner id
     * @param model      the Model
     * @return the reminder transactions template
     */
    @RequestMapping(value = {"/transactions"}, method = RequestMethod.POST)
    public String transactions(@RequestParam(value = "reminderId") long reminderId, Model model) {
        List<TransactionListDTO> transactions = transactionService.getReminderTransactions(reminderId, getUser().getId());

        model.addAttribute(TRANSACTIONS, transactions);
        return FORWARD_TRANSACTION_RESULT_LIST;
    }
}
