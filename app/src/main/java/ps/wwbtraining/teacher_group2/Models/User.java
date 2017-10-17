package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eman on 10/15/2017.
 */

public class User {

    @SerializedName("uid")
    private int uid;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("user_email")
    private String user_email;
    @SerializedName("user_mobile")
    private  String user_mobile;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;
    @SerializedName("user_state")
    private int user_state;

    public User(int uid, String user_name, String user_email, String user_mobile, String password, String token, int user_state) {
        this.uid = uid;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_mobile = user_mobile;
        this.password = password;
        this.token = token;
        this.user_state = user_state;
    }

    public User(String user_name, String user_email, String user_mobile, String password, int state) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_mobile = user_mobile;
        this.password = password;
        this.user_state = state;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getState() {
        return user_state;
    }

    public void setState(int state) {
        this.user_state = state;
    }
}
