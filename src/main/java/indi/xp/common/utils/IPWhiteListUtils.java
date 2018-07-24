package indi.xp.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;

/**
 * IP白名单工具类
 */
public class IPWhiteListUtils {

    /**
     * IP正则表达式，ipv4地址， ipv4, 还需要排除 0.0.0.0 和 255.255.255.255 这两种情况
     */
    private static Pattern pattern = Pattern
        .compile("(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
            + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})");

    // private static String Regex_IPV4_ADDR =
    // "^([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5]).([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5]).([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5]).([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])$";

    /**
     * 真实IP地址验证， 防止IP攻击，错误的IP导致安全问题
     */
    public static boolean isRealIPAddr(String ipAddr) {
        boolean result = true;
        String spIP1 = "0.0.0.0";
        String spIp2 = "255.255.255.255";
        // 判断是否正确的ip地址
        if (!pattern.matcher(ipAddr).matches()) {
            result = false;
        }
        if (result) {
            if (spIP1.equals(ipAddr) || spIp2.equals(ipAddr)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 根据IP白名单设置获取可用的IP列表.
     */
    private static Set<String> getAvailableIpRangeList(String ipWhiteConfig) {
        Set<String> availableIpList = new HashSet<String>();
        for (String allow : ipWhiteConfig.replaceAll("\\s", "").split(";")) {
            if (allow.indexOf("*") > -1) {
                String[] ips = allow.split("\\.");
                String[] from = new String[] { "0", "0", "0", "0" };
                String[] end = new String[] { "255", "255", "255", "255" };
                List<String> tem = new ArrayList<String>();
                for (int i = 0; i < ips.length; i++) {
                    if (ips[i].indexOf("*") > -1) {
                        tem = complete(ips[i]);
                        from[i] = null;
                        end[i] = null;
                    } else {
                        from[i] = ips[i];
                        end[i] = ips[i];
                    }
                }

                StringBuffer fromIP = new StringBuffer();
                StringBuffer endIP = new StringBuffer();
                for (int i = 0; i < 4; i++) {
                    if (from[i] != null) {
                        fromIP.append(from[i]).append(".");
                        endIP.append(end[i]).append(".");
                    } else {
                        fromIP.append("[*].");
                        endIP.append("[*].");
                    }
                }
                fromIP.deleteCharAt(fromIP.length() - 1);
                endIP.deleteCharAt(endIP.length() - 1);

                for (String s : tem) {
                    String ipRange = fromIP.toString().replace("[*]", s.split(";")[0]) + "-"
                        + endIP.toString().replace("[*]", s.split(";")[1]);
                    if (isAvailableIpRangeItem(ipRange)) {
                        availableIpList.add(ipRange);
                    }
                }
            } else {
                if (isAvailableIpRangeItem(allow)) {
                    availableIpList.add(allow);
                }
            }
        }
        return availableIpList;
    }

    /**
     * 对单个IP节点进行范围限定
     * 
     * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
     */
    private static List<String> complete(String arg) {
        List<String> com = new ArrayList<String>();
        if (arg.length() == 1) {
            com.add("0;255");
        } else if (arg.length() == 2) {
            String s1 = complete(arg, 1);
            if (s1 != null) {
                com.add(s1);
            }
            String s2 = complete(arg, 2);
            if (s2 != null) {
                com.add(s2);
            }
        } else {
            String s1 = complete(arg, 1);
            if (s1 != null) {
                com.add(s1);
            }
        }
        return com;
    }

    private static String complete(String arg, int length) {
        String from = "";
        String end = "";
        if (length == 1) {
            from = arg.replace("*", "0");
            end = arg.replace("*", "9");
        } else {
            from = arg.replace("*", "00");
            end = arg.replace("*", "99");
        }
        if (Integer.valueOf(from) > 255) {
            return null;
        }
        if (Integer.valueOf(end) > 255) {
            end = "255";
        }
        return from + ";" + end;
    }

    /**
     * 在添加至有效白名单列表时对列表项时进行格式校验 1、 不为空 2、最多只包含一个 - 3、输入地址为有效的ip
     * 
     * @param ipRangeItem：
     *            192.168.1.2 || 192.168.1.2-192.168.2.100
     */
    public static boolean isAvailableIpRangeItem(String ipRangeItem) {
        if (StringUtils.isBlank(ipRangeItem) || ipRangeItem.indexOf("-") != ipRangeItem.lastIndexOf("-")) {
            return false;
        }
        for (String ipAddr : ipRangeItem.split("-"))
            if (!isRealIPAddr(ipAddr)) {
                return false;
            }
        return true;
    }

    /**
     * 
     * 根据IP,及可用Ip列表来判断ip是否包含在白名单之中 range： 192.168.1.2 ||
     * 192.168.1.2-192.168.2.100
     */
    public static boolean checkVisitIpInAvailableRangeList(String ip, Set<String> availableIpRangeList) {
        if (availableIpRangeList.isEmpty() || availableIpRangeList.contains(ip))
            return true;
        else {
            for (String allow : availableIpRangeList) {
                if (allow.indexOf("-") > -1) {
                    String[] minIp = allow.split("-")[0].split("\\.");
                    String[] maxIp = allow.split("-")[1].split("\\.");
                    String[] currentIp = ip.split("\\.");

                    // 计算每个ip的数值，通过数值来进行判断
                    long minIpValue = 0;
                    long maxIpValue = 0;
                    long currentIpValue = 0;
                    for (int i = 0; i < 4; i++) {
                        long min = Long.valueOf(minIp[i]);
                        long max = Long.valueOf(maxIp[i]);
                        long current = Long.valueOf(currentIp[i]);

                        long weight = new Double(Math.pow(10, (3 - i) * 3)).longValue();

                        minIpValue = minIpValue + min * weight;
                        maxIpValue = maxIpValue + max * weight;
                        currentIpValue = currentIpValue + current * weight;
                    }

                    if (minIpValue <= currentIpValue && currentIpValue <= maxIpValue
                        || minIpValue >= currentIpValue && currentIpValue >= maxIpValue) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 根据IP地址，及IP白名单设置规则判断IP是否包含在白名单列表
     */
    public static boolean checkVisitIp(String ip, String ipWhiteConfig) {
        Set<String> availableIpRangeList = getAvailableIpRangeList(ipWhiteConfig);
        return checkVisitIpInAvailableRangeList(ip, availableIpRangeList);
    }

    /**
     * 根据IP地址，及IP白名单设置规则判断IP是否包含在白名单列表
     */
    public static boolean checkVisitIp(String ip, List<String> ipWhiteConfigList) {
        for (String ipWhiteConfig : ipWhiteConfigList) {
            Set<String> availableIpRangeList = getAvailableIpRangeList(ipWhiteConfig);
            boolean result = checkVisitIpInAvailableRangeList(ip, availableIpRangeList);
            if (result) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String testIp = "192.168.2.1";
        String testIpConfig = "192.162.4.1-192.168.4.9";

        System.out.println(isRealIPAddr(testIp));

        System.out.println(isAvailableIpRangeItem(testIpConfig));

        System.out.println(JSON.toJSONString(getAvailableIpRangeList(testIpConfig)));

        System.out.println(checkVisitIp(testIp, testIpConfig));

    }

}
