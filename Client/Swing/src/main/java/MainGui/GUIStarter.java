package MainGui;

/**
 * Class for starting GUI.
 * @author jschon
 */
public class GUIStarter {

    public static void main(final String[] args){
        SwingUtilities.invokeLater(new Runnable(){
        public void run(){
                Window menu = new Menu();
                menu.show();
            }
        });
    }
    
}
