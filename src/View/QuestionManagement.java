package View;

import DAO.ExamDAO;
import DAO.QuestionDAO;
import Model.Question;
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

public class QuestionManagement extends JFrame {
    private final User loginUser;
    private JPanel panelViewQuestionManagement;
    private JTextField textfieldExamIDViewQuestionManagement;
    private JTextField textfieldQuestionContentViewQuestionManagement;
    private JButton buttonAddViewQuestionManagement;
    private JButton buttonUpdateViewQuestionManagement;
    private JButton buttonDeleteViewQuestionManagement;
    private JTable tableViewQuestionManagement;
    private JButton buttonQuestionAnswerViewQuestionMangagement;
    private JButton buttonBackViewQuestionManagement;
    private JTextField textfieldFindViewQuestionManagement;
    private JLabel labelFindViewQuestionManagement;
    private JLabel labelExamIDViewQuestionManagement;
    private JLabel labelQuestionContentViewQuestionManagement;
    private JLabel labelLevelViewQuestionManagenment;
    private JComboBox<String> comboboxLevelViewQuestionManagement;
    private JTextField textfieldQuestionIDViewQuestionManagement;
    private JLabel labelQuestionIDViewQuestionManagement;
    private JButton buttonRefreshViewQuestionManagement;
    private DefaultTableModel columnModel;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter = null;
    private List<Question> list;
    private Question chosenQuestion = null;

    public QuestionManagement(User user) {
        this.loginUser = user;
        initComponents();
        addActionEvent();
        this.setTitle("Quản Lý Câu Hỏi");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewQuestionManagement);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        fillDataToTable();
        makeTableSearchable();
    }

    public static void main(String[] args) {
        User admin = new User("admin", "admin", "admin", true);
        EventQueue.invokeLater(() -> new QuestionManagement(admin));
    }

    private void initComponents() {
        textfieldQuestionIDViewQuestionManagement.setEnabled(false);
        tableViewQuestionManagement.setDefaultEditor(Object.class, null);
        tableViewQuestionManagement.getTableHeader().setReorderingAllowed(false);
        columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Câu hỏi", "Mã Đề thi", "Mức độ khó", "Nội dung"}
        );
        tableViewQuestionManagement.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewQuestionManagement.getModel();
    }

    private void addActionEvent() {
        tableViewQuestionManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableViewQuestionManagementMouseClicked();
            }

            private void tableViewQuestionManagementMouseClicked() {
                resetInputField();
                textfieldQuestionIDViewQuestionManagement.setEnabled(false);
                var index = tableViewQuestionManagement.getSelectedRow();
                chosenQuestion = list.get(index);
                textfieldQuestionIDViewQuestionManagement.setText(String.valueOf(chosenQuestion.getQuestion_id()));
                textfieldExamIDViewQuestionManagement.setText(String.valueOf(chosenQuestion.getExam_id()));
                textfieldQuestionContentViewQuestionManagement.setText(chosenQuestion.getContent());
                comboboxLevelViewQuestionManagement.setSelectedIndex(chosenQuestion.getLevel() - 1);
            }
        });

        buttonAddViewQuestionManagement.addActionListener(e -> {
            if (textfieldExamIDViewQuestionManagement.getText().isEmpty()
                    || textfieldQuestionContentViewQuestionManagement.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            var exam_id = Long.parseLong(textfieldExamIDViewQuestionManagement.getText().strip());
            var checkValidExamID = ExamDAO.selectByID(exam_id);
            if (checkValidExamID == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Mã đề thi không tồn tại. Hãy kiểm tra và thử lại sau!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            var level = comboboxLevelViewQuestionManagement.getSelectedIndex() + 1;
            var content = textfieldQuestionContentViewQuestionManagement.getText().strip();
            var question = new Question(exam_id, level, content);
            var isSuccess = QuestionDAO.insert(question);
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

        buttonUpdateViewQuestionManagement.addActionListener(e -> {
            if (textfieldQuestionIDViewQuestionManagement.getText().isEmpty()
                    || textfieldExamIDViewQuestionManagement.getText().isEmpty()
                    || textfieldQuestionContentViewQuestionManagement.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            var question_id = Long.parseLong(textfieldQuestionIDViewQuestionManagement.getText().strip());
            var exam_id = Long.parseLong(textfieldExamIDViewQuestionManagement.getText().strip());
            var checkValidExamID = ExamDAO.selectByID(exam_id);
            if (checkValidExamID == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Mã đề thi không tồn tại. Hãy kiểm tra và thử lại sau!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            var level = comboboxLevelViewQuestionManagement.getSelectedIndex() + 1;
            var content = textfieldQuestionContentViewQuestionManagement.getText().strip();
            var question = new Question(question_id, exam_id, level, content);
            var isSuccess = QuestionDAO.update(question);
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

        buttonDeleteViewQuestionManagement.addActionListener(e -> {
            var questionID = textfieldQuestionIDViewQuestionManagement.getText().strip();
            if (!questionID.isEmpty()) {
                var isSuccess = QuestionDAO.delete(Long.parseLong(questionID));
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
                        "Hãy chọn câu hỏi cần xoá để tiến hành xoá!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        buttonQuestionAnswerViewQuestionMangagement.addActionListener(e -> {
            new QuestionAnswerManagement(loginUser);
        });

        buttonRefreshViewQuestionManagement.addActionListener(e -> {
            resetInputField();
            textfieldFindViewQuestionManagement.setText("");
        });

        buttonBackViewQuestionManagement.addActionListener(e -> {
            if (loginUser.getUser_id().equals("admin")) {
                this.dispose();
                new MenuAdmin(loginUser);
            } else {
                this.dispose();
                new MenuHost(loginUser);
            }
        });
    }

    private void resetInputField() {
        textfieldQuestionIDViewQuestionManagement.setText("");
        textfieldExamIDViewQuestionManagement.setText("");
        textfieldQuestionContentViewQuestionManagement.setText("");
        comboboxLevelViewQuestionManagement.setSelectedIndex(0);
    }

    private void fillDataToTable() {
        list = QuestionDAO.selectAll();
        rowModel.setRowCount(0);
        for (var question : list) {
            rowModel.addRow(new Object[]{
                    question.getQuestion_id(),
                    question.getExam_id(),
                    question.getLevel(),
                    question.getContent()
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
        tableViewQuestionManagement.setRowSorter(rowSorter);
        textfieldFindViewQuestionManagement
                .getDocument()
                .addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        var text = textfieldFindViewQuestionManagement.getText().strip();
                        if (text.length() != 0) {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        } else {
                            rowSorter.setRowFilter(null);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        var text = textfieldFindViewQuestionManagement.getText().strip();
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

    private void createUIComponents() {
    }
}
