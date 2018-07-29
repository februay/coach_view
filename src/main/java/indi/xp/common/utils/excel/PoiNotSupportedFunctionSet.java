package indi.xp.common.utils.excel;

import org.apache.poi.ss.formula.WorkbookEvaluator;

import java.util.HashSet;
import java.util.Set;

public class PoiNotSupportedFunctionSet {

    private static Set<String> poiNotSupportedFunctionSet = new HashSet<>();

    static {
        for (String unsupportedFunName : WorkbookEvaluator.getNotSupportedFunctionNames()) {
            poiNotSupportedFunctionSet.add(unsupportedFunName);
        }
    }

    public static boolean isPoiNotSupportedFunction(String functionName) {
        if (functionName == null || functionName.equals("")) {
            return false;
        }
        return poiNotSupportedFunctionSet.contains(functionName.toUpperCase());
    }

}
