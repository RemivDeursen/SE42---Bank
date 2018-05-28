package auction.domain;

import com.sun.istack.internal.Nullable;
import nl.fontys.util.Money;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.getAll", query = "select a from Item as a"),
        @NamedQuery(name = "Item.count", query = "select count(a) from Item as a"),
        @NamedQuery(name = "Item.findByDescription", query = "select a from Item as a where a.description = :description")
})
public class Item implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User seller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category")
    private Category category;

    private String description;

    @OneToOne
    @JoinColumn(name = "highest", nullable = true)
    private Bid highest;

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Item() {
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
        highest = new Bid(buyer, amount);
        return highest;
    }

    public int compareTo(Object arg0) {
        Item other = (Item) arg0;
        if (this.getClass() == other.getClass()) return 0;
        return -1;
    }

    public boolean equals(Object o) {
        final Item other = (Item) o;
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
        return (int) (id * seller.hashCode() * category.hashCode() * description.hashCode());
    }
}
