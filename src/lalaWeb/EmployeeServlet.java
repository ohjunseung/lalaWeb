package lalaWeb;

import model.Employee;
import model.User;
import util.DButil;

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
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || DButil.checkAdmin(user))
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        else {
            Employee employee = DButil.getInformation(user);
            session.setAttribute("employee", employee);
            req.getRequestDispatcher("/WEB-INF/employee.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO Belum selesai cung!
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || DButil.checkAdmin(user))
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        else {
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(req.getParameter("id")));
            employee.setFname(req.getParameter("fname"));
            employee.setLname(req.getParameter("lname"));
            employee.setEmail(req.getParameter("email"));
            employee.setPhone(req.getParameter("phone"));
            User newUser = new User(req.getParameter("email"),user.getPass());
            DButil.editUser(user,newUser);
            DButil.editInformation(employee);
            employee = DButil.getInformation(user);
            session.setAttribute("employee", employee);
            session.setAttribute("user", newUser);
            req.getRequestDispatcher("/WEB-INF/employee.jsp").forward(req, resp);
        }
    }
}
