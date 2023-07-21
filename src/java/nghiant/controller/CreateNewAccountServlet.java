/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiant.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nghiant.registration.RegistrationCreateError;
import nghiant.registration.RegistrationDAO;
import nghiant.registration.RegistrationDTO;

/**
 *
 * @author nghia
 */
@WebServlet(name = "CreateNewAccountServlet", urlPatterns = {"/CreateNewAccountServlet"})
public class CreateNewAccountServlet extends HttpServlet {

    private final String ERROS_PAGE = "createNewAccount.jsp";
    private final String LOGIN_PAGE = "login.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROS_PAGE;
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        boolean foundError = false;
        RegistrationCreateError errors = new RegistrationCreateError();

        try {
            //1. Check user'validation
            if (username.trim().length() < 6
                    || username.trim().length() > 20) {
                foundError = true;
                errors.setUsernameLengthError("Username is required input from 6 to 20 characters");
            }
            if (password.trim().length() < 6
                    || password.trim().length() > 20) {
                foundError = true;
                errors.setPasswordLengthError("Password is required input from 6 to 20 characters");
            } else if (!confirm.trim().equals(password.trim())) {
                foundError = true;
                errors.setConfirmNotMatched("Confirm must match password");
            }
            if (fullname.trim().length() < 2
                    || fullname.trim().length() > 50) {
                foundError = true;
                errors.setFullnameLengthError("Fullname is required input from 2 to 50 characters");
            }
            //2.process
            //2.1 if errors occur, system display errors and log errors
            //2.2 otherwise, call Model
            if (foundError) {
                request.setAttribute("CREATE_ERROR", errors);
            } else {//no error
                RegistrationDTO dto = new RegistrationDTO(username, password, fullname, false);
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.createAccount(dto);
                if (result) {
                    url = LOGIN_PAGE;
                }// create action is successful
            }
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("CreateNewAccountServlet _ SQL " + msg);
            if (msg.contains("duplicate")) {
                errors.setConfirmNotMatched(username + " is existed");
                request.setAttribute("CREATE_ERROR", errors);
            }
        } catch (NamingException ex) {
            log("CreateNewAccountServlet _ Naming " + ex.getMessage()); 
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
        /**
         * Handles the HTTP <code>GET</code> method.
         *
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Handles the HTTP <code>POST</code> method.
         *
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
