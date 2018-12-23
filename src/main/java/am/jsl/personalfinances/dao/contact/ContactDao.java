package am.jsl.personalfinances.dao.contact;

import am.jsl.personalfinances.dao.BaseDao;
import am.jsl.personalfinances.domain.Contact;

/**
 * The Dao interface for accessing {@link Contact} domain object.
 * @author hamlet
 */
public interface ContactDao extends BaseDao<Contact> {
    /**
     * Returns contact title by its id and user id.
     * @param id the contact id
     * @param userId the user id
     * @return the title
     */
    String getTitle(long id, long userId);
}
