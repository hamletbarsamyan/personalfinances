package am.jsl.personalfinances.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Wraps pagination results of lists.
 * @author hamlet
 */
public class ListPaginatedResult<T> implements Serializable {

	/**
	 * The total number of items.
	 */
	private long total = 0;

	/**
	 * The paginated result.
	 */
	private List<T> list = new ArrayList<T>();

	/**
	 * Getter for property 'total'.
	 *
	 * @return Value for property 'total'.
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * Setter for property 'total'.
	 *
	 * @param total Value to set for property 'total'.
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * Getter for property 'list'.
	 *
	 * @return Value for property 'list'.
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * Setter for property 'list'.
	 *
	 * @param list Value to set for property 'list'.
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
}
