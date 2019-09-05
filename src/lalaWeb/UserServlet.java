package lalaWeb;

import model.User;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DBUtil.checkAdmin(user)) {
                String action = req.getParameter("action");
                if (action == null) {
                    req.getRequestDispatcher("/WEB-INF/editpass.jsp").forward(req, resp);
                }
                if (action.equals("add")) {
                    req.getRequestDispatcher("/WEB-INF/addadmin.jsp").forward(req, resp);
                } else throw new NullPointerException();
            } else resp.sendRedirect(getServletContext().getContextPath() + "/login");
        } catch (NullPointerException e) {
            resp.sendRedirect(getServletContext().getContextPath() + "/admin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String url = "";
        try {
            User user = (User) session.getAttribute("user");
            if (DBUtil.checkAdmin(user)) {
                String action = req.getParameter("action");
                if (action == null) {
                    User newUser = new User(req.getParameter("email"), req.getParameter("pass"));
                    DBUtil.editUser(user, newUser);
                    url = "/login";
                }
                if (action.equals("add")) {
                    User newUser = new User(req.getParameter("email"), req.getParameter("pass"));
                    if (DBUtil.register(newUser))
                        url = "/admin";
                    else url = "/user?action=add&incorrect=true";
                }
            } else url = "/login";
        } catch (NullPointerException e) {
            url = "/login";
        }
        resp.sendRedirect(getServletContext().getContextPath() + url);
    }
}
