package auction.domain;

import nl.fontys.util.Money;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.getCount", query = "SELECT COUNT(a) FROM Item AS a"),
        @NamedQuery(name = "Item.findById", query = "SELECT a FROM Item AS a WHERE a.id = :id"),
        @NamedQuery(name = "Item.findByDescr", query = "SELECT a FROM Item AS a WHERE a.description = :descr"),
        @NamedQuery(name = "Item.getAll", query = "SELECT a FROM Item AS a")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "item")
public class Item implements Comparable<Item> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User seller;
    @OneToOne
    private Category category;
    @Column
    private String description;
    @OneToOne
    private Bid highest;

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
        //seller.addItem(this);
    }

    public Item() {
    }

    public Bid getHighest() {
        return highest;
    }

    public void setHighest(Bid highest) {
        this.highest = highest;
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount, this);
        return highest;
    }

    public boolean equals(Item other) {
        if (this.getHighestBid() != other.getHighestBid()) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getDescription() != other.getDescription()) {
            return false;
        }
        if (this.getSeller() != other.getSeller()) {
            return false;
        }
        if (this.getCategory() != other.getCategory()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(id, category, description);
    }

    @Override
    public String toString() {
        return "Id: " + id + " | Seller: " + seller.getEmail();
    }

    @Override
    public int compareTo(Item o) {
        return o.getHighestBid().getAmount().compareTo(this.getHighestBid().getAmount());
    }
}
