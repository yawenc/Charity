package com.bertazoli.charity.server.businesslogic;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ItemCategoryType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentInfoType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

import com.bertazoli.charity.shared.beans.Donation;
import com.bertazoli.charity.shared.beans.DonationInformation;
import com.bertazoli.charity.shared.beans.Draw;
import com.bertazoli.charity.shared.beans.User;
import com.bertazoli.charity.shared.beans.enums.DrawStatus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.ActionException;

@Singleton
public class DonateBusinessLogic {
    @Inject Provider<HttpServletRequest> requestProvider;
    @Inject UserBusinessLogic userBL;

    @Inject
    public DonateBusinessLogic() {

    }
    
    private Map<String, String> getConfigurationMap() {
        Map<String,String> configurationMap = new HashMap<String,String>();
        configurationMap.put("acct1.UserName", "merchant_api1.bertazoli.com");
        configurationMap.put("acct1.Password", "1399164032");
        configurationMap.put("acct1.Signature", "AFcWxV21C7fd0v3bYYYRCpSSRl31AHFA8olLbg.H1MewmnulQI17YE5y");
        configurationMap.put("mode", "sandbox");
        return configurationMap;
    }

    public String setExpressChecktout(DonationInformation donation) throws ActionException {
        HttpServletRequest request = requestProvider.get();
        HttpSession session = request.getSession();
        User user = new User();
        Long userId = (Long) (session.getAttribute("user.id"));
        user.setId(userId);
        user = userBL.retrieve(user);
        
        Map<String,String> configurationMap = getConfigurationMap();
        
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
        SetExpressCheckoutRequestType setExpressCheckoutReq = new SetExpressCheckoutRequestType();
        SetExpressCheckoutRequestDetailsType details = new SetExpressCheckoutRequestDetailsType();
        
        boolean isSandboxMode = configurationMap.containsKey("mode") && configurationMap.get("mode").equalsIgnoreCase("sandbox") && request.getServerName().contains("127.0.0.1");
        StringBuffer url = new StringBuffer();
        url.append(request.getScheme()+"://");
        url.append(request.getServerName());
        
        if (isSandboxMode) {
            url.append(":");
            url.append(request.getServerPort());
        }
        String returnURL = url.toString() + "/doExpressCheckout";
        url.append("/Charity.html");
        if (isSandboxMode) {
            url.append("?gwt.codesvr=127.0.0.1:9998");
        }
        
        details.setReturnURL(returnURL);
        String cancelURL = url.toString() + "/#home";
        details.setCancelURL(cancelURL);
        details.setBuyerEmail(user.getEmail());
        List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();
        PaymentDetailsItemType item = new PaymentDetailsItemType();
        BasicAmountType amt = new BasicAmountType();
        amt.setCurrencyID(CurrencyCodeType.CAD);
        amt.setValue(String.valueOf(donation.getAmountToDonate()));
        item.setQuantity(1);
        item.setName("Donation");
        item.setAmount(amt);
        item.setItemCategory(ItemCategoryType.DIGITAL);
        item.setDescription("Donation to E-Charity");
        lineItems.add(item);
        List<PaymentDetailsType> payDetails = new ArrayList<PaymentDetailsType>();
        PaymentDetailsType paydtl = new PaymentDetailsType();
        paydtl.setPaymentAction(PaymentActionCodeType.SALE);
        paydtl.setOrderDescription("Order description");
        BasicAmountType itemsTotal = new BasicAmountType();
        itemsTotal.setValue(String.valueOf(donation.getAmountToDonate()));
        paydtl.setOrderTotal(new BasicAmountType(CurrencyCodeType.CAD, String.valueOf(donation.getAmountToDonate())));
        paydtl.setPaymentDetailsItem(lineItems);
        paydtl.setItemTotal(itemsTotal);
        payDetails.add(paydtl);
        details.setPaymentDetails(payDetails);
        details.setNoShipping("1");
        details.setBrandName("E-Charity");
        setExpressCheckoutReq.setSetExpressCheckoutRequestDetails(details);
        SetExpressCheckoutReq expressCheckoutReq = new SetExpressCheckoutReq();
        expressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutReq);

