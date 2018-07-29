package indi.xp.common.utils.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 2003及以前版本excel的解析
 */
public class XlsExcelReaderDelegate extends XlsExcelReaderDelegateAbstract implements ExcelReaderDelegate {

    private Map<String, List<List<String>>> tableDataMap = new LinkedHashMap<String, List<List<String>>>();

    public XlsExcelReaderDelegate(String fileName, InputStream inputStream, boolean maxRowLimit)
        throws IOException, FileNotFoundException {
        super(inputStream);
    }

    public XlsExcelReaderDelegate(InputStream inputStream) throws IOException, FileNotFoundException {
        super(inputStream);
    }

    public Map<String, List<List<String>>> getTableDataMap() {
        return tableDataMap;
    }

    public void setTableDataMap(Map<String, List<List<String>>> tableDataMap) {
        this.tableDataMap = tableDataMap;
    }

    @Override
    public void optRows(int sheetIndex, String sheetName, int curRow, List<String> rowlist) throws SQLException {

        // 构建Sheet数据
        List<List<String>> curDataTable = null;
        if (tableDataMap.containsKey(sheetName)) {
            curDataTable = tableDataMap.get(sheetName);
        } else {
            curDataTable = new ArrayList<List<String>>();
            tableDataMap.put(sheetName, curDataTable);
        }
        List<String> curRowList = new ArrayList<String>();
        curRowList.addAll(rowlist);
        curDataTable.add(curRowList);
    }

    public Map<String, List<List<String>>> convertExcelToRowListMap(InputStream inputStream) {

        Map<String, List<List<String>>> resultMap = new LinkedHashMap<>();

        try {
            this.process();
            Map<String, List<List<String>>> tableDataMap = this.getTableDataMap();

            for (Map.Entry<String, List<List<String>>> entry : tableDataMap.entrySet()) {
                String tableName = entry.getKey();
                List<List<String>> tableData = entry.getValue();

                // 修正行、列
                int firstRowNum = 0;
                int lastRowNum = tableData.size() - 1;
                long realLastRowNum = lastRowNum;
                boolean findNotEmptyRow = false;
                int realLastColumnNum = 0;

                // 计算实际行数
                for (int i = lastRowNum; i >= 0; i--) {
                    List<String> row = tableData.get(i);
                    int lastCellNum = row.size() - 1;
                    if (row != null && lastCellNum >= 0) {
                        // 判断当前行是否为空行
                        for (int j = 0; j <= lastCellNum; j++) {
                            String cell = row.get(j);
                            if (cell != null && !"".equals(cell.trim())) {
                                findNotEmptyRow = true;
                            }
                            cell = null;
                        }
                    }
                    if (findNotEmptyRow) {
                        realLastRowNum = i;
                        break;
                    }
                    row = null;
                }

                // 如果sheet为空则跳过
                if (findNotEmptyRow) {
                    // 计算出实际列数，包含数据的cell的最大值
                    for (int i = firstRowNum; i < realLastRowNum + 1; i++) {
                        List<String> row = tableData.get(i);
                        int lastCellNum = row.size() - 1;
                        if (row != null && lastCellNum >= 0) {
                            // 从每一行的最后一列向前面找到 第一个不为空的cell
                            for (int j = lastCellNum; j >= 0; j--) {
                                String cell = row.get(j);
                                if (cell != null && !"".equals(cell.trim())) {
                                    if (j > realLastColumnNum) {
                                        realLastColumnNum = j;
                                    }
                                }
                                cell = null;
                            }
                        }
                        row = null;
                    }

                    // 设置限制返回数据行数
                    long returnLastRowNum = realLastRowNum;
                    long returnLastColumnNum = realLastColumnNum;

                    // 构建数据
                    List<List<String>> rowList = new ArrayList<>();
                    for (int i = 0; i <= returnLastRowNum; i++) {
                        List<String> rowData = tableData.get(i);
                        if (rowData.size() <= returnLastColumnNum) {
                            for (int j = rowData.size(); j <= returnLastColumnNum; j++) {
                                rowData.add("");
                            }
                        } else {
                            int maxSize = rowData.size();
                            for (int j = maxSize - 1; j > returnLastColumnNum; j--) {
                                rowData.remove(j);
                            }
                        }
                        rowList.add(rowData);
                    }
                    resultMap.put(tableName, rowList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

}
