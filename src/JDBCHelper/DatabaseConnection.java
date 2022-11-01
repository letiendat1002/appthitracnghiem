package JDBCHelper;

import java.sql.*;

public class DatabaseConnection {
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbQuiz",
                "root",
                "123456"
        );
    }

    public static void main(String[] args) {
        Connection con = null;
        Statement statement;
        ResultSet resultSet;
        try {
            con = DatabaseConnection.getConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select * from users where full_name like '%L_%'");
            while(resultSet.next()){
                System.out.println(resultSet.getString("full_name"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
