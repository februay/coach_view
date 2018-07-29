package indi.xp.common.utils.excel;

import java.io.Serializable;

public class FileInfo implements Serializable {

    private static final long serialVersionUID = -7335120001093382343L;

    private FileType fileType;
    private FileExtension fileExtension;

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }

}
