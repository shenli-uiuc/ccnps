package ccnps.test;

import ccnps.sub.*;
import ccnps.util.*;

import org.ccnx.ccn.CCNHandle;

public class TestLSPost{
    public static void main(String args[]){
        int i = 0;
        CCNHandle handle = null;
        try{
            handle = CCNHandle.open();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        LSSubscriber subscriber = new LSSubscriber(args[0], handle);
        subscriber.post(args[1]); 
    } 
}
