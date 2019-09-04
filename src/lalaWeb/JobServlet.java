package lalaWeb;

import model.Job;
import model.User;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/job")
public class JobServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DBUtil.checkAdmin(user)) {
                String action = req.getParameter("action");
                if (action == null) {
                    req.setAttribute("jobs", DBUtil.getAllJobs());
                    req.getRequestDispatcher("/WEB-INF/editjobs.jsp").forward(req, resp);
                }
                if (action.equals("add")) {
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
                    Job job = new Job(req.getParameter("jobCode"),req.getParameter("jobName"),
                            Double.parseDouble(req.getParameter("jobSalary")));

                    DBUtil.editJob(job,job);
                    req.setAttribute("jobs", DBUtil.getAllJobs());
                    req.getRequestDispatcher("/WEB-INF/editjobs.jsp").forward(req, resp);
                }
                if (action.equals("add")) {
                    Job job = new Job(req.getParameter("jobCode"),req.getParameter("jobName"),
                            Double.parseDouble(req.getParameter("jobSalary")));
                    req.setAttribute("jobs", DBUtil.getJobs());
                    req.getRequestDispatcher("/WEB-INF/editjobs.jsp").forward(req, resp);
                }
            } else resp.sendRedirect(getServletContext().getContextPath() + "/login");
        } catch (NullPointerException e) {
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        }
    }
}
