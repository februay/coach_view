package indi.xp.coachview.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.model.VerificationCode;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.SpringContextUtil;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;
import indi.xp.common.utils.sms.AliyunDysmsUtils;
import indi.xp.config.AliyunDysmsConfig;

public class VerificationCodeManager {

    private static final Logger logger = LoggerFactory.getLogger(VerificationCodeManager.class);

    private static final AliyunDysmsConfig smsConfig = SpringContextUtil.getBean("aliyunDysmsConfig");

    private static final boolean sendSmsOn = !Boolean.FALSE.equals(smsConfig.getEnabled()); // 不设置关闭则开启

    private static final Map<String, VerificationCode> verificationCodeMap = new HashMap<String, VerificationCode>();
    // for test
    private static final Map<String, String> verificationCodeTempMap = new HashMap<String, String>();
    static {
        verificationCodeTempMap.put("13426233960", "1342"); // admin
        verificationCodeTempMap.put("13000000001", "5210"); // 俱乐部权限
        verificationCodeTempMap.put("13000000002", "8425"); // 学校权限
        verificationCodeTempMap.put("13000000003", "3397"); // 队权限

        verificationCodeTempMap.put("12345678901", "8901"); // admin
        verificationCodeTempMap.put("12345678902", "8902"); // 俱乐部权限
        verificationCodeTempMap.put("12345678903", "8903"); // 学校权限
        verificationCodeTempMap.put("12345678904", "8904"); // 队权限
    }

    private static boolean isActive = false; // 启动后标记为active状态，避免初始化多次
    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
    static {
        // 启动定时任务扫描自动更新source任务队列，每个任务完成后触发下一个(最少有一个线程)
        if (!isActive) {
            scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        checkVerificationCodeMap();
                    } catch (Exception e) {
                        logger.error("checkVerificationCodeMap error:" + e.getMessage(), e);
                    }
                }
            }, 5, 10, TimeUnit.MINUTES);
            isActive = true;
        }
    }

    /**
     * 检查sessionMap， 并清理过期的session
     * 
     * @date: 2018年7月28日
     * @author peng.xu
     */
    private static void checkVerificationCodeMap() {
        logger.info("start checkVerificationCodeMap ...");
        int count = 0;
        if (CollectionUtils.isNotEmpty(verificationCodeMap)) {
            Iterator<Map.Entry<String, VerificationCode>> iterator = verificationCodeMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, VerificationCode> entury = iterator.next();
                String key = entury.getKey();
                VerificationCode verificationCode = entury.getValue();
                if (entury.getValue().getExpiredTime() < System.currentTimeMillis()) {
                    logger.info(" >>> remove verificationCode<{}> :" + JSON.toJSONString(verificationCode), key);
                    iterator.remove();
                } else {
                    count++;
                }
            }
        }
        logger.info("success checkVerificationCodeMap: count: " + count);
    }

    public static boolean validateVerificationCode(String key, String value) {
        boolean result = false;
        if (StringUtils.isNotBlank(key, value)) {
            VerificationCode verificationCode = verificationCodeMap.get(key);
            result = verificationCode != null && value.equals(verificationCode.getValue())
                && verificationCode.getExpiredTime() > System.currentTimeMillis();
            if (result) {
                // 验证通过，清除验证码
                verificationCodeMap.remove(key);
            } else {
                // for test
                result = StringUtils.isNotBlank(key, value) && value.equals(verificationCodeTempMap.get(key));
            }
        }
        return result;
    }

    public static VerificationCode buildVerificationCode(String phoneNumber) {
        String verificationCodeStr = UuidUtils.generateRandomNumber(4);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setKey(phoneNumber);
        verificationCode.setValue(verificationCodeStr);
        verificationCode.setType(VerificationCode.TYPE_PHONE_LOGIN);
        verificationCode.setExpiredTime(System.currentTimeMillis() + 5 * 60 * 1000);
        verificationCodeMap.put(phoneNumber, verificationCode);
        sendLoginVerificationCode(phoneNumber, verificationCodeStr);
        return verificationCode;
    }

    private static void sendLoginVerificationCode(String phoneNumber, String verificationCode) {
        if (sendSmsOn) {
            String signName = smsConfig.getLoginVerificationCodeSign(); // 短信签名
            String templateCode = smsConfig.getLoginVerificationCodeTemplate(); // 短信模板
            Map<String, String> templateParamMap = new HashMap<String, String>();
            templateParamMap.put("code", verificationCode);
            AliyunDysmsUtils.sendSms(phoneNumber, signName, templateCode, templateParamMap);
        }
    }
}
