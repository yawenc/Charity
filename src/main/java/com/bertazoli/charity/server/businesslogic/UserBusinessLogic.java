package com.bertazoli.charity.server.businesslogic;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.bertazoli.charity.server.hibernate.HibernateUtil;
import com.bertazoli.charity.shared.beans.User;
import com.bertazoli.charity.shared.util.Util;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserBusinessLogic {

    @Inject
    public UserBusinessLogic() {

    }

    public User create(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            String salt = RandomStringUtils.randomAscii(50);
            String password = Util.getEncryptedPassword(user.getPassword(), salt);
            user.setPassword(password);
            user.setSalt(salt);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            user.setPassword(null);
            user.setSalt(null);
            user.setLoggedIn(true);
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }
        return user;
    }

    public User validateUser(String username, String password) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("username", username));
        User user = (User) criteria.uniqueResult();
        session.getTransaction().commit();
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
    }

    public User getUserByID(Long userID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        User user = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("id", userID));
            user = (User) criteria.uniqueResult();
            session.getTransaction().commit();
            user.setLoggedIn(true);
            user.setPassword(null);
            user.setSalt(null);
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }
        return user;
    }

    public boolean usernameExists(String username) {
        return false;
    }
}
