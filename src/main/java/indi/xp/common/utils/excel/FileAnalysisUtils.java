package indi.xp.common.utils.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ptmind.cpdetector.CpdetectorUtil;

import indi.xp.common.utils.ObjectUtils;

public class FileAnalysisUtils {

    public Map<String, List<List<String>>> convertToRowListMap(String fileName, FileType fileType,
        InputStream inputStream) throws Exception {
        Map<String, List<List<String>>> resultMap = new LinkedHashMap<>();
        ByteArrayOutputStream swapStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            // FileType fileType = FileType.getFileTypeByName("csv");
            if (fileType == FileType.EXCEL) {
                resultMap = ExcelAdvanceUtil.convertExcelToRowListMap(inputStream);
            } else if (fileType == FileType.CSV || fileType == FileType.TSV || fileType == FileType.TXT) {
                String spliter = ",";
                swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[1024 * 10]; // buff用于存放循环读取的临时数据
                int rc = 0;
                while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                byteArrayInputStream = new ByteArrayInputStream(swapStream.toByteArray());
                // 先监测编码
                Charset charset = CpdetectorUtil.determineChartSet(byteArrayInputStream);
                List<List<String>> rowList = SpliterFileUtil.convertSpliterFileToRowList(byteArrayInputStream, spliter,
                    charset);
                resultMap.put(fileName, rowList);
            }
        } finally {
            ObjectUtils.safeClose(byteArrayInputStream, swapStream, inputStream);
        }
        return resultMap;
    }

    public static void main(String[] args) {
        FileInputStream inputStream = null;
        try {
            String file = null;
            file = "C:/Users/peng.xu/Desktop/test-A.xlsx";
            file = "C:/Users/peng.xu/Desktop/test-A.xls";

            inputStream = new FileInputStream(new File(file));

            long start = System.currentTimeMillis();
            Map<String, List<List<String>>> resultMap = ExcelUtil.convertExcelToRowListMap(inputStream);
            System.out.println("\n\nExcelUtil cost " + (System.currentTimeMillis() - start) + "ms ==> "
                + JSON.toJSONString(resultMap));

            inputStream = new FileInputStream(new File(file));
            start = System.currentTimeMillis();
            Map<String, List<List<String>>> resultMap2 = ExcelAdvanceUtil.convertExcelToRowListMap(inputStream);
            System.out.println("\n\nExcelAdvanceUtil cost " + (System.currentTimeMillis() - start) + "ms ==> "
                + JSON.toJSONString(resultMap2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // for test
    private FileWriter writer;
    private String currentLogFileName;

    public void optRowsPrint(int sheetIndex, String sheetName, int curRow, List<String> rowlist) throws SQLException {
        String rowStr = curRow + "";
        for (int i = 0; i < rowlist.size(); i++) {
            rowStr = rowStr + "'" + rowlist.get(i) + "', ";
        }
        try {
            String logPath = "C:/Users/peng.xu/Desktop/_test/log/";
            String fileName = logPath + (logPath.endsWith("/") ? "" : "/") + sheetIndex + "-" + sheetName + ".log";
            if (writer == null || !fileName.equals(currentLogFileName)) {
                if (writer != null) {
                    writer.close();
                    Thread.sleep(200);
                }
                try {
                    writer = new FileWriter(fileName, true);
                    // currentLogFileName = fileName;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                writer.write(rowStr + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // for test
    private List<List<List<String>>> sheets = new ArrayList<List<List<String>>>();

    public void optRowsToList(int sheetIndex, String sheetName, int curRow, List<String> rowlist) throws SQLException {
        List<List<String>> curSheet = null;
        if (sheets.size() > sheetIndex) {
            curSheet = sheets.get(sheetIndex);
        } else {
            curSheet = new ArrayList<List<String>>();
            sheets.add(curSheet);
        }
        curSheet.add(rowlist);
    }

}
