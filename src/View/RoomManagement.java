package View;

import DAO.ExamDAO;
import DAO.RoomDAO;
import Model.Room;
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

public class RoomManagement extends JFrame {
    private final User loginUser;
    private JTextField textfiledExamIDViewRoomManagement;
    private JTextField textfieldRoomTitleViewRoomManaGement;
    private JTextField textfieldTimeLimitViewRoomManagement;
    private JRadioButton radiobuttonOpenViewRoomManagement;
    private JButton buttonAddViewRoomManagement;
    private JButton buttonUpdateViewRoomManagement;
    private JButton buttonDeleteViewRoomManagement;
    private JButton buttonRoomResultSummaryViewRoomManagement;
    private JButton buttonBackViewRoomManagement;
    private JTable tableViewRoomManagement;
    private JRadioButton radiobuttonCloseViewRoomManagement;
    private JLabel labelExamIDViewRoomManagement;
    private JLabel labelRoomTitleViewRoomManagement;
    private JLabel labelTimeLimitViewRoomManagement;
    private JLabel labelRoomPasswordViewRoomManagement;
    private JLabel labelRoomStatusViewRoomManagement;
    private JPanel panelViewRoomManagement;
    private JLabel labelFindViewRoomManagement;
    private JTextField textfieldFindViewRoomManagement;
    private JButton buttonRefreshViewRoomManagement;
    private JTextField textfieldRoomIDViewRoomManagement;
    private JLabel labelRoomIDViewRoomManagement;
    private JTextField textfieldRoomPasswordViewRoomManagement;
    private ButtonGroup buttongroupStatusViewRoomManagement;
    private DefaultTableModel columnModel;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter = null;
    private List<Room> list;
    private Room chosenRoom = null;

