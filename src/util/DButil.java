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
import java.util.HashMap;

public class DButil {
    private static BasicDataSource ds;

    static {
        if (ds == null) {
            ds = new BasicDataSource();
            ds.setUrl("jdbc:mysql://localhost:3306/eis");
            ds.setUsername("root");
            ds.setPassword("toor");

            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
        }
    }

    private static boolean checkAdmin(String admin, Connection conn) throws SQLException {
        boolean tmp = false;
        try (PreparedStatement ps = conn.prepareStatement("SELECT user.Email FROM user LEFT JOIN employee " +
                "ON user.Email = employee.Email WHERE employee.ID IS NULL")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    tmp = admin.equals(rs.getString(1));
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
                if (!rs.next()) {
                    tmp = 0;
                } else if (checkAdmin(rs.getString(1), conn))
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

    public static boolean insertEmployee(Employee employee) {
        boolean tmp = false;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO " +
                     "employee(Job_code,Firstname,Lastname,Email,Phone) VALUES(?,?,?,?,?)")) {
            ps.setString(1, employee.getJobName());
            ps.setString(2, employee.getFname());
            ps.setString(3, employee.getLname());
            ps.setString(4, employee.getEmail());
            ps.setString(5, employee.getPhone());
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

    private static HashMap<String, Job> getJobs(Connection conn) throws SQLException {
        HashMap<String, Job> jobs = new HashMap<>();
        try (PreparedStatement psJob = conn.prepareStatement("SELECT Job_code,Job_name,Job_salary " +
                "FROM employee NATURAL JOIN jobs;")) {
            try (ResultSet rs = psJob.executeQuery()) {
                while (rs.next()) {
                    Job job = new Job(rs.getString(1), rs.getString(2), rs.getDouble(3));
                    jobs.put(job.getCode(), job);
                }
            }
        }
        return jobs;
    }

    public static Employee getInformation(User user) {
        Employee employee = new Employee();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT ID, Firstname, Lastname, Email, Phone, Job_code " +
                     "FROM employee WHERE employee.Email = ?")) {
            HashMap<String, Job> jobs = getJobs(conn);
            ps.setString(1, user.getEmail());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee.setId(rs.getInt(1));
                    employee.setFname(rs.getString(2));
                    employee.setLname(rs.getString(3));
                    employee.setEmail(rs.getString(4));
                    employee.setPhone(rs.getString(5));
                    Job temp = jobs.get(rs.getString(6));
                    employee.setJobName(temp.getName());
                    employee.setSalary(temp.getSalary());
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
                     "Email = ? ,Phone = ? WHERE ID = ?")) {
            ps.setString(1, employee.getFname());
            ps.setString(2, employee.getLname());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getPhone());
            ps.setInt(5, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editUser(User oldUser, User newUser) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE user SET Email = ?, Pass = ? " +
                     "WHERE Email = ? AND Pass = ?")) {
            ps.setString(1, oldUser.getEmail());
            ps.setString(2, hashSHA256(oldUser.getPass()));
            ps.setString(3, newUser.getEmail());
            ps.setString(4, hashSHA256(newUser.getPass()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT ID, Firstname, Lastname, Email, Phone, Job_code" +
                     " FROM employee")) {
            HashMap<String, Job> jobs = getJobs(conn);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getInt(1));
                    employee.setFname(rs.getString(2));
                    employee.setLname(rs.getString(3));
                    employee.setEmail(rs.getString(4));
                    employee.setPhone(rs.getString(5));
                    Job temp = jobs.get(rs.getString(6));
                    employee.setJobName(temp.getName());
                    employee.setSalary(temp.getSalary());
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private static String hashSHA256(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digested = md.digest(msg.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digested.length; i++)
                hexString.append(Integer.toString((digested[i] & 0xff) + 0x100, 16).substring(1));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
