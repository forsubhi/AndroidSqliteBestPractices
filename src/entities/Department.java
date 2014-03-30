package entities;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by MohammedSubhi on 3/30/14.
 */
public class Department {
    @DatabaseField(generatedId =true )
    public Integer id;
    @DatabaseField
    public String name;
    @DatabaseField
    public String Desc;

    public Department() {
    }

    public Department(String name, String desc) {
        this.name = name;
        Desc = desc;
    }


}
