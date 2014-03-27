package com.bertazoli.charity.server.businesslogic;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.RandomStringUtils;

import com.bertazoli.charity.shared.beans.User;
import com.bertazoli.charity.shared.util.Util;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserBusinessLogic extends BaseDAO<User> {

    @Inject
    public UserBusinessLogic() {

    }

    @Override
    public User create(User user) {
        EntityManager em = createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            String salt = RandomStringUtils.randomAscii(50);
            String password = Util.getEncryptedPassword(user.getPassword(), salt);
            user.setPassword(password);
            user.setSalt(salt);
            tx.begin();
            em.persist(user);
            tx.commit();
            user.setPassword(null);
            user.setSalt(null);
            user.setLoggedIn(true);
        } catch (EntityExistsException e) {
            tx.rollback();
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public User retrieve(User object) {
        return null;
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public int delete(User object) {
        return 0;
    }

    public User validateUser(String username, String password) {
        EntityManager em = createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT a FROM User a WHERE username = ?", User.class);
            query.setParameter(1, username);
            User user = query.setMaxResults(1).getSingleResult();
            if (user != null) {
                if (user.getPassword().equals(Util.getEncryptedPassword(password, user.getSalt()))) {
                    user.setLoggedIn(true);
                    user.setPassword(null);
                    user.setSalt(null);
                    return user;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }
}
