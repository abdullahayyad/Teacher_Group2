package ps.wwbtraining.teacher_group2.WebService;

import ps.wwbtraining.teacher_group2.Models.GroupList;
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
                                 @Field("user_state") int user_state);

    @FormUrlEncoded
    @POST("addGroup.php")
    Call<Response_State> addGroup(@Field("group_name") String group_name,
                                  @Field("group_desc") String group_desc);

    @FormUrlEncoded
    @POST("checkUser.php")
    Call<LoginResponse> checkUser(@Field("user_name") String user_name,
                                  @Field("user_password") String user_password);

    @FormUrlEncoded
    @POST("addStdGroup.php")
    Call<Response_State> addStdGroup(@Field("gid") String gid,
                                     @Field("uid") String uid);

    @GET("getUsers.php")
    Call<Students> getStudents();


    @GET("getGroups.php")
    Call<GroupList> getGroups();

}
