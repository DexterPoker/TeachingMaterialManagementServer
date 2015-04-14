package bean;

import javax.persistence.*;

/**
 * Created by zql on 2015/4/9.
 */
@Entity
@IdClass(ClassstudentPK.class)
public class Classstudent {
    private int classClassId;
    private int usersUserId;
    private int status;

    @Id
    @Column(name = "Class_ClassID", nullable = false, insertable = true, updatable = true)
    public int getClassClassId() {
        return classClassId;
    }

    public void setClassClassId(int classClassId) {
        this.classClassId = classClassId;
    }

    @Id
    @Column(name = "Users_UserID", nullable = false, insertable = true, updatable = true)
    public int getUsersUserId() {
        return usersUserId;
    }

    public void setUsersUserId(int usersUserId) {
        this.usersUserId = usersUserId;
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

        Classstudent that = (Classstudent) o;

        if (classClassId != that.classClassId) return false;
        if (status != that.status) return false;
        if (usersUserId != that.usersUserId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classClassId;
        result = 31 * result + usersUserId;
        result = 31 * result + status;
        return result;
    }
}
