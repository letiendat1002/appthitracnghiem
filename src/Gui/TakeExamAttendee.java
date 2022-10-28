package Gui;

import Model.User;

import javax.swing.*;

public class TakeExamAttendee {
    private JButton buttonStartViewTakeExamAttendee;
    private JButton buttonSubmitViewTakeExamAttendee;
    private JButton buttonQuitViewTakeExamAttendee;
    private JButton buttonPrevViewTakeExamAttendee;
    private JButton buttonNextViewTakeExamAttendee;
    private JLabel labelRoomTitleViewTakeExamAttendee;
    private JLabel labelRoomIDViewTakeExamAttendee;
    private JLabel labelTimeLimitViewTakeExamAttendee;
    private JLabel labelTotalQuestionViewTakeExamAttendee;
    private JLabel labelDataRoomIDViewTakeExamAttendee;
    private JLabel labelDataTimeLimitViewTakeExamAttendee;
    private JLabel labelDataTotalQuestionViewTakeExamAttendee;
    private JLabel labelCountDownClockViewTakeExamAttendee;
    private JPanel panelViewTakeExamAttendee;
    private JPanel panelTakeExamViewTakeExamAttendee;
    private JLabel labelCurrentQuestionViewTakeExamAttendee;
    private JLabel labelQuestionContentViewTakeExamAttendee;
    private JPanel panelAnswerGroupViewTakeExamAttendee;

    private final User loginUser;

    public TakeExamAttendee(User loginUser) {
        this.loginUser = loginUser;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
