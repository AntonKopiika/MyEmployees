package demo.com.myemployees.api;



import demo.com.myemployees.pojo.EmployeeResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("testTask.json")
    Observable<EmployeeResponse> getEmployees();
}
