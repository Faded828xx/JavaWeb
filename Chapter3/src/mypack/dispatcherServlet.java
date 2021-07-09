package mypack;

import javax.servlet.*;
import java.io.IOException;

public class dispatcherServlet extends GenericServlet {
    private String target = "/index.jsp";

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        request.setAttribute("USER", username);
        ServletContext context = request.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }
}
