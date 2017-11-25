package MainGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import SubtitleFixerCore.*;
import java.io.IOException;

/**
 *
 * 
 * TODO: use locale and resource bundle for text
 * @author jschon
 */
public class Menu extends JFrame implements ActionListener {
    
    private JButton selectFile;
    private JButton selectFolder;
    private JButton doFix;
    private JButton cleanSelection;
//    private JButton exit;
    
    private final Dimension horizontalRigidSpace = new Dimension(40, 0);
    private final Dimension verticalRigidSpace = new Dimension(0, 20);
    
    private final SubtitleFixerCore sFC;
    
    private String sourceLocation;
    
    public Menu() {
        setGUI();
        setPreferences();
        sFC = new SubtitleFixerCore();
    }
    
    /**
     * Set GUI for Subtitle Fixer
     */
    private void setGUI() {
        this.setLayout(new BorderLayout());
        this.add(Box.createRigidArea(horizontalRigidSpace));
        this.add(createButtonsArea(), BorderLayout.CENTER);
        this.add(Box.createRigidArea(horizontalRigidSpace));
    }
    
    /**
     * Create Buttons area for GUI.
     * 
     * @return Component
     */
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
    
    /**
     * Set button for buttons area with name passed as argument.
     * 
     * @param buttonsArea
     * @param jButton
     * @param name 
     */
    private void setButton(JPanel buttonsArea, JButton jButton, String name) {
        jButton = new JButton(name);
        jButton.addActionListener(this);
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsArea.add(jButton);
        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
    }
    
    /**
     * Set button for buttons area with name and enabled switch.
     * 
     * @param buttonsArea
     * @param jButton
     * @param name
     * @param enabled 
     */
    private void setButton(JPanel buttonsArea, JButton jButton, String name, 
            Boolean enabled) {
        setButton(buttonsArea, jButton, name);
        jButton.setEnabled(enabled);
    }
    
    /**
     * Set preferences for GUI. <br/>
     * Exit on close, pack, non resizable, Title and location.
     */
    private void setPreferences(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setTitle("Subtitle Fixer");
        this.setLocationRelativeTo(null);
    }
    
    /**
     * Display gui.
     */
    public void show(){
        this.setVisible(true);
    }
    
    /**
     * Enable/disable selection actions for buttons.
     * 
     * @param status 
     */
    private void enableSelectionAction(Boolean status) {
        doFix.setEnabled(status);
        cleanSelection.setEnabled(status);
    }
    
    /**
     * Handle action performed.
     * 
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        if(event.equals(selectFile.getActionCommand())
                || event.equals(selectFolder.getActionCommand()))   selectFile();
        if(event.equals(doFix.getActionCommand()))                  runFix();
        if(event.equals(cleanSelection.getActionCommand()))         cleanSelection();
//        if(event.equals(exit.getActionCommand()))                   exit();
    }
    
    /**
     * Create pop-up for file selection.
     * Handle file selection.
     */
    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            sFC.setFile(fileChooser.getSelectedFile());
            enableSelectionAction(true);
        }
        
        
    }
    
    /**
     * Run fix for selection.
     */
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
    
    /**
     * Clean selection and disable selection actions.
     */
    private void cleanSelection() {
        sFC.setFile(null);
        enableSelectionAction(false);
    }
    
}
