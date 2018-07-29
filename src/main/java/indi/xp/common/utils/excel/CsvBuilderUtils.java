package indi.xp.common.utils.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.ObjectUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

/**
 * 
 * CSV文件导出工具类
 */
public class CsvBuilderUtils {

    private static final Logger logger = LoggerFactory.getLogger(CsvBuilderUtils.class);

    /**
     * CSV文件生成方法
     * 
     * @param head
     * @param dataList
     * @param outPutPath
     * @param filename
     * @return
     */
    public static File createCSVFile(List<String> headerList, List<List<String>> rowList, String outPutPath,
        String fileName) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        String filePath = (StringUtils.isNotBlank(outPutPath) ? outPutPath + File.separator : "");
        fileName = StringUtils.isNotBlank(fileName) ? fileName : UuidUtils.generateUUID();
        fileName = filePath + (StringUtils.endsWithIgnoreCase(fileName, ".csv") ? fileName : fileName + ".csv");
        try {
            csvFile = new File(fileName);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(csvFile);
            // 写入bom头
            // Excel本身默认会以 ANSI 格式打开，不做编码识别
            // excel文件需要通过文件头的bom来识别编码，所以写文件时，需要先写入bom头
            byte[] uft8bom = { (byte) 0XEF, (byte) 0XBB, (byte) 0XBF };
            fos.write(uft8bom);

            csvWtriter = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"), 1024);
            // 写入文件头部
            writeRow(headerList, csvWtriter);

            // 写入文件内容
            for (List<String> row : rowList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            logger.error("csv file<{}> build error ", fileName, e);
        } finally {
            ObjectUtils.safeClose(csvWtriter);
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     * 
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<String> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        if (CollectionUtils.isNotEmpty(row)) {
            for (Object data : row) {
                StringBuffer sb = new StringBuffer();
                String rowStr = sb.append("\"").append(data).append("\",").toString();
                csvWriter.write(new String(rowStr.getBytes("UTF-8")));
            }
        }
        csvWriter.newLine();
    }
}