package indi.xp.common.utils.excel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import indi.xp.common.utils.ObjectUtils;

public class SpliterFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(SpliterFileUtil.class);

    public static List<List<String>> convertSpliterFileToRowList(InputStream inputStream, String spliter,
        Charset charset) {
        List<List<String>> rowList = new ArrayList<>();
        try {
            String charsetName = "UTF-8";
            if (charset != null) {
                charsetName = charset.name();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
            String tmpLine = null;
            String cell = null;
            // String
            // csvPattern="((\"[^\"]*(\"{2})*[^\"]*\")|('[^']*('{2})*[^']*'))*[^,]*,";
            // 该正则用于校验字符串是否符合csv的规范
            // 参考：http://stackoverflow.com/questions/8493195/how-can-i-parse-a-csv-string-with-javascript
            // String
            // csvPattern="^\\s*(?:'[^'\\\\]*(?:\\\\[\\S\\s][^'\\\\]*)*'|\"[^\"\\\\]*(?:\\\\[\\S\\s][^\"\\\\]*)*\"|[^,'\"\\s\\\\]*(?:\\s+[^,'\"\\s\\\\]+)*)\\s*(?:,\\s*(?:'[^'\\\\]*(?:\\\\[\\S\\s][^'\\\\]*)*'|\"[^\"\\\\]*(?:\\\\[\\S\\s][^\"\\\\]*)*\"|[^,'\"\\s\\\\]*(?:\\s+[^,'\"\\s\\\\]+)*)\\s*)*$";
            String csvPattern = "(?!\\s*$)\\s*(?:'([^'\\\\]*(?:\\\\[\\S\\s][^'\\\\]*)*)'|\"([^\"\\\\]*(?:\\\\[\\S\\s][^\"\\\\]*)*)\"|([^,'\"\\s\\\\]*(?:\\s+[^,'\"\\s\\\\]+)*))\\s*(?:,|$)";
            while ((tmpLine = reader.readLine()) != null) {
                Pattern pCells = Pattern.compile(csvPattern);

                Matcher mCells = pCells.matcher(tmpLine);
                List<String> cells = new ArrayList<String>();// 每行记录一个list

                // 读取每个单元格
                while (mCells.find()) {
                    cell = mCells.group();
                    /*
                     * cell = cell.replaceAll(
                     * "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1"); cell =
                     * cell.replaceAll("(?sm)(\"(\"))", "$2");
                     */
                    if (cell.endsWith(",")) {
                        cell = cell.substring(0, cell.length() - 1);
                    }
                    cells.add(cell);
                }

                rowList.add(cells);
            }
        } catch (Exception e) {
            logger.error("convert spliter file to list error: ", e);
        } finally {
            ObjectUtils.safeClose(inputStream);
        }
        return rowList;
    }

    public static String removeQuotation(String originalValue) {
        String result = originalValue;
        if ((originalValue.startsWith("\"") && originalValue.endsWith("\""))
            || (originalValue.startsWith("'") && originalValue.endsWith("'"))) {
            // 当只有一个 双引号 或者 只有一个单双引号时
            if (originalValue.length() - 1 > 1) {
                result = originalValue.substring(1, originalValue.length() - 1);
            } else if (originalValue.equalsIgnoreCase("\"\"") || originalValue.equalsIgnoreCase("''"))// 只包含两个
                                                                                                      // 双引号或者单引号
            {
                result = "";
            } else if (originalValue.equalsIgnoreCase("\"") || originalValue.equalsIgnoreCase("'")) // 只包含一个双引号或者一个单引号
            {
                result = "";
            }

        }
        return result;
    }

}
