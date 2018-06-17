package auction;


import wsdlAuction.*;

import java.util.List;

public class WebServiceMethods {
    private static final AuctionService auctionService = new AuctionService();

    public static wsdlAuction.User registerUser(String email){
        Auction port = auctionService.getAuctionPort();
        return port.registerUser(email);
    }

    public static wsdlAuction.User getUser(String email){
        Auction port = auctionService.getAuctionPort();
        return port.getUser(email);
    }

    public static Item getItem(Long id) {
        Auction port = auctionService.getAuctionPort();
        return port.getItem(id);
    }

    public static List<Item> findItemByDescription(String description) {
        Auction port = auctionService.getAuctionPort();
        return port.findItemByDescription(description);
    }

    public static Bid newBid(Item item, wsdlAuction.User buyer, Money amount) {
        Auction port = auctionService.getAuctionPort();
        return port.newBid(item, buyer, amount);
    }

    public static Item offerItem(wsdlAuction.User seller, Category cat, String description) {
        Auction port = auctionService.getAuctionPort();
        return port.offerItem(seller, cat, description);
    }

    public static boolean revokeItem(Item item){
        Auction port = auctionService.getAuctionPort();
        return port.revokeItem(item);
    }
}
