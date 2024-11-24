import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class createIou {
    public void addIou(double amount, String creditor, String borrower, String dueDate) {
        // Prepare the date format for parsing
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection connection = bbaDatabase.getConnection()) {
            // First, get the borrower_id using the borrower name (assuming the borrower name is unique)
            String getUserIdQuery = "SELECT user_id FROM users WHERE username = ?";
            int borrowerId = -1; // Default value if borrower is not found

            try (PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdQuery)) {
                getUserIdStmt.setString(1, borrower); // Set the borrower name to get the user_id
                try (ResultSet resultSet = getUserIdStmt.executeQuery()) {
                    if (resultSet.next()) {
                        borrowerId = resultSet.getInt("user_id");  // Get user_id
                    }
                }
            }

            if (borrowerId == -1) {
                System.out.println("Borrower not found in the database.");
                return;  // Return if borrower doesn't exist
            }

            // SQL insert statement for adding the IOU record
            String sql = "INSERT INTO iou (amount, lender_name, borrower_name, borrower_id, due_date) VALUES (?, ?, ?, ?, ?)";

            // Parse the date string and convert it to java.sql.Date
            java.util.Date utilDate = dateFormat.parse(dueDate); // This can throw ParseException
            java.sql.Date sqlDate = new Date(utilDate.getTime());

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setDouble(1, amount);
                pstmt.setString(2, creditor);
                pstmt.setString(3, borrower);
                pstmt.setInt(4, borrowerId);  // Set the borrowerId obtained earlier
                pstmt.setDate(5, sqlDate);    // Insert the due date

                // Execute the insertion
                pstmt.executeUpdate();
                System.out.println("IOU added successfully!");
            }

        } catch (ParseException e) {
            System.err.println("Date parsing failed: Please use the format yyyy-MM-dd. Error: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
}

