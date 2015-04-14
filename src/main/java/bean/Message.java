package bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by zql on 2015/4/9.
 */
@Entity
public class Message {
    private int messageId;
    private int fileId;
    private int classId;
    private int userId;
    private int status;

    @Id
    @Column(name = "MessageID", nullable = false, insertable = true, updatable = true)
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "FileID", nullable = false, insertable = true, updatable = true)
    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
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
    @Column(name = "UserID", nullable = false, insertable = true, updatable = true)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "Status", nullable = false, insertable = true, updatable = true)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (classId != message.classId) return false;
        if (fileId != message.fileId) return false;
        if (messageId != message.messageId) return false;
        if (status != message.status) return false;
        if (userId != message.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId;
        result = 31 * result + fileId;
        result = 31 * result + classId;
        result = 31 * result + userId;
        result = 31 * result + status;
        return result;
    }
}
