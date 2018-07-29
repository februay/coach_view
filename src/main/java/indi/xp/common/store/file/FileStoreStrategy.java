package indi.xp.common.store.file;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileStoreStrategy {

    /**
     * 上传文件
     */
    public void uploadFile(InputStream inputStream, String path, String fileName) throws Exception;

    /**
     * 上传文件
     * 
     * @param filePath
     *            文件路径
     * @param fileName
     *            文件名
     * @param fileExtension
     *            文件后缀
     */
    public void uploadFile(InputStream inputStream, String path, String fileName, String fileExtension)
        throws Exception;

    /**
     * 上传文件
     */
    public void uploadFile(InputStream inputStream, String filePath) throws Exception;

    /**
     * 获取从文件存储读文件的流
     */
    public InputStream getReadFileInputStream(String filePath) throws Exception;

    /**
     * 获取向文件存储中写文件的流
     */
    public OutputStream getWriteFileOutputStream(String filePath) throws Exception;

}
