/*
 * NewEmptyJUnitTest2.java
 * JUnit based test
 *
 * Created on September 25, 2013, 10:44 AM
 */

package test;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import junit.framework.*;

/**
 *
 * @author compaq
 */
public class NewEmptyJUnitTest2 extends TestCase {
    
    public NewEmptyJUnitTest2(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
//last-modified: 1380077412000
//date-Wed Sep 25 10:50:12 CST 2013        
        StringBuffer sb = new StringBuffer();
    }

    protected void tearDown() throws Exception {
    }

    public void test0() throws Exception 
    {
        //URL url = new URL("http://localhost/downloads/modules/rameses-etracs-main.jar");
        URL url = new URL("http://localhost/downloads/modules/core/osiris2-client-themes.jar");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
        long dt = conn.getLastModified();
        System.out.println("last-modified: " + dt);
        System.out.println("date-" + new Date(dt));
        conn.disconnect();
        
        String s1 = "C:\\RAMESES_DEV\\projects\\etracs2.5\\platform\\etracsplatform_src\\osiris2\\modules";
        String s2 = "C:\\RAMESES_DEV\\projects\\etracs2.5\\platform\\etracsplatform_src/osiris2/modules/core/osiris2-client-themes.jar";
        File f1 = new File(s1); 
        System.out.println(f1.getPath());
        System.out.println(f1.getAbsolutePath());
        System.out.println(f1.getCanonicalPath());
        
        File f2 = new File(s2);
        String ss1 = f1.getAbsolutePath().replace('\\','/');
        String ss2 = f2.getAbsolutePath().replace('\\','/');
        System.out.println(ss2.replaceFirst(ss1, ""));
    }

}
