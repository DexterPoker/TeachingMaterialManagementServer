/**
 * 
 */
package dao;

import java.util.Map;


/**
 * @author zql
 *
 * @version ����ʱ�䣺2015��3��17�� 
 */
public interface IClassstudentDao {
	public void addClassStudent(int id,int studentId);
	public void deleteClassStudent(int id ,int studentId);
	public void deleteClassStudents(int id);
	public boolean selectClassstudent(int id);
	public boolean selectClassstudent(int id , int studentId);
	public Map<String,String> getAllStudent(int id);
}
