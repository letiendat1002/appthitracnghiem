package View;

import Model.User;

import javax.swing.*;
import java.awt.*;

public class MenuHost extends JFrame {
    private final User loginUser;
    private JButton buttonLogoutViewMenuHost;
    private JButton buttonRoomManagementViewMenuHost;
    private JButton buttonExamManagementViewMenuHost;
    private JButton buttonQuestionManagementViewMenuHost;
    private JPanel panelViewMenuHost;

    public MenuHost(User user) {
        this.loginUser = user;
        addActionEvent();
        this.setTitle("Menu Host");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewMenuHost);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        User host = new User(
                "host",
                "host",
                "host",
                true
        );
        EventQueue.invokeLater(() -> new MenuHost(host));
    }

    private void addActionEvent() {
        buttonExamManagementViewMenuHost.addActionListener(event -> {
            this.dispose();
            new ExamManagement(loginUser);
        });
        buttonQuestionManagementViewMenuHost.addActionListener(event -> {
            this.dispose();
            new QuestionManagement(loginUser);
        });
        buttonRoomManagementViewMenuHost.addActionListener(event -> {
            this.dispose();
            new RoomManagement(loginUser);
        });
        buttonLogoutViewMenuHost.addActionListener(event -> {
            this.dispose();
            new Login();
        });
    }

    private void createUIComponents() {
    }
}
