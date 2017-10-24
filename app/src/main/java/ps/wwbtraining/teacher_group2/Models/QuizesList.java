package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Eman on 10/22/2017.
 */

public class QuizesList {
    @SerializedName("state")
    Response_State state;
    @SerializedName("quizesList")
    ArrayList<Quiz> quizesList;

    public Response_State getState() {
        return state;
    }

    public void setState(Response_State state) {
        this.state = state;
    }

    public ArrayList<Quiz> getQuezesList() {
        return quizesList;
    }

    public void setQuezesList(ArrayList<Quiz> quezesList) {
        this.quizesList = quezesList;
    }
}
