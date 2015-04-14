/**
 * 
 */
package common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zql
 *
 * @version 创建时间：2015年4月10日 
 */
public class pushClient {
	
	public static boolean push(List<String> cid,String title, String text){
		JSONObject message = new JSONObject();
		String result = "false";
		Socket client = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		message.put("cid", cid);
		message.put("title", title);
		message.put("text", text);
//		try{
//			mysocket = new Socket("127.0.0.1",5888);
//			in = new DataInputStream(mysocket.getInputStream());
//			out = new DataOutputStream(mysocket.getOutputStream());
//			out.writeBytes(message.toString());
//			result = in.readLine();
//
//			in.close();
//			out.close();
//			mysocket.close();
//		}
//		catch(Exception e){
//			System.out.println(result);
//			System.out.println(e.getStackTrace());
//		}
//		finally{
//			return result.equals("true")? true:false;
//		}
		
		try {
			client = new Socket("127.0.0.1",5888);		 
			Writer writer = new OutputStreamWriter(client.getOutputStream(), "GBK");  
		      writer.write(message.toString());  
		      writer.write("eof\n");  
		      writer.flush();  
		      //写完以后进行读操作  
		     BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));  
		      //设置超时间为10秒  
		     client.setSoTimeout(10*1000);  
		      StringBuffer sb = new StringBuffer();  
		      String temp;  
		      int index;  
		      try {  
		         while ((temp=br.readLine()) != null) {  
		            if ((index = temp.indexOf("eof")) != -1) {  
		                sb.append(temp.substring(0, index));  
		                break;  
		            }  
		            sb.append(temp);
		         }  
		      } catch (SocketTimeoutException e) {  
		         System.out.println("数据读取超时。");  
		      }  
		      System.out.println("服务端: " + sb);  
		      result = sb.toString();
		      writer.close();  
		      br.close();  
		      client.close();  
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			return result.equals("true")? true:false;
		}
		
	}
}
