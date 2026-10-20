import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isEmpty()) {
            password = "java@lab";
        }
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/college", 
            "root", 
            password);
        System.out.println("Connected to database");
        Statement stmt = con.createStatement();
        PreparedStatement ps = con.prepareStatement("INSERT INTO student (id, name, branch) VALUES (?, ?, ?)");
        ps.setInt(1, 5);
        ps.setString(2, "Aniket");
        ps.setString(3, "CSE");
        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        con.close();
    }
}
