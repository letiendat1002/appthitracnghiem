package View;

import DAO.ExamDAO;
import Model.Exam;
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

public class ExamManagement extends JFrame {
    private final User loginUser;
    private JTextField textfieldSubjectNameViewExamManagement;
    private JTextField textfieldTotalQuestionViewExamManagement;
    private JTextField textfieldTotalScoreViewExamManagement;
    private JButton buttonAddViewExamManagement;
    private JButton buttonUpdateViewExamManagement;
    private JButton buttonDeleteViewExamManagement;
    private JButton buttonBackViewExamManagement;
    private JTextField textfieldFindViewExamManagement;
    private JTable tableViewExamManagement;
    private JLabel labelSubjectIDViewExamManagement;
    private JLabel labelTotalQuestionViewExamManagement;
    private JLabel labelTotalScoreViewExamManagement;
    private JLabel labelFindViewExamManagement;
    private JPanel panelViewExamManagement;
    private JButton buttonRefreshViewExamManagement;
    private JTextField textfieldExamIDViewExamManagement;
    private JLabel labelExamIDViewExamManagement;
    private DefaultTableModel columnModel;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter = null;
    private List<Exam> list;
    private Exam chosenExam = null;

    public ExamManagement(User user) {
        this.loginUser = user;
        initComponents();
        addActionEvent();
        this.setTitle("Quản Lý Đề Thi");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewExamManagement);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        fillDataToTable();
        makeTableSearchable();
    }

    public static void main(String[] args) {
        User admin = new User("admin", "admin", "admin", true);
        EventQueue.invokeLater(() -> new ExamManagement(admin));
    }

    private void initComponents() {
        textfieldExamIDViewExamManagement.setEnabled(false);
        tableViewExamManagement.setDefaultEditor(Object.class, null);
        tableViewExamManagement.getTableHeader().setReorderingAllowed(false);
        columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Đề Thi", "Tên Môn Thi", "Số Câu Hỏi", "Điểm Tổng", "Điểm Mỗi Câu"}
        );
        tableViewExamManagement.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewExamManagement.getModel();
    }

    private void addActionEvent() {
        tableViewExamManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableViewExamManagementMouseClicked();
            }

            private void tableViewExamManagementMouseClicked() {
                resetInputField();
                textfieldExamIDViewExamManagement.setEnabled(false);
                var index = tableViewExamManagement.getSelectedRow();
                chosenExam = list.get(index);
                textfieldExamIDViewExamManagement.setText(String.valueOf(chosenExam.getExam_id()));
                textfieldSubjectNameViewExamManagement.setText(chosenExam.getSubject());
                textfieldTotalQuestionViewExamManagement.setText(String.valueOf(chosenExam.getTotal_question()));
                textfieldTotalScoreViewExamManagement.setText(String.valueOf(chosenExam.getTotal_score()));
            }
        });

        buttonAddViewExamManagement.addActionListener(event -> {
            if (textfieldSubjectNameViewExamManagement.getText().isEmpty()
                    || textfieldTotalQuestionViewExamManagement.getText().isEmpty()
                    || textfieldTotalScoreViewExamManagement.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                var subject = textfieldSubjectNameViewExamManagement.getText().strip();
                var totalQuestion = Integer.parseInt(textfieldTotalQuestionViewExamManagement.getText().strip());
                var totalScore = Integer.parseInt(textfieldTotalScoreViewExamManagement.getText().strip());
                var scorePerQuestion = totalScore / (double) totalQuestion;
                var exam = new Exam(subject, totalQuestion, totalScore, scorePerQuestion);
                var isSuccess = ExamDAO.insert(exam);
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
            }
        });

        buttonUpdateViewExamManagement.addActionListener(event -> {
            if (textfieldExamIDViewExamManagement.getText().isEmpty()
                    || textfieldSubjectNameViewExamManagement.getText().isEmpty()
                    || textfieldTotalQuestionViewExamManagement.getText().isEmpty()
                    || textfieldTotalScoreViewExamManagement.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                var exam_id = Long.parseLong(textfieldExamIDViewExamManagement.getText().strip());
                var subject = textfieldSubjectNameViewExamManagement.getText().strip();
                var totalQuestion = Integer.parseInt(textfieldTotalQuestionViewExamManagement.getText().strip());
                var totalScore = Integer.parseInt(textfieldTotalScoreViewExamManagement.getText().strip());
                var scorePerQuestion = totalScore / (double) totalQuestion;
                var exam = new Exam(exam_id, subject, totalQuestion, totalScore, scorePerQuestion);
                var isSuccess = ExamDAO.update(exam);
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
            }
        });

        buttonDeleteViewExamManagement.addActionListener(event -> {
            var examID = textfieldExamIDViewExamManagement.getText().strip();
            if (!examID.isEmpty()) {
                var isSuccess = ExamDAO.delete(Long.parseLong(examID));
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
                        "Hãy chọn đề thi cần xoá để tiến hành xoá!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        buttonRefreshViewExamManagement.addActionListener(event -> {
            resetInputField();
            textfieldFindViewExamManagement.setText("");
        });

        buttonBackViewExamManagement.addActionListener(event -> {
            if (loginUser.getUser_id().equals("admin")) {
                this.dispose();
                new MenuAdmin(loginUser);
            } else {
                this.dispose();
                new MenuHost(loginUser);
            }
        });
    }

    private void fillDataToTable() {
        list = ExamDAO.selectAll();
        rowModel.setRowCount(0);
        for (var exam : list) {
            rowModel.addRow(new Object[]{
                    exam.getExam_id(),
                    exam.getSubject(),
                    exam.getTotal_question(),
                    exam.getTotal_score(),
                    exam.getScore_per_question()
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
        tableViewExamManagement.setRowSorter(rowSorter);
        textfieldFindViewExamManagement
                .getDocument()
                .addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        var text = textfieldFindViewExamManagement.getText().strip();
                        if (text.length() != 0) {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        } else {
                            rowSorter.setRowFilter(null);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        var text = textfieldFindViewExamManagement.getText().strip();
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

    private void resetInputField() {
        textfieldExamIDViewExamManagement.setText("");
        textfieldSubjectNameViewExamManagement.setText("");
        textfieldTotalQuestionViewExamManagement.setText("");
        textfieldTotalScoreViewExamManagement.setText("");
    }

    private void createUIComponents() {
    }
}
