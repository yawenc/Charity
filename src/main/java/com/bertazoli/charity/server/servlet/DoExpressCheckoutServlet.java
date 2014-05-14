package com.bertazoli.charity.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bertazoli.charity.server.businesslogic.DonateBusinessLogic;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DoExpressCheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = -4770072273568471602L;
    private DonateBusinessLogic donateBL;

    @Inject
    public DoExpressCheckoutServlet(DonateBusinessLogic donateBL) {
        this.donateBL = donateBL;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String payerID = req.getParameter("PayerID");
        donateBL.doExpressCheckout(token, payerID, req, resp);
    }
}
