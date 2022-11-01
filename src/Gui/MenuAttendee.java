package Gui;

import Model.User;

import javax.swing.*;
import java.awt.*;

public class MenuAttendee extends JFrame{
    private JButton buttonGoToRoomAttendeeViewMenuAttendee;
    private JButton buttonResultAttendeeViewMenuAttendee;
    private JButton buttonLogoutViewMenuAttendee;
    private JPanel panelViewMenuAttendee;

    private final User loginUser;

    public MenuAttendee(User user){
        this.loginUser = user;
        addActionEvent();
        this.setTitle("Menu Attendee");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelViewMenuAttendee);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addActionEvent() {
        buttonGoToRoomAttendeeViewMenuAttendee.addActionListener(event -> {
           this.dispose();
           new GoToRoomAttendee(loginUser);
        });
        buttonResultAttendeeViewMenuAttendee.addActionListener(event -> {
            this.dispose();
            new ResultAttendee(loginUser);
        });
        buttonLogoutViewMenuAttendee.addActionListener(event -> {
           this.dispose();
           new Login();
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        User attendee = new User(
                "attendee",
                "attendee",
                "attendee",
                false
        );
        EventQueue.invokeLater(() -> new MenuAttendee(attendee));
    }
}
