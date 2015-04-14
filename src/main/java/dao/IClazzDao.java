/**
 * 
 */
package dao;

import java.util.Map;

import bean.Clazz;

/**
 * @author zql
 *
 * @version ����ʱ�䣺2015��3��15�� 
 */
public interface IClazzDao {
	public Clazz selectClazz(int clazzId);
	public String selectClazzName(int clazzId);
	public Map<String,String> getAllClazzList();
	public Map<String,String> selectClazzListTeacher(int teacherId);
	public Map<String,String> selectClazzListStudent(int studentId);
	public void addClazz(Clazz clazz);
	public void modifyClazz(Clazz clazz);
	public void deleteClazz(Clazz clazz);
	public String getClassLevel(int id , int userId);
}
