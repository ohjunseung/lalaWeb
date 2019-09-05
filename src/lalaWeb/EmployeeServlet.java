package lalaWeb;

import model.Employee;
import model.User;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean redirect = true;
        String url;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || DBUtil.checkAdmin(user))
            url = "/login";
        else {
            Employee employee = DBUtil.getInformation(user);
            req.setAttribute("employee", employee);
            redirect = false;
            url = "/WEB-INF/employee.jsp";
        }
        if (redirect)
            resp.sendRedirect(getServletContext().getContextPath() + url);
        else req.getRequestDispatcher(url).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || DBUtil.checkAdmin(user))
            url = "/login";
        else {
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(req.getParameter("id")));
            employee.setFname(req.getParameter("fname"));
            employee.setLname(req.getParameter("lname"));
            employee.setEmail(req.getParameter("email"));
            employee.setPhone(req.getParameter("phone"));
            User newUser = new User(employee.getEmail(), user.getPass());
            DBUtil.editUser(user, newUser);
            DBUtil.editInformation(employee);
            session.setAttribute("user", newUser);
            url = "employee.jsp";
        }
        resp.sendRedirect(getServletContext().getContextPath() + url);
    }
}
