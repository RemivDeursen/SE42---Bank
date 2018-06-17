package auction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import wsdlAuction.Auction;
import wsdlAuction.AuctionService;
import wsdlAuction.User;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class RegistrationMgrTest {
    WebServiceMethods webServiceMethods = new WebServiceMethods();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void after() throws SQLException {
    }

    @Test
    public void registerUser() throws SQLException {
        webServiceMethods.Clean();
        User user1 = webServiceMethods.registerUser("xxx1@yyy");
        assertTrue(user1.getEmail().equals("xxx1@yyy"));
        User user2 = webServiceMethods.registerUser("xxx2@yyy2");
        assertTrue(user2.getEmail().equals("xxx2@yyy2"));
        User user2bis = webServiceMethods.registerUser("xxx2@yyy2");
        assertNotSame(user2bis, user2);
        //geen @ in het adres
        assertNull(webServiceMethods.registerUser("abc"));
    }

    @Test
    public void getUser() throws SQLException {
        webServiceMethods.Clean();
        User user1 = webServiceMethods.registerUser("xxx5@yyy5");
        User userGet = webServiceMethods.getUser("xxx5@yyy5");
        assertNotSame(userGet, user1);
        assertNull(webServiceMethods.getUser("aaa4@bb5"));
        webServiceMethods.registerUser("abc");
        assertNull(webServiceMethods.getUser("abc"));
    }
}
