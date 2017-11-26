package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
        Writer fstream = null;
           
        fstream = new OutputStreamWriter(new FileOutputStream(tmpFile), StandardCharsets.UTF_8);
        
        replaceCharacters(fstream, f);
        
        fstream.close();
        
        tmpFile.renameTo(f);
    }
    
    /**
     * Helper method for @replaceCharactersInFile method. <br/>
     * This method will get Reader for file and invoke changing characters 
     * line by line.
     * @param fstream
     * @param f
     * @throws IOException 
     */
    private void replaceCharacters(Writer fstream, File f) throws IOException {
//    		InputStreamReader fIStream = new InputStreamReader(new FileInputStream(f), StandardCharsets.ISO_8859_1);
    		InputStreamReader fIStream = new InputStreamReader(new FileInputStream(f), "Cp1250");
    		
    		BufferedReader br = new BufferedReader(fIStream);
        
        while (br.ready()) 
            fstream.write(replaceAllRequiredCharacters(br.readLine()) + "\n");
  
        br.close();
    }
    
    /**
     * Replaces all defined characters in line.
     * @param line
     * @return 
     */
    private String replaceAllRequiredCharacters(String line) {
        for (Map.Entry<String, String> replacePair : replacePairs.entrySet()) {
            line = line.replaceAll(replacePair.getKey(), 
                                    	replacePair.getValue());
            line = line.replaceAll(replacePair.getKey().toUpperCase(), 
                    					replacePair.getValue().toUpperCase());
        }
        return line;
    }
    
}
