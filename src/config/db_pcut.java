package config;
import java.sql.*;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils; 
public class db_pcut {
    // 1. Connection Method
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbPath = System.getProperty("user.dir") + java.io.File.separator + "PCut_db.db";
            con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            System.out.println("Connecting to: " + dbPath); // ✅ shows exact path in Output tab
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }
    // 2. Add Records
    public int addRecord(String sql, Object... values) {
        int rowsAffected = 0; 
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
            rowsAffected = pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
        return rowsAffected; 
    }
    // 3. Update Records
    public void updateRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
            pstmt.executeUpdate();
            System.out.println("Update Successful!");
        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }
    // 4. Get Data Method
    public ResultSet getData(String sql) throws SQLException {
        Connection conn = connectDB();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }
    // 5. Delete Record Method
    public void deleteRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
            pstmt.executeUpdate();
            System.out.println("Record deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Delete Error: " + e.getMessage());
        }
    }
    // 6. Display Data Method
    public void displayData(String sql, JTable table) {
        try (Connection conn = connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            System.out.println("Error displaying data: " + e.getMessage());
        }
    }
    // 7. Count Records Method
    public int countRecords(String sql) {
        int count = 0;
        try (Connection conn = connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Count Error: " + e.getMessage());
        }
        return count;
    }
    // 8. Save Profile Picture
    public void updateProfilePic(int userId, byte[] imageBytes) {
       String sql = "UPDATE users SET profile_pic = ? WHERE user_id = ?";
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBytes(1, imageBytes);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            System.out.println("Profile picture saved!");
        } catch (SQLException e) {
            System.out.println("Profile pic save error: " + e.getMessage());
        }
    }
    // 9. Load Profile Picture
    public byte[] getProfilePic(int userId) {
        String sql = "SELECT profile_pic FROM users WHERE user_id = " + userId;
        try (Connection conn = connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getBytes("profile_pic");
            }
        } catch (SQLException e) {
            System.out.println("Profile pic load error: " + e.getMessage());
        }
        return null;
    }
}
