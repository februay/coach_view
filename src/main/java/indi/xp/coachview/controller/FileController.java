package indi.xp.coachview.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import indi.xp.coachview.common.Constants;
import indi.xp.common.constants.MediaType;
import indi.xp.common.exception.BusinessException;
import indi.xp.common.restful.ResponseResult;
import indi.xp.common.store.file.FileStoreStrategy;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Configuration
@RestController("fileController")
@RequestMapping("/file")
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

    @Autowired
    private FileStoreStrategy fileStoreStrategy;

    @Value("${store.file.visit-domain}")
    private String fileVisitDomain;

    /**
     * 文件上传接口
     */
    @RequestMapping(value = "upload/{type}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Map<String, String>> imgUpload(@RequestBody MultipartFile file,
        @PathVariable(value = "type") String type,
        @RequestHeader(value = Constants.Header.TOKEN, required = false) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            String imgUrl = null;
            // String fileType = file.getContentType();
            String fileName = file.getOriginalFilename();
            // if (fileType.contains("image/")) {
            String fileExt = "";
            if (fileName.contains(".")) {
                fileExt = fileName.substring(fileName.lastIndexOf("."));
            }
            String remoteFileName = (StringUtils.isNotBlank(type) ? type + "/" : "")
                + dateFormat.format(System.currentTimeMillis()) + "/" + timeFormat.format(System.currentTimeMillis())
                + "-" + UuidUtils.generateUUID() + fileExt;

            fileStoreStrategy.uploadFile(file.getInputStream(), remoteFileName);

            imgUrl = this.fileVisitDomain + "/" + remoteFileName;
            resultMap.put("visitUrl", imgUrl);
            resultMap.put("contentType", file.getContentType());
            resultMap.put("size", file.getSize() + "");
            resultMap.put("originalFileName", fileName);
            logger.info("upload file<{}-" + fileName + "> success: " + imgUrl, type);
            // } else {
            // logger.error("file<{}-" + fileName + "> type not support" +
            // fileType, type);
            // }
            return ResponseResult.buildResult(resultMap);
        } catch (Exception e) {
            logger.error("upload file<{}> failed", type, e);
            throw new BusinessException();
        }
    }

}
