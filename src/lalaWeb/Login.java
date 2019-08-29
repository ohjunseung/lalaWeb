package lalaWeb;

import model.User;
import util.DButil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = new User(req.getParameter("email"), req.getParameter("pass"));
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            switch (DButil.checkUser(user)) {
                case 1:
                    resp.sendRedirect(getServletContext().getContextPath() + "/employee");
                    break;
                case 2:
                    resp.sendRedirect(getServletContext().getContextPath() + "/admin");
                    break;
                default:
                    throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            resp.sendRedirect(getServletContext().getContextPath() + "/login?incorrect=true");
        }
    }
}
