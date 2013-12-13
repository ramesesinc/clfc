/*
 * TestRuntimeExec.java
 * JUnit based test
 *
 * Created on November 29, 2013, 7:25 PM
 */

package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import junit.framework.*;

/**
 *
 * @author compaq
 */
public class TestRuntimeExec extends TestCase {
    
    public TestRuntimeExec(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    public void testMain() throws Exception {
        String cmd = "cmd /c C:\\RAMESES_DEV\\projects\\clfc\\platform\\clfc2_platform_thin\\clfc2_platform.bat";
        Process proc = Runtime.getRuntime().exec(cmd);
        InputStream eis = proc.getErrorStream(); 
        dump(eis); 
    }

    private void dump(InputStream inp) throws Exception {
        BufferedReader reader = null; 
        try {
            reader = new BufferedReader(new InputStreamReader(inp)); 
            String line = null;
            while ( (line=reader.readLine()) != null) {
                System.out.println("line: " + line);
            }
        } catch(Exception ex) {
            throw ex; 
        } finally {
            try { reader.close(); }catch(Exception ign){;} 
            try { inp.close(); }catch(Exception ign){;} 
        }
    }
}
