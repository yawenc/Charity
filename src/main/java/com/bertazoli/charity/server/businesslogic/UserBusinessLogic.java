package com.bertazoli.charity.server.businesslogic;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.RandomStringUtils;

import com.bertazoli.charity.shared.beans.User;
import com.bertazoli.charity.shared.beans.UserToken;
import com.bertazoli.charity.shared.exceptions.ValidationException;
import com.bertazoli.charity.shared.util.Util;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserBusinessLogic extends BaseDAO<User> {

    @Inject
    public UserBusinessLogic() {

    }

    @Override
    public User create(User user) throws ValidationException {
        EntityManager em = createEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (!user.validate()) {
            throw new ValidationException("user.validation.error");
        }

        try {
            String salt = RandomStringUtils.randomAscii(50);
            String password = Util.getEncryptedPassword(user.getPassword(), salt);
            user.setPassword(password);
            user.setSalt(salt);
            user.setActivated(false);
            tx.begin();
            em.persist(user);
            tx.commit();
            
            UserToken userToken = new UserToken();
            userToken.setUserId(user.getId());
            userToken.setToken(RandomStringUtils.randomAlphanumeric(100));
            tx.begin();
            em.persist(userToken);
            tx.commit();

            user.setPassword(null);
            user.setSalt(null);
            user.setLoggedIn(false);
        } catch (EntityExistsException e) {
            tx.rollback();
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public User retrieve(User object) {
        EntityManager em = createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT a FROM User a WHERE id = :id", User.class);
            query.setParameter("id", object.getId());
            User user = query.setMaxResults(1).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }

    @Override
    public User update(User object) {
        EntityManager em = createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(object);
            tx.commit();
        } finally {
            em.close();
        }
        return object;
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
    
    public boolean activateUser(String token) {
        EntityManager em = createEntityManager();
        try {
            TypedQuery<UserToken> query = em.createQuery("SELECT a FROM UserToken a WHERE token = ?", UserToken.class);
            query.setParameter(1, token);
            UserToken result = query.setMaxResults(1).getSingleResult();
            if (result != null) {
                User user = new User();
                user.setId(result.getUserId());
                user = retrieve(user);
                if (user != null && !user.isActivated()) {
                    user.setActivated(true);
                    user.setActivatedOn(new Timestamp(new Date().getTime()));
                    update(user);    
                }
                return true;
            } else {
                return false;
            }
        } finally {
            em.close();
        }
    }
}
