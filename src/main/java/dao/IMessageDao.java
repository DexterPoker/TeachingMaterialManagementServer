/**
 * 
 */
package dao;

import java.util.List;

import bean.Message;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月30日 
 */
public interface IMessageDao {
	public void addMessage(Message message);
	public List<Message> getMessage(int cursor,int classid);
}
