package view;

import javax.swing.*;

/**
 * The PlayersName class represents a dialog window for entering the names of players in the game
 */

public class PlayersName extends JFrame {
    /**
     * A static method to "ask" the user for the name of a player
     * @param i The index of the player
     * @return The name entered by the user
     */
    public static String fun(int i)
    {

         String playerName = JOptionPane.showInputDialog("Player "+ (i+1) +" name:");

         if (playerName == null) // cancel button
         {
             JOptionPane.showMessageDialog(null, "Player registration cancelled.");
             System.exit(-1);
         }else if (playerName.trim().isEmpty())
         {
             JOptionPane.showMessageDialog(null,
                     "You cannot register a player without a name!");

             return fun(i);
         }
         else {
         return playerName;
        }
         return null;
    }

}
