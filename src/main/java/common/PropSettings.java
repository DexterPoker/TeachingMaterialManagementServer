/**
 * 
 */
package common;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月17日 
 */
public class PropSettings {
	public static Properties prop = getProperties();

	public static Properties getProperties() {
		Properties prop = new Properties();
		try {
			FileInputStream file = new FileInputStream("D:/eclipse-jee/workspace/teaching-material-management/teaching-material-management/prop.properties");
			prop.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
}