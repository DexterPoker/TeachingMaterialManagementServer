/**
 * 
 */
package common;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author zql
 *
 * @version ����ʱ�䣺2015��3��17�� 
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