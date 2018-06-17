package auction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import wsdlAuction.User;

import static org.junit.Assert.*;

public class WebServiceTest {
    String email = "test@test.com";

    public WebServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//    @Test
//    public void registerUser() {
//        String expected = "test@test.com";
//        User user = WebServiceMethods.registerUser(email);
//        System.out.println(user);
//
//        assertEquals(expected, user.getEmail());
//    }
//
//    @Test
//    public void getUser() {
//        String expected = "test@test.com";
//        User user = WebServiceMethods.getUser(email);
//        System.out.println(user);
//
//        assertEquals(expected, user.getEmail());
//    }
}
