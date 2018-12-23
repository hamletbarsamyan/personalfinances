package am.jsl.personalfinances.dto.transaction;

import am.jsl.personalfinances.search.ListPaginatedResult;

import java.io.Serializable;
import java.util.List;

/**
 *  The TransactionSearchResult contains list of transactions and total amounts
 *  grouped by transaction type and account.
 * @author hamlet
 * @see TransactionListTotalDTO
 * @see TransactionListDTO
 */
public class TransactionSearchResult implements Serializable {
    private List<TransactionListTotalDTO> totals;
    private ListPaginatedResult<TransactionListDTO> listPaginatedResult;

    /**
     * Getter for property 'totals'.
     *
     * @return Value for property 'totals'.
     */
    public List<TransactionListTotalDTO> getTotals() {
        return totals;
    }

    /**
     * Setter for property 'totals'.
     *
     * @param totals Value to set for property 'totals'.
     */
    public void setTotals(List<TransactionListTotalDTO> totals) {
        this.totals = totals;
    }

    public ListPaginatedResult<TransactionListDTO> getListPaginatedResult() {
        return listPaginatedResult;
    }

    public void setListPaginatedResult(ListPaginatedResult<TransactionListDTO> listPaginatedResult) {
        this.listPaginatedResult = listPaginatedResult;
    }
}
