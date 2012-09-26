
import java.util.*;
import java.lang.*;
import org.ccnx.ccn.io.CCNVersionedOutputStream;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import  java.security.SignatureException;

import org.ccnx.ccn.CCNFilterListener;
import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.impl.support.Log;
import org.ccnx.ccn.io.*;
import org.ccnx.ccn.profiles.CommandMarker;
import org.ccnx.ccn.profiles.SegmentationProfile;
import org.ccnx.ccn.profiles.VersioningProfile;
import org.ccnx.ccn.profiles.metadata.MetadataProfile;
import org.ccnx.ccn.profiles.nameenum.NameEnumerationResponse;
import org.ccnx.ccn.profiles.nameenum.NameEnumerationResponse.NameEnumerationResponseMessage;
import org.ccnx.ccn.profiles.nameenum.NameEnumerationResponse.NameEnumerationResponseMessage.NameEnumerationResponseMessageObject;
import org.ccnx.ccn.profiles.security.KeyProfile;
import org.ccnx.ccn.protocol.CCNTime;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.Exclude;
import org.ccnx.ccn.protocol.ExcludeComponent;
import org.ccnx.ccn.protocol.Interest;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;

public class Publisher{
    private static final Integer CONTENT_LIFE_TIME = 1000; //ms
    
    private CCNHandle _handle = null;
    private CCNWriter _writer = null;

    public Publisher(CCNHandle handle){
        this._handle = handle;
        this._writer = new CCNWriter(handle);
    }
    
    private boolean publish(String prefix, String msg){

        try{
            _writer.put(prefix, msg.getBytes());
        }
        catch(SignatureException ex){
            ex.printStackTrace();
            return false;
        }
        catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
        return true;

    }

    public static void main(String args[]){
        Publisher publisher = new Publisher(CCNHandle.open());
        publisher.publish("/ccnps/test/Alice", "Alice's new tweet");
    }

}


