package auction.dao;

import auction.domain.User;
import nl.fontys.util.DatabaseCleaner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;

public class UserDAOJPAImpl implements UserDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    private EntityManager em;
    DatabaseCleaner cleaner = new DatabaseCleaner(em);

    public UserDAOJPAImpl() {
    }

    @Override
    public int count() {
        return findAll().size();
    }

    public void create(User user) {
        //verbeter punt, EM lokaal aanmaken
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public void edit(User user) {
        //Implementatie interface bekijken
        try{
            em.merge(user);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        em = emf.createEntityManager();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
    }

    public void remove(User user) {
        em.remove(em.merge(user));
    }

    public User findByEmail(String email) {
        em = emf.createEntityManager();
        Query q = em.createNamedQuery("User.findUserByEmail", User.class);
        q.setParameter("email", email);
        System.out.println(q.getFirstResult());
        try {
            User user = (User) q.getSingleResult();
            return user;
        }
        catch (NoResultException e){
            return null;
        }
    }

    public User find(Long id) {
        em = emf.createEntityManager();
        return em.find(User.class, id);
    }

}
