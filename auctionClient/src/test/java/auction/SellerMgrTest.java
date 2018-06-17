package auction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import wsdlAuction.Category;
import wsdlAuction.Item;
import wsdlAuction.User;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class SellerMgrTest {

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
        String omsch = "omsch";
        User user1 = WebServiceMethods.registerUser("xx@nl");
        Category cat = new Category();
        Item item1 = WebServiceMethods.offerItem(user1, cat, omsch);
        assertEquals(omsch, item1.getDescription());
        assertNotNull(item1.getId());
    }

    /**
     * Test of revokeItem method, of class SellerMgr.
     */
    @Test
    public void testRevokeItem() {
        String omsch = "omsch";
        String omsch2 = "omsch2";


        User seller = registrationMgr.registerUser("sel@nl");
        User buyer = registrationMgr.registerUser("buy@nl");
        Category cat = new Category("cat1");

            // revoke before bidding
        Item item1 = sellerMgr.offerItem(seller, cat, omsch);
        boolean res = sellerMgr.revokeItem(item1);
        assertTrue(res);
        int count = auctionMgr.findItemByDescription(omsch).size();
        assertEquals(1, count);

            // revoke after bid has been made
        Item item2 = sellerMgr.offerItem(seller, cat, omsch2);
        auctionMgr.newBid(item2, buyer, new Money(100, "Euro"));
        boolean res2 = sellerMgr.revokeItem(item2);
        assertTrue(res2);
        int count2 = auctionMgr.findItemByDescription(omsch2).size();
        assertEquals(1, count2);
    }

}
