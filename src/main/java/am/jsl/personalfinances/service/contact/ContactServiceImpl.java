package am.jsl.personalfinances.service.contact;

import am.jsl.personalfinances.dao.contact.ContactDao;
import am.jsl.personalfinances.domain.Contact;
import am.jsl.personalfinances.ex.CannotDeleteException;
import am.jsl.personalfinances.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The service implementation of the {@link ContactService}.
 * @author hamlet
 */
@Service("contactService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ContactServiceImpl extends BaseServiceImpl<Contact> implements ContactService {

    /**
     * The template file where is stored a html representation of contacts.
     */
    private static final String CONTACT_LOOKUP_HTML = "contact-lookup.html";

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(long id, long userId) throws CannotDeleteException {
        super.delete(id, userId);
        publish(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(Contact contact) throws Exception {
        super.create(contact);
        publish(contact.getUserId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Contact contact) throws Exception {
        super.update(contact);
        publish(contact.getUserId());
    }

    /**
     * Generates a html representation of contacts for the given user id.
     * @param userId the user id
     */
    private void publish(long userId) {
        if (!publishHtml) {
            log.info("Publish html is disabled");
            return;
        }

        List<Contact> contacts = lookup(userId);

        StringBuilder html = new StringBuilder();
        html.append("<option value='0'></option>");
        String name;

        for (Contact contact : contacts) {
            name = contact.getName();
            name = name == null ? "" : name;

            html.append("<option value='").append(contact.getId()).append("'>");
            html.append(name).append("</option>");
        }

        publish(userId, CONTACT_LOOKUP_HTML, html.toString());
    }

    /**
     * Setter for property 'contactDao'.
     *
     * @param contactDao Value to set for property 'contactDao'.
     */
    @Autowired
    public void setContactDao(@Qualifier("contactDao") ContactDao contactDao) {
        setBaseDao(contactDao);
    }
}
