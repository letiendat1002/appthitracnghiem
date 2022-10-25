package Gui;

import DAO.UserDAO;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private static final String username_admin = "admin";
    private static final String password_admin = "admin";

    private JPanel panelViewLogin;
    private JLabel labelUsernameViewLogin;
    private JLabel labelPasswordViewLogin;
    private JButton buttonLoginViewLogin;
    private JButton buttonQuitViewLogin;
    private JTextField textfieldUsernameViewLogin;
    private JPasswordField passwordfieldPasswordViewLogin;
    private JButton buttonSignupViewLogin;
    private JLabel labelSignupViewLogin;
    private JCheckBox checkboxShowPasswordViewLogin;

    public Login() {
        addActionEvent();
        this.setTitle("Đăng nhập");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewLogin);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Login::new);
    }

    private void addActionEvent() {
        buttonLoginViewLogin.addActionListener(event -> {
            try {
                var username = textfieldUsernameViewLogin.getText();
                var password = String.valueOf(passwordfieldPasswordViewLogin.getPassword());
                var password_encrypted = UserDAO.encryptPassword(password);
                var user = UserDAO.selectByAccount(username, password_encrypted);
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Tên đăng nhập hoặc mật khẩu không được bỏ trống!",
                            "Cảnh Báo Đăng Nhập",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else if (username.equals(username_admin) && password.equals(password_admin)) {
                    this.dispose();
                    new MenuAdmin();
                } else if (user != null) {
                    this.dispose();
                    var checkHost = user.isHost();
                    if (checkHost) {
                        new MenuHost();
                    } else {
                        new MenuAttendee();
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Sai tên đăng nhập hoặc mật khẩu",
                            "Cảnh Báo Đăng Nhập",
                            JOptionPane.WARNING_MESSAGE
                    );
                    passwordfieldPasswordViewLogin.setText("");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        });

        buttonSignupViewLogin.addActionListener(event -> {
            this.dispose();
            new Signup();
        });

        checkboxShowPasswordViewLogin.addActionListener(event -> {
            if (checkboxShowPasswordViewLogin.isSelected()) {
                passwordfieldPasswordViewLogin.setEchoChar((char) 0);
            } else {
                passwordfieldPasswordViewLogin.setEchoChar('*');
            }
        });

        buttonQuitViewLogin.addActionListener(event -> {
            var selection = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn thật sự muốn thoát?",
                    "Thoát Ứng Dụng",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });
    }

    private void createUIComponents() {
    }
}
