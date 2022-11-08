package View;

import DAO.UserDAO;
import Model.User;

import javax.swing.*;
import java.awt.*;

public class Signup extends JFrame {
    private JTextField textfieldUserIDViewSignup;
    private JTextField textfieldFullnameViewSignup;
    private JPasswordField passwordfieldPasswordViewSignup;
    private JPasswordField passwordfieldPasswordAgainViewSignup;
    private JButton buttonSignupViewSignup;
    private JButton buttonBackViewSignup;
    private JLabel labelUserIDViewSignup;
    private JLabel labelFullnameViewSignup;
    private JLabel labelPasswordAgainViewSignup;
    private JPanel panelViewSignup;
    private JLabel labelPasswordViewSignup;

    public Signup() {
        addActionEvent();
        this.setTitle("Đăng ký");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewSignup);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Signup::new);
    }

    private void addActionEvent() {
        buttonSignupViewSignup.addActionListener(event -> {
            var userID = textfieldUserIDViewSignup.getText().strip();
            var fullName = textfieldFullnameViewSignup.getText().strip();
            var password = String.valueOf(passwordfieldPasswordViewSignup.getPassword()).strip();
            var passwordAgain = String.valueOf(passwordfieldPasswordAgainViewSignup.getPassword()).strip();
            if (userID.isEmpty() || fullName.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường thông tin không được bỏ trống!",
                        "Cảnh Báo",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if (userID.equals("admin")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể tạo tài khoản với UserID này. Xin hãy sử dụng UserID khác!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                textfieldUserIDViewSignup.setText("");
                passwordfieldPasswordViewSignup.setText("");
                passwordfieldPasswordAgainViewSignup.setText("");
            } else if (verifyAccountNotExist(userID)) {
                var user = new User(userID, fullName, password, false);
                var isSuccess = UserDAO.insert(user);
                if (isSuccess) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Đăng ký tài khoản thành công.",
                            "Thông Báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Đăng ký tài khoản thất bại. Xin hãy thử lại!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                resetAll();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "UserID đã tồn tại, thử lại với UserID khác!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                passwordfieldPasswordViewSignup.setText("");
                passwordfieldPasswordAgainViewSignup.setText("");
            }
        });

        buttonBackViewSignup.addActionListener(event -> {
            this.dispose();
            new Login();
        });
    }

    private boolean verifyAccountNotExist(String userID) {
        var user = UserDAO.selectByID(userID);
        return user == null;
    }

    private void resetAll() {
        textfieldUserIDViewSignup.setText("");
        textfieldFullnameViewSignup.setText("");
        passwordfieldPasswordViewSignup.setText("");
        passwordfieldPasswordAgainViewSignup.setText("");
    }

    private void createUIComponents() {
    }
}
