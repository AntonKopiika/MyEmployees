package demo.com.myemployees.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import demo.com.myemployees.pojo.Employee;

@Database(entities = {Employee.class}, version = 2, exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {
    private static EmployeeDatabase employeeDatabase;
    private static final String DB_NAME = "employees.db";
    private static final Object LOCK = new Object();

    public static EmployeeDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (employeeDatabase == null) {
                employeeDatabase = Room.databaseBuilder(context, EmployeeDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }

            return employeeDatabase;
        }
    }

    public abstract EmployeeDao employeeDao();

}
