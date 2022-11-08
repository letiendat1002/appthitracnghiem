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


public class RoomResultSummary extends JFrame {
    private final User loginUser;
    private JPanel panelViewRoomResultSummary;
    private JTextField textfieldFindViewRoomResultSummary;
    private JTable tableViewRoomResultSummary;
    private JButton buttonBackViewRoomResultSummary;
    private JLabel labelFindViewRoomResultSummary;

    private DefaultTableModel columnModel;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter;

    public RoomResultSummary(User loginUser) {
        this.loginUser = loginUser;
        this.setTitle("Tổng kết điểm");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(panelViewRoomResultSummary);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initComponents();
        addActionEvent();
        fillDataToTable();
        makeTableSearchable();
    }

    public static void main(String[] args) {
        var admin = new User("admin", "admin", "admin", true);
        EventQueue.invokeLater(() -> new RoomResultSummary(admin));
    }

    private void createUIComponents() {
    }

    private void initComponents() {
        tableViewRoomResultSummary.setDefaultEditor(Object.class, null);
        tableViewRoomResultSummary.getTableHeader().setReorderingAllowed(false);
        columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"UserID", "Mã phòng thi", "Điểm thi"}
        );
        tableViewRoomResultSummary.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewRoomResultSummary.getModel();
    }

    private void addActionEvent() {
        buttonBackViewRoomResultSummary.addActionListener(event -> {
            this.dispose();
            new RoomManagement(loginUser);
        });
    }

    private void fillDataToTable() {
        List<Enrollment> list = EnrollmentDAO.selectAll();
        rowModel.setRowCount(0);
        for (var enrollment : list) {
            rowModel.addRow(new Object[]{
                    enrollment.getUser_id(),
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
        tableViewRoomResultSummary.setRowSorter(rowSorter);
        textfieldFindViewRoomResultSummary
                .getDocument()
                .addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        var text = textfieldFindViewRoomResultSummary.getText().strip();
                        if (text.length() != 0) {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        } else {
                            rowSorter.setRowFilter(null);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        var text = textfieldFindViewRoomResultSummary.getText().strip();
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
