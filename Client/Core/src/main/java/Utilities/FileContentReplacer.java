package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for replacing content of file(s).
 *
 * TODO: replace with getting replace pairs data from configuration file.
 * (probably in resources)
 * 
 * @author jschon
 */
public class FileContentReplacer {
    
    private final Map<String, String> replacePairs;
    
    
    public FileContentReplacer() {
        replacePairs = new HashMap<String, String>();
        replacePairs.put("ø","ř");
        replacePairs.put("ï","ď");
        replacePairs.put("ì","ě");
        replacePairs.put("è","č");
        replacePairs.put("ù","ú");
        replacePairs.put("ò","ň");
    }
    
    /**
     * Replace all characters of certain type in file.
     * @param f
     * @throws IOException 
     */
    public void replaceCharactersInFile(File f) throws IOException {
        File tmpFile = File.createTempFile("buffer", ".tmp");
        FileWriter fw = new FileWriter(tmpFile);
        
        replaceCharacters(fw, f);
        
        fw.close();
        tmpFile.renameTo(f);
    }
    
    /**
     * Helper method for @replaceCharactersInFile method. <br/>
     * This method will get Reader for file and invoke changing characters 
     * line by line.
     * @param fw
     * @param f
     * @throws IOException 
     */
    private void replaceCharacters(FileWriter fw, File f) throws IOException {
        Reader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        
        while (br.ready())
            fw.write(replaceAllRequiredCharacters(br.readLine()));
        
        br.close();
        fr.close();
    }
    
    /**
     * Replaces all defined characters in line.
     * @param line
     * @return 
     */
    private String replaceAllRequiredCharacters(String line) {
        for (Map.Entry<String, String> replacePair : replacePairs.entrySet())
            line = line.replaceAll(replacePair.getKey(), 
                                    replacePair.getValue());
        return line;
    }
    
}
