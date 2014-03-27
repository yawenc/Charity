package com.bertazoli.charity.server.businesslogic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class BaseDAO<T> {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager");

    public abstract T create(T object);
    public abstract T retrieve(T object);
    public abstract T update(T object);
    public abstract int delete(T object);

    public static EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
