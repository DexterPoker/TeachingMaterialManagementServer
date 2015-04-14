/**
 * 
 */
package dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Classstudent;
import bean.Users;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月10日 
 */
public class UserDao extends HibernateBaseDAO implements IUserDao  {
	   
	    public void AddUser(Users user) {
	        System.out.println("-------UserDaoImp.AddUser-----------"
	                + user.getUserName());
	        user.setStatus(1);
	        this.beginThransaction();
			try{
				this.getSession().clear();
				this.getSession().save(user);
				this.commitThransaction();
			}catch(Exception ex){
				ex.printStackTrace();
				this.rollbackThransaction();
			}
	    }

	    public void modifyUser(Users user) {
	        System.out.println("-------UserDaoImp.modifyUser-----------"
	                + user.getUserName());
	        this.beginThransaction();
			try{
				this.getSession().clear();
				this.getSession().merge(user);
				this.commitThransaction();
			}catch(Exception ex){
				ex.printStackTrace();
				this.rollbackThransaction();
			}
	    }

	    public void deleteUser(Users user) {
	        System.out.println("-------UserDaoImp.deleteUser-----------"
	                + user.getUserName());
	        this.beginThransaction();
			try{
				this.getSession().clear();
				this.getSession().merge(user);
				this.commitThransaction();
			}catch(Exception ex){
				ex.printStackTrace();
				this.rollbackThransaction();
			}
	    }
	    
	    public Users selectUser(int id , String password){
	    	System.out.println("-------UserDaoImp.selectUser----id+password-------" + id);
	    	List<Users> l = (List<Users>) this.query("from Users where userId =" + id + " and status = 1");
	    	if(!l.isEmpty()){
	    		Users user = l.get(0);
	    		if(user.getPassword().equals(password))
	    			return user;
	    		else
	    			return null;
	    	}
	    	else{
	    		return null;
	    	}
	    }
	    
		public Users selectUser(int id) {
			System.out.println("-------UserDaoImp.selectUser------id-----" + id);
			List<Users> l = (List<Users>) this.query("from Users where userId =" + id + " and status = 1");
	    	if(!l.isEmpty()){
	    		return l.get(0);
	    	}
	    	else
	    		return null;
		}
		/**
		 * @param args
		 */
		public static void main(String[] args){
			Users user = new Users();
			user.setUserName("123321");
			user.setPassword("123456");
//			user.setStatus(1);
			user.setUserLevel("Student");
			user.setUserId(1);
			SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String time = format.format(new Date());
		
			new UserDao().modifyUser(user);
			System.out.println(time);
//			user.setCreateTime(Timestamp.valueOf(time));
//			new UserDao().AddUser(user);
			System.out.println(new UserDao().selectUser(3));
		}

		/* (non-Javadoc)
		 * @see dao.IUserDao#getAllUser()
		 */
		@Override
		public Map<String, String> getAllUser() {
			// TODO Auto-generated method stub
			System.out.println("-------UserDaoImp.getAllUser-----------");
			List<Users> l = (List<Users>) this.query("from Users");
			Map<String, String> map = new HashMap<String, String>();
			for(Users u : l){
				if(u.getUserLevel().equals("Admin"))
					continue;
				map.put(u.getUserId() + "",u.getUserName());
			}
			return map;
		}

		/* (non-Javadoc)
		 * @see dao.IUserDao#getAllTeacher()
		 */
		@Override
		public Map<String, String> getAllTeacher() {
			// TODO Auto-generated method stub
			System.out.println("-------UserDaoImp.getAllTeacher-----------");
			List<Users> l = (List<Users>) this.query("from Users where userLevel = 'Teacher'");
			Map<String, String> map = new HashMap<String, String>();
			for(Users u : l){
				map.put(u.getUserId() + "",u.getUserName());
			}
			return map;
		}

		/* (non-Javadoc)
		 * @see dao.IUserDao#isStudent(int)
		 */
		@Override
		public boolean isStudent(int id) {
			// TODO Auto-generated method stub
			System.out.println("-------UserDaoImp.isStudent-----------");
			List<Users> l = (List<Users>) this.query("from Users where userId =" + id + " and status = 1 and userLevel = 'Student'");
			if(!l.isEmpty())
				return true;
			return false;
		}
}
