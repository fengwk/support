package com.fengwk.support.core.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
public class IpUtils {
    
    /**
     * 有线 enp/en/eth/em
     */
    private static final String ETH = "e";
    
    /**
     * 无线 wlp/wlan
     */
    private static final String WLAN = "w";
    
    /**
     * 无线 net
     */
    private static final String NET = "n";
    
    /**
     * bond
     */
    private static final String BOND = "b";
    
    /**
     * VPN
     */
    private static final String TAP = "tap";
    
    /**
     * 虚拟网卡
     */
    private static final String VIRTUAL = "virtual";
    
    /**
     * 缓存本地IPV4
     */
    private static volatile InetAddress LOCAL_IPV4;
    
    /**
     * 缓存本地IPV6
     */
    private static volatile InetAddress LOCAL_IPV6;

    private IpUtils() {}
    
    @SuppressWarnings("restriction")
    public static boolean isIPv4(String addr) {
        return sun.net.util.IPAddressUtil.isIPv4LiteralAddress(addr);
    }
    
    @SuppressWarnings("restriction")
    public static boolean isIPv6(String addr) {
        return sun.net.util.IPAddressUtil.isIPv6LiteralAddress(addr);
    }
    
    public static boolean isIPv4(InetAddress inetAddr) {
        return inetAddr.getAddress().length == 4;
    }
    
    public static boolean isIPv6(InetAddress inetAddr) {
        return inetAddr.getAddress().length == 16;
    }
    
    /**
     * 获取首个搜索到的IPv4类型的本地IP
     * 
     * @return
     */
    public static InetAddress getLocalIPv4() {
        if (LOCAL_IPV4 == null) {
            synchronized (IpUtils.class) {
                if (LOCAL_IPV4 == null) {
                    LOCAL_IPV4 = getLocalIP(Inet4Address.class);
                }
            }
        }
        return LOCAL_IPV4;
    }
    
    /**
     * 获取首个搜索到的IPv6类型的本地IP
     * 
     * @return
     */
    public static InetAddress getLocalIPv6() {
        if (LOCAL_IPV6 == null) {
            synchronized (IpUtils.class) {
                if (LOCAL_IPV6 == null) {
                    LOCAL_IPV6 = getLocalIP(Inet6Address.class);
                }
            }
        }
        return LOCAL_IPV6;
    }
    
    private static InetAddress getLocalIP(Class<? extends InetAddress> inetClass) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (isAvailableLocalNetworkInterface(networkInterface)) {
                    Enumeration<InetAddress> inetAddressEnum = networkInterface.getInetAddresses();
                    while (inetAddressEnum.hasMoreElements()) {
                        InetAddress inetAddress = inetAddressEnum.nextElement();
                        if (inetAddress.getClass() == inetClass) {
                            return inetAddress;
                        }
                    }
                }
            }
            return null;
        } catch (SocketException e) {
            log.error("", e);
            return null;
        }
    }
    
    private static boolean isAvailableLocalNetworkInterface(NetworkInterface networkInterface) throws SocketException {
        String displayName = networkInterface.getDisplayName().toLowerCase();
        String name = networkInterface.getName().toLowerCase();
        return networkInterface.isUp() 
                && !displayName.contains(TAP) 
                && !displayName.contains(VIRTUAL) 
                && (displayName.startsWith(ETH) || displayName.startsWith(WLAN) || displayName.startsWith(BOND) || displayName.startsWith(NET) || name.startsWith(ETH) || name.startsWith(WLAN) || name.startsWith(BOND) || name.startsWith(NET));
    }
    
    public static int ipv4ToInt(byte[] ipv4) {
        return ((0xFF & ipv4[0]) << 24) | ((0xFF & ipv4[1]) << 16) | ((0xFF & ipv4[2]) << 8) | (0xFF & ipv4[3]);
    }
    
    public static byte[] intToIPv4(int ipv4Int) {
        return new byte[] { (byte) ((ipv4Int >> 24) & 0xFF), (byte) ((ipv4Int >> 16) & 0xFF), (byte) ((ipv4Int >> 8) & 0xFF), (byte) (ipv4Int & 0xFF) };
    }
    
}
