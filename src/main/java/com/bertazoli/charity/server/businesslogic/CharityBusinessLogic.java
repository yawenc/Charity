package com.bertazoli.charity.server.businesslogic;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.bertazoli.charity.shared.action.CharitySearchResult;
import com.bertazoli.charity.shared.beans.Charity;
import com.bertazoli.charity.shared.exceptions.ValidationException;
import com.bertazoli.charity.shared.searchparams.CharitySearchParams;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.ActionException;

@Singleton
public class CharityBusinessLogic extends BaseDAO<Charity> {

    @Inject
    public CharityBusinessLogic() {

    }

    @Override
    public Charity create(Charity user) throws ValidationException, ActionException {
        return null;
    }

    @Override
    public Charity retrieve(Charity object) {
        return null;
    }

    @Override
    public Charity update(Charity object) {
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
    public int delete(Charity object) {
        return 0;
    }

    public CharitySearchResult search(CharitySearchParams searchParams) {
        EntityManager em = createEntityManager();
        try {
            ArrayList<Object> params = new ArrayList<Object>();
            StringBuilder sb = new StringBuilder("SELECT a FROM Charity a ");
            if (searchParams.getCharityId() != null && searchParams.getCharityId() != 0) {
                sb.append(" WHERE id = ?");
                params.add(searchParams.getCharityId());
            }
            
            TypedQuery<Charity> query = buildQuery(sb.toString(), params, Charity.class);
            if (searchParams.getLimit() != 0) {
                query.setMaxResults(searchParams.getLimit());
            }
            
            ArrayList<Charity> charities = (ArrayList<Charity>) query.getResultList();
            return new CharitySearchResult(charities);
        } finally {
            em.close();
        }
    }
}
