package demo.com.myemployees.screens.main_screen;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import demo.com.myemployees.api.ApiFactory;
import demo.com.myemployees.api.ApiService;
import demo.com.myemployees.data.EmployeeDatabase;
import demo.com.myemployees.pojo.Employee;
import demo.com.myemployees.pojo.EmployeeResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeViewModel extends AndroidViewModel {
    private static EmployeeDatabase db;
    private LiveData<List<Employee>> employees;
    private CompositeDisposable compositeDisposable;
    public void loadData(){
        ApiFactory apiFactory=ApiFactory.getInstance();
        ApiService apiService=apiFactory.getApiService();
        compositeDisposable= new CompositeDisposable();
        Disposable disposable=apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        deleteAllEmployees();
                        insertEmployees(employeeResponse.getResponse());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        compositeDisposable.add(disposable);
    }
//
//    @Override
//    protected void onCleared() {
//        compositeDisposable.dispose();
//        super.onCleared();
//    }

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        db=EmployeeDatabase.getInstance(application.getApplicationContext());
        employees=db.employeeDao().getAllEmployees();
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    public Employee getEmployeeByID(int id){
        try {
            return new GetEmployeeByIdTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class GetEmployeeByIdTask extends AsyncTask<Integer,Void,Employee>{

        @Override
        protected Employee doInBackground(Integer... integers) {
            if (integers!=null && integers.length>0){
                Employee employee=db.employeeDao().getEmployeeByID(integers[0]);
                return employee;
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void insertEmployees(List<Employee> employees){
        new InsertEmployeesTask().execute(employees);
    }
    private static class InsertEmployeesTask extends AsyncTask<List<Employee>,Void,Void>{

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Employee>... lists) {
            if (lists!=null && lists.length>0){
                db.employeeDao().insertEmployees(lists[0]);
            }
            return null;
        }
    }

    public void deleteAllEmployees(){
        new DeleteEmployeesTask().execute();
    }

    private static class DeleteEmployeesTask extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {
            db.employeeDao().deleteAllEmployees();
            return null;
        }
    }

}
