import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistration {

    public void registerUser(String username) {
        // Connect to the database and insert the user
        try (Connection connection = bbaDatabase.getConnection()) {
            String sql = "INSERT INTO users (username) VALUES (?)"; // Remove password column
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in production
        }
    }
}
