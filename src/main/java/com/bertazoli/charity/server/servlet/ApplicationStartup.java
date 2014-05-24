package com.bertazoli.charity.server.servlet;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.bertazoli.charity.server.businesslogic.DrawBusinessLogic;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ApplicationStartup extends HttpServlet {
    private static final long serialVersionUID = 6428179893669407495L;
    private DrawBusinessLogic drawBL;
    
    @Inject
    public ApplicationStartup(DrawBusinessLogic drawBL) {
        this.drawBL = drawBL;
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        // check current draw here
        drawBL.createDrawIfNotExists(new Date());
    }
}
