package ccnps.sub;

import ccnps.util.*;
import ccnps.protocol.*;

import org.ccnx.ccn.CCNFilterListener;
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


public class SubsribeThread extends Thread{

    private static final ONEDAY = 1000 * 60 * 60 * 24;

    private String _name = null;
    private CCNHandle _handle = null;
    private LinkedBlockingQueue _lbq = null;
    private CCNReader _reader = null;

    public SubscribeThread(Stirng name, CCNHandle handle, LinkedBlockingQueue lbq){
        this._name = name;
        this._handle = handle;
        this._lbq = lbq;
        this._reader = new CCNReader(handle);
    }
    
    public void run(){
        String curMsg = null;
        while(true){
            curMsg = receive();
            lbq.put(new MsgItem(_name, curMsg));
        }
    }

    public String receive(){
        try{
            ContentName contentName = ContentName.fromURI(Protocol.PUB_PREFIX + _name);
            Interest interest = new Interest(contentName);
            System.out.println("**************" + contentName.toURIString() + "\n" + "***************" + _strPrefix + ", " + request);
            ContentObject co = _reader.get(interest, ONEDAY);
            String ans = new String(co.content());
            System.out.println("Got data : " + ans);
            return ans;
        }
        catch (ConfigurationException e) {
            System.out.println("ConfigurationException in CCNQuerySender-sendQuery: " + e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("IOException in CCNQuerySender-sendQuery: " + e.getMessage());
            e.printStackTrace();
        }
        catch (MalformedContentNameStringException e) {
            System.out.println("MalformedContentNameStringException in CCNQuerySender-sendQuery : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

}
