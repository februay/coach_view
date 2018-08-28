package indi.xp.common.utils.sms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import indi.xp.common.utils.SpringContextUtil;
import indi.xp.config.AliyunDysmsConfig;

/**
 * 
 * 阿里云短信API工具类
 * 
 * @date: 2018年7月27日
 * @author peng.xu
 */
public class AliyunDysmsUtils {

    private static final Logger logger = LoggerFactory.getLogger(AliyunDysmsUtils.class);

    // 产品名称:云通信短信API产品（请勿修改）
    private static final String product = "Dysmsapi";
    // 产品域名（请勿修改）
    private static final String domain = "dysmsapi.aliyuncs.com";
    
    private static final AliyunDysmsConfig smsConfig = SpringContextUtil.getBean("aliyunDysmsConfig");

    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private static final String accessKeyId = smsConfig.getAccessKeyId();
    private static final String accessKeySecret = smsConfig.getAccessKeySecret();

    public static SendSmsResponse sendSms(String phoneNumber, String signName, String templateCode,
        Map<String, String> templateParamMap) {

        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        String templateParam = templateParamMap != null ? JSON.toJSONString(templateParamMap) : null;
        try {
            // 初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

            IAcsClient acsClient = new DefaultAcsClient(profile);

            // 组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            // 使用post提交
            request.setMethod(MethodType.POST);
            // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；
            // 发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
            request.setPhoneNumbers(phoneNumber);
            // 必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            // 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(templateCode);
            // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            // {"name":\"Tom\", \"code\":\"123\"}
            // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败

            request.setTemplateParam(templateParam);
            // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            // request.setSmsUpExtendCode("90997");

            // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");

            // hint 此处可能会抛出异常，注意catch
            // 请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                // 请求成功
                logger.info(
                    ">>> send sms<{}> success: signName=" + signName + ", templateCode=" + templateCode
                        + ", templateParam=" + templateParam + ", response" + JSON.toJSONString(sendSmsResponse),
                    phoneNumber);
            } else {
                logger.warn(
                    ">>> send sms<{}> failed: signName=" + signName + ", templateCode=" + templateCode
                        + ", templateParam=" + templateParam + ", response" + JSON.toJSONString(sendSmsResponse),
                    phoneNumber);
            }
            return sendSmsResponse;
        } catch (ClientException e) {
            logger.error(">>> send sms<{}> error: signName=" + signName + ", templateCode=" + templateCode
                + ", templateParam=" + templateParam, phoneNumber, e);
        }
        return null;
    }

    public static QuerySendDetailsResponse querySendDetails(String phoneNumber, String bizId) throws ClientException {

        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        // 必填-号码
        request.setPhoneNumber(phoneNumber);
        // 可选-流水号, 发送回执ID,可根据该ID查询具体的发送状态
        request.setBizId(bizId);
        // 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        // 必填-页大小
        request.setPageSize(10L);
        // 必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        // hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

    public static void main(String[] args) throws ClientException, InterruptedException {

        // 发短信
        String phoneNumber = "13426233960";
        String signName = "Coachview";
        String templateCode = "SMS_140690149";
        Map<String, String> templateParamMap = new HashMap<String, String>();
        templateParamMap.put("code", "123");
        SendSmsResponse response = sendSms(phoneNumber, signName, templateCode, templateParamMap);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

        Thread.sleep(3000L);

        // 查明细
        if (response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(phoneNumber, response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse
                .getSmsSendDetailDTOs()) {
                System.out.println("SmsSendDetailDTO[" + i + "]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }

    }

}
