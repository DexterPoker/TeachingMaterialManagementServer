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
public class Users {
    private int userId;
    private String userName;
    private String cid;
    private String userLevel;
    private String password;
    private Timestamp createTime;
    private int status;

    @Id
    @Column(name = "UserID", nullable = false, insertable = true, updatable = true)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "UserName", nullable = false, insertable = true, updatable = true, length = 45)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "Cid", nullable = true, insertable = true, updatable = true, length = 45)
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "UserLevel", nullable = false, insertable = true, updatable = true, length = 45)
    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    @Basic
    @Column(name = "Password", nullable = false, insertable = true, updatable = true, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

        Users users = (Users) o;

        if (status != users.status) return false;
        if (userId != users.userId) return false;
        if (cid != null ? !cid.equals(users.cid) : users.cid != null) return false;
        if (createTime != null ? !createTime.equals(users.createTime) : users.createTime != null) return false;
        if (password != null ? !password.equals(users.password) : users.password != null) return false;
        if (userLevel != null ? !userLevel.equals(users.userLevel) : users.userLevel != null) return false;
        if (userName != null ? !userName.equals(users.userName) : users.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (userLevel != null ? userLevel.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }
}
