/**
 * 
 */
package dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bean.Classstudent;
import bean.Clazz;
import bean.Users;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月15日
 */
public class ClazzDao extends HibernateBaseDAO implements IClazzDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IClazzDao#selectClazz(int)
	 */
	@Override
	public Clazz selectClazz(int clazzId) {
		// TODO Auto-generated method stub
		System.out.println("-------ClazzDaoImp.selectClazz-----------"
				+ clazzId);
		List<Clazz> l = (List<Clazz>) this.query("from Clazz where classId =" + clazzId + " and status = 1");
    	if(!l.isEmpty()){
    		return l.get(0);
    	}
    	else
    		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IClazzDao#selectClazzName(int)
	 */
	@Override
	public String selectClazzName(int clazzId) {
		// TODO Auto-generated method stub
		System.out.println("-------ClazzDaoImp.selectClazzName-----------"
				+ clazzId);
		List<Clazz> l = (List<Clazz>) this.query("from Users where classId =" + clazzId + " and status = 1");
    	if(!l.isEmpty()){
    		return l.get(0).getClassName();
    	}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IClazzDao#selectClazzList(int)
	 */
	@Override
	public Map<String,String> selectClazzListTeacher(int teacherId) {
		// TODO Auto-generated method stub
		System.out
				.println("-------ClazzDaoImp.selectClazzListTeacher-----------"
						+ teacherId);
		List<Clazz> l = (List<Clazz>) this.query("from Clazz where (teacher = "
				+ teacherId + " or master = " + teacherId + ") and status = 1");
		if (l.isEmpty()) {
			return null;
		} else {
			Map<String,String> result = new HashMap<String, String>();
			for (int i = 0; i < l.size(); i++) {
				result.put(l.get(i).getClassId() + "", l.get(i).getClassName());
			}
			return result;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IClazzDao#addClazz(bean.Clazz)
	 */
	@Override
	public void addClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		System.out.println("-------ClazzDaoImp.addClazz-----------"
				+ clazz.getClassName());
		clazz.setStatus(1);
		this.beginThransaction();
		try {
			this.getSession().clear();
			this.getSession().save(clazz);
			this.commitThransaction();
		} catch (Exception ex) {
			ex.printStackTrace();
			this.rollbackThransaction();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IClazzDao#modifyClazz(bean.Clazz)
	 */
	@Override
	public void modifyClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		System.out.println("-------ClazzDaoImp.modifyClazz-----------"
				+ clazz.getClassName());
		this.beginThransaction();
		try {
			this.getSession().clear();
			this.getSession().merge(clazz);
			this.commitThransaction();
		} catch (Exception ex) {
			ex.printStackTrace();
			this.rollbackThransaction();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IClazzDao#deleteClazz(bean.Clazz)
	 */
	@Override
	public void deleteClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		System.out.println("-------ClazzDaoImp.deleteClazz-----------"
				+ clazz.getClassName());
		this.beginThransaction();
		try {
			this.getSession().clear();
			this.getSession().merge(clazz);
			this.commitThransaction();
		} catch (Exception ex) {
			ex.printStackTrace();
			this.rollbackThransaction();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IClazzDao#selectClazzListStudent(int)
	 */
	@Override
	public Map<String,String> selectClazzListStudent(int studentId) {
		// TODO Auto-generated method stub
		System.out
				.println("-------ClazzDaoImp.selectClazzListStudent-----------"
						+ studentId);
		List<Classstudent> l = (List<Classstudent>) this
				.query("from Classstudent where usersUserId = " + studentId + " and status = 1");
		List<Clazz> l2 = (List<Clazz>) this
				.query("from Clazz where assistant = " + studentId + " and status = 1");
		if (l.isEmpty() && l2.isEmpty()) {
			return null;
		} else if (l.isEmpty() && !l2.isEmpty()) {
			Map<String,String> result = new HashMap<String, String>();
			for (int i = 0; i < l2.size(); i++) {
				result.put(l2.get(i).getClassId() + "",l2.get(i).getClassName());
			}
			return result;
		} else if (!l.isEmpty() && l2.isEmpty()) {
			Map<String,String> result = new HashMap<String, String>();
			for (int i = 0; i < l.size(); i++) {
				List<Clazz> l3 = (List<Clazz>) this
						.query("from Clazz where classId = " + l.get(i).getClassClassId() + " and status = 1");
				result.put(l.get(i).getClassClassId() + "",l3.get(0).getClassName());
			}
			return result;
		} else {
			Map<String,String> result = new HashMap<String, String>();
			for (int i = 0; i < l.size(); i++) {
				List<Clazz> l3 = (List<Clazz>) this
						.query("from Clazz where classId = " + l.get(i).getClassClassId() + " and status = 1");
				result.put(l.get(i).getClassClassId() + "",l3.get(0).getClassName());
				result.put(l.get(i).getClassClassId() + "",l3.get(0).getClassName());
			}
			for (int i = 0; i < l2.size(); i++) {
				if(result.containsKey(l2.get(i).getClassId() + ""))
					continue;
				result.put(l2.get(i).getClassId() + "",l2.get(i).getClassName());
			}
			return result;
		}
	}

	public Map<String,String> getAllClazzList() {
		System.out.println("-------ClazzDaoImp.getAllClazzList-----------");
		List<Clazz> l = (List<Clazz>) this.query("from Clazz where status = 1");
		if (l.isEmpty()) {
			return null;
		} else {
			Map<String,String> result = new HashMap<String, String>();
			for (int i = 0; i < l.size(); i++) {
				result.put(l.get(i).getClassId() + "", l.get(i).getClassName());
			}
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see dao.IClazzDao#getClassLevel(int)
	 */
	@Override
	public String getClassLevel(int id ,int userId) {
		// TODO Auto-generated method stub
		System.out.println("-------ClazzDaoImp.getClassLevel-----------");
		List<Clazz> l ;
		l = (List<Clazz>) this.query("from Clazz where master = " + userId + " and classId = " + id + " and status = 1");
		if(!l.isEmpty()){
			return "master";
		}
		else{
			l = (List<Clazz>) this.query("from Clazz where teacher = " + userId + " and classId = " + id + " and status = 1");
			if(!l.isEmpty()){
				return "teacher";
			}
			else{
				l = (List<Clazz>) this.query("from Clazz where assistant = " + userId + " and classId = " + id + " and status = 1");
				if(!l.isEmpty()){
					return "assistant";
				}
				else{
					l = (List<Clazz>) this.query("from Classstudent where usersUserId = " + userId + " and classClassId = " + id + " and status = 1");
					if(!l.isEmpty()){
						return "student";
					}
					else
						return "admin";
				}
			}
		}
	}

}
