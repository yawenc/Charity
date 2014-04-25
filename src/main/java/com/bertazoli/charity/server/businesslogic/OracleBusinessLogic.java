package com.bertazoli.charity.server.businesslogic;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.bertazoli.charity.client.application.oracle.IsOracleData;
import com.bertazoli.charity.server.oracle.DataTypeConfig;
import com.bertazoli.charity.server.oracle.definition.OracleDefinition;
import com.bertazoli.charity.shared.beans.oracle.OracleListLoadResultBean;
import com.bertazoli.charity.shared.beans.oracle.OracleLoadConfigBean;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OracleBusinessLogic {

    @Inject DataTypeConfig dataTypeConfig;
    
    @Inject
    public OracleBusinessLogic() {
    }

    public OracleListLoadResultBean search(OracleLoadConfigBean load) {
        EntityManager em = BaseDAO.createEntityManager();
        try {
            OracleDefinition definition = dataTypeConfig.getDefinition(load.getType());
            definition.setFilter(load.getFilter());
            
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ");
            sb.append(definition.getSelect());
            sb.append(" FROM ");
            sb.append(definition.getTable());
            sb.append(" " + definition.getWhere(load.getQuery() + " "));
            
            Query query = em.createQuery(sb.toString());
            ArrayList<IsOracleData> items = (ArrayList<IsOracleData>) query.getResultList();

            if (items != null) {
                OracleListLoadResultBean data = new OracleListLoadResultBean();
                data.setData(items);

                return data;
            }
        } finally {
            em.close();
        }
        return null;
    }
}
