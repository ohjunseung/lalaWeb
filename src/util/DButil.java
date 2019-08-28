package util;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class DButil {
    private static BasicDataSource ds;

    private static BasicDataSource getInstance(){
        if (ds == null){
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

    private static void checkUser(){
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
