package util;

import model.Employee;
import model.User;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private static boolean checkAdmin(String admin, Connection conn) {
        boolean tmp = false;
        try (PreparedStatement ps = conn.prepareStatement("SELECT user.Email FROM user LEFT JOIN employee ON user.Email = employee.Email WHERE employee.ID IS NULL")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    tmp = admin.equals(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            ps.setString(1, employee.getJobCode());
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

    public static Employee getInformation(User user) {
        Employee employee = new Employee();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT ID, Job_code, Firstname, Lastname, Email, Phone " +
                     "FROM employee WHERE employee.Email = ?")) {
            ps.setString(1, user.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                employee.setId(rs.getInt(1));
                employee.setJobCode(rs.getString(2));
                employee.setFname(rs.getString(3));
                employee.setLname(rs.getString(4));
                employee.setEmail(rs.getString(5));
                employee.setPhone(rs.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

/*    public static Employee[] getData() {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM employee")) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

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
