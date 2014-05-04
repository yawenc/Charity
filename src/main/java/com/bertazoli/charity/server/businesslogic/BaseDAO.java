package com.bertazoli.charity.server.businesslogic;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.bertazoli.charity.shared.exceptions.ValidationException;
import com.gwtplatform.dispatch.shared.ActionException;

public abstract class BaseDAO<T> {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager");

    public abstract T create(T object) throws ValidationException, ActionException;
    public abstract T retrieve(T object);
    public abstract T update(T object);
    public abstract int delete(T object);

    public static EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    
    protected TypedQuery<T> buildQuery(String string, ArrayList<Object> params, Class<T> resultClass) {
        String result = new String(string);
        for (int i=0; i<params.size(); i++) {
            result = string.replaceFirst("\\?", "?"+i);
        }
        
        TypedQuery<T> query = createEntityManager().createQuery(result, resultClass);
        for (int i=0; i<params.size(); i++) {
            query.setParameter(i, params.get(i));
        }
        return query;
    }
}
