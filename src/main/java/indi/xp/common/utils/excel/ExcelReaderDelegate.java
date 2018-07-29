package indi.xp.common.utils.excel;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ExcelReaderDelegate {

    public Map<String, List<List<String>>> convertExcelToRowListMap(InputStream inputStream) throws Exception;

}
