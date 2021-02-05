package demo.com.myemployees.screens.detailed_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import demo.com.myemployees.R;
import demo.com.myemployees.pojo.Employee;
import demo.com.myemployees.screens.main_screen.EmployeeViewModel;

public class EmployeeDetailsActivity extends AppCompatActivity {
    private TextView textViewName;
    private TextView textViewBirthday;
    private TextView textViewSpecialty;
    private EmployeeViewModel viewModel;
    private CircularImageView imageViewAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        textViewName=findViewById(R.id.textViewFullName);
        textViewBirthday=findViewById(R.id.textViewBirthday);
        textViewSpecialty=findViewById(R.id.textViewSpecialty);
        imageViewAvatar=findViewById(R.id.imageViewAvatar);
        viewModel= ViewModelProviders.of(this).get(EmployeeViewModel.class);
        Intent intent=getIntent();
        if (intent!=null && intent.hasExtra("id")){
            int employeeId=intent.getIntExtra("id",-1);
            Employee employee=viewModel.getEmployeeByID(employeeId);
            String fullName=String.format("%s %s",employee.getFName(),employee.getLName());
            String birthday=employee.getBirthday();
            String avatrUrl=employee.getAvatrUrl();
            textViewName.setText(fullName);

            if(birthday!=null && !birthday.equals("")){
                textViewBirthday.setText(birthday);
            }
            else {
                textViewBirthday.setText("Информация отсутствует");
            }

            if (avatrUrl!= null && !avatrUrl.equals("")){
                Picasso.get().load(employee.getAvatrUrl()).into(imageViewAvatar);
            }
            else {
                Picasso.get().load(R.drawable.placeholder).into(imageViewAvatar);
            }
            textViewSpecialty.setText(employee.getSpecialty().get(0).getName());
        }

    }
}