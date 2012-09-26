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


public class PostThread extends Thread{

    private static final ONEDAY = 1000 * 60 * 60 * 24;

    private String _name = null;
    private String _msg = null;
    private CCNHandle _handle = null;

    public SubscribeThread(Stirng name, String msg, CCNHandle handle){
        this._name = name;
        this._msg = msg;
        this._handle = handle;
    }
    
    public void run(){
        post();
    }

    //encode the message into the interest
    public String post(){
        try{
            ContentName contentName = ContentName.fromURI(Protocol.POST_PREFIX + _name + "/" + _msg);
            Interest interest = new Interest(contentName);
            System.out.println("**************" + contentName.toURIString() + "\n" + "***************" + _strPrefix + ", " + request);
            CCNReader reader = new CCNReader(_handle);
            ContentObject co = reader.get(interest, ONEDAY);
            String ans = new String(co.content());
            System.out.println("In Post - Got data : " + ans);
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