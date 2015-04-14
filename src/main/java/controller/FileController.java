/**
 * 
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Controller;

import bean.FileGroup;
import bean.Files;
import bean.Message;

import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;

import common.Constant;
import common.PushtoList;
import common.Tools;
import common.pushClient;
import dao.ClassstudentDao;
import dao.ClazzDao;
import dao.FileDao;
import dao.MessageDao;
import dao.UserDao;



/**
 * @author zql
 *
 * @version 创建时间：2015年3月26日 
 */

@Controller
@Path("/file")
public class FileController {

	FileDao filedao = new FileDao();
	UserDao userdao = new UserDao();
	ClazzDao classdao = new ClazzDao();
	MessageDao messagedao = new MessageDao();
	ClassstudentDao classstudentdao = new ClassstudentDao();
	List<String> cid = new ArrayList<String>();
	String title,text;
	
	static String appId = "3nlkSm6cYh7seBhLIEqmg1";
	static String appkey = "bSD0HoVg3O7hl8rhqZ5E09";
	static String master = "9pv9K2kFaa6FgCzRyo2iR7";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	

	@POST
	@Path("/uploadFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("fileName") String fileName,
			@FormDataParam("fileType") String fileType,
			@FormDataParam("fileDesc") String fileDesc,
			@FormDataParam("id") String id,
			@FormDataParam("classId") String classId,
			@FormDataParam("className") String className,
			@FormDataParam("timestamp") String timestamp) {

		JSONObject params = new JSONObject();
		params.put("id", id);
		params.put("classId", classId);
		params.put("className", className);
		params.put("timestamp", Tools.getTimestamp());
		params.put("fileName", fileName);
		params.put("fileType", fileType);
		params.put("fileDesc", fileDesc);
		System.out.println("uploadFile---------" + params);
//		System.out.println(signature.equals(Tools.signature(params)));
		
//		if(signature.equals(Tools.signature(params))){
			if(userdao.selectUser(Integer.parseInt(id)) == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				if(classdao.selectClazz(Integer.parseInt(classId)) == null){
					return Response.status(200).entity(Tools.setCode(301, null)).build();
				}
				else{
					String path = classId + className + "/";
					
					File f = new File(Constant.defaultPath + path);
					if(!f.exists())
						f.mkdirs();
					
					fileName = Tools.getTimestamp() + fileName ;
					String filePath = Constant.defaultPath + path + fileName;
					
					System.out.println(filePath);
					
					Files file = new Files();
					file.setClassId(Integer.parseInt(classId));
					file.setClassName(className);
					file.setDescription(fileDesc);
					file.setFileName(fileName);
					file.setFilePath(filePath);
					file.setSumitter(Integer.parseInt(id));
					file.setFileType(fileType);
					
					if(Tools.writeToFile(uploadedInputStream, filePath)){
						filedao.addFile(file);
						return Response.status(200).entity(Tools.setCode(0, null)).build();
					}
					else
						return Response.status(200).entity(Tools.setCode(401, null)).build();
				}
			}
//		}
//		else{
//			return Response.status(200).entity(Tools.setCode(100, null)).build();
//		}
	}

	@POST
    @Path("isExist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isExist(@FormParam("fileId") String fileId,
    		@FormParam("id") String id,
    		@FormParam("classId") String classId,
    		@FormParam("timestamp") String timestamp,
    		@FormParam("signature") String signature) {
		
		JSONObject params = new JSONObject();
		params.put("id", id);
		params.put("fileId", fileId);
		params.put("classId", classId);
		params.put("timestamp", timestamp);
		System.out.println("isExist---------" + params);
		if(signature.equals(Tools.signature(params))){
			if(userdao.selectUser(Integer.parseInt(id)) == null){
				return Response.status(200).entity(Tools.setCode(100, null)).build();
			}
			else{
				if(classdao.selectClazz(Integer.parseInt(classId)) == null){
					return Response.status(200).entity(Tools.setCode(301, null)).build();
				}
				else{
					Files file = filedao.selectFile(Integer.parseInt(fileId));
					if(file == null){
						return Response.status(200).entity(Tools.setCode(400, null)).build();
					}
					else{
						File f = new File(file.getFilePath());
						System.out.println("path---"+ file.getFilePath() + ",name---" + file.getFileName());
						if(!f.exists()){
							return Response.status(200).entity(Tools.setCode(402, null)).build();
						}
						else{
							return Response.status(200).entity(Tools.setCode(0, null)).build();
						}
					}
				}
			}
		}
		else
			return Response.status(200).entity(Tools.setCode(0, null)).build();
    }

	
	
	
	
	
	
