package DAO;

import JDBCHelper.DatabaseConnection;
import Model.EnrollmentAnswer;

import java.sql.SQLException;
import java.util.ArrayList;

public class EnrollmentAnswerDAO {
    public static ArrayList<EnrollmentAnswer> selectAll() {
        var list = new ArrayList<EnrollmentAnswer>();
        var query = "select * from enrollment_answers";
        try (var statement = DatabaseConnection.getConnection().createStatement()) {
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(
                        new EnrollmentAnswer(
                                resultSet.getLong("enrollment_answer_id"),
                                resultSet.getLong("enrollment_id"),
                                resultSet.getLong("question_id"),
                                resultSet.getLong("question_answer_id")
                        )
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static EnrollmentAnswer selectByID(long enrollment_answer_id) {
        var enrollmentAnswer = new EnrollmentAnswer();
        var query = "select * from enrollment_answers where enrollment_answer_id=?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, enrollment_answer_id);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                enrollmentAnswer.setEnrollment_answer_id(resultSet.getLong("enrollment_answer_id"));
                enrollmentAnswer.setEnrollment_id(resultSet.getLong("enrollment_id"));
                enrollmentAnswer.setQuestion_id(resultSet.getLong("question_id"));
                enrollmentAnswer.setQuestion_answer_id(resultSet.getLong("question_answer_id"));
                return enrollmentAnswer;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean insert(EnrollmentAnswer enrollmentAnswer) {
        var query = "insert into enrollment_answers(enrollment_id,question_id,question_answer_id) values(?,?,?)";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, enrollmentAnswer.getEnrollment_id());
            ps.setLong(2, enrollmentAnswer.getQuestion_id());
            ps.setLong(3, enrollmentAnswer.getQuestion_answer_id());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(EnrollmentAnswer enrollmentAnswer) {
        var query = "update enrollment_answers set enrollment_id = ?, question_id = ?, question_answer_id = ?  where enrollment_answer_id = ?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, enrollmentAnswer.getEnrollment_id());
            ps.setLong(2, enrollmentAnswer.getQuestion_id());
            ps.setLong(3, enrollmentAnswer.getQuestion_answer_id());
            ps.setLong(4, enrollmentAnswer.getEnrollment_answer_id());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(long enrollment_answer_id) {
        var query = "delete from enrollment_answers where enrollment_answer_id = ?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, enrollment_answer_id);
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
//        ArrayList<EnrollmentAnswer> enrollmentAnswers = EnrollmentAnswerDAO.selectAll();
//        System.out.println(enrollmentAnswers.get(0).getQuestion_answer_id());
//        EnrollmentAnswer enrollmentAnswer = EnrollmentAnswerDAO.selectByID(1);
//        System.out.println(
//                (enrollmentAnswer != null ? enrollmentAnswer.getEnrollment_answer_id() : -1)
//                        + " "
//                        + (enrollmentAnswer != null ? enrollmentAnswer.getEnrollment_id() : "empty")
//        );
//        EnrollmentAnswer enrollmentAnswer1 = new EnrollmentAnswer(1,2,4);
//        System.out.println(EnrollmentAnswerDAO.insert(enrollmentAnswer1));
//        enrollmentAnswer.setQuestion_answer_id(5);
//        System.out.println(EnrollmentAnswerDAO.update(enrollmentAnswer));
//        System.out.println(EnrollmentDAO.delete(1));
    }
}
