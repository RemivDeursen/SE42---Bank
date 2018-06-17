package auction;


import wsdlAuction.*;

import java.util.List;

public class WebServiceMethods {
    private Auction port;

    public WebServiceMethods(){
        AuctionService auctionService = new AuctionService();
        port = auctionService.getAuctionPort();
    }

    public User registerUser(String email){
        return port.registerUser(email);
    }

    public User getUser(String email){
        return port.getUser(email);
    }

    public Item getItem(Long id) {
        return port.getItem(id);
    }

    public List<Item> findItemByDescription(String description) {
        return port.findItemByDescription(description);
    }

    public Bid newBid(Item item, User buyer, Money amount) {
        return port.newBid(item, buyer, amount);
    }

    public Item offerItem(User seller, Category cat, String description) {
        return port.offerItem(seller, cat, description);
    }

    public boolean revokeItem(Item item){
        return port.revokeItem(item);
    }

    public void Clean(){
        port.cleanUp();
    }
}
