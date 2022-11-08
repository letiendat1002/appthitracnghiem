package View;

import Model.Question;
import Model.QuestionAnswer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuestionAndAnswerForm extends JPanel{
    private JLabel labelQuestionContentViewTakeExamAttendee;
    private JPanel panelAnswerGroupViewTakeExamAttendee;
    private JPanel panelTakeExamViewTakeExamAttendee;
    private JButton buttonClearChoiceViewTakeExamAttendee;

    private ButtonGroup buttonGroup;

    public QuestionAndAnswerForm(Question question, List<QuestionAnswer> questionAnswerList){
        initComponents();
        addEvents();
        showFormContent(question, questionAnswerList);
        this.add(panelTakeExamViewTakeExamAttendee);
    }


    private void initComponents() {
        buttonGroup = new ButtonGroup();
        panelAnswerGroupViewTakeExamAttendee.setLayout(new GridLayout(5,1));
    }
    private void addEvents() {
        buttonClearChoiceViewTakeExamAttendee.addActionListener(event -> {
            buttonGroup.clearSelection();
        });
    }

    private void showFormContent(Question question, List<QuestionAnswer> questionAnswerList) {
        labelQuestionContentViewTakeExamAttendee.setText(question.getContent());
        for(var questionAnswer : questionAnswerList){
            var radioButton = new JRadioButton(questionAnswer.getContent());
            radioButton.setFont(labelQuestionContentViewTakeExamAttendee.getFont());
            radioButton.setActionCommand(questionAnswer.getContent());
            buttonGroup.add(radioButton);
            panelAnswerGroupViewTakeExamAttendee.add(radioButton);
        }
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }
}
