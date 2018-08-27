package indi.xp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class AliyunDysmsConfig {

    @Value("${sms.enabled}")
    private Boolean enabled; // 是否发送短信

    @Value("${sms.aliyun-dysms.access-key-id}")
    private String accessKeyId;

    @Value("${sms.aliyun-dysms.access-key-secret}")
    private String accessKeySecret;

    @Value("${sms.aliyun-dysms.login-verification-code.sign}")
    private String loginVerificationCodeSign; // 短信签名

    @Value("${sms.aliyun-dysms.login-verification-code.template}")
    private String loginVerificationCodeTemplate;// 短信模板

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getLoginVerificationCodeSign() {
        return loginVerificationCodeSign;
    }

    public void setLoginVerificationCodeSign(String loginVerificationCodeSign) {
        this.loginVerificationCodeSign = loginVerificationCodeSign;
    }

    public String getLoginVerificationCodeTemplate() {
        return loginVerificationCodeTemplate;
    }

    public void setLoginVerificationCodeTemplate(String loginVerificationCodeTemplate) {
        this.loginVerificationCodeTemplate = loginVerificationCodeTemplate;
    }

}
