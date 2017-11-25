package MainGui;

import javax.swing.*;

/**
 * Class for starting GUI.
 * @author jschon
 */
class GUIStarter {

     public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
        public void run(){
                Menu menu = new Menu();
                menu.show();
            }
        });
    }
    
}
