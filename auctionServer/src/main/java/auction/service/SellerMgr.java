package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;

public class SellerMgr {

    private ItemDAO itemDAO;

    public SellerMgr(EntityManager em) {
        itemDAO = new ItemDAOJPAImpl(em);
    }
    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     *         en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        return itemDAO.create(new Item(seller, cat, description));
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        // TODO
        if (itemDAO.find(item.getId()).getHighestBid() != null){
            return false;
        }
        return true;
    }

}
