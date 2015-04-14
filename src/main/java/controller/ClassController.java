/**
 * 
 */
package controller;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Controller;

import bean.Clazz;
import bean.Users;

import com.alibaba.fastjson.JSONObject;
import common.Constant;
import common.Tools;

import dao.ClassstudentDao;
import dao.ClazzDao;
import dao.UserDao;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月16日
 */
@Controller
@Path("/classes")
public class ClassController {

	ClazzDao clazzdao = new ClazzDao();
	ClassstudentDao classstudentdao = new ClassstudentDao();
	UserDao userdao = new UserDao();
	
	@POST
	@Path("addClazz")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addClazz(
			@FormParam("timestamp") String timestamp,
			@FormParam("teacherId") String teacher,
			@FormParam("masterId") String master,
			@FormParam("name") String name ,
			@FormParam("userLevel") String userLevel,
			@FormParam("signature") String signature) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("timestamp", timestamp);
		params.put("teacherId",teacher);
		params.put("masterId",master);
		params.put("userLevel", userLevel);
		params.put("name", name);
		System.out.println("addclazz-----" + params);
		if (signature.equals(Tools.signature(params))) {
			if(userLevel.equals("Admin")){
			Clazz clazz = new Clazz();
			clazz.setClassName(name);
			clazz.setTeacher(Integer.parseInt(teacher));
			clazz.setMaster(Integer.parseInt(master));
			clazzdao.addClazz(clazz);
			return Response.status(200).entity(Tools.setCode(0, null)).build();
			}
			else{
				return Response.status(200).entity(Tools.setCode(302, null)).build();
			}
		} else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}

	@POST
	@Path("addClazzUser")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addClazzUser(@FormParam("id") String id,
			@FormParam("studentId") String studentId,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("studentId", studentId);
		params.put("timestamp", timestamp);
		System.out.println("addclazzuser-----" + params);
		if (signature.equals(Tools.signature(params))) {
			Clazz clazz =  clazzdao.selectClazz(Integer.parseInt(id));
			if(clazz == null){
				return Response.status(200).entity(Tools.setCode(301, null)).build();
			}
			else{
				
				if(studentId.indexOf(",") == -1){
					if(userdao.isStudent(Integer.parseInt(studentId)) == false){
						return Response.status(200).entity(Tools.setCode(205, null)).build();
					}
					else{
						if(classstudentdao.selectClassstudent(Integer.parseInt(id), Integer.parseInt(studentId))){
							return Response.status(200).entity(Tools.setCode(303, null)).build();
						}
						else{
							classstudentdao.addClassStudent(Integer.parseInt(id), Integer.parseInt(studentId));
							return Response.status(200).entity(Tools.setCode(0, null)).build();
						}
					}
				}
				else{
					String[] students = studentId.split(",");
					String exist = "";
					for(int i =0 ; i < studentId.length(); i ++){
						if(userdao.isStudent(Integer.parseInt(studentId)) == false){
							continue;
						}
						if(classstudentdao.selectClassstudent(Integer.parseInt(id), Integer.parseInt(students[i]))){
							exist = exist + students[i] + ",";
						}
						else{
							classstudentdao.addClassStudent(Integer.parseInt(id), Integer.parseInt(students[i]));
						}
					}
					if(exist.equals(""))
						return Response.status(200).entity(Tools.setCode(0, null)).build();
					else{
						JSONObject result = new JSONObject();
						result.put("existStudent", exist);
						return Response.status(200).entity(Tools.setCode(303, result)).build();
					}
				}
				
			}
		} else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
	
	@POST
	@Path("addClazzUserMulty")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClazzUserMulty(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("fileName") String fileName,
			@FormDataParam("id") String id,
			@FormDataParam("timestamp") String timestamp) {

		File f = new File(Constant.tmpPath);
		if(!f.exists())
			f.mkdirs();
		fileName = Tools.getTimestamp() + fileName ;
		String filePath = Constant.tmpPath + fileName;
		
		if(Tools.writeToFile(uploadedInputStream, filePath)){
			String studentId = Tools.getStudentsId(filePath);
			System.out.println("studentId---"  + studentId);
			if(studentId == null || studentId.equals("")||studentId.equals("null")){
				return Response.status(200).entity(Tools.setCode(405, null)).build();
			}
			else{
				Clazz clazz = clazzdao.selectClazz(Integer.parseInt(id));
				if (clazz == null) {
					return Response.status(200).entity(Tools.setCode(301, null)).build();
				} else {
					String[] students = studentId.split(",");
					String exist = "";
					for (int i = 0; i < students.length; i++) {
						if (userdao.isStudent(Integer.parseInt(students[i])) == false) {
							continue;
						}
						if (classstudentdao.selectClassstudent(Integer.parseInt(id),Integer.parseInt(students[i]))) {
							exist = exist + students[i] + ",";
						} else {
							classstudentdao.addClassStudent(Integer.parseInt(id),Integer.parseInt(students[i]));
						}
					}
					if (exist.equals(""))
						return Response.status(200).entity(Tools.setCode(0, null)).build();
					else {
						JSONObject result = new JSONObject();
						result.put("existStudent", exist);
						return Response.status(200).entity(Tools.setCode(303, result)).build();
					}
				}
			}
		}
		else{
			return Response.status(200).entity(Tools.setCode(401, null)).build();
		}
	}
	
	@POST
	@Path("deleteClazzUser")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteClazzUser(@FormParam("id") String id,
			@FormParam("studentId") String studentId,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("studentId", studentId);
		params.put("timestamp", timestamp);
		System.out.println("deleteclazzuser------" + params);
		if (signature.equals(Tools.signature(params))) {
			Clazz clazz =  clazzdao.selectClazz(Integer.parseInt(id));
			if(clazz == null){
				return Response.status(200).entity(Tools.setCode(301, null)).build();
			}
			else{
				if(classstudentdao.selectClassstudent(Integer.parseInt(id),Integer.parseInt(studentId)) == false){
					return Response.status(200).entity(Tools.setCode(300, null)).build();
				}
				else{
					classstudentdao.deleteClassStudent(Integer.parseInt(id), Integer.parseInt(studentId));
					return Response.status(200).entity(Tools.setCode(0, null)).build();
				}
			}
		} else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
	@POST
	@Path("deleteClazz")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteClazz(@FormParam("id") String id,
			@FormParam("classid") String classid,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("classid",classid);
		params.put("timestamp", timestamp);
		System.out.println("deleteclazz----" + params);
		if (signature.equals(Tools.signature(params))) {
			Users user = userdao.selectUser(Integer.parseInt(id));
			if(user == null || !user.getUserLevel().equals("Admin")){
				return Response.status(200).entity(Tools.setCode(305, null)).build();
			}
			else{
				if(clazzdao.selectClazz(Integer.parseInt(classid)) == null){
					return Response.status(200).entity(Tools.setCode(301, null)).build();
				}
				else{
					Clazz clazz = clazzdao.selectClazz(Integer.parseInt(classid));
					clazz.setStatus(0);
					clazzdao.deleteClazz(clazz);
					classstudentdao.deleteClassStudents(Integer.parseInt(classid));
					return Response.status(200).entity(Tools.setCode(0, null)).build();
				}
			}
		} else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}

	@POST
	@Path("getAllClasses")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllClasses(@FormParam("id") String id,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("timestamp", timestamp);
		System.out.println("getAllClasses----" + params);
		if (signature.equals(Tools.signature(params))) {
			Users user = userdao.selectUser(Integer.parseInt(id));
			if(user != null &&(user.getUserLevel().equals("Admin")||user.getUserLevel().equals("Teacher"))){
				Map<String,String> classes = null;
				JSONObject result = new JSONObject();
				classes = clazzdao.getAllClazzList();
				result.put("classes", classes);
				return Response.status(200).entity(Tools.setCode(0, result)).build();
			}
			else{
				return Response.status(200).entity(Tools.setCode(304, null)).build();
			}
		} else
			return Response.status(200).entity(Tools.setCode(100, null)).build();
	}
	
	
}
