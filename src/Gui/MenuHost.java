package Gui;

import Model.User;

import javax.swing.*;
import java.awt.*;

public class MenuHost extends JFrame{
    private JButton buttonLogoutViewMenuHost;
    private JButton buttonRoomManagementViewMenuHost;
    private JButton buttonExamManagementViewMenuHost;
    private JButton buttonQuestionManagementViewMenuHost;
    private JPanel panelViewMenuHost;

    private final User loginUser;

    public MenuHost(User user){
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
        // TODO: place custom component creation code here
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
}
