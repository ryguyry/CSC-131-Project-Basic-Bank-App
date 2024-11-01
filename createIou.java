import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class createIou {
		public enum Status {
			PENDING,
			PAID,
			OVERDUE
		}
	    public void addIou(double amount, String creditor, String borrower) {
	        try (Connection connection = bbaDatabase.getConnection()) {
	            //SQL insert statement
	            String sql = "INSERT INTO iou (amount, lender_name, borrower_name) VALUES (?, ?, ?)";

	            //add these but change them to key values later
	            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	                pstmt.setDouble(1, amount);
	                pstmt.setString(2, creditor);
	                pstmt.setString(3, borrower); // replace with actual borrower ID
	                //execute insertion
	                pstmt.executeUpdate();
	                System.out.println("IOU added successfully!");
	            }
	        } catch (SQLException e) {
	            System.err.println("Database connection failed: " + e.getMessage());
	        }
	    }
}
