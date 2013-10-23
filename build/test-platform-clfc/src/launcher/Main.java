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
        }catch(Exception e){;} 
        
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
        roles.put("DATAMGMT.ENTITY_ENCODER", null);
        roles.put("ACCOUNTS.ACCOUNT_DATAMGMT", null);
        roles.put("TREASURY.ACCOUNT_DATAMGMT", null); 
        roles.put("BPLS.BP_DATAMGMT", null); 
        roles.put("RPT.RPT_DATAMGMT", null); 
        
        roles.put("DATAMGMT.LOAN_DATAMGMT_AUTHOR", null);
        roles.put("LOAN.LOAN_CAO_OFFICER", null);
        //roles.put("LOAN.LOAN_CI_OFFICER", null);
        //roles.put("LOAN.LOAN_CRECOM_OFFICER", null);
        OsirisTestPlatform.runTest(env, roles, profile); 
    }

    public Main() {
    }
}
