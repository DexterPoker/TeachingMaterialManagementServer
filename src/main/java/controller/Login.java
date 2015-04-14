/**
 * 
 */
package controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;

import bean.Users;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.multipart.FormDataParam;

import common.Tools;
import dao.ClazzDao;
import dao.UserDao;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月11日
 */
@Controller
@Path("/login")
public class Login {
	
	UserDao userdao = new UserDao();
	ClazzDao clazzdao = new ClazzDao();
	
	@POST
	@Path("check")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginCheck(@FormParam("id") String id,
			@FormParam("password") String password,
			@FormParam("timestamp") String timestamp,
			@FormParam("cid") String cid,
			@FormParam("signature") String signature) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("password", password);
		params.put("timestamp", timestamp);
		params.put("cid", cid);
		System.out.println("check-----" + params);
		JSONObject result = new JSONObject();
		if(signature.equals(Tools.signature(params))){
			Users user = userdao.selectUser(Integer.parseInt(id));
			if(user != null){
				user = userdao.selectUser(Integer.parseInt(id), password);
				if(user != null){
					
					user.setCid(cid);
					userdao.modifyUser(user);
					
					Map<String,String> classes = null; 
					switch(user.getUserLevel()){
					case "Teacher":
						classes = clazzdao.selectClazzListTeacher(user.getUserId());
						break;
					case "Student":
						classes = clazzdao.selectClazzListStudent(user.getUserId());
						break;
					case "Admin":
						classes = clazzdao.getAllClazzList();
						break;
					}
					result.put("userId", user.getUserId());
					result.put("userName", user.getUserName());
					result.put("userLevel", user.getUserLevel());
					result.put("classes", classes);
					return Response.status(200).entity(Tools.setCode(0, result)).build();

				}
				else{
					return Response.status(200).entity(Tools.setCode(201, result)).build();

				}
			}
			else{
				return Response.status(200).entity(Tools.setCode(200, result)).build();
			}
		}
		else {
			return Response.status(200).entity(Tools.setCode(100, result)).build();
		}
	}
	
	@POST
	@Path("classList")
	@Produces({MediaType.APPLICATION_JSON})
	public Response classLevel(@FormParam("id") String id ,
			@FormParam("studentId") String studentid,
			@FormParam("timestamp") String timestamp ,
			@FormParam("signature") String signature ){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("studentId", studentid);
		params.put("timestamp", timestamp);
		System.out.println("classlist-------" + params);
		if(signature.equals(Tools.signature(params))){
			if(clazzdao.selectClazz(Integer.parseInt(id)) == null)
				return Response.status(200).entity(Tools.setCode(301, null)).build();
			else{
				JSONObject result = new JSONObject();
				if(clazzdao.getClassLevel(Integer.parseInt(id),Integer.parseInt(studentid)) == null){
					if(userdao.selectUser(Integer.parseInt(studentid)).getUserLevel().equals("Admin")){
						result.put("classLevel", "Admin");
						return Response.status(200).entity(Tools.setCode(0, result)).build();
					}
					else
						return Response.status(200).entity(Tools.setCode(300, null)).build();
				}
				result.put("classLevel", clazzdao.getClassLevel(Integer.parseInt(id), Integer.parseInt(studentid)));
				return Response.status(200).entity(Tools.setCode(0, result)).build();
			}
		}
		else{
			return Response.status(200).entity(Tools.setCode(100, null)).build();
		}
	}
	
}
