package bank.domain;

import bank.utils.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class FindandClear {
    final EntityManagerFactory emf = TestUtil.getEMF();
    EntityManager em, em1, em2;
    DatabaseCleaner cleaner;
    private static final Logger LOG = Logger.getLogger(StudentTest.class.getName());
    Account acc1 = new Account(77L);

    public FindandClear() {
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        em1 = emf.createEntityManager();
        em2 = emf.createEntityManager();
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        cleaner = new DatabaseCleaner(em);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        //Database bevat nu een account

    }

    @Test
    public void test() {
        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        assertNotSame(accF1, accF2);
        //Na een clear worden de gemanagede entiteiten gecleared en moeten ze opnieuw opgehaald worden, daarom kunnen ze niet dezelfde klasse zijn
        //TODO verklaar verschil tussen beide scenario’s
    }

    @After
    public void tearDown() throws Exception {
        cleaner.clean();
    }

}
