package View;

import Model.Question;
import Model.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class ResultForm extends JFrame {
    private final User loginUser;
    private final List<Question> questionList;
    private final List<String> chosenAnswerList;
    private final List<String> correctAnswerList;
    private final List<String> resultList;
    private final double score;
    private final int totalCorrect;
    private JTable tableViewResultForm;
    private JButton buttonQuitViewResultForm;
    private JLabel labelResultViewResultForm;
    private JPanel panelViewResultForm;
    private JTextField textfieldFindViewResultForm;
    private JLabel labelFindViewResultForm;
    private DefaultTableModel columnModel;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter;

    public ResultForm(User loginUser,
                      List<Question> questionList,
                      List<String> chosenAnswerList,
                      List<String> correctAnswerList,
                      List<String> resultList,
                      int totalCorrect,
                      double score) {
        this.loginUser = loginUser;
        this.questionList = questionList;
        this.chosenAnswerList = chosenAnswerList;
        this.correctAnswerList = correctAnswerList;
        this.resultList = resultList;
        this.totalCorrect = totalCorrect;
        this.score = score;
        this.setTitle("Kết Quả Làm Bài");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(panelViewResultForm);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initComponents();
        addActionEvent();
        fillData();
        makeTableSearchable();
    }

    public static void main(String[] args) {
        //EventQueue.invokeLater(() -> new ResultForm(null, null, null, null));
    }

    private void initComponents() {
        tableViewResultForm.setDefaultEditor(Object.class, null);
        tableViewResultForm.getTableHeader().setReorderingAllowed(false);
        columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Câu hỏi", "Đáp án chọn", "Đáp án đúng", "Kết quả"}
        );
        tableViewResultForm.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewResultForm.getModel();
    }

    private void addActionEvent() {
        buttonQuitViewResultForm.addActionListener(event -> {
            this.dispose();
            new MenuAttendee(loginUser);
        });
    }

    private void fillData() {
        rowModel.setRowCount(0);
        var index = 0;
        for (var question : questionList) {
            var chosenAnswer = chosenAnswerList.get(index);
            var correctAnswer = correctAnswerList.get(index);
            var result = resultList.get(index);
            index++;
            rowModel.addRow(new Object[]{
                    question.getContent(),
                    chosenAnswer,
                    correctAnswer,
                    result
            });
        }
        var resultText = "KẾT QUẢ: "
                + totalCorrect
                + " / "
                + questionList.size()
                + " câu - ĐIỂM: "
                + score;
        labelResultViewResultForm.setText(resultText);
    }

    private void makeTableSearchable() {
        rowSorter = new TableRowSorter<>(rowModel);
        var i = 0;
        while (i < columnModel.getColumnCount()) {
            rowSorter.setSortable(i, false);
            ++i;
        }
        tableViewResultForm.setRowSorter(rowSorter);
        textfieldFindViewResultForm
                .getDocument()
                .addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        var text = textfieldFindViewResultForm.getText().strip();
                        if (text.length() != 0) {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        } else {
                            rowSorter.setRowFilter(null);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        var text = textfieldFindViewResultForm.getText().strip();
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
