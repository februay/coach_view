package indi.xp.common.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils {

    private static Logger logger = LoggerFactory.getLogger(WebUtils.class);

    public static void sendMessage(HttpServletResponse response, String message, String contentType) {
        if (StringUtils.isNotBlank(contentType)) {
            response.setContentType(contentType);
        } else {
            response.setContentType("text/html;charset=UTF-8");
        }
        response.setCharacterEncoding("UTF-8");
        // * 表示允许任何域名跨域访问, http:localhost:8080/ 指定特定域名可以访问
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            logger.error("sendMessage Error: " + e.getMessage(), e);
        } finally {
            ObjectUtils.safeClose(writer);
        }

        // try{
        // writer = response.getWriter();
        // writer.append(message);
        // } catch (Exception e){
        // logger.error("sendMessage Error: " + e.getMessage(), e);
        // try {
        // response.sendError(500);
        // } catch (IOException e1) {
        // e1.printStackTrace();
        // }
        // }
    }

    /**
     * 获取客户端真实ip地址
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ("unKnown".equalsIgnoreCase(ip)) {
            ip = "";
        }
        if (StringUtils.isNotBlank(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                ip = ip.substring(0, index);
                if (!IPWhiteListUtils.isRealIPAddr(ip)) {
                    ip = "";
                }
            }
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("X-Real-IP");
            if (StringUtils.isNotBlank(ip)) {
                if (!IPWhiteListUtils.isRealIPAddr(ip)) {
                    ip = "";
                }
            }
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (StringUtils.isNotBlank(ip)) {
                if (!IPWhiteListUtils.isRealIPAddr(ip)) {
                    ip = "";
                }
            }
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (StringUtils.isNotBlank(ip)) {
                if (!IPWhiteListUtils.isRealIPAddr(ip)) {
                    ip = "";
                }
            }
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
