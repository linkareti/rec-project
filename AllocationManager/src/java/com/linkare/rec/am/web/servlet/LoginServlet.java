package com.linkare.rec.am.web.servlet;

import com.linkare.rec.am.web.Credentials;
import com.linkare.rec.am.web.Login;
import java.io.*;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author Joao
 *
 * This Servlet class demonstrates Weld injection.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    // Inject Weld Bean Manager.
    @Inject
    private BeanManager m;
    
    // Inject The Credentials Weld bean.
    @Inject
    private Credentials credentials;

    // Inject the Login Weld bean.
    @Inject
    private Login login;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        if (request.getParameter("action").equals("logout")) {

            getLogin().logout(request);
            response.sendRedirect("Logout.jsp");
        
        } else if (request.getParameter("action").equals("login")) {

            getCredentials().setUsername(request.getParameter("txtUserName"));
            getCredentials().setPassword(request.getParameter("txtPassword"));


            getLogin().login(request);

            if (getLogin().isLoggedIn()) {
                request.getSession().setAttribute("UserName", getCredentials().getUsername());
                response.sendRedirect(request.getContextPath() + "/faces/index.xhtml");
            } else {
                response.sendRedirect(request.getContextPath() + "/faces/LoginError.jsp");
            }
        }


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    /**
     * @return the m
     */
    public BeanManager getM() {
        return m;
    }

    /**
     * @return the credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * @return the login
     */
    public Login getLogin() {
        return login;
    }
    // </editor-fold>
}
