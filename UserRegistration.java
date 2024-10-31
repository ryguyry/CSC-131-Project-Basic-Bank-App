import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistration {

    public static boolean addUser(String username) {
        // Connect to the database and insert the user
    	 String insertQuery = "INSERT INTO users (username) VALUES (?)";
         try (Connection connection = bbaDatabase.getConnection();
              PreparedStatement statement = connection.prepareStatement(insertQuery)) {

             statement.setString(1, username);  // Set the user name in the query
             int rowsInserted = statement.executeUpdate();
             return rowsInserted > 0;  // Returns true if insertion was successful

         } catch (SQLException e) {
             System.out.println("Error inserting user: " + e.getMessage());
             return false;
         }
    }
    
    //TEST FUNCTION
    /*
    public static void main(String[] args) {
        String testUsername = "ron";  // You can replace this with any user name to test
        if (addUser(testUsername)) {
            System.out.println("User '" + testUsername + "' added successfully!");
        } else {
            System.out.println("Failed to add user '" + testUsername + "'.");
        }
    }
    */
}

