package com.bertazoli.charity.server.businesslogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
import com.bertazoli.charity.shared.beans.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.ActionException;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

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

    public String setExpressChecktout(Donation donation) throws ActionException {
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
        
        StringBuffer url = new StringBuffer();
        url.append(request.getScheme()+"://");
        url.append(request.getServerName());
        url.append(":");
        url.append(request.getServerPort());
        String returnURL = url.toString() + "/doExpressCheckout";
        details.setReturnURL(returnURL);
        
        url.append("/Charity.html");
        
        if (configurationMap.containsKey("mode") && configurationMap.get("mode").equalsIgnoreCase("sandbox")) {
            url.append("?gwt.codesvr=127.0.0.1:9997");
        }
        
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
                session.setAttribute("lastReq", service.getLastRequest());
                session.setAttribute("lastResp", service.getLastResponse());
                session.setAttribute("donation", donation);
                if (setExpressCheckoutResponse.getAck().toString()
                        .equalsIgnoreCase("SUCCESS")) {
                    Map<Object, Object> map = new LinkedHashMap<Object, Object>();
                    map.put("Ack", setExpressCheckoutResponse.getAck());
                    map.put("Token", setExpressCheckoutResponse.getToken());
                    map.put("Redirect URL", "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken());
                    session.setAttribute("map", map);
                    return "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken();
                } else {
                    session.setAttribute("Error", setExpressCheckoutResponse.getErrors());
                    return url.toString()+"/#error";
                }
            }
        } catch (SSLConfigurationException e) {
            e.printStackTrace();
        } catch (InvalidCredentialException e) {
            e.printStackTrace();
        } catch (HttpErrorException e) {
            e.printStackTrace();
        } catch (InvalidResponseDataException e) {
            e.printStackTrace();
        } catch (ClientActionRequiredException e) {
            e.printStackTrace();
        } catch (MissingCredentialException e) {
            e.printStackTrace();
        } catch (OAuthException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String doExpressCheckout(Donation donation, String token, String payerID, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> configurationMap = getConfigurationMap();
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
        
        HttpSession session = req.getSession();
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
        
        if (doCheckoutPaymentResponseType != null) {
            session.setAttribute("lastReq", service.getLastRequest());
            session.setAttribute("lastResp", service.getLastResponse());
            if (doCheckoutPaymentResponseType.getAck().toString().equalsIgnoreCase("SUCCESS")) {
                Map<Object, Object> map = new LinkedHashMap<Object, Object>();
                map.put("Ack", doCheckoutPaymentResponseType.getAck());
                Iterator<PaymentInfoType> iterator = doCheckoutPaymentResponseType.getDoExpressCheckoutPaymentResponseDetails().getPaymentInfo().iterator();
                int index = 1;
                /*
                 * Unique transaction ID of the payment.
                 Note:
                    If the PaymentAction of the request was Authorization or Order, 
                    this value is your AuthorizationID for use with the Authorization 
                    & Capture APIs.
                    Character length and limitations: 19 single-byte characters
                 */
                while (iterator.hasNext()) {
                    PaymentInfoType result = (PaymentInfoType) iterator.next();
                    map.put("Transaction ID" + index, result.getTransactionID());
                    index++;
                }
                session.setAttribute("transactionId", doCheckoutPaymentResponseType.getDoExpressCheckoutPaymentResponseDetails().getPaymentInfo().get(0).getTransactionID());
                session.setAttribute("map", map);
                try {
                    StringBuffer url = new StringBuffer();
                    url.append(req.getScheme()+"://");
                    url.append(req.getServerName());
                    url.append(":");
                    url.append(req.getServerPort());
                    url.append("/Charity.html");
                    if (configurationMap.containsKey("mode") && configurationMap.get("mode").equalsIgnoreCase("sandbox")) {
                        url.append("?gwt.codesvr=127.0.0.1:9997");
                    }
                    resp.sendRedirect(url.toString()+"#home");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                session.setAttribute("Error", doCheckoutPaymentResponseType.getErrors());
            }
        }
        return null;
    }
}
