package auction.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "select a from User as a"),
        @NamedQuery(name = "User.count", query = "select count(a) from User as a"),
        @NamedQuery(name = "User.findUserByEmail", query = "select a from User as a where a.email = :email")
})
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (unique = true)
    private String email;

    public User(String email) {
        this.email = email;

    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public boolean equals(Object obj) {
        final User other = (User) obj;
        if (this.email != other.email) {
            return false;
        }
        return true;
    }
}
