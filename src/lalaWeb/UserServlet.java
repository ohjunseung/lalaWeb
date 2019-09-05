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

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean redirect = true;
        String url;
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DBUtil.checkAdmin(user)) {
                String action = req.getParameter("action");
                if (action == null) {
                    redirect = false;
                    url = "/WEB-INF/editpass.jsp";
                }
                if (action.equals("add")) {
                    redirect = false;
                    url = "/WEB-INF/addadmin.jsp";
                } else throw new NullPointerException();
            } else url = "/login";
        } catch (NullPointerException e) {
            url = "/admin";
        }
        if (redirect)
            resp.sendRedirect(getServletContext().getContextPath() + url);
        else req.getRequestDispatcher(url).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