    public RoomManagement(User loginUser) {
        this.loginUser = loginUser;
        initComponents();
        addActionEvent();
        this.setTitle("Quản Lý Phòng Thi");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewRoomManagement);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        fillDataToTable();
        makeTableSearchable();
    }

    public static void main(String[] args) {
        User admin = new User("admin", "admin", "admin", true);
        EventQueue.invokeLater(() -> new RoomManagement(admin));
    }

    private void initComponents() {
        textfieldRoomIDViewRoomManagement.setEnabled(false);
        tableViewRoomManagement.setDefaultEditor(Object.class, null);
        tableViewRoomManagement.getTableHeader().setReorderingAllowed(false);
        columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Phòng thi", "Mã Đề thi", "Tiêu đề", "Thời gian", "Mật khẩu", "Trạng thái"}
        );
        tableViewRoomManagement.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewRoomManagement.getModel();
    }

    private void addActionEvent() {
        tableViewRoomManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableViewRoomManagementMouseClicked();
            }

            private void tableViewRoomManagementMouseClicked() {
                resetInputField();
                textfieldRoomIDViewRoomManagement.setEnabled(false);
                var index = tableViewRoomManagement.getSelectedRow();
                chosenRoom = list.get(index);
                textfieldRoomIDViewRoomManagement.setText(String.valueOf(chosenRoom.getRoom_id()));
                textfiledExamIDViewRoomManagement.setText(
                        String.valueOf((chosenRoom.getExam_id() == 0) ? "null" : chosenRoom.getExam_id())
                );
                textfieldRoomTitleViewRoomManaGement.setText(chosenRoom.getTitle());
                textfieldTimeLimitViewRoomManagement.setText(String.valueOf(chosenRoom.getTime_limit()));
                textfieldRoomPasswordViewRoomManagement.setText(chosenRoom.getPassword());
                if (chosenRoom.isAvailable()) {
                    radiobuttonOpenViewRoomManagement.setSelected(true);
                } else {
                    radiobuttonCloseViewRoomManagement.setSelected(true);
                }
            }
        });

        buttonAddViewRoomManagement.addActionListener(event -> {
            var radioOpen = radiobuttonOpenViewRoomManagement.isSelected();
            var radioClose = radiobuttonCloseViewRoomManagement.isSelected();
            var radioIsSelected = radioOpen || radioClose;
            if (textfiledExamIDViewRoomManagement.getText().isEmpty()
                    || textfieldRoomTitleViewRoomManaGement.getText().isEmpty()
                    || textfieldTimeLimitViewRoomManagement.getText().isEmpty()
                    || textfieldRoomPasswordViewRoomManagement.getText().isEmpty()
                    || !radioIsSelected) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            var exam_id = Long.parseLong(textfiledExamIDViewRoomManagement.getText().strip());
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
            var title = textfieldRoomTitleViewRoomManaGement.getText().strip();
            var time_limit = Integer.parseInt(textfieldTimeLimitViewRoomManagement.getText().strip());
            var password = textfieldRoomPasswordViewRoomManagement.getText().strip();
            var is_available = radiobuttonOpenViewRoomManagement.isSelected();
            var room = new Room(exam_id, title, time_limit, password, is_available);
            var isSuccess = RoomDAO.insert(room);
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

        buttonUpdateViewRoomManagement.addActionListener(event -> {
            var radioOpen = radiobuttonOpenViewRoomManagement.isSelected();
            var radioClose = radiobuttonCloseViewRoomManagement.isSelected();
            var radioIsSelected = radioOpen || radioClose;
            if (textfieldRoomIDViewRoomManagement.getText().isEmpty()
                    || textfiledExamIDViewRoomManagement.getText().isEmpty()
                    || textfieldRoomTitleViewRoomManaGement.getText().isEmpty()
                    || textfieldTimeLimitViewRoomManagement.getText().isEmpty()
                    || textfieldRoomPasswordViewRoomManagement.getText().isEmpty()
                    || !radioIsSelected) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            var room_id = Long.parseLong(textfieldRoomIDViewRoomManagement.getText().strip());
            var exam_id = Long.parseLong(textfiledExamIDViewRoomManagement.getText().strip());
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
            var title = textfieldRoomTitleViewRoomManaGement.getText().strip();
            var time_limit = Integer.parseInt(textfieldTimeLimitViewRoomManagement.getText().strip());
            var password = textfieldRoomPasswordViewRoomManagement.getText().strip();
            var is_available = radiobuttonOpenViewRoomManagement.isSelected();
            var room = new Room(room_id, exam_id, title, time_limit, password, is_available);
            var isSuccess = RoomDAO.update(room);
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

        buttonDeleteViewRoomManagement.addActionListener(event -> {
            var roomID = textfieldRoomIDViewRoomManagement.getText().strip();
            if (!roomID.isEmpty()) {
                var isSuccess = RoomDAO.delete(Long.parseLong(roomID));
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
                        "Hãy chọn phòng cần xoá để tiến hành xoá!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        buttonRoomResultSummaryViewRoomManagement.addActionListener(event -> {
            this.dispose();
            new RoomResultSummary(loginUser);
        });

        buttonRefreshViewRoomManagement.addActionListener(event -> {
            resetInputField();
            textfieldFindViewRoomManagement.setText("");
        });

        buttonBackViewRoomManagement.addActionListener(event -> {
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
        textfieldRoomIDViewRoomManagement.setText("");
        textfiledExamIDViewRoomManagement.setText("");
        textfieldRoomTitleViewRoomManaGement.setText("");
        textfieldTimeLimitViewRoomManagement.setText("");
        textfieldRoomPasswordViewRoomManagement.setText("");
        buttongroupStatusViewRoomManagement.clearSelection();
    }

    private void fillDataToTable() {
        list = RoomDAO.selectAll();
        rowModel.setRowCount(0);
        for (var room : list) {
            rowModel.addRow(new Object[]{
                    room.getRoom_id(),
                    (room.getExam_id() == 0) ? "null" : room.getExam_id(),
                    room.getTitle(),
                    room.getTime_limit(),
                    room.getPassword(),
                    room.isAvailable()
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
        tableViewRoomManagement.setRowSorter(rowSorter);
        textfieldFindViewRoomManagement
                .getDocument()
                .addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        var text = textfieldFindViewRoomManagement.getText().strip();
                        if (text.length() != 0) {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        } else {
                            rowSorter.setRowFilter(null);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        var text = textfieldFindViewRoomManagement.getText().strip();
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
