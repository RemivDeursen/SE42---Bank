package auction.domain;

import javax.jws.WebMethod;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.getCount", query = "SELECT COUNT(a) FROM User AS a"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT a FROM User AS a WHERE a.email = :email"),
        @NamedQuery(name = "User.findById", query = "SELECT a FROM User AS a WHERE a.id = :id"),
        @NamedQuery(name = "User.getAll", query = "SELECT a FROM User AS a")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (unique = true)
    private String email;

    public User(String email) {
        this.email = email;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @WebMethod
    public boolean equals(Object obj) {
        final User other = (User) obj;
        if (this.email != other.email) {
            return false;
        }
        return true;
    }

    @WebMethod
    public int hashCode(){
        return this.getId().hashCode();
    }
}
