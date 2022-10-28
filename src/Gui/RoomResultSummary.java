package Gui;
import Model.User;

import javax.swing.*;


public class RoomResultSummary {
    private final User loginUser;
    private JPanel panelViewRoomResultSummary;
    private JTextField textfieldFindByRoomIDViewRoomResultSummary;
    private JTextField textfieldFindByUserIDViewRoomResultSummary;
    private JTable tableViewRoomResultSummary;
    private JButton buttonBackViewRoomResultSummary;
    private JLabel labelFindByRoomIDViewRoomResultSummary;
    private JLabel labelFindByUserIDViewRoomResultSummary;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public RoomResultSummary(User loginUser) {
        this.loginUser = loginUser;
    }
}
