package com.bertazoli.charity.server.businesslogic;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.Session;

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

import com.bertazoli.charity.shared.Constants;
import com.bertazoli.charity.shared.beans.Donation;
import com.bertazoli.charity.shared.beans.DonationInformation;
import com.bertazoli.charity.shared.beans.User;
import com.bertazoli.charity.shared.beans.UserDonation;
import com.bertazoli.charity.shared.beans.UserTicket;
import com.bertazoli.charity.shared.util.Util;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.ActionException;

@Singleton
public class DonateBusinessLogic {
    @Inject Provider<HttpServletRequest> requestProvider;
    @Inject UserBusinessLogic userBL;
    @Inject DrawBusinessLogic drawBL;

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
        url.append(":"+request.getServerPort());
        
        if (!Util.isNullOrEmpty(request.getContextPath())) {
            url.append(request.getContextPath());
        }
        
        String returnURL = url.toString() + "/doExpressCheckout";
        url.append("/Charity.html");
        if (isSandboxMode) {
            url.append("?gwt.codesvr=127.0.0.1:9998");
        }
        
        details.setReturnURL(returnURL);
        String cancelURL = url.toString() + "#home";
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
        item.setDescription("Donation to E-Charity"); // TODO
        lineItems.add(item);
        List<PaymentDetailsType> payDetails = new ArrayList<PaymentDetailsType>();
        PaymentDetailsType paydtl = new PaymentDetailsType();
        paydtl.setPaymentAction(PaymentActionCodeType.SALE);
        paydtl.setOrderDescription("Order description"); // TODO
        BasicAmountType itemsTotal = new BasicAmountType();
        itemsTotal.setValue(String.valueOf(donation.getAmountToDonate()));
        paydtl.setOrderTotal(new BasicAmountType(CurrencyCodeType.CAD, String.valueOf(donation.getAmountToDonate())));
        paydtl.setPaymentDetailsItem(lineItems);
        paydtl.setItemTotal(itemsTotal);
        payDetails.add(paydtl);
        details.setPaymentDetails(payDetails);
        details.setNoShipping("1");
        details.setBrandName("E-Charity"); // TODO
        setExpressCheckoutReq.setSetExpressCheckoutRequestDetails(details);
        SetExpressCheckoutReq expressCheckoutReq = new SetExpressCheckoutReq();
        expressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutReq);

        try {
            SetExpressCheckoutResponseType setExpressCheckoutResponse = service.setExpressCheckout(expressCheckoutReq);
            if (setExpressCheckoutResponse != null) {
                Long drawId = drawBL.getCurrentDraw().getId();
                donation.setPaypalToken(setExpressCheckoutResponse.getToken());
                donation.setDrawId(drawId);
                if (setExpressCheckoutResponse.getAck().toString().equalsIgnoreCase("SUCCESS")) {
                    Donation bean = new Donation();
                    EntityManager em = BaseDAO.createEntityManager();
                    EntityTransaction tx = em.getTransaction();
                    try {
                        bean.setUserId(user.getId());
                        bean.setDrawId(drawId);
                        bean.setCharityId(donation.getCharityId());
                        bean.setTransaction("Not receiveid yet");
                        bean.setDonationDate(new Timestamp(new Date().getTime())); // Temporary date
                        bean.setPaypalToken(setExpressCheckoutResponse.getToken());
                        tx.begin();
                        em.persist(donation);
                        em.persist(bean);
                        tx.commit();
                    } finally {
                        em.close();
                    }
                    return "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken();
                } else {
                    return url.toString()+"#error";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void doExpressCheckout(String token, String payerID, HttpServletRequest request, HttpServletResponse resp) {
        Map<String, String> configurationMap = getConfigurationMap();
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
        
        DonationInformation donation = getDonationInfoFromToken(token);
        User user = new User();
        Long userId = donation.getUserId();
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
        paymentItem.setName("Donation"); // TODO
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
        
        boolean isSandboxMode = configurationMap.containsKey("mode") && configurationMap.get("mode").equalsIgnoreCase("sandbox") && request.getServerName().contains("127.0.0.1");
        StringBuffer url = new StringBuffer();
        url.append(request.getScheme()+"://");
        url.append(request.getServerName());
        url.append(":"+request.getLocalPort());
        
        if (!Util.isNullOrEmpty(request.getContextPath())) {
            url.append(request.getContextPath());
        }
        
        url.append("/Charity.html");
        if (isSandboxMode) {
            url.append("?gwt.codesvr=127.0.0.1:9998");
        }
        
        String returnURL = url.toString() + "#home";
        String transactionError = url.toString() + "#transactionError";
        
        if (doCheckoutPaymentResponseType != null) {
            try {
                if (doCheckoutPaymentResponseType.getAck().toString().equalsIgnoreCase("SUCCESS")) {
                    Map<Object, Object> map = new LinkedHashMap<Object, Object>();
                    map.put("Ack", doCheckoutPaymentResponseType.getAck());
                    Iterator<PaymentInfoType> iterator = doCheckoutPaymentResponseType.getDoExpressCheckoutPaymentResponseDetails().getPaymentInfo().iterator();
                    while (iterator.hasNext()) {
                        // there should be only one payment for now
                        PaymentInfoType result = (PaymentInfoType) iterator.next();
                        Donation bean = getDonationFromToken(token);
                        EntityManager em = BaseDAO.createEntityManager();
                        EntityTransaction tx = em.getTransaction();
                        try {
                            XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(result.getPaymentDate());
                            Calendar c2 = cal.toGregorianCalendar();
                            bean.setDonationDate(new Timestamp(c2.getTime().getTime()));
                            bean.setTransaction(result.getTransactionID());
                            bean.setPercentageToKeep(donation.getPercentageToKeep());
                            bean.setFeeAmountCurrency(result.getFeeAmount().getCurrencyID());
                            bean.setFeeAmountValue(Double.parseDouble(result.getFeeAmount().getValue()));
                            bean.setGrossAmountCurrency(result.getGrossAmount().getCurrencyID());
                            bean.setGrossAmountValue(Double.parseDouble(result.getGrossAmount().getValue()));
                            bean.setPaymentStatus(result.getPaymentStatus());
                            bean.setPaymentType(result.getPaymentType());
                            bean.setCompleted(true);
                            
                            tx.begin();
                            em.merge(bean);
                            tx.commit();
                            
                            createUserTickets(bean);
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
    }

    private Donation getDonationFromToken(String token) {
        EntityManager em = BaseDAO.createEntityManager();
        try {
            TypedQuery<Donation> query = em.createQuery("SELECT a FROM Donation a WHERE paypalToken = :paypalToken", Donation.class);
            query.setParameter("paypalToken", token);
            Donation donation = query.setMaxResults(1).getSingleResult();
            return donation;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    private DonationInformation getDonationInfoFromToken(String token) {
        EntityManager em = BaseDAO.createEntityManager();
        try {
            TypedQuery<DonationInformation> query = em.createQuery("SELECT a FROM DonationInformation a WHERE paypalToken = :paypalToken", DonationInformation.class);
            query.setParameter("paypalToken", token);
            DonationInformation donationInfo = query.setMaxResults(1).getSingleResult();
            return donationInfo;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    private void createUserTickets(Donation bean) {
        ArrayList<UserTicket> tickets = new ArrayList<UserTicket>();
        for (int x=0; x<bean.getGrossAmountValue();) {
            x=x+5; // for every five dollars we generate 1 ticket number
            UserTicket ticket = new UserTicket();
            ticket.setDonationId(bean.getId());
            ticket.setTicketNumber(createMD5(x+bean.getId()+bean.getCharityId()+bean.getDrawId()+bean.getUserId()+bean.getTransaction()+new Timestamp(new Date().getTime())));
            tickets.add(ticket);
        }
        
        EntityManager em = BaseDAO.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (UserTicket ticket : tickets) {
            em.persist(ticket);
            em.flush();
            em.clear();
        }
        tx.commit();
        em.close();
        
        userBL.sendConfirmationToUser(bean, tickets);
    }

    private String createMD5(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(Constants.ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(string.getBytes());
        byte[] mdbytes = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public Double fetchTotalDonations() {
        Long currentDrawId = drawBL.getCurrentDraw().getId();
        EntityManager em = BaseDAO.createEntityManager();
        Query query = em.createNativeQuery("select sum(grossAmountValue) from donation where drawId = :drawId AND completed IS TRUE");
        query.setParameter("drawId", currentDrawId);
        try {
            BigDecimal total = (BigDecimal) query.getSingleResult();
            total = total.multiply(new BigDecimal(0.93)); // subtracts 7%
            return total.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public ArrayList<UserDonation> searchMyDonations() {
        Long userId = (Long) requestProvider.get().getSession().getAttribute("user.id");
        EntityManager em = BaseDAO.createEntityManager();
        Session session = em.unwrap(Session.class);
        org.hibernate.Query q = session.createQuery("SELECT d.id, d.userId, d.drawId, d.charityId, d.donationDate, d.grossAmountValue, c.name as charityName FROM donation d JOIN charity c ON (d.charityId = c.id) WHERE d.completed IS TRUE and d.userId = :userId");
        
        Query query = em.createNativeQuery("SELECT d.id, d.userId, d.drawId, d.charityId, d.donationDate, d.grossAmountValue, c.name as charityName FROM donation d JOIN charity c ON (d.charityId = c.id) WHERE d.completed IS TRUE and d.userId = :userId");
        
        query.setParameter("userId", userId);
        ArrayList<UserDonation> donations = (ArrayList<UserDonation>) query.getResultList();
        
        return donations;
    }
}
