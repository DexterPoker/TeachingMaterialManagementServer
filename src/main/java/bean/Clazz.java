package bean;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zql on 2015/4/9.
 */
@Entity
@Table(name = "class", schema = "", catalog = "teachingmaterialmanagement")
public class Clazz {
    private int classId;
    private String className;
    private int teacher;
    private Integer master;
    private Integer assistant;
    private Timestamp createTime;
    private int status;

    @Id
    @Column(name = "ClassID", nullable = false, insertable = true, updatable = true)
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "ClassName", nullable = false, insertable = true, updatable = true, length = 45)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "Teacher", nullable = false, insertable = true, updatable = true)
    public int getTeacher() {
        return teacher;
    }

    public void setTeacher(int teacher) {
        this.teacher = teacher;
    }

    @Basic
    @Column(name = "Master", nullable = true, insertable = true, updatable = true)
    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }

    @Basic
    @Column(name = "Assistant", nullable = true, insertable = true, updatable = true)
    public Integer getAssistant() {
        return assistant;
    }

    public void setAssistant(Integer assistant) {
        this.assistant = assistant;
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

        Clazz clazz = (Clazz) o;

        if (classId != clazz.classId) return false;
        if (status != clazz.status) return false;
        if (teacher != clazz.teacher) return false;
        if (assistant != null ? !assistant.equals(clazz.assistant) : clazz.assistant != null) return false;
        if (className != null ? !className.equals(clazz.className) : clazz.className != null) return false;
        if (createTime != null ? !createTime.equals(clazz.createTime) : clazz.createTime != null) return false;
        if (master != null ? !master.equals(clazz.master) : clazz.master != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classId;
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + teacher;
        result = 31 * result + (master != null ? master.hashCode() : 0);
        result = 31 * result + (assistant != null ? assistant.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }
}
