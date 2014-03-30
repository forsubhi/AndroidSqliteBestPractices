package entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MohammedSubhi on 3/30/14.
 */
public class Employee {

    public Employee() {
    }



    public Employee(String name, Date birthDay, String desc) {
        this.name = name;
        this.birthDay = birthDay;
        Desc = desc;
    }

    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    public String name;

    @DatabaseField
    public Date birthDay;

    @DatabaseField
    public String Desc;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public ArrayList<String> childrenNames;

    @DatabaseField(foreign = true,canBeNull = true)
    public  Manager managedBy;




}
