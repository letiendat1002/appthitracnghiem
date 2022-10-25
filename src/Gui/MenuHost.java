package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuHost extends JFrame{
    private JButton buttonLogoutViewMenuHost;
    private JButton buttonRoomManagementViewMenuHost;
    private JButton buttonExamManagementViewMenuHost;
    private JButton buttonQuestionManagementViewMenuHost;
    private JPanel panelViewMenuHost;

    public MenuHost(){
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
           new ExamManagement();
        });
        buttonQuestionManagementViewMenuHost.addActionListener(event -> {
           this.dispose();
           new QuestionManagement();
        });
        buttonRoomManagementViewMenuHost.addActionListener(event -> {
           this.dispose();
           new RoomManagement();
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
        EventQueue.invokeLater(MenuHost::new);
    }
}
