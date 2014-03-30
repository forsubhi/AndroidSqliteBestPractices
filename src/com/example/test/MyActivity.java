package com.example.test;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import database.DatabaseHelper;
import entities.Department;
import entities.Employee;
import entities.EmployeeDepartment;
import entities.Manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    RuntimeExceptionDao departmentDao;
    private RuntimeExceptionDao employeeDao;
    private RuntimeExceptionDao managerDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();


        try {
            departmentDao = databaseHelper.getDepartmentDao();
            Dao employeeDepartmentDao = databaseHelper.getEmployeeDepartmentDao();
            employeeDao = databaseHelper.getEmployeeDao();
            managerDao = databaseHelper.getManagerDao();


            departmentDao.deleteBuilder().delete();


            Department programmersDepartment = new Department("Programmers", "We are the programmers");
            departmentDao.create(programmersDepartment);


            Department testersDepartment = new Department("Testes", "We are the testers");
            long t = System.currentTimeMillis();
            departmentDao.create(testersDepartment);

            t = System.currentTimeMillis() - t;

            writeToStatus("ormlite time of insert department =" + t);
            //  db.execSQL("insert into Department values ( 100 , 'hackers','We are hackers')");

            ContentValues cv = new ContentValues();
            cv.put("name", "Programmers");
            cv.put("Desc", "We are the programmers");

            t = System.currentTimeMillis();
            db.insertOrThrow("Department", null, cv);
            t = System.currentTimeMillis() - t;
            writeToStatus("normal time of insert department =" + t);

            //    printDepartments();

            departmentDao.deleteBuilder().delete();
            employeeDao.deleteBuilder().delete();
            // part2 of lesson

            EmployeeDepartment ED = new EmployeeDepartment();
            Department d = new Department("Programmers", "We are the programmers");
            Employee e = new Employee("Subhi", new Date(1990, 7, 26), "he is java developer");

            ED.d = d;
            ED.e = e;
            ED.numOfHours = 10;
            employeeDepartmentDao.create(ED);

         //   printDepartments();
         //   printEmployees();

            departmentDao.deleteBuilder().delete();
            employeeDao.deleteBuilder().delete();
            managerDao.deleteBuilder().delete();

            // part3 of lesson
            Manager m =  new Manager("Bahadir",new Department("ARI3","designers"));

            ArrayList<Employee> employees =    new ArrayList<Employee>();
            Employee Subhi = new Employee("Subhi", new Date(1990, 7, 26), "he is java developer");
            Subhi.managedBy=m;
            Employee Eyad = new Employee("Eyad", new Date(1992, 1, 1), "he is C# developer");
            Eyad.managedBy=m;
            employees.add(Subhi);
            employees.add(Eyad);
            m.employees =employees;

            managerDao.create(m);


            employeeDao.create(Subhi);
            employeeDao.create(Eyad);

            printManagers();
            printDepartments();
            printEmployees();


        } catch (SQLException e) {
            writeToStatus(Log.getStackTraceString(e));
        }

    }

    private void printDepartments() {
        List<Department> list = departmentDao.queryForAll();
        for (Department department : list) {
            writeToStatus("department = "+department.id + " " + department.name + " " + department.Desc);
        }
    }


    private void printEmployees() {
        List<Employee> list = employeeDao.queryForAll();
        for (Employee employee : list) {
            writeToStatus("employee= "+employee.id + " " + employee.name + " " + employee.Desc + " " + employee.birthDay);
        }
    }


    private void printManagers() {
        List<Manager> list = managerDao.queryForAll();
        for (Manager manager : list) {
            writeToStatus("manager = "+manager.id + " " + manager.name + " " + manager.manage.name );

            for (Employee employee : manager.employees) {
                writeToStatus("manager --> employee= "+employee.id + " " + employee.name + " " + employee.Desc + " " + employee.birthDay);
            }
        }
    }

    public static void writeToStatus(String s) {
        Log.d("test:status", s);
    }
}
