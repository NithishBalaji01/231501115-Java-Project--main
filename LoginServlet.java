package equipment_managment;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Use Login.java for backend logic
        String role = Login.loginUser(username, password);

        if (role != null) {
            if ("member".equalsIgnoreCase(role)) {
                response.sendRedirect("member_dashboard.html");
            } else if ("manager".equalsIgnoreCase(role)) {
                response.sendRedirect("manager_dashboard.html");
            } else {
                response.getWriter().write("Unknown role.");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid credentials.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
            dispatcher.forward(request, response);
        }
    }
}
