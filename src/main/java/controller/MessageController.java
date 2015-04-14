/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;

import bean.FileGroup;
import bean.Files;
import bean.Message;

import com.alibaba.fastjson.JSONObject;

import common.Tools;
import dao.ClazzDao;
import dao.FileDao;
import dao.MessageDao;
import dao.UserDao;

/**
 * @author zql
 *
 * @version 创建时间：2015年4月9日 
 */
@Controller
@Path("/message")
public class MessageController {
	
	ClazzDao classdao = new ClazzDao();
	UserDao userdao = new UserDao();
	MessageDao messagedao = new MessageDao();
	FileDao filedao = new FileDao();
	
	@POST
    @Path("getMessage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessage(
    		@FormParam("id") String id,
    		@FormParam("classId") String classId,
    		@FormParam("cursor") String cursor,
    		@FormParam("timestamp") String timestamp,
    		@FormParam("signature") String signature) {
		
		JSONObject params = new JSONObject();
		params.put("id", id);
		params.put("classId", classId);
		params.put("cursor", cursor);
		params.put("timestamp", timestamp);
		System.out.println("getMessage---------" + params);
		if(signature.equals(Tools.signature(params))){
			if(userdao.selectUser(Integer.parseInt(id)) == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				if(classdao.selectClazz(Integer.parseInt(classId)) == null){
					return Response.status(200).entity(Tools.setCode(301, null)).build();
				}
				else{
					FileGroup files = new FileGroup();
					List<Message> messagelist = messagedao.getMessage(Integer.parseInt(cursor), Integer.parseInt(classId));
					if(messagelist == null){
						return Response.status(200).entity(Tools.setCode(500, null)).build();
					}
					else{
						List<Files> filelist = new ArrayList<Files>();
						for(Message message : messagelist){
							Files file = filedao.selectFile(message.getFileId());
							filelist.add(file);
						}
						files.setFiles(filelist);
						String filesJsonString = JSONObject.toJSONString(files);
						JSONObject result = new JSONObject();
						result.put("files", filesJsonString);
						return Response.status(200).entity(Tools.setCode(0, result)).build();
					}
				}
			}
		}else{
			return Response.status(200).entity(Tools.setCode(100, null)).build();
		}
	}
}
