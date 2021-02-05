package demo.com.myemployees.screens.main_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import demo.com.myemployees.R;
import demo.com.myemployees.adapters.EmployeeAdapter;
import demo.com.myemployees.pojo.Employee;
import demo.com.myemployees.screens.detailed_screen.EmployeeDetailsActivity;

public class EmployeesListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewEmployees;
    private EmployeeAdapter adapter;
    private EmployeeViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewEmployees=findViewById(R.id.recyclerViewEmployees);
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        adapter=new EmployeeAdapter();
        recyclerViewEmployees.setAdapter(adapter);
        viewModel= ViewModelProviders.of(this).get(EmployeeViewModel.class);
        viewModel.getEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                adapter.setEmployees(employees);
            }
        });
        viewModel.loadData();
        adapter.setOnCardClickListener(new EmployeeAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                Intent intent=new Intent(getApplicationContext(), EmployeeDetailsActivity.class);
                int id =adapter.getEmployees().get(position).getId();
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}