package DAO;

import JDBCHelper.DatabaseConnection;
import Model.Exam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {
    public static List<Exam> selectAll() {
        var list = new ArrayList<Exam>();
        var query = "select * from exams";
        try (var statement = DatabaseConnection.getConnectionInstance().createStatement()) {
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(
                        new Exam(
                                resultSet.getLong("exam_id"),
                                resultSet.getString("subject"),
                                resultSet.getInt("total_question"),
                                resultSet.getInt("total_score"),
                                resultSet.getDouble("score_per_question")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static Exam selectByID(long examID) {
        var exam = new Exam();
        var query = "select * from exams where exam_id=?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, examID);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                exam.setExam_id(resultSet.getLong("exam_id"));
                exam.setSubject(resultSet.getString("subject"));
                exam.setTotal_question(resultSet.getInt("total_question"));
                exam.setTotal_score(resultSet.getInt("total_score"));
                exam.setScore_per_question(resultSet.getDouble("score_per_question"));
                return exam;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean insert(Exam exam) {
        var query = "insert into exams(subject,total_question,total_score,score_per_question) values(?,?,?,?)";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setString(1, exam.getSubject());
            ps.setInt(2, exam.getTotal_question());
            ps.setInt(3, exam.getTotal_score());
            ps.setDouble(4, exam.getScore_per_question());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(Exam exam) {
        var query = "update exams set subject = ?, total_question = ?, total_score = ?, score_per_question = ? where exam_id = ?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setString(1, exam.getSubject());
            ps.setInt(2, exam.getTotal_question());
            ps.setInt(3, exam.getTotal_score());
            ps.setDouble(4, exam.getScore_per_question());
            ps.setLong(5, exam.getExam_id());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(long exam_id) {
        var query = "delete from exams where exam_id = ?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, exam_id);
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Exam> exams = ExamDAO.selectAll();
        System.out.println(exams.get(0).getExam_id());
        Exam exam = ExamDAO.selectByID(1);
        System.out.println(
                (exam != null ? exam.getScore_per_question() : -1)
                        + " "
                        + (exam != null ? exam.getSubject() : "empty")
        );
//        Exam exam1 = new Exam("Toán", 20 , 10, 10.0/20.0);
//        System.out.println("Insert: " + ExamDAO.insert(exam1));
//        exam.setTotal_question(40);
//        System.out.println("Update: " + ExamDAO.update(exam));
//        System.out.println("delete: " + ExamDAO.delete(1));
    }
}
