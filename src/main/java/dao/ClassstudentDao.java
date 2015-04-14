/**
 * 
 */
package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Classstudent;
import bean.Users;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月17日 
 */
public class ClassstudentDao extends HibernateBaseDAO implements IClassstudentDao{

	private UserDao userdao = new UserDao();
	
	/* (non-Javadoc)
	 * @see dao.IClassstudentDao#addClassStudent(int, int)
	 */
	@Override
	public void addClassStudent(int id, int studentId) {
		// TODO Auto-generated method stub
	        System.out.println("-------ClassstudentDaoImp.AddUser-----------"
	                + "classid " + id + " studentid " + studentId);
	        Classstudent classstudent = new Classstudent();
	        classstudent.setClassClassId(id);
	        classstudent.setUsersUserId(studentId);
	        classstudent.setStatus(1);
	        this.beginThransaction();
			try{
				this.getSession().clear();
				this.getSession().save(classstudent);
				this.commitThransaction();
			}catch(Exception ex){
				ex.printStackTrace();
				this.rollbackThransaction();
			}
	}

	/* (non-Javadoc)
	 * @see dao.IClassstudentDao#deleteClassStudent(int, int)
	 */
	@Override
	public void deleteClassStudent(int id, int studentId) {
		// TODO Auto-generated method stub
		 System.out.println("-------ClassstudentDaoImp.DeleteUser-----------"
	                + "classid " + id + " studentid " + studentId);
	        Classstudent classstudent = new Classstudent();
	        classstudent.setClassClassId(id);
	        classstudent.setUsersUserId(studentId);
	        classstudent.setStatus(0);
	        this.beginThransaction();
			try{
				this.getSession().clear();
				this.getSession().merge(classstudent);
				this.commitThransaction();
			}catch(Exception ex){
				ex.printStackTrace();
				this.rollbackThransaction();
			}
	}

	/* (non-Javadoc)
	 * @see dao.IClassstudentDao#selectClassstudent(int)
	 */
	@Override
	public boolean selectClassstudent(int id) {
		// TODO Auto-generated method stub
		 System.out.println("-------ClassstudentDaoImp.DeleteUser-----------"
	                + "classid " + id);
		 List<Classstudent> l = (List<Classstudent>) this.query("from Classstudent where classClassId =" + id + " and status = 1");
		 if(l.isEmpty()){
			 return false;
		 }
		 else{
			 return true;
		 }
	}

	/* (non-Javadoc)
	 * @see dao.IClassstudentDao#selectClassstudent(int, int)
	 */
	@Override
	public boolean selectClassstudent(int id, int studentId) {
		// TODO Auto-generated method stub
		System.out.println("-------ClassstudentDaoImp.selectClassstudent-----------"
                + "classid " + id);
	 List<Classstudent> l = (List<Classstudent>) this.query("from Classstudent where classClassId =" + id + " and usersUserId =" + studentId + " and status = 1");
	 if(l.isEmpty()){
		 return false;
	 }
	 else{
		 return true;
	 }
	}

	/* (non-Javadoc)
	 * @see dao.IClassstudentDao#deleteClassStudents(int)
	 */
	@Override
	public void deleteClassStudents(int id) {
		// TODO Auto-generated method stub
		System.out.println("-------ClassstudentDaoImp.DeleteUser-----------"
                + "classid " + id );
		List<Classstudent> l = (List<Classstudent>) this.query("from Classstudent where classClassId =" + id + " and status = 1");
		if(l.isEmpty())
			return;
        for(Classstudent classstudent : l){
        	classstudent.setStatus(0);
            this.beginThransaction();
    		try{
    			this.getSession().delete(classstudent);
    			this.commitThransaction();
    		}catch(Exception ex){
    			ex.printStackTrace();
    			this.rollbackThransaction();
    		}
        }
	}

	/* (non-Javadoc)
	 * @see dao.IClassstudentDao#getAllStudent(int)
	 */
	@Override
	public Map<String, String> getAllStudent(int id) {
		// TODO Auto-generated method stub
		System.out.println("-------ClassstudentDaoImp.getAllStudent-----------");
		List<Classstudent> l = (List<Classstudent>) this.query("from Classstudent where classClassId =" + id + " and status = 1");
		Map<String, String> map = new HashMap<String, String>();
		for(Classstudent classstudent : l){
			Users user = userdao.selectUser(classstudent.getUsersUserId());
			if(user != null)
				map.put(user.getUserId() + "",user.getUserName());
		}
		System.out.println();
		return map;
	}

}
