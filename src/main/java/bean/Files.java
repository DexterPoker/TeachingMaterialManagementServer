package bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by zql on 2015/4/9.
 */
@Entity
public class Files {
    private int fileId;
    private String fileName;
    private String filePath;
    private String fileType;
    private String description;
    private int ownerId;
    private int sumitter;
    private int classId;
    private int downCount;
    private int supportCount;
    private int isResuing;
    private int status;
    private Timestamp createTime;
    private String className;

    @Id
    @Column(name = "FileID", nullable = false, insertable = true, updatable = true)
    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "FileName", nullable = false, insertable = true, updatable = true, length = 200)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "FilePath", nullable = false, insertable = true, updatable = true, length = 200)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "FileType", nullable = false, insertable = true, updatable = true, length = 45)
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "Description", nullable = false, insertable = true, updatable = true, length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "OwnerID", nullable = false, insertable = true, updatable = true)
    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(name = "Sumitter", nullable = false, insertable = true, updatable = true)
    public int getSumitter() {
        return sumitter;
    }

    public void setSumitter(int sumitter) {
        this.sumitter = sumitter;
    }

    @Basic
    @Column(name = "ClassID", nullable = false, insertable = true, updatable = true)
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "DownCount", nullable = false, insertable = true, updatable = true)
    public int getDownCount() {
        return downCount;
    }

    public void setDownCount(int downCount) {
        this.downCount = downCount;
    }

    @Basic
    @Column(name = "SupportCount", nullable = false, insertable = true, updatable = true)
    public int getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(int supportCount) {
        this.supportCount = supportCount;
    }

    @Basic
    @Column(name = "IsResuing", nullable = false, insertable = true, updatable = true)
    public int getIsResuing() {
        return isResuing;
    }

    public void setIsResuing(int isResuing) {
        this.isResuing = isResuing;
    }

    @Basic
    @Column(name = "Status", nullable = false, insertable = true, updatable = true)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "CreateTime", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "ClassName", nullable = false, insertable = true, updatable = true, length = 45)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Files files = (Files) o;

        if (classId != files.classId) return false;
        if (downCount != files.downCount) return false;
        if (fileId != files.fileId) return false;
        if (isResuing != files.isResuing) return false;
        if (ownerId != files.ownerId) return false;
        if (status != files.status) return false;
        if (sumitter != files.sumitter) return false;
        if (supportCount != files.supportCount) return false;
        if (className != null ? !className.equals(files.className) : files.className != null) return false;
        if (createTime != null ? !createTime.equals(files.createTime) : files.createTime != null) return false;
        if (description != null ? !description.equals(files.description) : files.description != null) return false;
        if (fileName != null ? !fileName.equals(files.fileName) : files.fileName != null) return false;
        if (filePath != null ? !filePath.equals(files.filePath) : files.filePath != null) return false;
        if (fileType != null ? !fileType.equals(files.fileType) : files.fileType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fileId;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + ownerId;
        result = 31 * result + sumitter;
        result = 31 * result + classId;
        result = 31 * result + downCount;
        result = 31 * result + supportCount;
        result = 31 * result + isResuing;
        result = 31 * result + status;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        return result;
    }
}
