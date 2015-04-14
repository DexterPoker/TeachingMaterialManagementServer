package bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zql on 2015/4/9.
 */
public class ClassstudentPK implements Serializable {
    private int classClassId;
    private int usersUserId;

    @Column(name = "Class_ClassID", nullable = false, insertable = true, updatable = true)
    @Id
    public int getClassClassId() {
        return classClassId;
    }

    public void setClassClassId(int classClassId) {
        this.classClassId = classClassId;
    }

    @Column(name = "Users_UserID", nullable = false, insertable = true, updatable = true)
    @Id
    public int getUsersUserId() {
        return usersUserId;
    }

    public void setUsersUserId(int usersUserId) {
        this.usersUserId = usersUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassstudentPK that = (ClassstudentPK) o;

        if (classClassId != that.classClassId) return false;
        if (usersUserId != that.usersUserId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classClassId;
        result = 31 * result + usersUserId;
        return result;
    }
}
