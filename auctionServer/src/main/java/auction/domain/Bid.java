package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Bid.getAll", query = "select a from Bid as a"),
        @NamedQuery(name = "Bid.count", query = "select count(a) from Bid as a")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    private FontysTime time;
    @OneToOne
    private User buyer;
    @OneToOne
    @JoinColumn(name = "item", nullable = false)
    private Item item;
    @Embedded
    private Money amount;

    public Bid(User buyer, Money amount, Item item) {
        this.buyer = buyer;
        this.amount = amount;
        this.item = item;
    }

    public Bid() {
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }

        Bid b = (Bid)obj;



        return  this.buyer.equals(b.buyer) && this.amount.equals(b.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyer, amount, time);
    }
}
