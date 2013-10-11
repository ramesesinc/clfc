package com.rameses.platform.interfaces;

import java.util.Map;

public interface SubWindow 
{    
    String getName();
    
    String getTitle();
    void setTitle(String title);
    
    void closeWindow(); 
        
    void setListener(SubWindowListener listener);
    
    void update(Map windowAttributes);
}
