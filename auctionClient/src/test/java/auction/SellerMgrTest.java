package auction;

import org.junit.*;
import wsdlAuction.Category;
import wsdlAuction.Item;
import wsdlAuction.Money;
import wsdlAuction.User;

import java.sql.SQLException;

import static junit.framework.TestCase.*;

public class SellerMgrTest {
    WebServiceMethods webServiceMethods = new WebServiceMethods();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void after() throws SQLException {
    }
    /**
     * Test of offerItem method, of class SellerMgr.
     */
    @Test
    public void testOfferItem() {
        webServiceMethods.Clean();
        String omsch = "omsch";
        User user1 = webServiceMethods.registerUser("xx@nl");
        Category cat = new Category();
        cat.setDescription("cat1");
        Item item1 = webServiceMethods.offerItem(user1, cat, omsch);
        assertEquals(omsch, item1.getDescription());
        assertNotNull(item1.getId());
    }

    /**
     * Test of revokeItem method, of class SellerMgr.
     */
    @Test
    public void testRevokeItem() {
        webServiceMethods.Clean();
        String omsch = "omsch";
        String omsch2 = "omsch2";


        User seller = webServiceMethods.registerUser("sel@nl");
        User buyer = webServiceMethods.registerUser("buy@nl");
        Category cat = new Category();
        cat.setDescription("cat1");

            // revoke before bidding
        Item item1 = webServiceMethods.offerItem(seller, cat, omsch);
        boolean res = webServiceMethods.revokeItem(item1);
        assertTrue(res);
        int count = webServiceMethods.findItemByDescription(omsch).size();
        assertEquals(1, count);

            // revoke after bid has been made
        Item item2 = webServiceMethods.offerItem(seller, cat, omsch2);
        Money money = new Money();
        money.setCents(100);
        money.setCurrency("Euro");
        webServiceMethods.newBid(item2, buyer, money);
        boolean res2 = webServiceMethods.revokeItem(item2);
        assertFalse(res2);
        int count2 = webServiceMethods.findItemByDescription(omsch2).size();
        assertEquals(1, count2);
    }

}
