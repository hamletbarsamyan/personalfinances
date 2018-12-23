package am.jsl.personalfinances.dto.transaction;

import java.io.Serializable;
import java.util.List;

/**
 * Contains list of transactions grouped by category and account.
 * @author hamlet
 */
public class TransactionByCategoryResultDTO implements Serializable {

    /**
     * The total amount
     */
    private String totalStr;

    /**
     * The list of grouped transactions
     */
    private List<TransactionByCategoryDTO> list;

    /**
     * Getter for property 'totalStr'.
     *
     * @return Value for property 'totalStr'.
     */
    public String getTotalStr() {
        return totalStr;
    }

    /**
     * Setter for property 'totalStr'.
     *
     * @param totalStr Value to set for property 'totalStr'.
     */
    public void setTotalStr(String totalStr) {
        this.totalStr = totalStr;
    }

    /**
     * Getter for property 'list'.
     *
     * @return Value for property 'list'.
     */
    public List<TransactionByCategoryDTO> getList() {
        return list;
    }

    /**
     * Setter for property 'list'.
     *
     * @param list Value to set for property 'list'.
     */
    public void setList(List<TransactionByCategoryDTO> list) {
        this.list = list;
    }
}
