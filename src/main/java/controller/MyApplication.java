/**
 * 
 */
package controller;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月27日 
 */
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register resources and features
        classes.add(MultiPartFeature.class);
//        classes.add(MultiPartResource.class);
//        classes.add(UploadFileService.class);
        classes.add(LoggingFilter.class);
        classes.add(FileController.class);
        return classes;
    }
}
