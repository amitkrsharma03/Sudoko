package Sudoko;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        SudokoGUI gui = new SudokoGUI();
        gui.setVisible(true);
      }
    });
  }
}
