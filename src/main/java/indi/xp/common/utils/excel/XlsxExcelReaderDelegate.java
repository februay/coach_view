package indi.xp.common.utils.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.ObjectUtils;

public class XlsxExcelReaderDelegate implements ExcelReaderDelegate {

    public Map<String, List<List<String>>> convertExcelToRowListMap(InputStream inputStream) throws Exception {

        Map<String, List<List<String>>> resultMap = new LinkedHashMap<>();

        OPCPackage opcPackage = OPCPackage.open(inputStream);
        XSSFReader xssfReader = new XSSFReader(opcPackage);
        Map<String, String> sheetNameToInternalId = new LinkedHashMap<String, String>();

        /*
         * 1、首先解析出sheet列表
         */
        XlsxWorkbookToTablesHandler workbookToTablesHandler = new XlsxWorkbookToTablesHandler(sheetNameToInternalId);
        this.buildTables(xssfReader, workbookToTablesHandler);

        for (Map.Entry<String, String> sheetEntry : sheetNameToInternalId.entrySet()) {
            String sheetName = sheetEntry.getKey();
            String sheetId = sheetEntry.getValue();
            List<List<String>> rowList = this.buildRowList(sheetId, xssfReader);
            resultMap.put(sheetName, rowList);
        }
        return resultMap;
    }

    private void buildTables(XSSFReader xssfReader, XlsxWorkbookToTablesHandler workbookToTablesHandler)
        throws Exception {
        InputStream workbookData = null;
        try {
            workbookData = xssfReader.getWorkbookData();
            XMLReader workbookParser = ExcelAdvanceUtil.createXmlReader();
            workbookParser.setContentHandler(workbookToTablesHandler);
            workbookParser.parse(new InputSource(workbookData));
        } finally {
            ObjectUtils.safeClose(workbookData);
        }

    }

    private List<List<String>> buildRowList(String relationshipId, XSSFReader xssfReader) throws Exception {
        InputStream sheetData = null;
        List<List<String>> rowList = new ArrayList<>();
        try {
            sheetData = xssfReader.getSheet(relationshipId);
            XlsxSheetToRowsHandler xlsxSheetToRowsHandler = new XlsxSheetToRowsHandler(xssfReader);
            XMLReader sheetParser = ExcelAdvanceUtil.createXmlReader();
            sheetParser.setContentHandler(xlsxSheetToRowsHandler);
            sheetParser.parse(new InputSource(sheetData));

            // 在这里sheet的数据已经准备好，现在开始组装DataSet及Table的Column信息
            List<List<String>> allRowList = xlsxSheetToRowsHandler.getAllRowList();
            if (CollectionUtils.isNotEmpty(allRowList)) {

                // 先处理列数据，并提取出最大的有数据的列索引
                // 原提取最大列索引的代码：int realLastCellNum =
                // xlsxSheetToRowsHandler.getMaxCols() + 1;
                // 原代码会提取出无数据的最大列索引，所以导致列数据超出范围的问题，继而影响正常上传
                int realLastCellNum = ExcelAdvanceUtil.removeEmptyValueOfListThenReturnMaxSize(allRowList);

                // 然后处理行数据：判断allRowList是否是个空表，提取最大有数据的行索引
                Object[] object = ExcelAdvanceUtil.checkIsEmptyTableAndGetLastRowNum(allRowList);
                int lastRowNum = (int) object[0];
                boolean isEmptyTable = (boolean) object[1];
                if (isEmptyTable) {
                    return rowList;
                }

                long realLastRowNum = lastRowNum;
                /*
                 * 生成数据
                 */
                for (int m = 0; m < realLastRowNum; m++) {
                    List<String> rowData = new ArrayList<String>();
                    List<String> row = allRowList.get(m);
                    if (row != null && !row.isEmpty()) {
                        int lastCellNum = realLastCellNum;
                        for (int j = 0; j < lastCellNum; j++) {
                            if (j > row.size() - 1) {
                                rowData.add("");
                            } else {
                                String cell = row.get(j);
                                rowData.add(cell);
                            }
                        }
                    } else {
                        for (int n = 0; n < realLastCellNum; n++) {
                            rowData.add("");
                        }
                    }
                    rowList.add(rowData);
                    row = null;
                }
            }
        } finally {
            ObjectUtils.safeClose(sheetData);
        }
        return rowList;
    }

}
