package com.bertazoli.charity.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bertazoli.charity.server.businesslogic.UserBusinessLogic;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.core.client.util.Util;

@Singleton
public class ActivateUserServlet extends HttpServlet {
    private static final long serialVersionUID = -1878139859360979388L;
    private UserBusinessLogic userBL;
    
    @Inject
    public ActivateUserServlet(UserBusinessLogic userBL) {
        this.userBL = userBL;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if (!Util.isEmptyString(token)) {
            // try to activate user
            if (userBL.activateUser(token)) {
                resp.sendRedirect("http://bertazoli.com/Charity/#login");
            } else {
                resp.sendRedirect("http://bertazoli.com/Charity/#error");
            }
        }
    }

}
