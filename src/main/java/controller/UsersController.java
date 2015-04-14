/**
 * 
 */
package controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;

import bean.Users;

import com.alibaba.fastjson.JSONObject;
import common.Tools;

import dao.ClassstudentDao;
import dao.UserDao;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月15日 
 */
@Controller
@Path("/users")
public class UsersController {
	
	UserDao userdao = new UserDao();
	
	@POST
	@Path("addUser")
	@Produces({MediaType.APPLICATION_JSON})
	public Response addUser(@FormParam("password") String password,
			@FormParam("timestamp") String timestamp, 
			@FormParam("level") String level,
			@FormParam("name") String name,
			@FormParam("signature") String signature){
		Map<String, String> params = new HashMap<String, String>();
		params.put("password", password);
		params.put("name", name);
		params.put("level", level);
		params.put("timestamp", timestamp);
		System.out.println("adduser-----" + params);
		System.out.println("signature client---" + signature);
		System.out.println("signature server---" + Tools.signature(params));
		System.out.println(signature.equals(Tools.signature(params)));
		if(signature.equals(Tools.signature(params))){
			Users user = new Users();
			user.setUserLevel(level);
			user.setPassword(password);
			user.setUserName(name);
			userdao.AddUser(user);
			return Response.status(200).entity(Tools.setCode(0, null)).build();
		}
		else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
	@POST
	@Path("changePassword")
	@Produces({MediaType.APPLICATION_JSON})
	public Response changePassword(@FormParam("id") String id, 
			@FormParam("passwordold") String passwordold,
			@FormParam("passwordnew") String passwordnew,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("passwordold", passwordold);
		params.put("passwordnew", passwordnew);
		params.put("timestamp", timestamp);
		System.out.println("changePwd------" + params);
		if(signature.equals(Tools.signature(params))){
			Users user = userdao.selectUser(Integer.parseInt(id));
			if(user == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				user = userdao.selectUser(Integer.parseInt(id), passwordold);
				if(user == null){
					return Response.status(200).entity(Tools.setCode(201, null)).build();
				}
				else{
					user.setPassword(passwordnew);
					userdao.modifyUser(user);
					return Response.status(200).entity(Tools.setCode(0, null)).build();
				}
			}
		}
		else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
	@POST
	@Path("resetPassword")
	@Produces({MediaType.APPLICATION_JSON})
	public Response resetPassword(@FormParam("id") String id,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("timestamp", timestamp);
		System.out.println("resetPwd-----" + params);
		if(signature.equals(Tools.signature(params))){
			Users user = userdao.selectUser(Integer.parseInt(id));
			if(user == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				user.setPassword(Tools.md5(id + ""));
				userdao.modifyUser(user);
				return Response.status(200).entity(Tools.setCode(0, null)).build();
			}
		}
		else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
	@POST
	@Path("deleteUser")
	@Produces({MediaType.APPLICATION_JSON})
	public Response deleteUser(@FormParam("id") String id,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("timestamp", timestamp);
		System.out.println("deleteUser----" + params);
		if(signature.equals(Tools.signature(params))){
			Users user = userdao.selectUser(Integer.parseInt(id));
			if(user == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				user.setStatus(0);
				userdao.modifyUser(user);
				return Response.status(200).entity(Tools.setCode(0, null)).build();
			}
		}
		else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
	@POST
	@Path("getAllUser")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllUser(@FormParam("id") String id,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("timestamp", timestamp);
		System.out.println("getAlluser-----" + params);
		if(signature.equals(Tools.signature(params))){
			Users user = userdao.selectUser(Integer.parseInt(id));
			if(user == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				if(user.getUserLevel().equals("Admin")){
					JSONObject result = new JSONObject();
					result.put("users",userdao.getAllUser());
					return Response.status(200).entity(Tools.setCode(0, result)).build();
				}
				else{
					return Response.status(200).entity(Tools.setCode(202, null)).build();
				}
			}
		}
		else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
	@POST
	@Path("getAllTeacher")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllTeacher(@FormParam("id") String id,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("timestamp", timestamp);
		System.out.println("getAllTeacher-----" + params);
		if(signature.equals(Tools.signature(params))){
			Users user = userdao.selectUser(Integer.parseInt(id));
			if(user == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				if(user.getUserLevel().equals("Admin")){
					JSONObject result = new JSONObject();
					result.put("users",userdao.getAllTeacher());
					return Response.status(200).entity(Tools.setCode(0, result)).build();
				}
				else{
					return Response.status(200).entity(Tools.setCode(203, null)).build();
				}
			}
		}
		else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
	
	@POST
	@Path("getAllStuents")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllStuents(@FormParam("id") String id,
			@FormParam("classid") String classid,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("classid",classid);
		params.put("timestamp", timestamp);
		System.out.println("getAllStuents-----" + params);
		if(signature.equals(Tools.signature(params))){
			Users user = userdao.selectUser(Integer.parseInt(id));
			if(user == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				if(user.getUserLevel().equals("Admin")||user.getUserLevel().equals("Teacher")){
					JSONObject result = new JSONObject();
					result.put("users",new ClassstudentDao().getAllStudent(Integer.parseInt(classid)));
					return Response.status(200).entity(Tools.setCode(0, result)).build();
				}
				else{
					return Response.status(200).encoding(Tools.setCode(204, null)).build();
				}
			}
		}
		else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
}
