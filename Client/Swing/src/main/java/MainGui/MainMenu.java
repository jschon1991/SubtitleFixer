package MainGui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import SubtitleFixerCore.SubtitleFixerCore;

/**
 *
 * 
 * TODO: use locale and resource bundle for text
 * @author jschon
 */
public class MainMenu extends JFrame implements ActionListener {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8859262425157979546L;
	private JButton selectFile;
    private JButton selectFolder;
    private JButton doFix;
    private JButton cleanSelection;
    
    private final Dimension verticalRigidSpace = new Dimension(0, 20);
    
    private final SubtitleFixerCore sFC;
    
    public MainMenu() {
    		super();
        setGUI();
        setPreferences();
        sFC = new SubtitleFixerCore();
    }
    
    /**
     * Set GUI for Subtitle Fixer
     */
    private void setGUI() {
        getContentPane().add(createButtonsArea());
    }
    
    /**
     * Create Buttons area for GUI.
     * 
     * @return Component
     */
    private JPanel createButtonsArea() {
    		prepareButtons();
        JPanel buttonsArea = new JPanel();
        buttonsArea.setLayout(new BoxLayout(buttonsArea, BoxLayout.PAGE_AXIS));
        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
        buttonsArea.add(selectFile);
        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
        buttonsArea.add(selectFolder);
        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
        buttonsArea.add(doFix);
        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
        buttonsArea.add(cleanSelection);
        buttonsArea.add(Box.createRigidArea(verticalRigidSpace));
        return buttonsArea;
    }
    
    private void prepareButtons() {
        selectFile = setButton("Select File");
        selectFolder = setButton("Select Folder");
        doFix = setButton("Fix Selection", false);
        cleanSelection = setButton("Clear Selection", false);
    }
    
    /**
     * Set button for buttons area with name passed as argument.
     * 
     * @param name 
     */
    private JButton setButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
    
    /**
     * Set button for buttons area with name and enabled switch.
     * 
     * @param name
     * @param enabled 
     */
    private JButton setButton(String name, Boolean enabled) {
        JButton button = setButton(name);
        button.setEnabled(enabled);
        return button;
    }
    
    /**
     * Set preferences for GUI. <br/>
     * Exit on close, pack, non resizable, Title and location.
     */
    private void setPreferences(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
//        this.setSize(300, 150);
        this.setResizable(true);
        this.setTitle("Subtitle Fixer");
        this.setLocationRelativeTo(null);
    }
    
    /**
     * Display gui.
     */
    public void showMenu(){
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
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        if(event.equals(selectFile.getActionCommand()))		selectFile();
        if(event.equals(selectFolder.getActionCommand()))   	selectFolder();
        if(event.equals(doFix.getActionCommand()))           	runFix();
        if(event.equals(cleanSelection.getActionCommand()))  	cleanSelection();
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
     * Create pop-up for folder selection.
     * Handle folder selection.
     */
    private void selectFolder() {
    		JFileChooser chooser = new JFileChooser();
    	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	    chooser.setAcceptAllFileFilterUsed(false);
    	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
    	    		sFC.setFile(chooser.getSelectedFile());
    	    		enableSelectionAction(true);
    	    }
    }
    
    /**
     * Run fix for selection.
     */
    private void runFix() {
        try {
            sFC.runFix();
            JOptionPane.showMessageDialog(this, "Fix completed");
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
