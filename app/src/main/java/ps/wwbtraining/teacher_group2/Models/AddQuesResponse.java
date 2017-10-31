package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Eman on 10/30/2017.
 */

public class AddQuesResponse {
    @SerializedName("state")
    Response_State state;
    @SerializedName("questionslist")
    ArrayList<Question> questionslist;
    @SerializedName("stringResult")
    String stringResult;

    public String getStringResult() {
        return stringResult;
    }

    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
    }

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

