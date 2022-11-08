package View;

import DAO.QuestionAnswerDAO;
import DAO.QuestionDAO;
import Model.QuestionAnswer;
import Model.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class QuestionAnswerManagement extends JFrame {
    private final User loginUser;
    private JTextField textfieldFindViewQuestionAnswerManagement;
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
    private JLabel labelFindViewQuestionAnswerManagement;
    private JPanel panelViewQuestionAnswerManagement;
    private JButton buttonRefreshViewQuestionAnswerManagement;
    private JTextField textfieldQuestionAnswerIDViewQuestionAnswerManagement;
    private JLabel labelQuestionAnswerIDViewQuestionAnswerManagement;
    private DefaultTableModel columnModel;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter = null;
    private List<QuestionAnswer> list;
    private QuestionAnswer chosenQuestionAnswer = null;

    public QuestionAnswerManagement(User user) {
        this.loginUser = user;
        initComponents();
        addActionEvent();
        this.setTitle("Quản Lý Đáp Án Câu Hỏi");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewQuestionAnswerManagement);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        fillDataToTable();
        makeTableSearchable();
    }

    public static void main(String[] args) {
        User admin = new User("admin", "admin", "admin", true);
        EventQueue.invokeLater(() -> new QuestionAnswerManagement(admin));
    }

    private void createUIComponents() {
    }

    private void initComponents() {
        textfieldQuestionAnswerIDViewQuestionAnswerManagement.setEnabled(false);
        tableViewQuestionAnswerManagement.setDefaultEditor(Object.class, null);
        tableViewQuestionAnswerManagement.getTableHeader().setReorderingAllowed(false);
        columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Đáp án", "Mã Câu hỏi", "Nội dung", "Đáp án đúng"}
        );
        tableViewQuestionAnswerManagement.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewQuestionAnswerManagement.getModel();
    }

    private void addActionEvent() {
        tableViewQuestionAnswerManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableViewQuestionAnswerManagementMouseClicked();
            }

            private void tableViewQuestionAnswerManagementMouseClicked() {
                resetInputField();
                textfieldQuestionAnswerIDViewQuestionAnswerManagement.setEnabled(false);
                var index = tableViewQuestionAnswerManagement.getSelectedRow();
                chosenQuestionAnswer = list.get(index);
                textfieldQuestionAnswerIDViewQuestionAnswerManagement.setText(String.valueOf(chosenQuestionAnswer.getQuestion_answer_id()));
                textfieldQuestionIDViewQuestionAnswerManagement.setText(String.valueOf(chosenQuestionAnswer.getQuestion_id()));
                textfieldAnswerContentViewQuestionAnswerManagement.setText(chosenQuestionAnswer.getContent());
                checkboxCorrectAnswerViewQuestionAnswerManagement.setSelected(chosenQuestionAnswer.isCorrect());
            }
        });

        buttonAddViewQuestionAnswerManagement.addActionListener(event -> {
            if (textfieldQuestionIDViewQuestionAnswerManagement.getText().isEmpty()
                    || textfieldAnswerContentViewQuestionAnswerManagement.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            var question_id = Long.parseLong(textfieldQuestionIDViewQuestionAnswerManagement.getText().strip());
            var checkValidQuestionID = QuestionDAO.selectByID(question_id);
            if (checkValidQuestionID == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Mã câu hỏi không tồn tại. Hãy kiểm tra và thử lại sau!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            var content = textfieldAnswerContentViewQuestionAnswerManagement.getText().strip();
            var isCorrect = checkboxCorrectAnswerViewQuestionAnswerManagement.isSelected();
            var questionAnswer = new QuestionAnswer(question_id, content, isCorrect);
            var isSuccess = QuestionAnswerDAO.insert(questionAnswer);
            if (isSuccess) {
                JOptionPane.showMessageDialog(
                        this,
                        "Thêm thành công.",
                        "Thông Báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                fillDataToTable();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Thêm thất bại. Xin hãy thử lại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            resetInputField();
        });

        buttonUpdateViewQuestionAnswerManagement.addActionListener(event -> {
            if (textfieldQuestionAnswerIDViewQuestionAnswerManagement.getText().isEmpty()
                    || textfieldQuestionIDViewQuestionAnswerManagement.getText().isEmpty()
                    || textfieldAnswerContentViewQuestionAnswerManagement.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            var question_answer_id = Long.parseLong(textfieldQuestionAnswerIDViewQuestionAnswerManagement.getText().strip());
            var question_id = Long.parseLong(textfieldQuestionIDViewQuestionAnswerManagement.getText().strip());
            var checkValidQuestionID = QuestionDAO.selectByID(question_id);
            if (checkValidQuestionID == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Mã câu hỏi không tồn tại. Hãy kiểm tra và thử lại sau!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            var content = textfieldAnswerContentViewQuestionAnswerManagement.getText().strip();
            var isCorrect = checkboxCorrectAnswerViewQuestionAnswerManagement.isSelected();
            var questionAnswer = new QuestionAnswer(question_answer_id, question_id, content, isCorrect);
            var isSuccess = QuestionAnswerDAO.update(questionAnswer);
            if (isSuccess) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật thành công.",
                        "Thông Báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                fillDataToTable();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật thất bại. Xin hãy thử lại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            resetInputField();
        });

        buttonDeleteViewQuestionAnswerManagement.addActionListener(event -> {
            var questionAnswerID = textfieldQuestionAnswerIDViewQuestionAnswerManagement.getText().strip();
            if (!questionAnswerID.isEmpty()) {
                var isSuccess = QuestionAnswerDAO.delete(Long.parseLong(questionAnswerID));
                if (isSuccess) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Xoá thành công.",
                            "Thông Báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    fillDataToTable();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Xoá thất bại. Xin hãy thử lại!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                resetInputField();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Hãy chọn đáp án cần xoá để tiến hành xoá!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        buttonRefreshViewQuestionAnswerManagement.addActionListener(event -> {
            resetInputField();
            labelFindViewQuestionAnswerManagement.setText("");
        });

        buttonBackViewQuestionAnswerManagement.addActionListener(event -> {
            this.dispose();
        });
    }

    private void resetInputField() {
        textfieldQuestionAnswerIDViewQuestionAnswerManagement.setText("");
        textfieldQuestionIDViewQuestionAnswerManagement.setText("");
        textfieldAnswerContentViewQuestionAnswerManagement.setText("");
        checkboxCorrectAnswerViewQuestionAnswerManagement.setSelected(false);
    }

    private void fillDataToTable() {
        list = QuestionAnswerDAO.selectAll();
        rowModel.setRowCount(0);
        for (var questionAnswer : list) {
            rowModel.addRow(new Object[]{
                    questionAnswer.getQuestion_answer_id(),
                    questionAnswer.getQuestion_id(),
                    questionAnswer.getContent(),
                    questionAnswer.isCorrect()
            });
        }
    }

    private void makeTableSearchable() {
        rowSorter = new TableRowSorter<>(rowModel);
        var i = 0;
        while (i < columnModel.getColumnCount()) {
            rowSorter.setSortable(i, false);
            ++i;
        }
        tableViewQuestionAnswerManagement.setRowSorter(rowSorter);
        textfieldFindViewQuestionAnswerManagement
                .getDocument()
                .addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        var text = textfieldFindViewQuestionAnswerManagement.getText().strip();
                        if (text.length() != 0) {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        } else {
                            rowSorter.setRowFilter(null);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        var text = textfieldFindViewQuestionAnswerManagement.getText().strip();
                        if (text.length() != 0) {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        } else {
                            rowSorter.setRowFilter(null);
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                    }
                });
    }
}

