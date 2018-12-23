package am.jsl.personalfinances.search;

/**
 * The base query class that contains pagination information for querying items with generic way.
 *
 * @param <T> the type parameter
 * @author hamlet
 */
public class Query<T> {
	/**
	 * The item id to be find.
	 */
	protected long id;
	/**
	 * The current page to query.
	 */
	protected int page = 1;
	/**
	 * The number of items to be returned.
	 */
	protected int pageSize = -1;
	/**
	 * The Sort by.
	 */
	protected String sortBy;
	/**
	 * True if sort by ascending order.
	 */
	protected boolean asc;

	/**
	 * Instantiates a new Query.
	 */
	public Query() {
	}

	/**
	 * Instantiates a new Query.
	 *
	 * @param page     the page
	 * @param pageSize the page size
	 */
	public Query(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}


	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets page.
	 *
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Sets page.
	 *
	 * @param page the page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * Gets page size.
	 *
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Sets page size.
	 *
	 * @param pageSize the page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Gets sort by.
	 *
	 * @return the sort by
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * Sets sort by.
	 *
	 * @param sortBy the sort by
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * Is asc boolean.
	 *
	 * @return the boolean
	 */
	public boolean isAsc() {
		return asc;
	}

	/**
	 * Sets asc.
	 *
	 * @param asc the asc
	 */
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
}
