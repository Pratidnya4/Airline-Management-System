package airline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conn implements AutoCloseable {
    
    public Connection c;
    public Statement s;
    
    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // ✅ Make sure your database name is included here
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/airline", "root", "prat04h@");
            s = c.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ✅ AutoCloseable implementation
    @Override
    public void close() {
        try {
            if (s != null) s.close();
            if (c != null) c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Conn conn = new Conn()) {
            System.out.println("✅ Connection Successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
