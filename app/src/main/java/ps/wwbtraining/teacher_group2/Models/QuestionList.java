package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Eman on 10/22/2017.
 */

public class QuestionList {
    @SerializedName("state")
    Response_State state;
    @SerializedName("questionslist")
    ArrayList<Question> questionslist;

    public ArrayList<Question> getQuestionslist() {
        return questionslist;
    }

    public void setQuestionslist(ArrayList<Question> questionslist) {
        this.questionslist = questionslist;
    }

    public Response_State getState() {
        return state;
    }

    public void setState(Response_State state) {
        this.state = state;
    }
}
