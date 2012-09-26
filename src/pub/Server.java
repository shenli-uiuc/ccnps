package hermes.server;

import hermes.util.*;

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

public class Server implements CCNFilterListener{
    
    private Interest _interest = null;
    private CCNHandle _handle = null;
    private Publisher _pub = null;
   
 
    public CCNDataWriterReg() throws MalformedContentNameStringException, ConfigurationException, IOException {
        _prefix = ContentName.fromURI(_strPrefix);
        _handle = CCNHandle.open();
        _pub = new Publisher(_handle);
    }


    public void start() throws IOException{
        // All we have to do is say that we're listening on our main prefix.
        _handle.registerFilter(Protocol.POST_PREFIX, this);
    }

    private MsgItem getMsgItem(Interest interest){
        String strMsg = interest.name().toURIString().subString(Protocol.POST_PREFIX.length);
        int splitIndex = strMsg.indexof("/");
        String usr = strMsg.subString(0, splitIndex);
        String msg = strMsg.subString(splitIndex + 1, strMsg.length);
        String strPub = Protocol.PUB_PREFIX + user;
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
        
        MsgItem msgItem = getMsgItem(interest);
    
        if(null != msgItem)
            _pub.publish(interest.getContentName.toURIString(), Protocol.SUCCESS);
        else
            _pub.publish(interest.getContentName.toURIString(), Protocol.ERROR);
        //split string
        _pub.publish(msgItem.getPublisher, msgItem.getMsg);
        return true;
    }

    /**
     * Turn off everything.
     * @throws IOException 
     */
    public void shutdown() throws IOException {
        if (null != _handle) {
            _handle.unregisterFilter(Protocol.POST_PREFIX, this);
            System.out.println("CCNQueryListener Closed!\n");
        }
    }


    public static void main(String args[]){
        Server server = new Server();
        server.start();
    }

}


