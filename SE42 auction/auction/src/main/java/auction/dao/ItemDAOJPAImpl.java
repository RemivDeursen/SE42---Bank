package auction.dao;

import auction.domain.Item;
import auction.domain.User;
import nl.fontys.util.DatabaseCleaner;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.List;

public class ItemDAOJPAImpl implements ItemDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    private EntityManager em;
    DatabaseCleaner cleaner = new DatabaseCleaner(em);

    public ItemDAOJPAImpl() {
    }

    @Override
    public int count() {
        return findAll().size();
    }

    @Override
    public void create(Item item) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void edit(Item item) {
        em = emf.createEntityManager();
        em.merge(item);
    }

    @Override
    public Item find(Long id) {
        em = emf.createEntityManager();
        return em.find(Item.class, id);
    }

    @Override
    public List<Item> findAll() {
        em = emf.createEntityManager();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findByDescription(String description) {
        em = emf.createEntityManager();
        Query q = em.createNamedQuery("Item.findByDescription", Item.class);
        q.setParameter("description", description);
        System.out.println(q.getResultList());
        try {
            List<Item> items = q.getResultList();
            return items;
        }
        catch (NoResultException e){
            return null;
        }
    }

    @Override
    public void remove(Item item) {
        em = emf.createEntityManager();
        em.remove(em.merge(item));
    }

    public void clean(){
        try {
            cleaner.clean();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
