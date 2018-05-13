package bank.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MergeTest {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU");
    EntityManager em = emf.createEntityManager();
    DatabaseCleaner cleaner = new DatabaseCleaner(em);

    Account acc = new Account(1L);
    Account acc2 = new Account(2L);
    Account acc9 = new Account(9L);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void Scenario1(){
        // scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();
        Long expectedBalance = 100L;
        Long expectedThreshold = 0L;
        assertEquals(expectedBalance, acc.getBalance());
        assertEquals(expectedThreshold, acc.getThreshold());

        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
    }

    @Test
    public void Scenario2(){
        // scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2a+balance2a);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
        // HINT: gebruik acccountDAO.findByAccountNr
    }

    @Test
    public void Scenario3(){
        // scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        Account acc2 = em.merge(acc);
        assertFalse(em.contains(acc)); // verklaar
        assertTrue(em.contains(acc2)); // verklaar
        assertNotEquals(acc,acc2);  //verklaar
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit() ;
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
    }

    @Test
    public void Scenario4(){
        // scenario 4
        Account account = new Account(114L) ;
        account.setBalance(450L) ;
        EntityManager em = emf.createEntityManager() ;
        em.getTransaction().begin() ;
        em.persist(account) ;
        em.getTransaction().commit() ;

        Account account2 = new Account(114L) ;
        Account tweedeAccountObject = account2 ;
        tweedeAccountObject.setBalance(650l) ;
        assertEquals((Long)650L,account2.getBalance()) ;  //verklaar
        account2.setId(account.getId()) ;
        em.getTransaction().begin() ;
        account2 = em.merge(account2) ;
        assertSame(account,account2) ;  //verklaar
        assertTrue(em.contains(account2)) ;  //verklaar
        assertFalse(em.contains(tweedeAccountObject)) ;  //verklaar
        tweedeAccountObject.setBalance(850l) ;
        assertEquals((Long)650L,account.getBalance()) ;  //verklaar
        assertEquals((Long)650L,account2.getBalance()) ;  //verklaar
        em.getTransaction().commit() ;
        em.close() ;
    }

    @After
    public void tearDown() throws Exception {
        //cleaner.clean();
    }
}
