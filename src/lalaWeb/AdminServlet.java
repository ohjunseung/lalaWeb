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
        boolean redirect = true;
        String url;
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DBUtil.checkAdmin(user)) {
                try {
                    String action = req.getParameter("action");
                    if (action.equals("add")) {
                        req.setAttribute("jobs", DBUtil.getJobs());
                        redirect = false;
                        url = "/WEB-INF/adduser.jsp";
                    } else url = "/login";
                } catch (NullPointerException e) {
                    req.setAttribute("employeeData", DBUtil.getEmployees());
                    req.setAttribute("jobs", DBUtil.getJobs());
                    redirect = false;
                    url = "/WEB-INF/admin.jsp";
                }
            } else url = "/login";
        } catch (NullPointerException e) {
            url = "/login";
        }
        if (redirect)
            resp.sendRedirect(getServletContext().getContextPath() + url);
        else req.getRequestDispatcher(url).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = "";
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DBUtil.checkAdmin(user)) {
                try {
                    String action = req.getParameter("action");
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
                            url = "/admin";
                        } else
                            url = "/admin?action=add&incorrect=true";
                    }
                    if (action.equals("delete")) {
                        Employee employee = new Employee();
                        employee.setId(Integer.parseInt(req.getParameter("id")));
                        employee.setEmail(req.getParameter("email"));
                        DBUtil.deleteEmployee(employee);
                        url = "/admin";
                    }
                } catch (NullPointerException e) {
                    Employee employee = new Employee();
                    employee.setId(Integer.parseInt(req.getParameter("id")));
                    employee.setJobCode(req.getParameter("job"));
                    DBUtil.editEmployee(employee, Integer.parseInt(req.getParameter("oldID")));
                    url = "/admin";
                }
            } else url = "/login";
        } catch (NullPointerException e) {
            url = "/login";
        }
        resp.sendRedirect(getServletContext().getContextPath() + url);
    }
}