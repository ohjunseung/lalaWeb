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

    private static BasicDataSource getInstance() {
        if (ds == null) {
            ds = new BasicDataSource();
            ds.setUrl("jdbc:mysql://localhost:3306/eis");
            ds.setUsername("root");
            ds.setPassword("toor");

            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
        }
        return ds;
    }

    public static boolean checkAdmin(User user) {
        boolean tmp = false;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT user.Email FROM user LEFT JOIN employee ON user.Email = employee.Email WHERE employee.ID IS NULL")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                tmp = user.getEmail().equals(rs.getString(1));
        } catch (SQLException e) {
            for (Throwable t : e
            ) {
                t.printStackTrace();
            }
        }
        return tmp;
    }

    public static boolean checkUser(User user) {
        boolean tmp = false;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT Email FROM user WHERE Email = ? AND Pass = ?")) {
            ps.setString(1, user.getEmail());
            ps.setString(2, hashSHA256(user.getPass()));
            ResultSet rs = ps.executeQuery();
            tmp = rs.next();
        } catch (SQLException e) {
            for (Throwable t : e
            ) {
                t.printStackTrace();
            }
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
            else for (Throwable t : e
                ) {
                    t.printStackTrace();
                }
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
            else for (Throwable t : e
                ) {
                    t.printStackTrace();
                }
        }
        return tmp;
    }

    public static User getInformation(Employee employee) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM employee WHERE employee.id = ?")) {
            ps.setInt(1,employee.getId());
        } catch (SQLException e) {
            for (Throwable t : e
            ) {
                t.printStackTrace();
            }
        }
    }

    public static User[] getData() {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM employee")) {

        } catch (SQLException e) {
            for (Throwable t : e
            ) {
                t.printStackTrace();
            }
        }
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
