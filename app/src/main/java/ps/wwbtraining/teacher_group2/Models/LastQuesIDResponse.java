package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eman on 10/28/2017.
 */

public class LastQuesIDResponse {
    @SerializedName("ques_id")
    private int ques_id;
    @SerializedName("state")
    private Response_State state;

    public int getQues_id() {
        return ques_id;
    }

    public void setQues_id(int ques_id) {
        this.ques_id = ques_id;
    }

    public Response_State getState() {
        return state;
    }

    public void setState(Response_State state) {
        this.state = state;
    }
}
