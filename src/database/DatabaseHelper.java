package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.test.MyActivity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import entities.Department;
import entities.Employee;
import entities.EmployeeDepartment;
import entities.Manager;

import java.sql.SQLException;
import java.util.*;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "sqlite-best-practices.db";

    private static final int DATABASE_VERSION = 8;

    private RuntimeExceptionDao DepartmentDao = null;

    private Dao  EmployeeDepartment ;
    private RuntimeExceptionDao  Employee  ;

    public TransactionManager transactionManager ;
    private RuntimeExceptionDao  ManagerDao;

    public DatabaseHelper(Context context) {
        // R.raw.ormlite_config should make db 20 times faster !
        super(context, "/mnt/sdcard/" +  DATABASE_NAME, null, DATABASE_VERSION);

        MyActivity.writeToStatus("/sdcard/" + DATABASE_NAME);
        ConnectionSource connectionSource = getConnectionSource();
        transactionManager = new TransactionManager(connectionSource);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {

            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Department.class);
            TableUtils.createTable(connectionSource, Employee.class);
            TableUtils.createTable(connectionSource, EmployeeDepartment.class);
            TableUtils.createTable(connectionSource, Manager.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Department.class, true);
            TableUtils.dropTable(connectionSource, Employee.class, true);
            TableUtils.dropTable(connectionSource, EmployeeDepartment.class, true);
            TableUtils.dropTable(connectionSource, Manager.class, true);
            deleteAllTablesFromDb(db);
            onCreate(db, connectionSource);
        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public RuntimeExceptionDao getDepartmentDao() throws SQLException {
        if (DepartmentDao == null) {
            DepartmentDao = getRuntimeExceptionDao(Department.class);

        }
        return DepartmentDao;
    }


    public Dao getEmployeeDepartmentDao() throws SQLException {
        if (EmployeeDepartment == null) {
            EmployeeDepartment = getDao(EmployeeDepartment.class);

        }
        return EmployeeDepartment;
    }


    public RuntimeExceptionDao getEmployeeDao() throws SQLException {
        if (Employee == null) {
            Employee = getRuntimeExceptionDao(Employee.class);

        }
        return Employee;
    }


    public RuntimeExceptionDao getManagerDao() throws SQLException {
        if (ManagerDao == null) {
            ManagerDao = getRuntimeExceptionDao(Manager.class);

        }
        return ManagerDao;
    }


    void deleteAllTablesFromDb(SQLiteDatabase db) {
        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
        }
        int i = 0;
        for (String tableName : arrTblNames) {
            // skip the first Table (meta data)
            // skip the second Table (sqlite_sequence)
            ++i;
            if (i == 1 || i == 2) {
                continue;
            }
            String sql = "drop Table " + tableName;
            try {
                db.execSQL(sql);
            } catch (android.database.SQLException e) {

                e.printStackTrace();
            }
        }
    }
}
