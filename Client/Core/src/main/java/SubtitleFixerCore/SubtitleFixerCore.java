package SubtitleFixerCore;

import Utilities.FileContentReplacer;
import Utilities.FileUtilities;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Core class for SubtitlesFixer application. <br/>
 * This method run workflows for fixing subtitles encoding issues within srt 
 * files.
 * @author jschon
 */
public class SubtitleFixerCore {
    
	private static final int MAX_ERRORS_TO_DISPLAY = 3;
	
    private final FileContentReplacer fCR;
    
    private File loadedFile;
    
    public SubtitleFixerCore() {
        fCR = new FileContentReplacer();
    }
    
    public File getFile() {
        return loadedFile;
    }
    
    public void setFile(File f) {
        loadedFile = f;
    }
 
    /**
     * Fix characters in all srt files within selected directory. 
     * @param dir
     * @throws IOException 
     */
    public void fixSubtitlesFiles(File dir) throws IOException {
        if (dir.isDirectory())
            for (File f : FileUtilities.getSrtFromDir(dir))
                try {
                    fCR.replaceCharactersInFile(f);
                } catch (IOException ex) {
                    Logger.getLogger(SubtitleFixerCore.class.getName()).log(Level.SEVERE, null, ex);
                }
        else throw new IOException(dir.getName() + " is not directory");
    }
    
    /**
     * Fix characters in selected srt file.
     * @param f
     * @throws IOException 
     */
    public void fixSubtitlesFile(File f) throws IOException {
        if (f.isFile() && FileUtilities.isSrt(f))
            try {
                fCR.replaceCharactersInFile(f);
            } catch (IOException ex) {
                Logger.getLogger(SubtitleFixerCore.class.getName()).log(Level.SEVERE, null, ex);
            }
        else throw new IOException(f.getName() + " is not file");
    }
    
    public void runFix() throws IOException, Exception {
        if (loadedFile != null)
            if (FileUtilities.isDir(loadedFile)) runFixOnDir(loadedFile);
            else if (FileUtilities.isFile(loadedFile)) fixSubtitlesFile(loadedFile);
            else throw new IOException("Unknown type of file: " 
                + loadedFile.getAbsolutePath() 
                + loadedFile.getName());
        else throw new Exception("File was not loaded!");
    }
    
    private void runFixOnDir(File dir) throws Exception {
    		File[] files = dir.listFiles();
    		String errors = "";
    		Integer numOfErrors = 0;
    		for (File f : files) {
    			try {
    				fixSubtitlesFile(f);
    			} catch(IOException ex) {
    				if(numOfErrors < MAX_ERRORS_TO_DISPLAY) {
    					if(numOfErrors > 0) errors += "\n";
    					errors += ex.getMessage();
    				}
    				numOfErrors++;
    			}
    		}
    		if(numOfErrors > MAX_ERRORS_TO_DISPLAY) 
    			errors += "\nand " + (numOfErrors - MAX_ERRORS_TO_DISPLAY) + " more errors.";
    		if(numOfErrors > 0) throw new Exception(errors);
    }
}