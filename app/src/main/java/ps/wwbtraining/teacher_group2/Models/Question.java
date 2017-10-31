package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Eman on 10/22/2017.
 */

public class Question implements Serializable{
    @SerializedName("ques_id")
    private int ques_id;
    @SerializedName("qid")
    private int qid;
    @SerializedName("ques_type")
    private String ques_type;
    @SerializedName("ques_statement")
    private String ques_statement;
    @SerializedName("correct_answer")
    private String correct_answer;
    @SerializedName("ans1")
    private String ans1;
    @SerializedName("ans2")
    private String ans2;
    @SerializedName("ans3")
    private String ans3;
    @SerializedName("ans4")
    private String ans4;

/*
    public Question(int ques_id,String type, String stmt,int qid, String correct_answer) {
        this.type=type;
        this.stmt = stmt;
        this.correct_answer = correct_answer;
        this.ques_id=ques_id;
    }
    */

    public Question(int ques_id,String type, String stmt,int qid, String correct_answer,String ans1,String ans2,String ans3,String ans4) {
        this.ques_type=type;
        this.ques_statement = stmt;
        this.correct_answer = correct_answer;
        this.ans1=ans1;
        this.ans2=ans2;
        this.ans3=ans3;
        this.ans4=ans4;
        this.ques_id=ques_id;
        this.qid=qid;
    }
/*
    public Question(int ques_id, int qid, String type, String stmt, String correct_answer, String ans1, String ans2, String ans3, String ans4) {
        this.ques_id = ques_id;
        this.qid = qid;
        this.type = type;
        this.stmt = stmt;
        this.correct_answer = correct_answer;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
    }
*/

    public String getQues_type() {
        return ques_type;
    }

    public void setQues_type(String ques_type) {
        this.ques_type = ques_type;
    }

    public String getQues_statement() {
        return ques_statement;
    }

    public void setQues_statement(String ques_statement) {
        this.ques_statement = ques_statement;
    }

    public int getQues_id() {
        return ques_id;
    }

    public void setQues_id(int ques_id) {
        this.ques_id = ques_id;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }


    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }
}
