package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maryam on 10/17/2017.
 */

public class GroupResponse {
    @SerializedName("group")
    Group group;

    @SerializedName("state")
    Response_State state;

    public GroupResponse(Group group, Response_State state) {
        this.group = group;
        this.state = state;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Response_State getState() {
        return state;
    }

    public void setState(Response_State state) {
        this.state = state;
    }
}
