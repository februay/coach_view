package indi.xp.common.store.file.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import indi.xp.common.store.file.FileStoreStrategy;
import indi.xp.common.utils.StringUtils;

@Configuration
@Component("fileStoreStrategy")
public class LocalFileStoreStrategy implements FileStoreStrategy {

    @Value("${store.file.local-strategy.path}")
    private String localPath;

    private String buildFilePath(String path, String fileName, String fileExtension) {
        StringBuilder pathBuilder = new StringBuilder(localPath);
        pathBuilder.append((localPath.endsWith("/") || path.startsWith("/")) ? "" : "/");
        if (StringUtils.isNotBlank(path)) {
            pathBuilder.append(path);
            pathBuilder.append(path.endsWith("/") ? "" : "/");
        }
        pathBuilder.append(fileName).append(fileExtension);
        return pathBuilder.toString();
    }

    @Override
    public void uploadFile(InputStream inputStream, String path, String fileName, String fileExtension)
        throws Exception {
        String filePath = this.buildFilePath(path, fileName, fileExtension);
        this.uploadFile(inputStream, filePath);
    }

    @Override
    public void uploadFile(InputStream inputStream, String path, String fileName) throws Exception {
        String filePath = this.buildFilePath(path, fileName, "");
        this.uploadFile(inputStream, filePath);
    }

    @Override
    public void uploadFile(InputStream inputStream, String filePath) throws Exception {
        filePath = this.buildFilePath("", filePath, "");
        OutputStream out = this.getWriteFileOutputStream(filePath);
        // IOUtils.copyBytes(inputStream, out, 4096, true);
        IOUtils.copy(inputStream, out);
    }

    @Override
    public InputStream getReadFileInputStream(String path) throws Exception {
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        return in;
    }

    @Override
    public OutputStream getWriteFileOutputStream(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
            if (!file.getParentFile().exists()) {
                if (file.getParentFile().mkdirs()) {
                    file.createNewFile();
                }
            }
        }
        OutputStream out = new FileOutputStream(file);
        return out;
    }

    // /////////////////////////////////////////////////////

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

}
