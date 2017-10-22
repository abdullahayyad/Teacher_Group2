package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eman on 10/19/2017.
 */

public class Quiz {
    @SerializedName("qid")
    private int qid;
    @SerializedName("quiz_name")
    private String quiz_name;
    @SerializedName("quiz_desc")
    private String quiz_desc;
    @SerializedName("quiz_date")
    private String quiz_date;
    @SerializedName("notified")
    private String notified;

    public Quiz(String name, String desc, String quiz_date,String notified) {
        this.quiz_name = name;
        this.quiz_desc = desc;
        this.quiz_date=quiz_date;
        this.notified=notified;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int id) {
        this.qid = id;
    }

    public String getQuizName() {
        return quiz_name;
    }

    public void setQuizName(String name) {
        this.quiz_name = name;
    }

    public String getQuizDesc() {
        return quiz_desc;
    }

    public void setQuizDesc(String desc) {
        this.quiz_desc = desc;
    }

    public String getQuiz_date() {
        return quiz_date;
    }

    public void setQuiz_date(String quiz_date) {
        this.quiz_date = quiz_date;
    }

    public String getNotified() {
        return notified;
    }

    public void setNotified(String notified) {
        this.notified = notified;
    }
}
