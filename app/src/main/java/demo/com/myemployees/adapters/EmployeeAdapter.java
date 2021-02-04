package demo.com.myemployees.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import demo.com.myemployees.R;
import demo.com.myemployees.pojo.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employees=new ArrayList<>();

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item,parent,false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.textViewFName.setText(employees.get(position).getFName());
        holder.textViewLName.setText(employees.get(position).getLName());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewFName;
        private TextView textViewLName;
        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFName=itemView.findViewById(R.id.textViewFName);
            textViewLName=itemView.findViewById(R.id.textViewLName);
        }
    }
}
