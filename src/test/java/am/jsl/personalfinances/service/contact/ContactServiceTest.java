package am.jsl.personalfinances.service.contact;

import am.jsl.personalfinances.domain.Contact;
import am.jsl.personalfinances.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains ContactService tests.
 */
public class ContactServiceTest extends BaseTest {

    /**
     * Executed before all ContactServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
    }

    @Test
    @DisplayName("Create Contact Test")
    public void testCreateContact() throws Exception {
        log.info("Starting test for create contact");
        Contact contact = new Contact();
        contact.setName("test contact");
        contact.setEmail("email");
        contact.setPhone("phone");
        contact.setDescription("description");

        contact.setCreatedAt(LocalDateTime.now());
        contact.setUserId(user.getId());
        contactService.create(contact);

        assertTrue(contact.getId() > 0);
        log.info("Finished test for create contact");
    }

    @Test
    @DisplayName("Update Contact Test")
    public void testUpdateContact() throws Exception {
        log.info("Starting test for update contact");
        String contactName = "name updated";
        String email = "email updated";
        String phone = "phone updated";
        String description = "description updated";

        Contact contact = createContact();

        // update contact
        contact.setName(contactName);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setDescription(description);

        contactService.update(contact);

        // validate contact
        contact = contactService.get(contact.getId(), contact.getUserId());

        assertEquals(contactName, contact.getName());
        assertEquals(email, contact.getEmail());
        assertEquals(phone, contact.getPhone());
        assertEquals(description, contact.getDescription());

        log.info("Finished test for update contact");
    }

    @Test
    @DisplayName("Delete Contact Test")
    public void testDeleteContact() throws Exception {
        log.info("Starting test for delete contact");

        Contact contact = createContact();
        long contactId = contact.getId();
        long userId = contact.getUserId();

        contactService.delete(contact.getId(), contact.getUserId());

        // validate contact
        contact = contactService.get(contactId, userId);
        assertNull(contact);

        log.info("Finished test for delete contact");
    }

    @Test
    @DisplayName("List Contacts Test")
    public void testListContacts() throws Exception {
        log.info("Starting test for list contacts");
        Contact contact = new Contact();
        contact.setName("test contact");
        contact.setEmail("email");
        contact.setPhone("phone");
        contact.setDescription("description");

        contact.setCreatedAt(LocalDateTime.now());
        contact.setUserId(user.getId());
        contactService.create(contact);

        List<Contact> contacts = contactService.list(user.getId());

        assertEquals(1, contacts.size());
        log.info("Finished test for list contacts");
    }

    @Test
    @DisplayName("Lookup Contacts Test")
    public void testLookupContacts() throws Exception {
        log.info("Starting test for lookup contacts");
        Contact contact = new Contact();
        contact.setName("test contact");
        contact.setEmail("email");
        contact.setPhone("phone");
        contact.setDescription("description");

        contact.setCreatedAt(LocalDateTime.now());
        contact.setUserId(user.getId());
        contactService.create(contact);

        List<Contact> contacts = contactService.lookup(user.getId());

        assertEquals(1, contacts.size());
        log.info("Finished test for lookup contacts");
    }

    /**
     * Executed after all ContactServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
