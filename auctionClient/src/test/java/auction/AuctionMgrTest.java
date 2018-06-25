package auction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import wsdlAuction.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AuctionMgrTest {
    WebServiceMethods webServiceMethods = new WebServiceMethods();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void after() throws SQLException {
    }

    @Test
    public void getItem() {
        webServiceMethods.Clean();
        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = webServiceMethods.registerUser(email);
        //Geen modellen aanmaken in de frontend
        Category cat = new Category();
        cat.setDescription("cat2");
        Item item1 = webServiceMethods.offerItem(seller1, cat, omsch);
        Item item2 = webServiceMethods.getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription() {
        webServiceMethods.Clean();
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = webServiceMethods.registerUser(email3);
        User seller4 = webServiceMethods.registerUser(email4);
        Category cat = new Category();
        cat.setDescription("cat3");
        Item item1 = webServiceMethods.offerItem(seller3, cat, omsch);
        Item item2 = webServiceMethods.offerItem(seller4, cat, omsch);

        List<Item> res = webServiceMethods.findItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = webServiceMethods.findItemByDescription(omsch);
        assertEquals(2, res.size());

    }

    @Test
    public void newBid() {
        webServiceMethods.Clean();
        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = webServiceMethods.registerUser(email);
        User buyer = webServiceMethods.registerUser(emailb);
        User buyer2 = webServiceMethods.registerUser(emailb2);
        // eerste bod
        Category cat = new Category();
        cat.setDescription("cat9");
        Item item1 = webServiceMethods.offerItem(seller, cat, omsch);
        Money money = new Money();
        money.setCents(10);
        money.setCurrency("eur");
        Bid new1 = webServiceMethods.newBid(item1, buyer, money);
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod

        Money money2 = new Money();
        money.setCents(9);
        money.setCurrency("eur");
        Bid new2 = webServiceMethods.newBid(item1, buyer2, money2);
        assertNotNull(new2);

        // hoger bod
        Money money3 = new Money();
        money.setCents(11);
        money.setCurrency("eur");
        Bid new3 = webServiceMethods.newBid(item1, buyer2, money3);
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }
}
