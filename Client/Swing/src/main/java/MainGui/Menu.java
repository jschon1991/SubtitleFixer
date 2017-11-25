package MainGui;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * 
 * TODO: use locale and resource bundle for text
 * @author jschon
 */
public class Menu extends JPanel implements ActionListener {
    
    private JButton selectFile;
    private JButton selectFolder;
    private JButton doFix;
    private JButton cleanSelection;
//    private JButton exit;
    
    private final Dimension horizontalRigidSpace = new Dimension(40, 0);
    private final Dimension verticalRigidSpace = new Dimension(0, 20);
    
    private SubtitleFixerCore sFC;
    
    private String sourceLocation;
    
    public Menu() {
        setGUI();
        setPreferences();
        sFC = new SubtitleFixerCore();
    }
    
    private void setGUI() {
        this.setLayout(new BorderLayout());
        this.add(Box.createRigidArea(horizontalRigidSpace));
        this.add(createButtonsArea(), BorderLayout.CENTER));
        this.add(Box.createRigidArea(horizontalRigidSpace));
    }
    
    private Component createButtonsArea() {
        JPanel buttonsArea = new JPanel();
        buttonsArea.setLayout(new BoxLayout(buttonsArea, BoxLayout.PAGE_AXIS));
        setButton(buttonsArea, selectFile, "Select File");
        setButton(buttonsArea, selectFolder, "Select Folder");
        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
        setButton(buttonsArea, doFix, "Fix Selection", false);
        setButton(buttonsArea, cleanSelection, "Clear Selection", false);
//        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
//        setButton(buttonsArea, exit, "Exit");
        return buttonsArea;
    }
    
    private void setButton(JPanel buttonsArea, JButton jButton, String name) {
        jButton = new JButton(name);
        jButton.addActionListener(this);
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsArea.add(jButton);
        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
    }
    
    private void setButton(JPanel buttonsArea, JButton jButton, String name, 
            Boolean enabled) {
        setButton(buttonsArea, jButton, name);
        jButton.setEnabled(enabled);
    }
    
    private void setPreferences(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setTitle("Subtitle Fixer");
        this.setLocationRelativeTo(null);
    }
    
    public void show(){
        this.setVisible(true);
    }
    
    private void enableSelectionAction(Boolean status) {
        doFix.setEnabled(status);
        cleanSelection.setEnabled(status);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        if(event.equals(selectFile.getActionCommand())
                || event.equals(selectFolder.getActionCommand()))   selectFile();
        if(event.equals(doFix.getActionCommand()))                  runFix();
        if(event.equals(cleanSelection.getActionCommand()))         cleanSelection();
//        if(event.equals(exit.getActionCommand()))                   exit();
    }
    
    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(modalToComponent) == JFileChooser.APPROVE_OPTION) {
            sFC.setFile(fileChooser.getSelectedFile());
            enableSelectionAction(true);
        }
        
        
    }
    
    private void runFix() {
        try {
            sFC.runFix();
            cleanSelection();
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            cleanSelection();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            cleanSelection();
        }                          
    }
    
    private void cleanSelection() {
        sFC.setFile(null);
        enableSelectionActions(false);
    }
    
}
