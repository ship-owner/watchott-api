package com.watchott.common.util;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * packageName    : watchott.util
 * fileName       : JwtTokenProvider
 * author         : shipowner
 * date           : 2023-09-25
 * description    : Request 유틸
 */

public class RequestUtil {

    public static String getClientIp(HttpServletRequest request){
        return getLocalServerIp(request);
    }

    /**
     * methodName : getLocalServerIp
     * author : shipowner
     * description : 접속 IP 조회
     *
     */
    public static String getLocalServerIp( HttpServletRequest request)
    {
        String ip = request.getHeader("X-Forwarded-For");

        try
        {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("Proxy-Client-IP");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("WL-Proxy-Client-IP");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("HTTP_CLIENT_IP");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getRemoteAddr();

            if (ip == null || ip.length() == 0 || "0:0:0:0:0:0:0:1".equalsIgnoreCase(ip))
            {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
                {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                    {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress())
                            ip = inetAddress.getHostAddress().toString();
                    }
                }
            }

            if (ip == null || ip.length() == 0 || "0:0:0:0:0:0:0:1".equalsIgnoreCase(ip))
                ip = InetAddress.getLocalHost().getHostAddress();

            if(ip.indexOf(",") != -1)
                ip = ip.substring(0, ip.indexOf(","));

        }
        catch (SocketException ex)
        {
            ip = "unknown ip address";
        } catch (UnknownHostException e) {
            ip = "unknown ip address";
        }
        return ip;
    }

}
