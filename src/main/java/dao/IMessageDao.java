/**
 * 
 */
package dao;

import java.util.List;

import bean.Message;

/**
 * @author zql
 *
 * @version ����ʱ�䣺2015��3��30�� 
 */
public interface IMessageDao {
	public void addMessage(Message message);
	public List<Message> getMessage(int cursor,int classid);
}
