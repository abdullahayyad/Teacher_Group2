package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.Models.User;

/**
 * Created by Maryam on 10/17/2017.
 */

public class LoginResponse {
    @SerializedName("user")
    User user;

    @SerializedName("state")
    Response_State state;

    public LoginResponse(User user, Response_State state) {
        this.user = user;
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Response_State getState() {
        return state;
    }

    public void setState(Response_State state) {
        this.state = state;
    }
}
