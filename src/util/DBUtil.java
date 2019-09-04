package util;

import model.*;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DBUtil {
    private static BasicDataSource ds;

    static {
        ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/eis");
        ds.setUsername("root");
        ds.setPassword("toor");

        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    private static String hashSHA256(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digested = md.digest(msg.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : digested) hexString.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean checkAdmin(String admin, Connection conn) throws SQLException {
        boolean tmp = false;
        try (PreparedStatement ps = conn.prepareStatement("SELECT user.Email FROM user LEFT JOIN employee " +
                "ON user.Email = employee.Email WHERE employee.ID IS NULL")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (admin.equals(rs.getString(1))) {
                        tmp = true;
                        break;
                    }
                }
            }
        }
        return tmp;
    }

    public static boolean checkAdmin(User user) {
        try (Connection conn = ds.getConnection()) {
            return checkAdmin(user.getEmail(), conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int checkUser(User user) {
        /*  0 = FALSE
            1 = Normal
            2 = Admin
        */
        int tmp = 0;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Email FROM user WHERE Email = ? AND Pass = ?")) {
            ps.setString(1, user.getEmail());
            ps.setString(2, hashSHA256(user.getPass()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) if (checkAdmin(rs.getString(1), conn))
                    tmp = 2;
                else tmp = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    public static boolean register(User user) {
        boolean tmp = false;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO user(Email,Pass) VALUES(?,?)")) {
            ps.setString(1, user.getEmail());
            ps.setString(2, hashSHA256(user.getPass()));
            ps.executeUpdate();
            tmp = true;
        } catch (SQLException e) {
            String state = e.getSQLState();
            if (state.equals("21S01")) // Null inserted
                tmp = false;
            else if (state.equals("23000")) // Duplicate Value
                tmp = false;
            else e.printStackTrace();
        }
        return tmp;
    }

    public static void editUser(User oldUser, User newUser) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE user SET Email = ?, Pass = ? " +
                     "WHERE Email = ? AND Pass = ?")) {
            ps.setString(1, newUser.getEmail());
            ps.setString(2, hashSHA256(newUser.getPass()));
            ps.setString(3, oldUser.getEmail());
            ps.setString(4, hashSHA256(oldUser.getPass()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static HashMap<String, Job> getJobs(Connection conn) throws SQLException {
        HashMap<String, Job> jobs = new HashMap<>();
        try (PreparedStatement psJob = conn.prepareStatement("SELECT Job_code,Job_name,Job_salary " +
                "FROM jobs")) {
            try (ResultSet rs = psJob.executeQuery()) {
                while (rs.next()) {
                    Job job = new Job(rs.getString(1), rs.getString(2), rs.getDouble(3));
                    jobs.put(job.getCode(), job);
                }
            }
        }
        return jobs;
    }

    public static HashMap<String, String> getJobs() {
        HashMap<String, String> jobsPair = new HashMap<>();
        try (Connection conn = ds.getConnection()) {
            HashMap<String, Job> jobs = getJobs(conn);
            jobs.forEach((K, V) -> jobsPair.put(K, V.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobsPair;
    }

    public static ArrayList<Job> getAllJobs() {
        try (Connection conn = ds.getConnection()) {
            HashMap<String, Job> jobs = getJobs(conn);
            ArrayList<Job> jobList = new ArrayList<>();
            jobList.addAll(jobs.values());
            return jobList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insertJob(Job job) {
        boolean tmp = false;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO jobs(Job_code, Job_name, Job_salary)" +
                     " VALUES(?,?,?)")) {
            ps.setString(1, job.getCode());
            ps.setString(2, hashSHA256(job.getName()));
            ps.setDouble(3, job.getSalary());
            ps.executeUpdate();
            tmp = true;
        } catch (SQLException e) {
            String state = e.getSQLState();
            if (state.equals("21S01")) // Null inserted
                tmp = false;
            else if (state.equals("23000")) // Duplicate Value
                tmp = false;
            else e.printStackTrace();
        }
        return tmp;
    }

    public static void editJob(Job job, String jobCode) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE jobs SET Job_code = ?,Job_name = ? ,Job_salary = ?" +
                     " WHERE Job_code = ?")) {
            ps.setString(1, job.getCode());
            ps.setString(2, job.getName());
            ps.setDouble(3, job.getSalary());
            ps.setString(4, jobCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Employee getInformation(User user) {
        Employee employee = new Employee();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT ID, Firstname, Lastname, Email, Phone, Job_code, " +
                     "Job_name,Job_salary FROM employee NATURAL JOIN jobs WHERE employee.Email = ?")) {
            ps.setString(1, user.getEmail());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee.setId(rs.getInt(1));
                    employee.setFname(rs.getString(2));
                    employee.setLname(rs.getString(3));
                    employee.setEmail(rs.getString(4));
                    employee.setPhone(rs.getString(5));
                    employee.setJobCode(rs.getString(6));
                    employee.setJobName(rs.getString(7));
                    employee.setSalary(rs.getDouble(8));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public static void editInformation(Employee employee) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE employee SET Firstname = ? ,Lastname = ? ," +
                     "Phone = ? WHERE ID = ?")) {
            ps.setString(1, employee.getFname());
            ps.setString(2, employee.getLname());
            ps.setString(3, employee.getPhone());
            ps.setInt(4, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean insertEmployee(Employee employee) {
        boolean tmp = false;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO " +
                     "employee(Firstname, Lastname, Email, Phone, Job_code) VALUES(?,?,?,?,?)")) {
            ps.setString(1, employee.getFname());
            ps.setString(2, employee.getLname());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getPhone());
            ps.setString(5, employee.getJobCode());
            ps.executeUpdate();
            tmp = true;
        } catch (SQLException e) {
            String state = e.getSQLState();
            if (state.equals("21S01")) // Null inserted
                tmp = false;
            else if (state.equals("23000")) // Duplicate Value
                tmp = false;
            else e.printStackTrace();
        }
        return tmp;
    }

    public static ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT ID, Firstname, Lastname, Email, Phone, Job_code, " +
                     "Job_name,Job_salary FROM employee NATURAL JOIN jobs ORDER BY ID")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getInt(1));
                    employee.setFname(rs.getString(2));
                    employee.setLname(rs.getString(3));
                    employee.setEmail(rs.getString(4));
                    employee.setPhone(rs.getString(5));
                    employee.setJobCode(rs.getString(6));
                    employee.setJobName(rs.getString(7));
                    employee.setSalary(rs.getDouble(8));
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static void editEmployee(Employee employee) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE employee SET ID = ?,Job_code = ? WHERE ID = ?")) {
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getJobCode());
            ps.setInt(3, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
