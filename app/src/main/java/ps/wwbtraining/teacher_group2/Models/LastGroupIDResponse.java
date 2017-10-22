package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eman on 10/20/2017.
 */

public class LastGroupIDResponse {
    @SerializedName("gid")
    private int gid;
    @SerializedName("state")
    private Response_State state;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public Response_State getState() {
        return state;
    }

    public void setState(Response_State state) {
        this.state = state;
    }
}
