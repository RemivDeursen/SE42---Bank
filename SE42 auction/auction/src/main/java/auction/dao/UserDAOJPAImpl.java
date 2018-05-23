package auction.dao;

import auction.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserDAOJPAImpl implements UserDAO {

    private HashMap<String, User> users;
    private EntityManager em;
    private EntityManagerFactory emf;

    public UserDAOJPAImpl() {
        users = new HashMap<String, User>();
        emf = Persistence.createEntityManagerFactory("auctionPU");
        em = emf.createEntityManager();
    }

    @Override
    public int count() {
        return users.size();
    }

    @Override
    public void create(User user) {
        if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        em.getTransaction();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }
        users.put(user.getEmail(), user);
    }


    @Override
    public List<User> findAll() {
        return new ArrayList<User>(users.values());
    }

    @Override
    public User findByEmail(String email) {
        return users.get(email);
    }

    @Override
    public void remove(User user) {
        users.remove(user.getEmail());
    }
}
