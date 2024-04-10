package trungndph39729.fpoly.lab6.Service;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import trungndph39729.fpoly.lab6.Response;
import trungndph39729.fpoly.lab6.Student;

public interface ApiServices {
    public static String BASE_URL = "http://10.0.2.2:3000/api/";

    @GET("get-list-student")
    Call<Response<ArrayList<Student>>> getListStudent();

    @POST("add-Student")
    Call<Response<Student>> addStudent(@Body Student Student);

    @DELETE("delete-student-by-id/{id}")
    Call<Response<Student>> deleteStudentById(@Path("id") String id);

    @PUT("update-student-by-id/{id}")
    Call<Response<Student>> updateStudentById(@Path("id") String id, @Body Student Student);


}
