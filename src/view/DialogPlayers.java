package view;

import javax.swing.*;
import java.awt.*;

/**
 * The DialogPlayers class represents a dialog window for selecting the number of players in the game
 */
public class DialogPlayers extends JFrame {
    // Options for the number of players
    Object[] options = {"2",
            "3",
            "4"};
    private Component frame;
    public int n = JOptionPane.showOptionDialog(frame,
            "How many players will play",
            "Number of players",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]) + 2;

}
