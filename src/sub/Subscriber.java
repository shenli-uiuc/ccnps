package ccnps.sub;

import ccnps.util.*;

import java.util.*;
import java.sql.Timestamp;
import java.util.concurrent.*;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.impl.support.Log;
import org.ccnx.ccn.io.CCNReader;
import org.ccnx.ccn.io.CCNWriter;
import org.ccnx.ccn.protocol.CCNTime;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.Exclude;
import org.ccnx.ccn.protocol.ExcludeComponent;
import org.ccnx.ccn.protocol.Interest;
import org.ccnx.ccn.protocol.ContentObject;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;

import org.ccnx.ccn.utils.CommonSecurity;


public class Subscriber {

    private CCNHandle _handle = null;   
    private String _name = null;
    private HashSet<String> _subSet = null;
    private LinkedBlockingQueue<MsgItem> _lbq = null;
    private ArrayList<SubscribeThread> _threadList = null;

    public Subscriber(String name, CCNHandle handle){
        this._handle = handle;
        this._name = name;
        this._subSet = new HashSet<String>();
        this._lbq = new LinkedBlockingQueue<MsgItem>();
        this._threadList = new ArrayList<SubscribeThread>();
    } 

    public synchronized boolean subscribe(String name){
        if(_subSet.contains(name)){
            //already subscribing to the name
            return false;
        }
        _subSet.add(name);
        SubscribeThread st = new SubscribeThread(name, _handle, _lbq);
        _threadList.add(st);
        st.start();
        return true;
    }

    //this should be blocking
    public MsgItem receive(){
        MsgItem msgItem = null;
        try{
            msgItem = _lbq.take();
        }
        catch(InterruptedException ex){
            ex.printStackTrace();

        }
        return msgItem;
    }

    //this is non-blocking, post is done by a separate thread
    public void post(String msg){
        PostThread pt = new PostThread(_name, msg, _handle);
        pt.start();
    }



    public static void main(String argv[]){
        try{
            Subscriber subscriber = new Subscriber("Bob", CCNHandle.open());
            subscriber.subscribe("Alice");
            while(true){
                System.out.println(subscriber.receive().getMsg());
            }
        }
        catch(ConfigurationException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }


}
