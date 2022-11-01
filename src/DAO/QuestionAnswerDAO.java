package DAO;

import JDBCHelper.DatabaseConnection;
import Model.QuestionAnswer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerDAO {
    public static List<QuestionAnswer> selectAll() {
        var list = new ArrayList<QuestionAnswer>();
        var query = "select * from question_answers";
        try (var statement = DatabaseConnection.getConnectionInstance().createStatement()) {
            var resultSet = statement.executeQuery(query);
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

    public static QuestionAnswer selectByID(long questionAnswerID) {
        var questionAnswer = new QuestionAnswer();
        var query = "select * from question_answers where question_answer_id=?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, questionAnswerID);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                questionAnswer.setQuestion_answer_id(resultSet.getLong("question_answer_id"));
                questionAnswer.setQuestion_id(resultSet.getLong("question_id"));
                questionAnswer.setContent(resultSet.getString("content"));
                questionAnswer.setCorrect(resultSet.getBoolean("is_correct"));
                return questionAnswer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean insert(QuestionAnswer questionAnswer) {
        var query = "insert into question_answers(question_id,content,is_correct) values(?,?,?)";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, questionAnswer.getQuestion_id());
            ps.setString(2, questionAnswer.getContent());
            ps.setBoolean(3, questionAnswer.isCorrect());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(QuestionAnswer questionAnswer) {
        var query = "update question_answers set question_id = ?, content = ?, is_correct = ?  where question_answer_id = ?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, questionAnswer.getQuestion_id());
            ps.setString(2, questionAnswer.getContent());
            ps.setBoolean(3, questionAnswer.isCorrect());
            ps.setLong(4, questionAnswer.getQuestion_answer_id());
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(long question_answer_id) {
        var query = "delete from question_answers where question_answer_id = ?";
        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
            ps.setLong(1, question_answer_id);
            var count = ps.executeUpdate();
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<QuestionAnswer> questionAnswers = QuestionAnswerDAO.selectAll();
        System.out.println(questionAnswers.get(0).getQuestion_id());
        QuestionAnswer questionAnswer = QuestionAnswerDAO.selectByID(1);
        System.out.println(
                (questionAnswer != null ? questionAnswer.isCorrect() : -1)
                        + " "
                        + (questionAnswer != null ? questionAnswer.getContent() : "empty")
        );
    }
}
