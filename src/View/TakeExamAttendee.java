package View;

import DAO.EnrollmentAnswerDAO;
import DAO.EnrollmentDAO;
import DAO.RoomDAO;
import DAO.TakeExamDAO;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TakeExamAttendee extends JFrame {
    private final User loginUser;
    private final Room room;
    private JButton buttonSubmitViewTakeExamAttendee;
    private JLabel labelRoomTitleViewTakeExamAttendee;
    private JLabel labelRoomIDViewTakeExamAttendee;
    private JLabel labelTimeLimitViewTakeExamAttendee;
    private JLabel labelTotalQuestionViewTakeExamAttendee;
    private JLabel labelDataRoomIDViewTakeExamAttendee;
    private JLabel labelDataTimeLimitViewTakeExamAttendee;
    private JLabel labelDataTotalQuestionViewTakeExamAttendee;
    private JLabel labelCountDownClockViewTakeExamAttendee;
    private JPanel panelViewTakeExamAttendee;
    private JTabbedPane tabbedpanelTakeExamViewTakeExamAttendee;

    private Exam exam;

    private List<Question> questionList;

    private List<List<QuestionAnswer>> listOfQuestionAnswerList;

    private List<QuestionAndAnswerForm> questionAndAnswerFormList;

    private int prevQAAFormIndex;

    private Timer timer;

    private List<String> correctAnswerList;

    private List<String> chosenAnswerList;

    private List<String> resultList;

    private int totalCorrect;

    private double score;

    public TakeExamAttendee(User loginUser, Room room) {
        this.loginUser = loginUser;
        this.room = room;
        this.setTitle("Phòng thi");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setContentPane(panelViewTakeExamAttendee);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initComponents();
        addActionEvent();
        setTimer();
    }

    public static void main(String[] args) {
        var user = new User("user", "user", "user", false);
        var room = RoomDAO.selectByID(1);
        EventQueue.invokeLater(() -> new TakeExamAttendee(user, room));
    }

    private void initComponents() {
        exam = TakeExamDAO.selectExamOfRoom(room.getRoom_id());
        if (exam == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Đề thi đã bị lỗi. Bạn sẽ tự động thoát khỏi phòng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            this.dispose();
            new MenuAttendee(loginUser);
            return;
        }
        questionList = TakeExamDAO.selectQuestionOfExam(exam.getExam_id());
        if (questionList.size() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hiện tại đề thi không có câu hỏi. Bạn sẽ tự động thoát khỏi phòng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            this.dispose();
            new MenuAttendee(loginUser);
            return;
        }
        labelDataTotalQuestionViewTakeExamAttendee.setText(String.valueOf(exam.getTotal_question()));
        labelRoomTitleViewTakeExamAttendee.setText(room.getTitle().strip());
        labelDataRoomIDViewTakeExamAttendee.setText(String.valueOf(room.getRoom_id()));
        labelDataTimeLimitViewTakeExamAttendee.setText(String.valueOf(room.getTime_limit()));
        listOfQuestionAnswerList = new ArrayList<>();
        correctAnswerList = new ArrayList<>();
        chosenAnswerList = new ArrayList<>();
        questionAndAnswerFormList = new ArrayList<>();
        resultList = new ArrayList<>();
        prevQAAFormIndex = 0;
        totalCorrect = 0;
        fillDataToQAAForm();
    }

    private void addActionEvent() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                var frame = (JFrame) e.getSource();
                var selection = JOptionPane.showConfirmDialog(
                        frame,
                        new Object[]{
                                "Kết quả làm bài của bạn vẫn sẽ tính nếu bạn thoát ngay lúc này!",
                                "Bạn có chắc chắn muốn thoát phòng thi?"
                        },
                        "Xác Nhận",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    timer.stop();
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    executeAndShowResult();
                }
            }
        });

        tabbedpanelTakeExamViewTakeExamAttendee.addChangeListener(event -> {
            var prevQAAForm = (QuestionAndAnswerForm) tabbedpanelTakeExamViewTakeExamAttendee.getComponentAt(prevQAAFormIndex);
            if (prevQAAForm.getButtonGroup().getSelection() != null) {
                tabbedpanelTakeExamViewTakeExamAttendee.setForegroundAt(prevQAAFormIndex, Color.WHITE);
                tabbedpanelTakeExamViewTakeExamAttendee.setBackgroundAt(prevQAAFormIndex, Color.BLUE);
            } else {
                tabbedpanelTakeExamViewTakeExamAttendee.setForegroundAt(prevQAAFormIndex, null);
                tabbedpanelTakeExamViewTakeExamAttendee.setBackgroundAt(prevQAAFormIndex, null);
            }
            prevQAAFormIndex = tabbedpanelTakeExamViewTakeExamAttendee.getSelectedIndex();
        });

        buttonSubmitViewTakeExamAttendee.addActionListener(event -> {
            var selection = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn thật sự muốn nộp bài?",
                    "Xác Nhận",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.OK_OPTION) {
                timer.stop();
                this.dispose();
                executeAndShowResult();
            }
        });
    }

    private void executeAndShowResult() {
        executeExamResult();
        saveExamResultToDatabase();
        new ResultForm(
                loginUser,
                questionList,
                chosenAnswerList,
                correctAnswerList,
                resultList,
                totalCorrect,
                score
        );
    }

    private void executeExamResult() {
        for (var questionAndAnswerForm : questionAndAnswerFormList) {
            String s1;
            try {
                s1 = questionAndAnswerForm.getButtonGroup().getSelection().getActionCommand();
            } catch (Exception e) {
                s1 = "Chưa chọn";
            }
            chosenAnswerList.add(s1);
        }
        var index = 0;
        for (var chosenAnswer : chosenAnswerList) {
            var correctAnswer = correctAnswerList.get(index++);
            if (chosenAnswer.equals(correctAnswer)) {
                resultList.add("Đúng");
                totalCorrect++;
            } else {
                resultList.add("Sai");
            }
        }
        score = totalCorrect * exam.getScore_per_question();
    }

    private void saveExamResultToDatabase() {
        var user_id = loginUser.getUser_id();
        var room_id = room.getRoom_id();
        Enrollment enrollment = new Enrollment(user_id, room_id, score);
        var isSuccess = EnrollmentDAO.insert(enrollment);
        if (isSuccess) {
            var enrollment_id = EnrollmentDAO.selectIDByModel(enrollment);
            var index = 0;
            var enrollmentAnswer = new EnrollmentAnswer();
            for (var questionAnswerList : listOfQuestionAnswerList) {
                for (var questionAnswer : questionAnswerList) {
                    var chosenAnswer = chosenAnswerList.get(index);
                    if (chosenAnswer.contains("Chưa chọn")) {
                        enrollmentAnswer = new EnrollmentAnswer(
                                enrollment_id,
                                questionAnswer.getQuestion_id(),
                                null
                        );
                    }
                    if (questionAnswer.getContent().equals(chosenAnswer)) {
                        enrollmentAnswer = new EnrollmentAnswer(
                                enrollment_id,
                                questionAnswer.getQuestion_id(),
                                questionAnswer.getQuestion_answer_id()
                        );
                    }
                }
                EnrollmentAnswerDAO.insert(enrollmentAnswer);
                index++;
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Lưu kết quả dự thi thất bại",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void fillDataToQAAForm() {
        var index = 1;
        for (var question : questionList) {
            List<QuestionAnswer> questionAnswerList = TakeExamDAO.selectQuestionAnswerOfQuestion(question.getQuestion_id());
            var questionAndAnswerForm = new QuestionAndAnswerForm(question, questionAnswerList);
            tabbedpanelTakeExamViewTakeExamAttendee.addTab(String.valueOf(index++), questionAndAnswerForm);
            for (var questionAnswer : questionAnswerList) {
                if (questionAnswer.isCorrect()) {
                    correctAnswerList.add(questionAnswer.getContent());
                }
            }
            questionAndAnswerFormList.add(questionAndAnswerForm);
            listOfQuestionAnswerList.add(questionAnswerList);
        }
    }

    private void setTimer() {
        timer = new Timer(1000, new ActionListener() {
            int time_limit = room.getTime_limit() * 60;
            int hours = time_limit / 3600;
            int minutes = time_limit % 3600 / 60;
            int seconds = time_limit - hours * 3600 - minutes * 60;

            String h, m, s, timeFormat;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (time_limit == 0) {
                    timer.stop();
                    dispose();
                    JOptionPane.showMessageDialog(
                            panelViewTakeExamAttendee,
                            "Hết giờ làm bài thi!",
                            "Thông Báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    executeAndShowResult();
                }
                if (hours < 10) {
                    h = "0" + hours;
                } else {
                    h = String.valueOf(hours);
                }
                if (minutes < 10) {
                    m = "0" + minutes;
                } else {
                    m = String.valueOf(minutes);
                }
                if (seconds < 10) {
                    s = "0" + seconds;
                } else {
                    s = String.valueOf(seconds);
                }
                timeFormat = String.format("%s:%s:%s", h, m, s);
                labelCountDownClockViewTakeExamAttendee.setText(timeFormat);
                if (seconds == 0) {
                    seconds = 59;
                    if (minutes == 0) {
                        minutes = 59;
                        hours--;
                    } else {
                        minutes--;
                    }
                } else {
                    seconds--;
                }
                time_limit--;
            }
        });
        timer.start();
    }

    private void createUIComponents() {
    }
}
