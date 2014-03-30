package entities;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by MohammedSubhi on 3/30/14.
 */
public class EmployeeDepartment {


    @DatabaseField(foreign = true, foreignAutoCreate = true)
    public Department d;


    @DatabaseField(foreign = true, foreignAutoCreate = true)
    public Employee e;
    @DatabaseField
    public int numOfHours;

    public EmployeeDepartment() {
    }

}
