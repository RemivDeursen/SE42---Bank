package auction.dao;

import auction.domain.User;
import nl.fontys.util.DatabaseCleaner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;

public class UserDAOJPAImpl implements UserDAO {

    private EntityManager em;

    public UserDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        return findAll().size();
    }

    @Override
    public void create(User user) {

        if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        //verbeter punt, EM lokaal aanmaken
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void edit(User user) {
        //Implementatie interface bekijken
        try{
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public void remove(User user) {
        em.getTransaction().begin();
        em.remove(em.merge(user));
        em.getTransaction().commit();
    }

    @Override
    public User findByEmail(String email) {
        Query q = em.createNamedQuery("User.findByEmail", User.class);
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
        return em.find(User.class, id);
    }
}
