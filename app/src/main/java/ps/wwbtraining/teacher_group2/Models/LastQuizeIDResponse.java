package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eman on 10/24/2017.
 */

public class LastQuizeIDResponse {
    @SerializedName("qid")
    private int qid;
    @SerializedName("state")
    private Response_State state;

    public int getGid() {
        return qid;
    }

    public void setGid(int gid) {
        this.qid = gid;
    }

    public Response_State getState() {
        return state;
    }

    public void setState(Response_State state) {
        this.state = state;
    }
}
