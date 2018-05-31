package auction.service;

import java.util.*;

import auction.dao.UserDAOJPAImpl;
import auction.domain.User;
import auction.dao.UserDAOCollectionImpl;
import auction.dao.UserDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class RegistrationMgr {

    private UserDAO userDAO;

    public RegistrationMgr() {
        userDAO = new UserDAOJPAImpl();
    }

    /**
     * Registreert een gebruiker met het als parameter gegeven e-mailadres, mits
     * zo'n gebruiker nog niet bestaat.
     * @param email
     * @return Een Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres (nieuw aangemaakt of reeds bestaand). Als het e-mailadres
     * onjuist is ( het bevat geen '@'-teken) wordt null teruggegeven.
     */
    public User registerUser(String email) {
        if (!email.contains("@")) {
            return null;
        }
        User user = userDAO.findByEmail(email);
        if (user != null) {
            return user;
        }
        user = new User(email);
        userDAO.create(user);
        return user;
    }

    /**
     *
     * @param email een e-mailadres
     * @return Het Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres of null als zo'n User niet bestaat.
     */
    public User getUser(String email) {
        User user = userDAO.findByEmail(email);
        if (user != null){
            return user;
        }
        return null;
    }

    /**
     * @return Een iterator over alle geregistreerde gebruikers
     */
    public List<User> getUsers() {
        return userDAO.findAll();
    }
}