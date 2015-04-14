/**
 * 
 */
package common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月13日
 */
public class Tools {

	public static String signature(Map params) {
		String original = Constant.appkey;
		List list = new ArrayList<String>();
		Iterator iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if(key.equals("signature"))
				continue;
			list.add(key);
		}
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			original = original + params.get(list.get(i));
		}
		original = original + Constant.appkey;
//		original = changeCharset(original, "UTF-8");
		return md5(original);
	}

	public static String md5(String original) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = original.getBytes("UTF-8");
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String setCode(int errorCode, JSONObject result) {
		if (result != null) {
			result.put("errorCode", errorCode);
			System.out.println("setcode1-------------" + result);
			return result.toString();
		} 
		else {
			JSONObject temp = new JSONObject();
			temp.put("errorCode", errorCode);
//			String code = "errorCode" + errorCode;
//			temp.put("msg", ErrorCode.code);
			System.out.println("setcode2---------------" + temp);
			return temp.toString();
		}
	}

	public static String getTimestamp(){
		SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd-HH-mm-ss" );
		String time = format.format(new Date());
		return time;
	}
	
	public static String changeCharset(String str, String string) {
		// TODO Auto-generated method stub
		 if (str != null) {
			   byte[] bs = str.getBytes();
			   try {
				return new String(bs, string);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  }
			  return null;
	}
	
	public static boolean writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static String getStudentsId(String filepath){
		String studentId = null;
		Excels excel = new Excels(filepath);
		int rowNum = 0;
		try {
			rowNum = excel.getRowNum(Constant.sheetname);
			if(rowNum != 0){
				studentId = "";
				JSONObject row = new JSONObject();
				for(int i = 1 ;i < rowNum ; i ++){
					row = excel.getParams(i, Constant.sheetname);
					if(row.getString(Constant.colname) == null)
						continue;
					studentId = studentId + row.getString(Constant.colname) + ",";
				}
				row = excel.getParams(rowNum, Constant.sheetname);
				studentId = studentId + row.getString(Constant.colname);
				return studentId;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sheetname不正确...");
			e.printStackTrace();
		}
		return studentId;
	}
	
	public static void main(String[] args) {
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("b", "n");
		// map.put("ac", "mi");
		// map.put("ab", "ad");
		// System.out.println(signature(map));
//		System.out.println(JSON.toJSONString(null));
//		 System.out.println(setCode(10, null));
//		JSONObject result = new JSONObject();
//		result.put("1", "1");
//		 System.out.println(md5("123456"));
//
//		Users user = new Users();
//		user.setPassword(md5("123"));
//		user.setUserLevel("Student");
//		user.setUserName("42");
//		new UserDao().AddUser(user);
//		Client client = Client.create();
////		Constant.setIp("122.235.97.92");
//		WebResource webResource = client.resource("http://122.235.97.92:8080/teaching-material-management/TMMapi/login/check");
//		JSONObject params = new JSONObject();
//		params.put("id", "1");
//		params.put("password", Tools.md5("admin"));
//		params.put("timestamp", Tools.getTimestamp());
//		params.put("signature", Tools.signature(params));
//		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, params.toString());
//		System.out.println(response);
//		System.out.println("Response for delete request: " + response.getStatus());
//		System.out.println(Tools.md5("7SB8OGSStudent试试6512BD43D9CAA6E02C990B0A82652DCA2015-03-26 11:37:31.07SB8OGS"));
//		setCode(205, null);
		System.out.println(md5("111"));
	}

}
