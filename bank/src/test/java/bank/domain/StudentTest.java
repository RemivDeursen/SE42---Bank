package bank.domain;

import bank.utils.TestUtil;
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

public class StudentTest {
    final EntityManagerFactory emf = TestUtil.getEMF();
    EntityManager em, em1, em2;
    private static final Logger LOG = Logger.getLogger(StudentTest.class.getName());

    public StudentTest() {
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
    }

    @Test
    public void test1() {

    }

}
