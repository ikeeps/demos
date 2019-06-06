package util;

import java.lang.invoke.MethodHandles;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * IpUtils
 * 
 * @author hujianfeng
 * @date 2018/07/10
 *
 * @see https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
 */
public class IpUtils {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private IpUtils() {
    }

    public static List<String> getMachineIp() {
        List<String> names = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return Collections.<String>emptyList();
            }
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface iface = networkInterfaces.nextElement();
                if (iface.isUp()) {
                    List<InterfaceAddress> interfaceAddresses = iface.getInterfaceAddresses();
                    for (InterfaceAddress addr : interfaceAddresses) {
                        names.add(addr.getAddress().getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            log.error("get worker ip error", e);
        }
        return names;
    }

}
