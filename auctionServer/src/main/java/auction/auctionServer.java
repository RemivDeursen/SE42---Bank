package auction;


import auction.webService.Auction;

import javax.xml.ws.Endpoint;

public class auctionServer {
    private static final String url = "http://localhost:8080/WebAuction";
    public static void main(String[] args) {
        Endpoint.publish(url, new Auction());
    }
}
