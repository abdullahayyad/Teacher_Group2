package ps.wwbtraining.teacher_group2.WebService;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import ps.wwbtraining.teacher_group2.Models.AddQuesResponse;
import ps.wwbtraining.teacher_group2.Models.CheckedStudents;
import ps.wwbtraining.teacher_group2.Models.Group;
import ps.wwbtraining.teacher_group2.Models.GroupList;
import ps.wwbtraining.teacher_group2.Models.GroupResponse;
import ps.wwbtraining.teacher_group2.Models.LastGroupIDResponse;
import ps.wwbtraining.teacher_group2.Models.LastQuesIDResponse;
import ps.wwbtraining.teacher_group2.Models.LastQuizeIDResponse;
import ps.wwbtraining.teacher_group2.Models.LoginResponse;
import ps.wwbtraining.teacher_group2.Models.QuestionList;
import ps.wwbtraining.teacher_group2.Models.QuizesList;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.Models.Students;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @FormUrlEncoded
    @POST("updateGroup.php")
    Call<Response_State> updateGroup(@Field("gid") int gid,
                                     @Field("group_name") String group_name,
                                     @Field("group_desc") String group_desc);



    @POST("updateGroupStds.php")
    Call<Response_State> updateGroupStds(@Body RequestBody json);


    @FormUrlEncoded
    @POST("updateStdState.php")
    Call<Response_State> updateStdState(@Field("uid") int uid,
                                        @Field("user_state") int user_state);


    @FormUrlEncoded
    @POST("setQuizNotified.php")
    Call<Response_State> setQuizNotified(@Field("qid")int qid, @Field("notified")String notified );


    @FormUrlEncoded
    @POST("addQuiz.php")
    Call<LastQuizeIDResponse> addQuiz(@Field("quiz_name") String quiz_name,
                                      @Field("quiz_desc") String quiz_desc,
                                       @Field("deadline") String deadline);

    @FormUrlEncoded
    @POST("deleteQuiz.php")
    Call<Response_State> deleteQuiz(@Field("qid") int qid);

    @POST("addQuizQuestions.php")
    Call<AddQuesResponse> addQuizQuestions(@Body RequestBody body);


    @FormUrlEncoded
    @POST("updateProfile.php")
    Call<Response_State> updateProfile(@Field("uid") int uid,
                                       @Field("user_name") String user_name,
                                       @Field("user_mobile") String user_mobile,
                                       @Field("user_email") String user_email);

    @FormUrlEncoded
    @POST("updatePassword.php")
    Call<Response_State> updatePassword(@Field("uid") int uid,
                                        @Field("user_password") String user_password);

    @FormUrlEncoded
    @POST("studentsFilter.php")
    Call<Students> studentsFilter(@Field("user_state") int user_state);

    @FormUrlEncoded
    @POST("addFcmToken.php")
    Call<Response_State> addFcmToken(@Field("uid")int uid,
            @Field("fcm_token")String token);

    @POST("updateQues.php")
    Call<Response_State> updateQuestion(@Body RequestBody body);

    @FormUrlEncoded
    @POST("deleteQues.php")
    Call<Response_State> deleteQuestion(@Field("qus_id") int ques_id);
}
