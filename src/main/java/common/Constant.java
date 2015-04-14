/**
 * 
 */
package common;

import java.util.Properties;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月17日 
 */
public class Constant {
	public static String appkey;
	public static String defaultPath;
	public static String tmpPath;
	public static Properties prop = PropSettings.getProperties();
	static {
		appkey = prop.getProperty("Appkey");
		defaultPath = prop.getProperty("DefaultPath");
		tmpPath = prop.getProperty("TempPath");
	}
	public static String sheetname = "Sheet1";
	public static String colname = "id";
}
