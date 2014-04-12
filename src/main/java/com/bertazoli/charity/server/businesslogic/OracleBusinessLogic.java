package com.bertazoli.charity.server.businesslogic;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.bertazoli.charity.shared.beans.oracle.Country;
import com.bertazoli.charity.shared.beans.oracle.OracleListLoadResult;
import com.bertazoli.charity.shared.beans.oracle.OracleListLoadResultBean;
import com.bertazoli.charity.shared.beans.oracle.OracleLoadConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OracleBusinessLogic {

    @Inject
    public OracleBusinessLogic() {
    }

    public OracleListLoadResult search(OracleLoadConfig load) {
        EntityManager em = BaseDAO.createEntityManager();
        try {
            TypedQuery<Country> query = em.createQuery("SELECT a FROM Country a WHERE UPPER(name) LIKE ? OR UPPER(code) = ?", Country.class);
            query.setParameter(1, "%" + load.getQuery().toUpperCase() + "%");
            query.setParameter(2, load.getQuery().toUpperCase());
            ArrayList<Country> items = (ArrayList<Country>) query.getResultList();

            if (items != null) {
                // ListLoadResultBean<Country> data = new
                // ListLoadResultBean<Country>(items);

                OracleListLoadResult data = new OracleListLoadResultBean();
                data.setData(items);

                return data;
            }
        } finally {
            em.close();
        }
        return null;
    }
}
