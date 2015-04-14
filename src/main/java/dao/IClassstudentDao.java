/**
 * 
 */
package dao;

import java.util.Map;


/**
 * @author zql
 *
 * @version 创建时间：2015年3月17日 
 */
public interface IClassstudentDao {
	public void addClassStudent(int id,int studentId);
	public void deleteClassStudent(int id ,int studentId);
	public void deleteClassStudents(int id);
	public boolean selectClassstudent(int id);
	public boolean selectClassstudent(int id , int studentId);
	public Map<String,String> getAllStudent(int id);
}
