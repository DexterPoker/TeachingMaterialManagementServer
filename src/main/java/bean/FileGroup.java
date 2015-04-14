/**
 * 
 */
package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月29日 
 */
public class FileGroup {
	private List<Files> files = new ArrayList<Files>();

	public List<Files> getFiles() {
		return files;
	}

	public void setFiles(List<Files> files) {
		this.files = files;
	}
	
}
