package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Maryam on 10/19/2017.
 */

public class CheckedStudents {
    @SerializedName("state")
    Response_State state;
    @SerializedName("checkedStds")
    ArrayList<Integer> checkedStds;

    public CheckedStudents(Response_State state, ArrayList<Integer> checkedStds) {
        this.state = state;
        this.checkedStds = checkedStds;
    }

    public Response_State getState() {
        return state;
    }

    public void setState(Response_State state) {
        this.state = state;
    }

    public ArrayList<Integer> getCheckedStds() {
        return checkedStds;
    }

    public void setCheckedStds(ArrayList<Integer> checkedStds) {
        this.checkedStds = checkedStds;
    }
}
