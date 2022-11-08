package View;

import DAO.EnrollmentDAO;
import Model.Enrollment;
import Model.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class ResultAttendee extends JFrame {
    private final User loginUser;
    private JTextField textfieldFindViewResultAttendee;
    private JTable tableViewResultAttendee;
    private JButton buttonBackViewResutlAttendee;
    private JLabel labelFindViewResultAttendee;
    private JPanel panelViewResultAttendee;
    private DefaultTableModel columnModel;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter;

    public ResultAttendee(User loginUser) {
        this.loginUser = loginUser;
        this.setTitle("Xem điểm thi");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(panelViewResultAttendee);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initComponents();
        addActionEvent();
        fillDataToTable();
        makeTableSearchable();
    }

    public static void main(String[] args) {
        var attendee = new User("attendee", "attendee", "attendee", false);
        EventQueue.invokeLater(() -> new ResultAttendee(attendee));
    }

    private void initComponents() {
        tableViewResultAttendee.setDefaultEditor(Object.class, null);
        tableViewResultAttendee.getTableHeader().setReorderingAllowed(false);
        columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã phòng thi", "Điểm thi"}
        );
        tableViewResultAttendee.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewResultAttendee.getModel();
    }

    private void addActionEvent() {
        buttonBackViewResutlAttendee.addActionListener(event -> {
            this.dispose();
            new MenuAttendee(loginUser);
        });
    }

    private void fillDataToTable() {
        List<Enrollment> list = EnrollmentDAO.selectByUserID(loginUser.getUser_id());
        rowModel.setRowCount(0);
        for (var enrollment : list) {
            rowModel.addRow(new Object[]{
                    enrollment.getRoom_id(),
                    enrollment.getScore()
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
        tableViewResultAttendee.setRowSorter(rowSorter);
        textfieldFindViewResultAttendee
                .getDocument()
                .addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        var text = textfieldFindViewResultAttendee.getText().strip();
                        if (text.length() != 0) {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        } else {
                            rowSorter.setRowFilter(null);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        var text = textfieldFindViewResultAttendee.getText().strip();
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
