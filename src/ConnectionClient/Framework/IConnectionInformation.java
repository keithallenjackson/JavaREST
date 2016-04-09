package ConnectionClient.Framework;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 3/28/2016
 * File:
 * Description:
 */
public interface IConnectionInformation {
    InetAddress getAddress() throws UnknownHostException;
    int getPort();
}


