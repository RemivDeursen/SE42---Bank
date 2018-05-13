package bank.domain;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Table;

import static org.junit.Assert.*;

public class AccountNumberTest {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU");
    EntityManager em = emf.createEntityManager();
    DatabaseCleaner cleaner = new DatabaseCleaner(em);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void accountNumberTest() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        //TODO: verklaar en pas eventueel aan
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        //TODO: verklaar en pas eventueel aan
        assertTrue(account.getId() > 0L);

    }

    @After
    public void tearDown() throws Exception {
        cleaner.clean();
    }
}