package DAO;

import JDBCHelper.DatabaseConnection;
import Model.Exam;
import Model.Question;
import Model.QuestionAnswer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TakeExamDAO {

    public static boolean verifyUserAlreadyTakenExam(String user_id, long room_id) {
        var query = "select user_id from enrollments where user_id = ? and room_id = ?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setString(1, user_id);
            ps.setLong(2, room_id);
            var resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Exam selectExamOfRoom(long room_id) {
        var exam = new Exam();
        var query = "select exams.* from rooms inner join exams on rooms.exam_id = exams.exam_id where room_id = ?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, room_id);
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

    public static List<Question> selectQuestionOfExam(long exam_id) {
        var list = new ArrayList<Question>();
        var query = "select questions.* from questions inner join exams on questions.exam_id = exams.exam_id where exams.exam_id= ? order by questions.level asc";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, exam_id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(
                        new Question(
                                resultSet.getLong("question_id"),
                                resultSet.getLong("exam_id"),
                                resultSet.getInt("level"),
                                resultSet.getString("content")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<QuestionAnswer> selectQuestionAnswerOfQuestion(long question_id) {
        var list = new ArrayList<QuestionAnswer>();
        var query = "select question_answers.* from question_answers inner join questions on question_answers.question_id = questions.question_id where question_answers.question_id = ? order by rand()";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, question_id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(
                        new QuestionAnswer(
                                resultSet.getLong("question_answer_id"),
                                resultSet.getLong("question_id"),
                                resultSet.getString("content"),
                                resultSet.getBoolean("is_correct")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
