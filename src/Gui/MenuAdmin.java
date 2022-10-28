package Gui;

import Model.User;

import javax.swing.*;
import java.awt.*;

public class MenuAdmin extends JFrame{
    private JButton buttonUserManagementViewMenuAdmin;
    private JButton buttonLogoutViewMenuAdmin;
    private JButton buttonRoomManagementViewMenuAdmin;
    private JButton buttonQuestionManagementViewMenuAdmin;
    private JButton buttonExamManagementViewMenuAdmin;
    private JPanel panelViewMenuAdmin;

    private final User loginUser;

    public MenuAdmin(User user){
        this.loginUser = user;
        addActionEvent();
        this.setTitle("Menu Admin");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewMenuAdmin);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addActionEvent() {
        buttonUserManagementViewMenuAdmin.addActionListener(event -> {
           this.dispose();
           new UserManagement(loginUser);
        });
        buttonExamManagementViewMenuAdmin.addActionListener(event -> {
            this.dispose();
            new ExamManagement(loginUser);
        });
        buttonQuestionManagementViewMenuAdmin.addActionListener(event -> {
           this.dispose();
           new QuestionManagement(loginUser);
        });
        buttonRoomManagementViewMenuAdmin.addActionListener(event -> {
            this.dispose();
            new RoomManagement(loginUser);
        });
        buttonLogoutViewMenuAdmin.addActionListener(event -> {
            this.dispose();
            new Login();
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        User admin = new User("admin", "admin", "admin", true);
        EventQueue.invokeLater(() -> new MenuAdmin(admin));
    }
}
