package com.catchu.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {

    public static String getCurrentServerIp() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }


}
