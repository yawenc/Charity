package com.bertazoli.charity.server.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.bertazoli.charity.server.businesslogic.DonateBusinessLogic;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ApplicationStartup extends HttpServlet {
    private static final long serialVersionUID = 6428179893669407495L;
    private DonateBusinessLogic donateBL;
    
    @Inject
    public ApplicationStartup(DonateBusinessLogic donateBL) {
        this.donateBL = donateBL;
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        // check current draw here
        donateBL.createDrawIfNotExists();
//        LoadData ld = new LoadData();
//        ld.run();
    }
}
