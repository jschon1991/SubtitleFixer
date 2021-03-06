package Utilities;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Group of methods for manipulating and getting informations from files and
 * directories.
 * @author jschon
 */
public class FileUtilities {
    
    /**
     * Return list of srt files in directory.
     * @param dir
     * @return 
     */
    public static File[] getSrtFromDir(File dir) {
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".srt");
            }
        });
    }
    
    /**
     * Validate if file has srt extension.
     * @param f
     * @return 
     */
    public static Boolean isSrt(File f) {
        String name = f.getName();
        Integer index = name.lastIndexOf(".");
        if (index > 0) 
            return name.substring(index+1).toLowerCase().equals("srt");
        return false;
    }
    
    /**
     * Loads file from url.
     * @param url
     * @return
     */
    public static File loadDestination(String url) {
        return new File(url);
    }
    
    /**
     * Check if file is directory.
     * @param f
     * @return
     */
    public static Boolean isDir(File f) {
        return f.isDirectory();
    }
    
    /**
     * Check if file is file(not directory).
     * @param f
     * @return
     */
    public static Boolean isFile(File f) {
        return f.isFile();
    }
    
}