        try {
            SetExpressCheckoutResponseType setExpressCheckoutResponse = service.setExpressCheckout(expressCheckoutReq);
            if (setExpressCheckoutResponse != null) {
                if (setExpressCheckoutResponse.getAck().toString().equalsIgnoreCase("SUCCESS")) {
                    session.setAttribute("donation", donation);
                    return "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken();
                } else {
                    return url.toString()+"/#error";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String doExpressCheckout(DonationInformation donation, String token, String payerID, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> configurationMap = getConfigurationMap();
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
        
        HttpSession session = req.getSession();
        User user = new User();
        Long userId = (Long) (session.getAttribute("user.id"));
        user.setId(userId);
        user = userBL.retrieve(user);
        DoExpressCheckoutPaymentRequestType doCheckoutPaymentRequestType = new DoExpressCheckoutPaymentRequestType();
        DoExpressCheckoutPaymentRequestDetailsType details = new DoExpressCheckoutPaymentRequestDetailsType();

        details.setToken(token);
        details.setPayerID(payerID);
        details.setPaymentAction(PaymentActionCodeType.SALE);

        PaymentDetailsType paymentDetails = new PaymentDetailsType();
        BasicAmountType orderTotal = new BasicAmountType();
        orderTotal.setValue(String.valueOf(donation.getAmountToDonate()));
        orderTotal.setCurrencyID(CurrencyCodeType.CAD);
        paymentDetails.setOrderTotal(orderTotal);

        BasicAmountType itemTotal = new BasicAmountType();
        itemTotal.setValue(String.valueOf(donation.getAmountToDonate()));
        itemTotal.setCurrencyID(CurrencyCodeType.CAD);
        paymentDetails.setItemTotal(itemTotal);

        List<PaymentDetailsItemType> paymentItems = new ArrayList<PaymentDetailsItemType>();
        PaymentDetailsItemType paymentItem = new PaymentDetailsItemType();
        paymentItem.setName("Donation");
        paymentItem.setQuantity(1);
        BasicAmountType amount = new BasicAmountType();
        amount.setValue(String.valueOf(donation.getAmountToDonate()));
        amount.setCurrencyID(CurrencyCodeType.CAD);
        paymentItem.setAmount(amount);
        paymentItems.add(paymentItem);
        paymentDetails.setPaymentDetailsItem(paymentItems);
        
        List<PaymentDetailsType> payDetailType = new ArrayList<PaymentDetailsType>();
        payDetailType.add(paymentDetails);
        details.setPaymentDetails(payDetailType);

        doCheckoutPaymentRequestType.setDoExpressCheckoutPaymentRequestDetails(details);
        DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
        doExpressCheckoutPaymentReq.setDoExpressCheckoutPaymentRequest(doCheckoutPaymentRequestType);
        DoExpressCheckoutPaymentResponseType doCheckoutPaymentResponseType = null;
        try{
            doCheckoutPaymentResponseType = service.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        boolean isSandboxMode = configurationMap.containsKey("mode") && configurationMap.get("mode").equalsIgnoreCase("sandbox") && req.getServerName().contains("127.0.0.1");
        StringBuffer url = new StringBuffer();
        url.append(req.getScheme()+"://");
        url.append(req.getServerName());
        
        if (isSandboxMode) {
            url.append(":");
            url.append(req.getServerPort());
        }
        
        url.append("/Charity.html");
        if (isSandboxMode) {
            url.append("?gwt.codesvr=127.0.0.1:9998");
        }
        String returnURL = url.toString() + "/#home";
        String transactionError = url.toString() + "/#transactionError";
        
        if (doCheckoutPaymentResponseType != null) {
            try {
                if (doCheckoutPaymentResponseType.getAck().toString().equalsIgnoreCase("SUCCESS")) {
                    Map<Object, Object> map = new LinkedHashMap<Object, Object>();
                    map.put("Ack", doCheckoutPaymentResponseType.getAck());
                    Iterator<PaymentInfoType> iterator = doCheckoutPaymentResponseType.getDoExpressCheckoutPaymentResponseDetails().getPaymentInfo().iterator();
                    while (iterator.hasNext()) {
                        // there should be only one payment for now
                        PaymentInfoType result = (PaymentInfoType) iterator.next();
                        Donation bean = new Donation();
                        EntityManager em = BaseDAO.createEntityManager();
                        EntityTransaction tx = em.getTransaction();
                        try {
                            XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(result.getPaymentDate());
                            Calendar c2 = cal.toGregorianCalendar();
                            bean.setDonationDate(new Timestamp(c2.getTime().getTime()));
                            bean.setUserId(user.getId());
                            bean.setDrawId(getCurrentDrawId());
                            bean.setTransaction(result.getTransactionID());
                            bean.setFeeAmountCurrency(result.getFeeAmount().getCurrencyID());
                            bean.setFeeAmountValue(Double.parseDouble(result.getFeeAmount().getValue()));
                            bean.setGrossAmountCurrency(result.getGrossAmount().getCurrencyID());
                            bean.setGrossAmountValue(Double.parseDouble(result.getGrossAmount().getValue()));
                            bean.setPaymentStatus(result.getPaymentStatus());
                            bean.setPaymentType(result.getPaymentType());
                            
                            tx.begin();
                            em.persist(bean);
                            tx.commit();
                            
                        } catch (DatatypeConfigurationException e) {
                            e.printStackTrace();
                        } finally {
                            em.close();
                        }
                    }
                    resp.sendRedirect(returnURL);
                } else {
                    resp.sendRedirect(transactionError);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Long getCurrentDrawId() {
        EntityManager em = BaseDAO.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Draw draw = null;
        try {
            TypedQuery<Draw> query = em.createQuery("SELECT a FROM Draw a WHERE drawDateStart <= :dateStart AND drawDateEnd >= :dateEnd AND active IS TRUE AND status = :status", Draw.class);
            Date now = new Date();
            query.setParameter("dateStart", now);
            query.setParameter("dateEnd", now);
            query.setParameter("status", DrawStatus.CURRENT);
            try {
                draw = query.getSingleResult();
            } catch (NoResultException e) {
                throw new PersistenceException("draw invalid");
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        if (draw != null) {
            return draw.getId();
        }
        return null;
    }

    public void createDrawIfNotExists() {
        EntityManager em = BaseDAO.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            TypedQuery<Draw> query = em.createQuery("SELECT a FROM Draw a WHERE drawDateStart <= :dateStart AND drawDateEnd >= :dateEnd AND active IS TRUE", Draw.class);
            Date now = new Date();
            query.setParameter("dateStart", now);
            query.setParameter("dateEnd", now);
            Draw draw = null;
            try {
                draw = query.getSingleResult();
            } catch (NoResultException e) {
                
            }
            
            if (draw == null) {
                // create new draw
                Date startDate = null;
                Date endDate = null;
                Calendar cal = new GregorianCalendar();
                cal.setTime(now);
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

    public Double fetchTotalDonations() {
        Long currentDrawId = getCurrentDrawId();
        EntityManager em = BaseDAO.createEntityManager();
        Query query = em.createNativeQuery("select sum(grossAmountValue) from donation where drawId = :drawId");
        query.setParameter("drawId", currentDrawId);
        try {
            BigDecimal total = (BigDecimal) query.getSingleResult();
            total = total.multiply(new BigDecimal(0.9)); // subtracts 10%
            return total.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
}
