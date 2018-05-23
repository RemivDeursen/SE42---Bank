package bank.domain;

import bank.dao.AccountDAO;
import bank.dao.AccountDAOJPAImpl;
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
    AccountDAO accountDAO = new AccountDAOJPAImpl(em);

    Account acc = new Account(1L);
    Account acc2 = new Account(2L);
    Account acc9 = new Account(9L);

    @Before
    public void setUp() throws Exception {
    }

    //    1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
//    2.	Welke SQL statements worden gegenereerd?
//    3.	Wat is het eindresultaat in de database?
//    4.	Verklaring van bovenstaande drie observaties.
    @Test
    public void Scenario1() {
        // scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();
        Long expectedBalance = 100L;
        Long expectedThreshold = 0L;
        //acc wordt gepersist en komen dus alle veranderingen aan acc ook in de em te staan.
        assertEquals(expectedBalance, acc.getBalance());
        assertEquals(expectedBalance, accountDAO.findByAccountNr(1L).getBalance());
        assertEquals(expectedThreshold, acc.getThreshold());
        assertEquals(expectedThreshold, accountDAO.findByAccountNr(1L).getThreshold());

        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
    }

    @Test
    public void Scenario2() {
        // scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        //acc wordt gemerged met acc9 waardoor acc9 gelijk wordt gezet aan acc en meteen ook gepersist word
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        //Omdat acc9 gepersist is worden de waardes van acc niet meegenomen in de database maar die van acc9 wel
        acc9.setBalance(balance2a + balance2a);
        em.getTransaction().commit();

        Long accnr = 2L;
        Long expectedBalance = 211L;
        Long expectedMergeBalance = balance2a + balance2a;
        Long expectedThreshold = 0L;

        assertEquals(expectedBalance, acc.getBalance());
        assertNotEquals(expectedBalance, acc9.getBalance());
        assertEquals(expectedThreshold, acc.getThreshold());
        assertEquals(expectedThreshold, acc9.getThreshold());

        assertEquals(expectedMergeBalance, accountDAO.findByAccountNr(accnr).getBalance());
        assertEquals(expectedThreshold, accountDAO.findByAccountNr(accnr).getThreshold());
        assertEquals(expectedThreshold, accountDAO.findByAccountNr(accnr).getThreshold());

        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
        // HINT: gebruik acccountDAO.findByAccountNr
    }

    @Test
    public void Scenario3() {
        // scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        Account acc2 = em.merge(acc);
        //acc is niet gepersist door de em omdat hij gemerged is met acc2
        assertFalse(em.contains(acc)); // verklaar
        //acc2 een kopie van acc die door de merge als persisted is gezet. alle veranderingen worden gemonitored door de EM
        assertTrue(em.contains(acc2)); // verklaar
        //Volgends de Equals in de account klasse staat zijn acc en acc2 gelijk omdat acc2 een merge is van acc
        assertEquals(acc, acc2);  //verklaar

        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit();

        Long expectedBalanceb = 322L;
        Long expectedBalancec = 333L;

        //Het java object acc wat nog niet gepersist is krijgt de waarde 333L van balanceB
        //Het wordt alleen niet in de database geupdate
        assertEquals(expectedBalancec, acc.getBalance());
        assertEquals(expectedBalanceb, acc2.getBalance());

        //Omdat acc2 een kopie is van acc zijn de accountnrs ook hetzelfde
        //en zal de database waarde van het account gelijk zijn aan de laatst geupdate waarde. acc2 met balance3b dus
        assertNotEquals(expectedBalancec, accountDAO.findByAccountNr(acc.getAccountNr()).getBalance());
        assertEquals(expectedBalanceb, accountDAO.findByAccountNr(acc2.getAccountNr()).getBalance());

        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
    }

    @Test
    public void Scenario4() {
        // scenario 4
        Account account = new Account(114L);
        account.setBalance(450L);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        Account account2 = new Account(114L);
        Account tweedeAccountObject = account2;
        tweedeAccountObject.setBalance(650l);
        //tweedeaccount wordt een referentie naar account2, alle veranderingen aan tweedeaccount worden dus doorgevoerd naar account2
        assertEquals((Long) 650L, account2.getBalance());  //verklaar

        account2.setId(account.getId());
        em.getTransaction().begin();
        account2 = em.merge(account2);
        //Merge maakt gebruik van een id, omdat we de id van account2 hiervoor veranderen zoekt hij het account met dat id
        assertSame(account, account2);  //verklaar
        //Na een merge doet een entity manager ook een persist op dat object
        assertTrue(em.contains(account2));  //verklaar
        //Er is nooit em.persist() gecalled op tweedeaccountobject
        assertFalse(em.contains(tweedeAccountObject));  //verklaar
        tweedeAccountObject.setBalance(850l);
        //De referentie naar het oude object van account2 is niet meer mogelijk. Door een merge is account2 een referentie naar account geworden.
        assertEquals((Long) 650L, account.getBalance());  //verklaar
        assertEquals((Long) 650L, account2.getBalance());  //verklaar
        em.getTransaction().commit();
        em.close();
    }

    @After
    public void tearDown() throws Exception {
        //cleaner.clean();
    }
}
