package com.bertazoli.charity.server.loaddata;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.bertazoli.charity.server.businesslogic.BaseDAO;
import com.bertazoli.charity.shared.beans.Charity;
import com.bertazoli.charity.shared.beans.CharityAddress;
import com.bertazoli.charity.shared.beans.Country;
import com.bertazoli.charity.shared.beans.State;
import com.bertazoli.charity.shared.beans.enums.CharityCategory;
import com.bertazoli.charity.shared.beans.enums.CharityDesignationCode;
import com.bertazoli.charity.shared.beans.enums.CharitySanction;
import com.bertazoli.charity.shared.beans.enums.CharityStatus;

public class CharityData {
    public static void main(String[] args) {
        CharityData cd = new CharityData();
        cd.run();
    }
    
    public void run() {
        String csvFile = System.getProperty("user.dir") + "/database/data/Charities_results_2014-05-06-09-16-46.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";
        ArrayList<DummyCharity> charities = new ArrayList<CharityData.DummyCharity>();
        
        EntityManager em = BaseDAO.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        TypedQuery<Charity> q = em.createQuery("SELECT a FROM Charity a", Charity.class);
        q.setMaxResults(10);
        int total = q.getResultList().size();
        if (total > 0)
            return;

        try {
     
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "ISO-8859-1"));
            while ((line = br.readLine()) != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                DummyCharity charity = new DummyCharity();
                String[] brokenLine = line.split(cvsSplitBy);
                if (brokenLine.length < 12) {
                    continue;
                }
                charity.registrationNumber = brokenLine[0];
                charity.name = brokenLine[1];
                charity.status = CharityStatus.getFromString(brokenLine[2]);
                charity.effectiveDateOfStatus = sdf.parse(brokenLine[3]);
                charity.sanction = CharitySanction.getFromString(brokenLine[4]);
                charity.designationCode = CharityDesignationCode.getFromString(brokenLine[5]);
                charity.category = CharityCategory.getFromString(brokenLine[6]);
                DummyCharityAddress address = new DummyCharityAddress();
                address.address = brokenLine[7];
                address.city = brokenLine[8];
                address.province = brokenLine[9];
                address.country = brokenLine[10];
                address.postalCode = brokenLine[11];
                charity.address = address;
                charities.add(charity);
            }
     
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        List<Country> countries = null;
        List<State> states = null;
        
        TypedQuery<Country> query = em.createQuery("SELECT a FROM Country a", Country.class);
        countries = query.getResultList();
        
        TypedQuery<State> query1 = em.createQuery("SELECT a FROM State a", State.class);
        states = query1.getResultList();
        
        tx.begin();
        for (DummyCharity charity : charities) {
            Charity bean = new Charity();
            bean.setRegistrationNumber(charity.registrationNumber);
            bean.setName(charity.name);
            bean.setStatus(charity.status);
            bean.setSanction(charity.sanction);
            bean.setDesignationCode(charity.designationCode);
            if (charity.effectiveDateOfStatus != null)
            bean.setEffectiveDateOfStatus(new Timestamp(charity.effectiveDateOfStatus.getTime()));
            bean.setCategory(charity.category);

            em.persist(bean);
            
            CharityAddress address = new CharityAddress();
            address.setCharityId(bean.getId());
            address.setCountryId(getCountry(countries, charity.address.country));
            address.setStateId(getState(states, address.getCountryId(), charity.address.province));
            address.setStreet(charity.address.address);
            address.setCity(charity.address.city);
            address.setPostalCode(charity.address.postalCode);
            
            em.persist(address);
            em.flush();
            em.clear();
        }
        tx.commit();
        em.close();
    }

    private Long getState(List<State> states, Long countryId, String province) {
        for (State state : states) {
            if (state.getCountryId() == countryId && state.getCode().equalsIgnoreCase(province)) {
                return state.getId();
            }
        }
        return null;
    }

    private Long getCountry(List<Country> countries, String country) {
        for (Country info : countries) {
            if (info.getCode().equalsIgnoreCase(country)) {
                return info.getId();
            }
        }
        return null;
    }

    class DummyCharity {
        String registrationNumber;
        String name;
        CharityStatus status;
        CharitySanction sanction;
        CharityDesignationCode designationCode;
        CharityCategory category;
        Date effectiveDateOfStatus;
        DummyCharityAddress address;
    }
    
    class DummyCharityAddress {
        String address;
        String city;
        String province;
        String country;
        String postalCode;
    }
}
