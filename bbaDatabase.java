import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bbaDatabase {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/";
    private static final String USER = "publicUser";  //user name created with read/write access only for schema
    private static final String PASSWORD = "BasicBank0327"; //pass word
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void main(String[] args) {
        try {
            bbaDatabase.getConnection();
            System.out.println("Connection established successfully!");
            // Add your logic here to interact with the database
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
