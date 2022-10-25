package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAttendee extends JFrame{
    private JButton buttonGoToRoomAttendeeViewMenuAttendee;
    private JButton buttonResultAttendeeViewMenuAttendee;
    private JButton buttonLogoutViewMenuAttendee;
    private JPanel panelViewMenuAttendee;

    public MenuAttendee(){
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
           new GoToRoomAttendee();
        });
        buttonResultAttendeeViewMenuAttendee.addActionListener(event -> {
            this.dispose();
            new ResultAttendee();
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
        EventQueue.invokeLater(MenuAttendee::new);
    }
}
