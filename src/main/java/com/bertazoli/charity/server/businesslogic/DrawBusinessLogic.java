package com.bertazoli.charity.server.businesslogic;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.bertazoli.charity.shared.action.admin.RunDrawResult;
import com.bertazoli.charity.shared.beans.Draw;
import com.bertazoli.charity.shared.beans.UserTicket;
import com.bertazoli.charity.shared.beans.enums.DrawStatus;


public class DrawBusinessLogic {
    
    public Draw getCurrentDraw() {
        EntityManager em = BaseDAO.createEntityManager();
        Draw draw = null;
        try {
            TypedQuery<Draw> query = em.createQuery("SELECT a FROM Draw a WHERE active IS TRUE AND status = :status", Draw.class);
            query.setParameter("status", DrawStatus.CURRENT);
            draw = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        return draw;
    }
    
    public void createDrawIfNotExists(Date date) {
        EntityManager em = BaseDAO.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Draw draw = getCurrentDraw();
            
            if (draw == null) {
                // create new draw
                Date startDate = null;
                Date endDate = null;
                Calendar cal = new GregorianCalendar();
                cal.setTime(date);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                startDate = cal.getTime();
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                endDate = cal.getTime();
                
                draw = new Draw();
                draw.setActive(true);
                draw.setDrawDateStart(new Timestamp(startDate.getTime()));
                draw.setDrawDateEnd(new Timestamp(endDate.getTime()));
                draw.setStatus(DrawStatus.CURRENT);
                
                tx.begin();
                em.persist(draw);
                tx.commit();
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public RunDrawResult runDraw() {
        Draw currentDraw = getCurrentDraw();
//        currentDraw.setStatus(DrawStatus.FINALIZED);
//        save(currentDraw);
//        createDrawIfNotExists(new Date());
        
        // get winner
        calculatePrize(currentDraw);
        
        return new RunDrawResult();
    }

    private void calculatePrize(Draw bean) {
        List<UserTicket> donations = null;
        EntityManager em = BaseDAO.createEntityManager();
        Query query = em.createNativeQuery("SELECT a.* FROM user_ticket a JOIN donation b ON (a.donationId = b.id) AND b.drawId = :drawId", UserTicket.class);
        query.setParameter("drawId", bean.getId());
        
        donations = query.getResultList();
        
        int quantity = donations.size();
        int selectedNumber = (int) (Math.random() * quantity);
        System.out.println(donations.get(selectedNumber).getTicketNumber());
    }

    private void save(Draw bean) {
        EntityManager em = BaseDAO.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            em.merge(bean);
            tx.commit();    
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
