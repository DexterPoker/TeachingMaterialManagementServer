/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import common.Constant;
import bean.Files;
import bean.Users;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月26日 
 */
public class FileDao extends HibernateBaseDAO implements IFileDao{

	/* (non-Javadoc)
	 * @see dao.IFileDao#addFile(bean.File)
	 */
	@Override
	public void addFile(Files file) {
		// TODO Auto-generated method stub
        System.out.println("-------FileDaoImp.AddFile-----------");
        file.setStatus(1);
        file.setDownCount(0);
        file.setSupportCount(0);
        file.setOwnerId(file.getSumitter());
        file.setIsResuing(0);
        if(file.getFilePath() == null || file.equals(""))
        	file.setFilePath(Constant.defaultPath + file.getClassId() + file.getClassName() + "/");
        this.beginThransaction();
		try{
			this.getSession().clear();
			this.getSession().save(file);
			this.commitThransaction();
		}catch(Exception ex){
			ex.printStackTrace();
			this.rollbackThransaction();
		}
	}

	/* (non-Javadoc)
	 * @see dao.IFileDao#modifyFile(bean.File)
	 */
	@Override
	public void modifyFile(Files file) {
		// TODO Auto-generated method stub
        System.out.println("-------FileDaoImp.modifyFile-----------"
                + file.getFileId());
        this.beginThransaction();
		try{
			this.getSession().clear();
			this.getSession().merge(file);
			this.commitThransaction();
		}catch(Exception ex){
			ex.printStackTrace();
			this.rollbackThransaction();
		}
	}

	/* (non-Javadoc)
	 * @see dao.IFileDao#deleteFile(bean.File)
	 */
	@Override
	public void deleteFile(Files file) {
		// TODO Auto-generated method stub
        System.out.println("-------FileDaoImp.deleteFile-----------"
                + file.getFileId());
        file.setStatus(0);
        this.beginThransaction();
		try{
			this.getSession().clear();
			this.getSession().merge(file);
			this.commitThransaction();
		}catch(Exception ex){
			ex.printStackTrace();
			this.rollbackThransaction();
		}
	}

	/* (non-Javadoc)
	 * @see dao.IFileDao#getFileTen(int)
	 */
	@Override
	public List<Files> getFileTen(int clazzId,int cursor) {
		// TODO Auto-generated method stub
		System.out.println("-------fileDaoImp.getFileTen-----------" 
				+ "clazzid" + clazzId + "cursor" + cursor);
		this.getSession().clear();
    	List<Files> l = (List<Files>) this.query("from Files where classId =" + clazzId + " and fileId > " + cursor + "  and status = 1 ");
    	if(!l.isEmpty()){
    		List<Files> file = new ArrayList<Files>();
    		for(int i = 0; i < l.size() ; i++){
    			if(i==10){
    				break;
    			}
    			file.add(l.get(i));
    		}
    		return file;
    	}
    	else{
    		return null;
    	}
	}


	/* (non-Javadoc)
	 * @see dao.IFileDao#downloadFile(int)
	 */
	@Override
	public void downloadFile(int id) {
		// TODO Auto-generated method stub
		System.out.println("-------fileDaoImp.downloadFileCount-----------" 
				+ "fileid" + id );
		List<Files> l = (List<Files>) this.query("from Files where fileId =" + id + "  and status = 1 ");
    	if(!l.isEmpty()){
    		Files f = l.get(0);
    		f.setDownCount(l.get(0).getDownCount() + 1);
    		modifyFile(f);
    	}
	}

	/* (non-Javadoc)
	 * @see dao.IFileDao#supportFile(int)
	 */
	@Override
	public void supportFile(int id) {
		// TODO Auto-generated method stub
		System.out.println("-------fileDaoImp.downloadFileCount-----------" 
				+ "fileid" + id );
		List<Files> l = (List<Files>) this.query("from Files where fileId =" + id + "  and status = 1 ");
    	if(!l.isEmpty()){
    		Files f = l.get(0);
    		f.setSupportCount(l.get(0).getSupportCount() + 1);
    		modifyFile(f);
    	}
	}

	/* (non-Javadoc)
	 * @see dao.IFileDao#collectFile(int, int)
	 */
	@Override
	public void collectFile(int id, int teacherId) {
		// TODO Auto-generated method stub
		System.out.println("-------fileDaoImp.collectFile-----------" 
				+ "id" + id + "teacher" + teacherId);
    	List<Files> l = (List<Files>) this.query("from Files where fileId =" + id + " and status = 1 ");
    	if(!l.isEmpty()){
    		Files file = l.get(0);
    		file.setOwnerId(teacherId);
    		file.setIsResuing(1);
    		modifyFile(file);
    	}
    	else{
    		return ;
    	}
	}

	/* (non-Javadoc)
	 * @see dao.IFileDao#reuseFile(int)
	 */
	@Override
	public void reuseFile(int classIdBefore,int classIdAfter,String className) {
		// TODO Auto-generated method stub
		System.out.println("-------fileDaoImp.reuseFile-----------" 
				+ "classid" + classIdBefore + "classname" + className );
    	List<Files> l = (List<Files>) this.query("from Files where classId = " + classIdBefore + " and isResuing = 1 and status = 1 ");
    	if(!l.isEmpty()){
    		for(Files f : l){
    			Files file = new Files();
    			file.setFileName(f.getFileName());
    			file.setFilePath(f.getFilePath());
    			file.setFileType(f.getFileType());
    			file.setDescription(f.getDescription());
    			file.setOwnerId(f.getOwnerId());
    			file.setSumitter(f.getSumitter());
    			file.setClassId(classIdAfter);
    			file.setDownCount(0);
    			file.setSupportCount(0);
    			file.setIsResuing(0);
    			file.setStatus(1);
    			file.setClassName(className);
    			addFile(file);
    		}
    	}
    	else{
    		return ;
    	}
	}

	/* (non-Javadoc)
	 * @see dao.IFileDao#selectFile(int)
	 */
	@Override
	public Files selectFile(int fileId) {
		// TODO Auto-generated method stub
		System.out.println("-------fileDaoImp.selectFile-----------" 
				+ "id" + fileId );
		this.getSession().clear();
    	List<Files> l = (List<Files>) this.query("from Files where fileId =" + fileId + " and status = 1 ");
    	if(!l.isEmpty()){
    		return l.get(0);
    	}
    	else{
    		return null;
    	}
	}

}
