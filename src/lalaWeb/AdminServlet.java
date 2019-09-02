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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DButil.checkAdmin(user)) {
                session.setAttribute("employeeData", DButil.getEmployees());
                req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
            } else resp.sendRedirect(getServletContext().getContextPath() + "/login");
        } catch (NullPointerException e) {
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DButil.checkAdmin(user)) {
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(req.getParameter("id")));
                employee.setPhone(req.getParameter("jobName"));
                DButil.editEmployee(employee);
                session.setAttribute("employeeData", DButil.getEmployees());
                session.setAttribute("jobs", DButil.getJobs());
                req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
            } else resp.sendRedirect(getServletContext().getContextPath() + "/login");
        } catch (NullPointerException e) {
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        }
    }
}