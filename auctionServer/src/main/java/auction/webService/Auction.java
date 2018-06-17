package auction.webService;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.RegistrationMgr;
import auction.service.SellerMgr;
import nl.fontys.util.DatabaseCleaner;
import nl.fontys.util.Money;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

@WebService
public class Auction {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private AuctionMgr auctionMgr;
    private SellerMgr sellerMgr;
    private RegistrationMgr registrationMgr;
    private DatabaseCleaner cleaner;


    public Auction() {
        emf = Persistence.createEntityManagerFactory("auctionPU");
        em = emf.createEntityManager();
        this.auctionMgr = new AuctionMgr(em);
        this.sellerMgr = new SellerMgr(em);
        this.registrationMgr = new RegistrationMgr(em);
        cleaner = new DatabaseCleaner(em);
    }

    public void cleanUp() {
        try {
            cleaner.clean();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Item getItem(Long id) {
        return auctionMgr.getItem(id);
    }

    public List<Item> findItemByDescription(String description) {
        return auctionMgr.findItemByDescription(description);
    }

    public Bid newBid(Item item, User buyer, Money amount) {
        return auctionMgr.newBid(item, buyer, amount);
    }

    public Item offerItem(User seller, Category cat, String description) {
        return sellerMgr.offerItem(seller, cat, description);
    }

    public boolean revokeItem(Item item) {
        return sellerMgr.revokeItem(item);
    }

    public User registerUser(String email) {
        return registrationMgr.registerUser(email);
    }

    public User getUser(String email) {
        return registrationMgr.getUser(email);
    }
}
