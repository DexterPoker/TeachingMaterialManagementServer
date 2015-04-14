/**
 * 
 */
package dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import bean.Files;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月15日 
 */
public interface IFileDao {
	public void addFile(Files file);
	public void modifyFile(Files file);
	public void deleteFile(Files file);
	public Files selectFile(int fileId);
	public List<Files> getFileTen(int clazzId,int cursor);
	public void downloadFile(int id);
	public void supportFile(int id);
	public void collectFile(int id , int teacherId);
	public void reuseFile(int classIdBefore,int classIdAfter,String className);
	
}
