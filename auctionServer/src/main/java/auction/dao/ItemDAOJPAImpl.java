package auction.dao;

import auction.domain.Item;
import auction.domain.User;
import nl.fontys.util.DatabaseCleaner;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOJPAImpl implements ItemDAO {

    private EntityManager em;

    public ItemDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        return findAll().size();
    }

    @Override
    public Item create(Item item) {
        Item temp = null;
        em.getTransaction().begin();
        em.persist(item);
        temp = em.merge(item);
        em.getTransaction().commit();
        return temp;
    }

    @Override
    public void edit(Item item) {
        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();
    }

    @Override
    public Item find(Long id) {
        return em.find(Item.class, id);
    }

    @Override
    public List<Item> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findByDescription(String description) {
        Query q = em.createNamedQuery("Item.findByDescr", Item.class);
        q.setParameter("descr", description);
        System.out.println(q.getResultList());
        try {
            List<Item> items = q.getResultList();
            return items;
        }
        catch (NoResultException e){
            return new ArrayList<Item>();
        }
    }

    @Override
    public void remove(Item item) {
        em.getTransaction().begin();
        em.remove(em.merge(item));
        em.getTransaction().commit();
    }
}
