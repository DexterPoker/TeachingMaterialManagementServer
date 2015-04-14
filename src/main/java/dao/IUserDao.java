/**
 * 
 */
package dao;

import java.util.Map;

import bean.Users;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月10日 
 */
public interface IUserDao {
    public void AddUser(Users user);
    public void modifyUser(Users user);
    public void deleteUser(Users user);
    public Users selectUser(int id , String password);
    public Users selectUser(int id);
    public Map<String,String> getAllUser();
    public Map<String,String> getAllTeacher();
    public boolean isStudent(int id);
}
