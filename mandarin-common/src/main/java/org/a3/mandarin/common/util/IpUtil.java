package org.a3.mandarin.common.util;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
    // 自身服务器通常会为了保护信息或者负载均衡的目的，对自身服务器做反向代理。
    // 此时如果我们通过request.getRemoteAddr();可能获取到的是自身代理服务器的IP。
    public static String getIPAddress(HttpServletRequest request){
        //X-Forwarded-For：Squid 服务代理
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //Proxy-Client-IP：apache 服务代理
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //X-Real-IP：nginx服务代理
            ip = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ip != null && ip.length() != 0) {
            ip = ip.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
