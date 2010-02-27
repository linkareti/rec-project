package com.linkare.rec.am.web.servlet;

import com.linkare.rec.am.web.Credentials;
import com.linkare.rec.am.web.Login;
import java.io.*;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author Joao
 *
 * This Servlet class demonstrates Weld injection.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

//    static {
//        BasicConfigurator.configure();
//    }

    // Inject Weld Bean Manager.
    @Inject
    BeanManager m;
    // Inject The Credentials Weld bean.
    @Inject
    Credentials credentials;
    // Inject the Login Weld bean.
    @Inject
    Login login;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        credentials.setUsername(request.getParameter("txtUserName"));
        credentials.setPassword(request.getParameter("txtPassword"));

        login.login(request);

        if (login.isLoggedIn()) {
            response.sendRedirect(request.getContextPath() + "/faces/index.xhtml");
        } else {
            response.sendRedirect(request.getContextPath() + "/faces/LoginError.jsp");
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
    // </editor-fold>
}
