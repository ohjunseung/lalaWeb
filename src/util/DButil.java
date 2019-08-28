package util;

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

    private static boolean checkUser(User user) {
        boolean tmp = false;
        try {
            Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT email FROM user WHERE email = ? AND pass = ?");
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

    public static ResultSet getInformation() {
        try {
            Connection conn = ds.getConnection();
        } catch (SQLException e) {
            for (Throwable t : e
            ) {
                t.printStackTrace();
            }
        }
    }

    public static ResultSet getData() {
        try {
            Connection conn = ds.getConnection();
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
