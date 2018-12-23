package am.jsl.personalfinances.web.form;

import java.io.Serializable;

/**
 * The BaseSearchForm defines common fields for searching transactions, reminders via web page.
 *
 * @author hamlet
 */
public class BaseSearchForm implements Serializable {
    /**
     * The object id for single object search.
     */
    private long id;

    /**
     * The page to retrieve.
     */
    private int page;

    /**
     * Total elements in page
     */
    private int size;

    /**
     * The contact
     */
    private long contact;

    /**
     * The category
     */
    private long category;

    /**
     * The account
     */
    private long account;

    /**
     * The object type
     */
    private byte type;

    /**
     * The creation date range
     */
    private String daterange;

    /**
     * The object description
     */
    private String description;

    /**
     * The object status
     */
    private short status;

    /**
     * The sort by field
     */
    private String sortBy;

    /**
     * The sorting direction
     */
    private boolean asc;

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
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public long getContact() {
        return contact;
    }

    /**
     * Sets contact.
     *
     * @param contact the contact
     */
    public void setContact(long contact) {
        this.contact = contact;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public long getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(long category) {
        this.category = category;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    public long getAccount() {
        return account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    public void setAccount(long account) {
        this.account = account;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public byte getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * Gets daterange.
     *
     * @return the daterange
     */
    public String getDaterange() {
        return daterange;
    }

    /**
     * Sets daterange.
     *
     * @param daterange the daterange
     */
    public void setDaterange(String daterange) {
        this.daterange = daterange;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public short getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(short status) {
        this.status = status;
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
