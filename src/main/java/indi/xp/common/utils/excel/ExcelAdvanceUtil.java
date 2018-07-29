package indi.xp.common.utils.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.POIXMLDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

/**
 * 使用poi底层的流解析方式， 避免内存占用的快速增长
 */
public class ExcelAdvanceUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelAdvanceUtil.class);

    public static Map<String, List<List<String>>> convertExcelToRowListMap(InputStream inputStream) throws Exception {
        if (!inputStream.markSupported()) {
            inputStream = new PushbackInputStream(inputStream, 8);
        }

        ExcelReaderDelegate excelReaderDelegate = getExcelReaderDelegate(inputStream);
        return excelReaderDelegate.convertExcelToRowListMap(inputStream);
    }

    @SuppressWarnings("deprecation")
    private static ExcelReaderDelegate getExcelReaderDelegate(InputStream inputStream) {

        ExcelReaderDelegate excelReaderDelegate = null;
        try {
            if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
                excelReaderDelegate = new XlsxExcelReaderDelegate();
            } else {
                excelReaderDelegate = new XlsExcelReaderDelegate(inputStream);
            }
        } catch (IOException e) {
            logger.warn("Could not identify spreadsheet type ,", e);
        }

        return excelReaderDelegate;

    }

    public static XMLReader createXmlReader() throws ParserConfigurationException, SAXException {
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        return sheetParser;
    }

    /**
     * 从后往前清理行数据中空串的列，直到找到最后一个不为空的列，该列索引就是该行最大的列数<br>
     * 从行列表中找到最大的列数
     */
    public static int removeEmptyValueOfListThenReturnMaxSize(List<List<String>> rowList) {
        int maxSize = 0;
        if (CollectionUtils.isEmpty(rowList)) {
            return maxSize;
        }
        for (List<String> row : rowList) {
            if (CollectionUtils.isEmpty(row)) {
                continue;
            }
            int childSize = row.size();
            for (int i = childSize - 1; i >= 0; i--) {
                String childValue = row.get(i);
                if (StringUtils.isBlank(childValue)) {
                    // childList中清理掉空串
                    row.remove(i);
                } else {
                    int nowSize = i + 1;
                    // 最后一个没有空串的
                    if (maxSize < nowSize) {
                        maxSize = nowSize;
                    }
                    break;
                }
            }
        }
        return maxSize;
    }

    /**
     * 验证二维表是否是一个空表，并返回最后一个有数据的行号<br>
     * 空表验证规则：某一行不为空<br>
     * 有数据的行号：由后往前检查行中是否每一列都是空串，<br>
     * 如果是空串，则当前是空行，如果不是空串，则当前是最后 一行有数据的行，返回当前行号
     * 
     * @return Object[0] == lastRowNum, Object[1] == isEmptyTable
     */
    public static Object[] checkIsEmptyTableAndGetLastRowNum(List<List<String>> allRowList) {
        boolean isEmptyTable = true;
        boolean isEmptyRow = true;
        // 原代码为：int lastRowNum = allRowList.size();
        // 原代码是错误的，因为默认的最后行号如果是allRowList的大小
        // 那么当所有行的列数据都是空串时，最后的行号会是allRowList大小，但正确结果应该是0
        int lastRowNum = 0;
        for (int i = allRowList.size() - 1; i >= 0; i--) {

            List<String> tmpRow = allRowList.get(i);

            // 如果该行没有列数据，则不需要再往下进行了
            if (CollectionUtils.isEmpty(tmpRow)) {
                continue;
            }

            for (String tmpCol : tmpRow) {
                if (!StringUtils.isBlank(tmpCol)) {
                    isEmptyRow = false;
                    break;
                }
            }
            if (!isEmptyRow) {
                lastRowNum = i + 1;
                // 原本的判断逻辑是：if (tmpRow.size > 0){ isEmptyTable = false;}
                // 原逻辑是错误的，因为如果二维表中所有行的所有列都是空串的时候
                // 实际上这个二维表是个空表，但原逻辑会设置成isEmptyTable=false
                // 而放到if (!isEmptyRow)
                // 中则不存在空表的问题，因为能进入该判断就证明了该二维表不是个空表，不进入就证明是空表
                isEmptyTable = false;
                break;
            }
        }
        return new Object[] { lastRowNum, isEmptyTable };
    }

}