	@POST
    @Path("download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] download(@FormParam("fileId") String fileId,
    		@Context HttpServletResponse response) {
		System.out.println("download---------" + fileId);
		
		FileInputStream fis = null;
		Files f = filedao.selectFile(Integer.parseInt(fileId));
		File file = new File(f.getFilePath());

		try
		{
			fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			String filename = f.getFileName().trim();
			response.setHeader("Content-Disposition","attachment;filename=" + new String(filename.getBytes("UTF-8")));
			filedao.downloadFile(Integer.parseInt(fileId));
			return b;
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return null;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
	
	}
	
	
	@POST
    @Path("getFileTen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFileTen(
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
		System.out.println("getFileTen---------" + params);
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
					List<Files> filelist = filedao.getFileTen(Integer.parseInt(classId), Integer.parseInt(cursor));
					if(filelist == null)
						return Response.status(200).entity(Tools.setCode(403, null)).build();
					else{
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
	
	@POST
    @Path("modifyFile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyFile(
    		@FormParam("id") String id,
    		@FormParam("classId") String classId,
    		@FormParam("fileId") String fileId,
    		@FormParam("fileType") String fileType,
    		@FormParam("fileDesc") String fileDesc,
    		@FormParam("timestamp") String timestamp,
    		@FormParam("signature") String signature) {
		
		JSONObject params = new JSONObject();
		params.put("id", id);
		params.put("classId", classId);
		params.put("fileId", fileId);
		params.put("fileType", fileType);
		params.put("fileDesc", fileDesc);
		params.put("timestamp", timestamp);
		System.out.println("modifyFile---------" + params);
		if(signature.equals(Tools.signature(params))){
			String level = classdao.getClassLevel(Integer.parseInt(classId), Integer.parseInt(id)).toLowerCase();
			if(level.equals("teacher")||level.equals("master")){
				Files file = filedao.selectFile(Integer.parseInt(fileId));
				if(file == null){
					return Response.status(200).entity(Tools.setCode(400, null)).build();
				}else{
					file.setFileType(fileType);
					file.setDescription(fileDesc);
					filedao.modifyFile(file);
					return Response.status(200).entity(Tools.setCode(0, null)).build();
				}
			}else{
				return Response.status(200).entity(Tools.setCode(404, null)).build();
			}
		}else{
			return Response.status(200).entity(Tools.setCode(100, null)).build();
		}
	}
	
	@POST
	@Path("deleteFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFile(@FormParam("id") String id,
			@FormParam("fileId") String fileId,
			@FormParam("classId") String classId,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		JSONObject params = new JSONObject();
		params.put("id", id);
		params.put("fileId",fileId);
		params.put("classId", classId);
		params.put("timestamp",timestamp);
		System.out.println("deleteFile---------------" + params);
		if(signature.equals(Tools.signature(params))){
			if(userdao.selectUser(Integer.parseInt(id)) == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				String level = classdao.getClassLevel(Integer.parseInt(classId), Integer.parseInt(id)).toLowerCase();
				if(level.equals("teacher")||level.equals("master")){
					Files file = filedao.selectFile(Integer.parseInt(fileId));
					if(file==null){
						return Response.status(200).entity(Tools.setCode(400, null)).build();
					}
					else{
						filedao.deleteFile(file);
						return Response.status(200).entity(Tools.setCode(0, null)).build();
					}
				}
				return Response.status(200).entity(Tools.setCode(404, null)).build();
			}
		}else{
			return Response.status(200).entity(Tools.setCode(100, null)).build();
		}
	}
	
	@POST
	@Path("supportTime")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supportTime(@FormParam("id") String id,
			@FormParam("fileId") String fileId,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		JSONObject params = new JSONObject();
		params.put("id", id);
		params.put("fileId", fileId);
		params.put("timestamp", timestamp);
		System.out.println("supportTime---------" + params);
		if(signature.equals(Tools.signature(params))){
			if(userdao.selectUser(Integer.parseInt(id)) == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				Files file = filedao.selectFile(Integer.parseInt(fileId));
				if(file == null){
					return Response.status(200).entity(Tools.setCode(400, null)).build();
				}
				else{
					filedao.supportFile(Integer.parseInt(fileId));
					return Response.status(200).entity(Tools.setCode(0, null)).build();
				}
			}
		}
		else{
			return Response.status(200).entity(Tools.setCode(100, null)).build();
		}
	}
	
	@POST
	@Path("collectFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response collectFile(@FormParam("id") String id,
			@FormParam("fileId") String fileId,
			@FormParam("classId") String classId,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		JSONObject params = new JSONObject();
		params.put("id", id);
		params.put("fileId",fileId);
		params.put("classId", classId);
		params.put("timestamp",timestamp);
		System.out.println("collectFile---------------" + params);
		if(signature.equals(Tools.signature(params))){
			if(userdao.selectUser(Integer.parseInt(id)) == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				String level = classdao.getClassLevel(Integer.parseInt(classId), Integer.parseInt(id)).toLowerCase();
				if(level.equals("teacher")||level.equals("master")){
					Files file = filedao.selectFile(Integer.parseInt(fileId));
					if(file==null){
						return Response.status(200).entity(Tools.setCode(400, null)).build();
					}
					else{
						filedao.collectFile(Integer.parseInt(fileId), Integer.parseInt(id));
						return Response.status(200).entity(Tools.setCode(0, null)).build();
					}
				}
				return Response.status(200).entity(Tools.setCode(404, null)).build();
			}
		}else{
			return Response.status(200).entity(Tools.setCode(100, null)).build();
		}
	}
	
	@POST
	@Path("reuseFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reuseFile(@FormParam("id") String id,
			@FormParam("className") String className,
			@FormParam("classIdBefore") String classIdBefore,
			@FormParam("classIdAfter") String classIdAfter,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		JSONObject params = new JSONObject();
		params.put("id", id);
		params.put("className",className);
		params.put("classIdBefore", classIdBefore);
		params.put("classIdAfter", classIdAfter);
		params.put("timestamp",timestamp);
		System.out.println("reuseFile---------------" + params);
		if(signature.equals(Tools.signature(params))){
			if(classIdBefore.equals(classIdAfter))
				return Response.status(200).entity(Tools.setCode(306, null)).build();
			if(userdao.selectUser(Integer.parseInt(id)) == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				String levelbefore = classdao.getClassLevel(Integer.parseInt(classIdBefore), Integer.parseInt(id)).toLowerCase();
				String levelafter = classdao.getClassLevel(Integer.parseInt(classIdAfter), Integer.parseInt(id)).toLowerCase();
				if((levelbefore.equals("teacher")||levelbefore.equals("master"))&&
						(levelafter.equals("teacher")||levelafter.equals("master"))){
					filedao.reuseFile(Integer.parseInt(classIdBefore),Integer.parseInt(classIdAfter),className);
					return Response.status(200).entity(Tools.setCode(0, null)).build();
				}
				return Response.status(200).entity(Tools.setCode(404, null)).build();
			}
		}else{
			return Response.status(200).entity(Tools.setCode(100, null)).build();
		}
	}
	
	@POST
	@Path("recommendFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recommendFile(@FormParam("id") String id,
			@FormParam("fileId") String fileId,
			@FormParam("classId") String classId,
			@FormParam("timestamp") String timestamp,
			@FormParam("signature") String signature){
		JSONObject params = new JSONObject();
		params.put("id", id);
		params.put("fileId",fileId);
		params.put("classId", classId);
		params.put("timestamp",timestamp);
		System.out.println("recommendFile---------------" + params);
		if(signature.equals(Tools.signature(params))){
			if(userdao.selectUser(Integer.parseInt(id)) == null){
				return Response.status(200).entity(Tools.setCode(200, null)).build();
			}
			else{
				if(classdao.selectClazz(Integer.parseInt(classId)) == null){
					return Response.status(200).entity(Tools.setCode(301, null)).build();
				}
				else{
					Files file = filedao.selectFile(Integer.parseInt(fileId));
					if(file == null){
						return Response.status(200).entity(Tools.setCode(400, null)).build();
					}
					else{
						File f = new File(file.getFilePath());
						if(!f.exists()){
							return Response.status(200).entity(Tools.setCode(402, null)).build();
						}
						else{
							String level = classdao.getClassLevel(Integer.parseInt(classId), Integer.parseInt(id)).toLowerCase();
							if(level.equals("teacher")||level.equals("master")){
								Message message = new Message();
								message.setFileId(Integer.parseInt(fileId));
								message.setClassId(Integer.parseInt(classId));
								message.setUserId(Integer.parseInt(id));
								messagedao.addMessage(message);

								List<String> students = new ArrayList<String>();
								
								Map<String,String> studentmap = new HashMap<String, String>();
								title = file.getClassName() + " 课程有新的教师推荐资料";
								text = file.getFileName() + " " + file.getFileType() + " " + file.getDescription();
								studentmap = classstudentdao.getAllStudent(Integer.parseInt(classId));
								Iterator iterator = studentmap.keySet().iterator();
								while (iterator.hasNext()) {
									String key = (String) iterator.next();
									students.add(key);
								}
								for(String s : students){
									String tempcid = userdao.selectUser(Integer.valueOf(s)).getCid();
									if(tempcid == null)
										continue;
									cid.add(tempcid);
								}
								
								if(pushClient.push(cid, title, text))
									return Response.status(200).entity(Tools.setCode(0, null)).build();
								else
									return Response.status(200).entity(Tools.setCode(501, null)).build();
							}
							else{
								return Response.status(200).entity(Tools.setCode(404, null)).build();
							}
						}
					}
				}
			}
		}else{
			return Response.status(200).entity(Tools.setCode(100, null)).build();
		}
	}
	



}
