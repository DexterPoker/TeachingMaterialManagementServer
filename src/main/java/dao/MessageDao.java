/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import bean.Files;
import bean.Message;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月30日 
 */
public class MessageDao extends HibernateBaseDAO implements IMessageDao{

	/* (non-Javadoc)
	 * @see dao.IMessageDao#addMessage(bean.Message)
	 */
	@Override
	public void addMessage(Message message) {
		// TODO Auto-generated method stub
		System.out.println("-------MesageDaoImp.addMessage-----------"
				+ message.getFileId());
		message.setStatus(1);
		this.beginThransaction();
		try {
			this.getSession().clear();
			this.getSession().save(message);
			this.commitThransaction();
		} catch (Exception ex) {
			ex.printStackTrace();
			this.rollbackThransaction();
		}
	}

	/* (non-Javadoc)
	 * @see dao.IMessageDao#getMessage(int, int)
	 */
	@Override
	public List<Message> getMessage(int cursor, int classid) {
		// TODO Auto-generated method stub
		System.out.println("-------fileDaoImp.getFileTen-----------" 
				+ "clazzid" + classid + "cursor" + cursor);
    	List<Message> l = (List<Message>) this.query("from Message where classId =" + classid + " and messageId > " + cursor + "  and status = 1 ");
    	if(!l.isEmpty()){
    		List<Message> message = new ArrayList<Message>();
    		for(int i = 0; i < l.size() ; i++){
    			message.add(l.get(i));
    		}
    		return message;
    	}
    	else{
    		return null;
    	}
	}

}
