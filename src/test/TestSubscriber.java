package ccnps.test;

import ccnps.sub.*;
import ccnps.util.*;

import org.ccnx.ccn.CCNHandle;

public class TestSubscriber{
    public static void main(String args[]){
        int i = 0;
        CCNHandle handle = null;
        try{
            handle = CCNHandle.open();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        Subscriber subscriber = new Subscriber(args[0], handle);
        for(i = 1; i < args.length; ++i){
            subscriber.subscribe(args[i]);
        }
        MsgItem msgItem = null;
        while(true){
            msgItem = subscriber.receive();
            System.out.println(msgItem.getPublisher() + ": " + msgItem.getMsg());
        }
    } 
}
