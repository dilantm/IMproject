/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author User
 */
public class JDBC {
    
    String url = "jdbc:mysql://localhost:3306/hms";

    Connection con() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection(url, "root", "admin@dil.N");
        return c;

    }

    public void putData(String sql) throws Exception {
        Statement s = con().createStatement();
        s.executeUpdate(sql);
    }

    public ResultSet getData(String sql) throws Exception {
        Statement s = con().createStatement();
        ResultSet r = s.executeQuery(sql);
        return r;
    }
}
    
