package trungndph39729.fpoly.lab6;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import trungndph39729.fpoly.lab6.Service.HttpRequest;
import trungndph39729.fpoly.lab6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements Handle_student {
    private ActivityMainBinding binding;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        httpRequest = new HttpRequest();
        httpRequest.callAPI()
                .getListStudent()
                .enqueue(getListStudentResponse);
    }

    retrofit2.Callback<Response<ArrayList<Student>>> getListStudentResponse = new Callback<Response<ArrayList<Student>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Student>>> call, retrofit2.Response<Response<ArrayList<Student>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Student> ds = response.body().getData();

                    getData(ds);

                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Student>>> call, Throwable t) {
            Log.d(">>>> getListStudent", "onFailure: " + t.getMessage());
        }
    };
    Callback<Response<Student>>  responseStudentAPI =  new Callback<Response<Student>>() {
        @Override
        public void onResponse(Call<Response<Student>> call, retrofit2.Response<Response<Student>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    httpRequest.callAPI()
                            .getListStudent()
                            .enqueue(getListStudentResponse);
                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Student>> call, Throwable t) {
            Log.d("ZZZZZZZZZZ", "onFailure: "+t.getMessage());

        }
    };

    private void getData(ArrayList<Student> ds) {
        binding.recycleViewStudent.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recycleViewStudent.setAdapter(new StudentAdapter(ds, this, this));
    }


    @Override
    public void onDeleteStudent(String id) {
        httpRequest.callAPI()
                .deleteStudentById(id)
                .enqueue(responseStudentAPI);
    }

    @Override
    public void onUpdateStudent(String id,Student student) {
        httpRequest.callAPI()
                .updateStudentById(id,student)
                .enqueue(responseStudentAPI);
    }
}