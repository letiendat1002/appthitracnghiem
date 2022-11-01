package JDBCHelper;

import javax.swing.*;
import java.sql.*;

public class DatabaseConnection {
    private static final String database = "dbQuiz";
    private static final String user = "root";
    private static final String password = "123456";
    private static final String url = "jdbc:mysql://localhost:3306/" + database;

    private static Connection instance;

    static {
        try {
            instance = DatabaseConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Không thể kết nối đến cơ sở dữ liệu. Hãy chạy lại ứng dụng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
            System.exit(1);
        }
    }

    private DatabaseConnection() {
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                url,
                user,
                password
        );
    }

    public static Connection getConnectionInstance() {
        return instance;
    }

    public static void main(String[] args) {
        Connection con;
        Statement statement;
        ResultSet resultSet;
        try {
            con = DatabaseConnection.getConnectionInstance();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select * from users where full_name like '%L_%'");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("full_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
