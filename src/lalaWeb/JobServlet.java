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
        boolean redirect = true;
        String url = "";
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DBUtil.checkAdmin(user)) {
                String action = req.getParameter("action");
                if (action == null) {
                    req.setAttribute("jobs", DBUtil.getAllJobs());
                    redirect = false;
                    url = "/WEB-INF/jobs.jsp";
                }
                if (action.equals("add")) {
                    redirect = false;
                    url = "/WEB-INF/addjobs.jsp";
                }
            } else url = "/login";
        } catch (NullPointerException e) {
            url = "/login";
        }
        if (redirect)
            resp.sendRedirect(url);
        else req.getRequestDispatcher(url).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = "";
        HttpSession session = req.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (DBUtil.checkAdmin(user)) {
                String action = req.getParameter("action");
                if (action == null) {
                    Job job = new Job(req.getParameter("jobCode"), req.getParameter("jobName"),
                            Double.parseDouble(req.getParameter("jobSalary")));
                    DBUtil.editJob(job, req.getParameter("oldCode"));
                    url = "/job";
                }
                if (action.equals("add")) {
                    Job job = new Job(req.getParameter("jobCode"), req.getParameter("jobName"),
                            Double.parseDouble(req.getParameter("jobSalary")));
                    if (DBUtil.insertJob(job))
                        url = "/job";
                    else url = "/job?action=add&incorrect=true";
                }
                if (action.equals("delete")) {
                    DBUtil.deleteJob(req.getParameter("jobCode"));
                    url = "/job";
                }
            } else url = "/login";
        } catch (NullPointerException e) {
            url = "/login";
        }
        resp.sendRedirect(url);
    }
}
