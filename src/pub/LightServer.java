package ccnps.pub;

import ccnps.util.*;
import ccnps.protocol.*;

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

public class LightServer implements CCNFilterListener{
    
    private Interest _interest = null;
    private CCNHandle _handle = null;
    private Publisher _pub = null;
    private CCNWriter _writer = null;

    public LightServer(){
        try{
            _handle = CCNHandle.open();
            _pub = new Publisher(CCNHandle.open());
            _writer = new CCNWriter(_handle);
        }
        catch(ConfigurationException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }


    public void start(){
        // All we have to do is say that we're listening on our main prefix.
        try{
            _handle.registerFilter(ContentName.fromURI(Protocol.LIGHT_POST_PREFIX), this);
        }
        catch(MalformedContentNameStringException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private MsgItem getMsgItem(Interest interest){
        String strMsg = interest.name().toURIString().substring(Protocol.LIGHT_POST_PREFIX.length());
        int splitIndex = strMsg.indexOf("/");
        String usr = strMsg.substring(0, splitIndex);
        String msg = strMsg.substring(splitIndex + 1, strMsg.length());
        String strPub = Protocol.LIGHT_PUB_PREFIX + usr;
        return new MsgItem(strPub, msg);
    }



    public boolean handleInterest(Interest interest) {
        System.out.println("===========================received Interest : " + interest.name().toURIString() + "\n");

        if (SegmentationProfile.isSegment(interest.name()) && !SegmentationProfile.isFirstSegment(interest.name())) {
            System.out.println("Got an interest for something other than a first segment, ignoring : " + interest.name().toURIString());
            return false;
        } 
        else if (MetadataProfile.isHeader(interest.name())) {
            System.out.println("Got an interest for the first segment of the header, ignoring : " + interest.name().toURIString());
            return false;
        }     

        MsgItem msgItem = getMsgItem(interest);
        try{
            _writer.addOutstandingInterest(interest);
            if(null != msgItem)
                _writer.put(interest.name(), Protocol.SUCCESS, 1);
            else
                _writer.put(interest.name(), Protocol.ERROR, 1);
        }
        catch(SignatureException ex){
            ex.printStackTrace();
        }
        catch(MalformedContentNameStringException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        //split string
        System.out.println("getPublisher result: " + msgItem.getPublisher());
        _pub.publish(msgItem.getPublisher(), msgItem.getMsg());
        return true;
    }

    /**
     * Turn off everything.
     * @throws IOException 
     */
    public void shutdown() throws IOException {
        if (null != _handle) {
            try{
                _handle.unregisterFilter(ContentName.fromURI(Protocol.LIGHT_POST_PREFIX), this);
                System.out.println("CCNQueryListener Closed!\n");
            }
            catch(MalformedContentNameStringException ex){
                ex.printStackTrace();
            }
        }
    }


    public static void main(String args[]){
        LightServer server = new LightServer();
        server.start();
    }

}


