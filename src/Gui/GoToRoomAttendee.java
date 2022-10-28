package Gui;

import DAO.RoomDAO;
import Model.User;

import javax.swing.*;
import java.awt.*;

public class GoToRoomAttendee extends JFrame {
    private final User loginUser;
    private JTextField textfieldRoomIDViewGoToRoomAttendee;
    private JPasswordField passwordfieldPasswordViewGoToRoomAttendee;
    private JButton buttonGoToRoomViewGoToRoomAttendee;
    private JButton buttonBackViewGoToRoomAttendee;
    private JLabel labelRoomIDViewGoToRoomAttendee;
    private JLabel labelPasswordViewGoToRoomAttendee;
    private JPanel panelViewGoToRoomAttendee;
    private JCheckBox checkboxShowPasswordViewGoToRoomAttendee;

    public GoToRoomAttendee(User loginUser) {
        this.loginUser = loginUser;
        addActionEvent();
        this.setTitle("Vào Phòng Thi");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewGoToRoomAttendee);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        User attendee = new User("attendee", "attendee", "attendee", false);
        EventQueue.invokeLater(() -> new GoToRoomAttendee(attendee));
    }

    private void addActionEvent() {
        checkboxShowPasswordViewGoToRoomAttendee.addActionListener(event -> {
            if (checkboxShowPasswordViewGoToRoomAttendee.isSelected()) {
                passwordfieldPasswordViewGoToRoomAttendee.setEchoChar((char) 0);
            } else {
                passwordfieldPasswordViewGoToRoomAttendee.setEchoChar('*');
            }
        });

        buttonGoToRoomViewGoToRoomAttendee.addActionListener(event -> {
            var roomID = textfieldRoomIDViewGoToRoomAttendee.getText().trim();
            var password = String.valueOf(passwordfieldPasswordViewGoToRoomAttendee.getPassword()).trim();
            if (roomID.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tên đăng nhập hoặc mật khẩu không được bỏ trống!",
                        "Cảnh Báo Đăng Nhập",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                var room = RoomDAO.selectByRoomIDAndPassword(roomID, password);
                if (room != null) {
                    this.dispose();
                    new TakeExamAttendee(loginUser);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Sai mã phòng hoặc mật khẩu phòng",
                            "Cảnh Báo",
                            JOptionPane.WARNING_MESSAGE
                    );
                    passwordfieldPasswordViewGoToRoomAttendee.setText("");
                }
            }
        });

        buttonBackViewGoToRoomAttendee.addActionListener(event -> {
            this.dispose();
            new MenuAttendee(loginUser);
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
