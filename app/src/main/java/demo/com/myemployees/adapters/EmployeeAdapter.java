package demo.com.myemployees.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import demo.com.myemployees.R;
import demo.com.myemployees.pojo.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employees=new ArrayList<>();
    private OnCardClickListener onCardClickListener;
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }

    public interface OnCardClickListener{
        void onCardClick(int position);
    }
    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item,parent,false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee=employees.get(position);
        holder.textViewFName.setText(employee.getFName());
        holder.textViewLName.setText(employee.getLName());
        if (employee.getAvatrUrl()!= null && !employee.getAvatrUrl().equals("")){
            Picasso.get().load(employee.getAvatrUrl()).into(holder.imageViewEmployee);
        }
        else {
            Picasso.get().load(R.drawable.placeholder).into(holder.imageViewEmployee);
        }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewFName;
        private TextView textViewLName;
        private ImageView imageViewEmployee;
        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFName=itemView.findViewById(R.id.textViewFName);
            textViewLName=itemView.findViewById(R.id.textViewLName);
            imageViewEmployee=itemView.findViewById(R.id.imageViewEmployee);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onCardClickListener!=null){
                        onCardClickListener.onCardClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
