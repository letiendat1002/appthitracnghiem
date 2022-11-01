package Gui;

import DAO.UserDAO;
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

public class UserManagement extends JFrame {
    private final User loginUser;
    private JTextField textfieldUserIDViewUserManagement;
    private JTextField textfieldFullnameViewUserManagement;
    private JTextField textfieldPasswordViewUserManagement;
    private JRadioButton radiobuttonHostViewUserManagement;
    private JRadioButton radiobuttonAttendeeViewUserManagement;
    private JButton buttonAddViewUserManagement;
    private JButton buttonBackViewUserManagement;
    private JTextField textfieldFindViewUserManagement;
    private JTable tableViewUserManagement;
    private JLabel labelFullNameViewUserManagement;
    private JLabel labelUserIDViewUserManagement;
    private JLabel labelPasswordViewUserManagement;
    private JLabel labelUserRoleViewUserManagement;
    private JButton buttonUpdateViewUserManagement;
    private JButton buttonDeleteViewUserManagement;
    private JLabel labelFindViewUserManagement;
    private JPanel panelViewUserManagement;
    private JButton buttonRefreshViewUserManagement;
    private JCheckBox checkboxChangePasswordViewUserManagement;
    private ButtonGroup buttonGroupViewUserManagement;

    private DefaultTableModel columnModel;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter = null;
    private List<User> list;
    private String passwordBeforeChanged;
    private User chosenUser = null;

    public UserManagement(User user) {
        this.loginUser = user;
        initComponents();
        addActionEvent();
        this.setTitle("Quản Lý Người Dùng");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewUserManagement);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        fillDataToTable();
        makeTableSearchable();
    }

    public static void main(String[] args) {
        User admin = new User("admin", "admin", "admin", true);
        EventQueue.invokeLater(() -> new UserManagement(admin));
    }

    private void initComponents() {
        buttonGroupViewUserManagement = new ButtonGroup();
        buttonGroupViewUserManagement.add(radiobuttonHostViewUserManagement);
        buttonGroupViewUserManagement.add(radiobuttonAttendeeViewUserManagement);
        tableViewUserManagement.setDefaultEditor(Object.class, null);
        tableViewUserManagement.getTableHeader().setReorderingAllowed(false);
        columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"UserID", "Họ Tên", "Mật Khẩu", "Host"}
        );
        tableViewUserManagement.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewUserManagement.getModel();
    }

    private void addActionEvent() {
        tableViewUserManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableViewUserManagementMouseClicked();
            }

            private void tableViewUserManagementMouseClicked() {
                resetInputField();
                textfieldUserIDViewUserManagement.setEnabled(false);
                checkboxChangePasswordViewUserManagement.setSelected(false);
                textfieldPasswordViewUserManagement.setEnabled(false);
                var index = tableViewUserManagement.getSelectedRow();
                chosenUser = list.get(index);
                passwordBeforeChanged = chosenUser.getPassword();
                textfieldUserIDViewUserManagement.setText(chosenUser.getUser_id());
                textfieldFullnameViewUserManagement.setText(chosenUser.getFull_name());
                textfieldPasswordViewUserManagement.setText(passwordBeforeChanged);
                if (chosenUser.isHost()) {
                    radiobuttonHostViewUserManagement.setSelected(true);
                } else {
                    radiobuttonAttendeeViewUserManagement.setSelected(true);
                }
            }
        });

        checkboxChangePasswordViewUserManagement.addActionListener(e ->
                textfieldPasswordViewUserManagement.setEnabled(checkboxChangePasswordViewUserManagement.isSelected())
        );

        buttonAddViewUserManagement.addActionListener(event -> {
            var userID = textfieldUserIDViewUserManagement.getText().trim();
            var fullName = textfieldFullnameViewUserManagement.getText().trim();
            var password = textfieldPasswordViewUserManagement.getText().trim();
            var radioHost = radiobuttonHostViewUserManagement.isSelected();
            var radioAttendee = radiobuttonAttendeeViewUserManagement.isSelected();
            var radioIsSelected = radioHost || radioAttendee;
            if (userID.isEmpty() || fullName.isEmpty() || password.isEmpty() || !radioIsSelected) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if (userID.equals("admin")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể thêm tài khoản với UserID này. Xin hãy sử dụng UserID khác!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                textfieldUserIDViewUserManagement.setText("");
                textfieldPasswordViewUserManagement.setText("");
            } else if (verifyAccountNotExist(userID)) {
                var user = new User(userID, fullName, password, radioHost);
                var isSuccess = UserDAO.insert(user);
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
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "UserID đã tồn tại, thử lại với UserID khác!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        buttonUpdateViewUserManagement.addActionListener(event -> {
            var userID = textfieldUserIDViewUserManagement.getText().trim();
            var fullName = textfieldFullnameViewUserManagement.getText().trim();
            var password = textfieldPasswordViewUserManagement.getText().trim();
            var radioHost = radiobuttonHostViewUserManagement.isSelected();
            var radioAttendee = radiobuttonAttendeeViewUserManagement.isSelected();
            var radioIsSelected = radioHost || radioAttendee;
            if (userID.isEmpty() || fullName.isEmpty() || password.isEmpty() || !radioIsSelected) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                var passwordIsChanged = !password.equals(passwordBeforeChanged);
                var password_encrypted = (passwordIsChanged) ? UserDAO.encryptPassword(password) : passwordBeforeChanged;
                var user = new User(
                        userID,
                        fullName,
                        password_encrypted,
                        radioHost
                );
                var isSuccess = UserDAO.update(user);
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

        buttonDeleteViewUserManagement.addActionListener(event -> {
            var userID = textfieldUserIDViewUserManagement.getText().trim();
            if (!userID.isEmpty()) {
                var isSuccess = UserDAO.delete(userID);
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
                        "Hãy chọn tài khoản cần xoá để tiến hành xoá!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        buttonRefreshViewUserManagement.addActionListener(event -> {
            resetInputField();
            textfieldFindViewUserManagement.setText("");
        });

        buttonBackViewUserManagement.addActionListener(event -> {
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
        list = UserDAO.selectAll();
        rowModel.setRowCount(0);
        for (var user : list) {
            rowModel.addRow(new Object[]{
                    user.getUser_id(),
                    user.getFull_name(),
                    user.getPassword(),
                    user.isHost()
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
        tableViewUserManagement.setRowSorter(rowSorter);
        textfieldFindViewUserManagement
                .getDocument()
                .addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        var text = textfieldFindViewUserManagement.getText().trim();
                        if (text.length() != 0) {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        } else {
                            rowSorter.setRowFilter(null);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        var text = textfieldFindViewUserManagement.getText().trim();
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
        textfieldUserIDViewUserManagement.setText("");
        textfieldUserIDViewUserManagement.setEnabled(true);
        textfieldFullnameViewUserManagement.setText("");
        textfieldPasswordViewUserManagement.setText("");
        buttonGroupViewUserManagement.clearSelection();
        checkboxChangePasswordViewUserManagement.setSelected(true);
    }

    private boolean verifyAccountNotExist(String userID) {
        var user = UserDAO.selectByID(userID);
        return user == null;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
