package auction.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class User {

    @Id @GeneratedValue
    private long id;

    @Column (unique = true)
    private String email;

    public User(String email) {
        this.email = email;

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
}
