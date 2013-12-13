package launcher;

import com.rameses.osiris2.test.OsirisTestPlatform;
import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;

public class Main 
{
    public static void main(String[] args) throws Exception 
    {
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){;} 
        
        //dumpSystemInfo();
        
        Map env = new HashMap();
        env.put("app.debugMode", "true");         
        env.put("app.cluster", "osiris3");
        env.put("app.context", "clfc");
        env.put("app.host", "localhost:8070");
        env.put("readTimeout", "30000"); 
        
        Map profile = new HashMap();
        profile.put("CLIENTTYPE", "desktop");
        profile.put("USERID", "sa");
        profile.put("USER", "sa");
        profile.put("ORGID", "006");
        profile.put("ORGNAME", "N.BACALSO BRANCH");
        
        Map roles = new HashMap();
        roles.put("ADMIN.SYSADMIN", null);
        roles.put("DATAMGMT.DATAMGMT_AUTHOR", null);
        roles.put("LOAN.FIELD_COLLECTOR", null);
        roles.put("LOAN.CASHIER", null);
        
        roles.put("LOAN.CAO_OFFICER", null);
        roles.put("LOAN.CI_OFFICER", null);
        roles.put("LOAN.CRECOM_OFFICER", null);
        roles.put("LOAN.ACCT_OFFICER", null);
        OsirisTestPlatform.runTest(env, roles, profile); 
    }

    private static void dumpSystemInfo() {
        System.getProperties().list(System.out); 
    }
    
    public Main() {
    }
    
    
}
