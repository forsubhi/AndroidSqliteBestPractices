package entities;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;

/**
 * Created by MohammedSubhi on 3/30/14.
 */

// this class make the performance better for ormlite
public class DatabaseConfigUtil extends OrmLiteConfigUtil {


    private static final Class<?>[] classes = new Class[] {
            Department.class,Employee.class,EmployeeDepartment.class,Manager.class
    };

    public static void main(String[] args) throws Exception {
        writeConfigFile(new File("D:\\New folder\\temp\\res\\raw\\ormlite_config.txt"),classes);
    }
}
