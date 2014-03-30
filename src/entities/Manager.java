package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by MohammedSubhi on 3/30/14.
 */


public class Manager {

    @DatabaseField(generatedId= true)
    public  Integer id ;
    @DatabaseField
    public  String name ;
    @DatabaseField(foreign = true ,foreignAutoCreate = true,foreignAutoRefresh = true, columnDefinition = "integer references manage(id) on delete cascade")
    public  Department manage ;

    @ForeignCollectionField(eager = true)
    public Collection<Employee> employees;

    public Manager() {
    }

    public Manager(String name, Department manage) {
        this.name = name;
        this.manage = manage;
    }



}
