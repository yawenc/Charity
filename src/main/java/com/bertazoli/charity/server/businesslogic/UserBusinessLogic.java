package com.bertazoli.charity.server.businesslogic;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

import com.bertazoli.charity.shared.beans.User;
import com.bertazoli.charity.shared.beans.UserToken;
import com.bertazoli.charity.shared.exceptions.ValidationException;
import com.bertazoli.charity.shared.util.Util;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.ActionException;

@Singleton
public class UserBusinessLogic extends BaseDAO<User> {
    @Inject Provider<HttpServletRequest> requestProvider;

    @Inject
    public UserBusinessLogic() {

    }

    @Override
    public User create(User user) throws ValidationException, ActionException {
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
            user.setCreatedOn(new Timestamp(new Date().getTime()));
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
        } catch (PersistenceException e) {
            tx.rollback();
            throw new ActionException("user.create.alreadyexists");
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
                if (user.getPassword().equals(Util.getEncryptedPassword(password, user.getSalt())) && user.isActive() && user.isActivated()) {
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
                    user.setActive(true);
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

    /**
     * Search if the given username exists
     * @param username
     * @return true if username is found, false otherwise
     */
    public Boolean searchUsername(String username) {
        EntityManager em = createEntityManager();
        try {
            Query query = em.createQuery("SELECT a.username FROM User a WHERE username = :username");
            query.setParameter("username", username);
            String result = (String) query.getSingleResult();
            if (result != null) {
                return true;
            } else {
                return false;
            }
        } catch (NoResultException e ) {
            return false;
        } finally {
            em.close();
        }
    }

    public User fetchFromSession(String sessionID) {
        HttpServletRequest request = requestProvider.get();
        HttpSession session = request.getSession();
        if (session.getId().equals(sessionID)) {
            Long userID = (Long) session.getAttribute("user.id");
            User user = new User();
            user.setId(userID);
            user = retrieve(user);
            user.setLoggedIn(true);
            user.setPassword(null);
            user.setSalt(null);
            return user;
        }
        return null;
    }
}
