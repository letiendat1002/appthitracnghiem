package DAO;

import JDBCHelper.DatabaseConnection;
import Model.Enrollment;

import java.sql.SQLException;
import java.util.ArrayList;

public class EnrollmentDAO {
    public static ArrayList<Enrollment> selectAll() {
        var list = new ArrayList<Enrollment>();
        var query = "select * from enrollments";
        try (var statement = DatabaseConnection.getConnection().createStatement()) {
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(
                        new Enrollment(
                                resultSet.getLong("enrollment_id"),
                                resultSet.getString("user_id"),
                                resultSet.getLong("room_id"),
                                resultSet.getDouble("score")
                        )
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static Enrollment selectByID(long enrollment_id) {
        var enrollment = new Enrollment();
        var query = "select * from enrollments where enrollment_id=?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, enrollment_id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                enrollment.setEnrollment_id(resultSet.getLong("enrollment_id"));
                enrollment.setUser_id(resultSet.getString("user_id"));
                enrollment.setRoom_id(resultSet.getLong("room_id"));
                enrollment.setScore(resultSet.getDouble("score"));
                return enrollment;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean insert(Enrollment enrollment) {
        var query = "insert into enrollments(user_id,room_id,score) values(?,?,?)";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setString(1, enrollment.getUser_id());
            ps.setLong(2, enrollment.getRoom_id());
            ps.setDouble(3, enrollment.getScore());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(Enrollment enrollment) {
        var query = "update enrollments set user_id = ?, room_id = ?, score = ?  where enrollment_id = ?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setString(1, enrollment.getUser_id());
            ps.setLong(2, enrollment.getRoom_id());
            ps.setDouble(3, enrollment.getScore());
            ps.setLong(4, enrollment.getEnrollment_id());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(long enrollment_id) {
        var query = "delete from enrollments where enrollment_id = ?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, enrollment_id);
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ArrayList<Enrollment> enrollments = EnrollmentDAO.selectAll();
        System.out.println(enrollments.get(0).getScore());
        Enrollment enrollment = EnrollmentDAO.selectByID(1);
        System.out.println(
                (enrollment != null ? enrollment.getRoom_id() : -1)
                        + " "
                        + (enrollment != null ? enrollment.getUser_id() : "empty")
        );
//        Enrollment enrollment1 = new Enrollment("19h1010020", 1, 8.8);
//        System.out.println(EnrollmentDAO.insert(enrollment1));
//        assert enrollment != null;
//        enrollment.setScore(9.5);
//        System.out.println(EnrollmentDAO.update(enrollment));
//        System.out.println(EnrollmentDAO.delete(2));
    }
}
