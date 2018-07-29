package indi.xp.common.utils.excel;

/**
 * 文件扩展名
 */
public enum FileExtension {

    XLS(".xls"),
    XLSX(".xlsx"),
    CSV(".csv"),
    TSV(".tsv"),
    TXT(".txt");

    private String value;

    private FileExtension(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static FileExtension getFileExtensionByValue(String value) {
        FileExtension fileExt = TXT;
        for (FileExtension tmpFileExt : FileExtension.values()) {
            if (tmpFileExt.value().equalsIgnoreCase(value)) {
                fileExt = tmpFileExt;
                break;
            }
        }
        return fileExt;
    }

}
