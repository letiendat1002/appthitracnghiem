package Main;

import Gui.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(Login::new);
    }
}