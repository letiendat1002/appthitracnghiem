package gui;

import javax.swing.*;

public class QuestionAnswerManagement extends JFrame {
    private JTextField textfieldFindByQuestionIDViewQuestionAnswerManagement;
    private JTextField textfieldFindByAnswerIDViewQuestionAnswerManagement;
    private JTable tableViewQuestionAnswerManagement;
    private JCheckBox checkboxCorrectAnswerViewQuestionAnswerManagement;
    private JButton buttonAddViewQuestionAnswerManagement;
    private JButton buttonUpdateViewQuestionAnswerManagement;
    private JButton buttonDeleteViewQuestionAnswerManagement;
    private JButton buttonBackViewQuestionAnswerManagement;
    private JTextField textfieldQuestionIDViewQuestionAnswerManagement;
    private JTextField textfieldAnswerContentViewQuestionAnswerManagement;
    private JLabel labelQuestionIDViewQuestionAnswerManagement;
    private JLabel labelAnswerContentViewQuestionAnswerManagement;
    private JLabel labelFindByQuestionIDViewQuestionAnswerManagement;
    private JLabel labelFindByAnswerIDViewQuestionAnswerManagement;
    private JPanel panelViewQuestionAnswerManagement;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public QuestionAnswerManagement(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args){
        JFrame frame = new QuestionAnswerManagement("Quản Lý Đáp Án Câu Hỏi");
        frame.setVisible(true);
    }
}

