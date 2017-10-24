package ps.wwbtraining.teacher_group2.WebService;

import ps.wwbtraining.teacher_group2.Models.CheckedStudents;
import ps.wwbtraining.teacher_group2.Models.GroupList;
import ps.wwbtraining.teacher_group2.Models.GroupResponse;
import ps.wwbtraining.teacher_group2.Models.LastGroupIDResponse;
import ps.wwbtraining.teacher_group2.Models.LoginResponse;
import ps.wwbtraining.teacher_group2.Models.QuestionList;
import ps.wwbtraining.teacher_group2.Models.QuizesList;
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
    Call<LastGroupIDResponse> addGroup(@Field("group_name") String group_name,
                                       @Field("group_desc") String group_desc);

    @FormUrlEncoded
    @POST("checkUser.php")
    Call<LoginResponse> checkUser(@Field("user_name") String user_name,
                                  @Field("user_password") String user_password);

    @FormUrlEncoded
    @POST("getGroupId.php")
    Call<GroupResponse> getGroup(@Field("gid") int gid);

    @FormUrlEncoded
    @POST("getLastGroupId.php")
    Call<LastGroupIDResponse> getLastGroupId();


    @FormUrlEncoded
    @POST("addStdGroup.php")
    Call<Response_State> addStdGroup(@Field("gid") int gid,
                                     @Field("uid") int uid);


    @FormUrlEncoded
    @POST("getGroupStds.php")
    Call<CheckedStudents> getGroupStds(@Field("gid") int gid);

    @FormUrlEncoded
    @POST("getStudents.php")
    Call<Students> getStudents(@Field("flag") int flag);

    @FormUrlEncoded
    @POST("getQues.php")
    Call<QuestionList> getQuestionsList(@Field("qid") int qid);


    @POST("getQuizes.php")
    Call<QuizesList> getQuizesList();

    // @GET("getUsers.php")
    //Call<Students> getStudents();



    @GET("getGroups.php")
    Call<GroupList> getGroups();

}
