
package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;


public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        if(request.getParameter("uuid") != null ){
            
            String uuid = request.getParameter("uuid");
            request.setAttribute("uuid", uuid);
            session.setAttribute("uuid", uuid);
            System.out.println(uuid);
            
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
            return;
        }
        
        
        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        AccountService as = new AccountService();
        User user = new User();
        
        String uuid = (String) session.getAttribute("uuid");
        
        if(request.getParameter("resetUUID") == null) {
 
            String resetEmail = request.getParameter("resetEmail");
            String url = request.getRequestURL().toString();
            String path = getServletContext().getRealPath("/WEB-INF");

            as.resetPassword(resetEmail, path, url);

            String message = "Please check the email provided. Thank you!";

            request.setAttribute("confirmation", message);

            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            return;
        } 
        
                   
          String password = request.getParameter("newPassword");
    
           if(as.changePassword(uuid, password) == true) {
               request.setAttribute("confirmation", "password update");
               
           } else{
               request.setAttribute("confirmation", "unable to update password");
           }

           getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);

    }

  
}
