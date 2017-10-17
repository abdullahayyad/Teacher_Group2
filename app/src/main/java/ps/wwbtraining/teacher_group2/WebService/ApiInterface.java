package ps.wwbtraining.teacher_group2.WebService;

import ps.wwbtraining.teacher_group2.Models.LoginResponse;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.Models.Students;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("addUser.php")
    Call<Response_State> addUser(@Field("user_name") String user_name,
                                 @Field("user_password") String user_password,
                                 @Field("user_mobile") String user_mobile,
                                 @Field("user_email") String user_email,
                                 @Field("user_state") int user_state) ;

    @FormUrlEncoded
    @POST("checkUser.php")
    Call<LoginResponse> checkUser(@Field("user_name") String user_name,
                                  @Field("user_password") String user_password);

    @GET("getUsers.php")
    Call<Students> getStudents() ;






}
