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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DBUtil.checkAdmin(user)) {
                String action = req.getParameter("action");
                if (action == null) {
                    session.setAttribute("employeeData", DBUtil.getEmployees());
                    session.setAttribute("jobs", DBUtil.getJobs());
                    req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
                }
                if (action.equals("add")) {
                    session.setAttribute("jobs", DBUtil.getJobs());
                    req.getRequestDispatcher("/WEB-INF/adduser.jsp").forward(req, resp);
                }
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
            if (DBUtil.checkAdmin(user)) {
                String action = req.getParameter("action");
                if (action == null) {
                    Employee employee = new Employee();
                    employee.setId(Integer.parseInt(req.getParameter("id")));
                    employee.setJobCode(req.getParameter("job"));
                    DBUtil.editEmployee(employee);
                    session.setAttribute("employeeData", DBUtil.getEmployees());
                    session.setAttribute("jobs", DBUtil.getJobs());
                    req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
                }
                if (action.equals("add")) {
                    Employee employee = new Employee();
                    employee.setFname(req.getParameter("fname"));
                    employee.setLname(req.getParameter("lname"));
                    employee.setPhone(req.getParameter("phone"));
                    employee.setEmail(req.getParameter("email"));
                    employee.setJobCode(req.getParameter("job"));
                    User insertUser = new User(employee.getEmail(), req.getParameter("pass"));
                    if (DBUtil.register(insertUser)) {
                        DBUtil.insertEmployee(employee);
                        session.setAttribute("employeeData", DBUtil.getEmployees());
                        session.setAttribute("jobs", DBUtil.getJobs());
                        req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
                    } else
                        resp.sendRedirect(getServletContext().getContextPath() + "/admin?action=add&incorrect=true");
                }
            } else resp.sendRedirect(getServletContext().getContextPath() + "/login");
        } catch (NullPointerException e) {
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        }
    }
}