package ps.wwbtraining.teacher_group2.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group2.Activities.AddQuestionsActivity;
import ps.wwbtraining.teacher_group2.Activities.ShowQuizActivity;
import ps.wwbtraining.teacher_group2.Constants;
import ps.wwbtraining.teacher_group2.Models.AddQuesResponse;
import ps.wwbtraining.teacher_group2.Models.Question;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.wwbtraining.teacher_group2.Activities.ShowQuizActivity.getPagerMenu;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowQuesFragment extends Fragment{


String [] list={"a","b","c","d"};
String [] TF={"true","false"};
    String correctAns,s_ans1,s_ans2,s_ans3,s_ans4,s_stmt;
    Dialog dialog;
    Spinner spinner;
    TextView stmt,ans1,ans2,ans3,ans4;

    Menu menu;
    private Button btn_save;
    Question ques;
    public ShowQuesFragment() {
        // Required empty public constructor
    }


    public static ShowQuesFragment newInstance(Question q) {

        Bundle args = new Bundle();
        args.putSerializable("ques",q);
        ShowQuesFragment fragment = new ShowQuesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_show_ques, container, false);

          ques=(Question)getArguments().getSerializable("ques");

        correctAns=ques.getCorrect_answer();
         stmt=v.findViewById(R.id.ques_stmt);
         ans1=v.findViewById(R.id.ques_ans1);
         ans2=v.findViewById(R.id.ques_ans2);
         ans3=v.findViewById(R.id.ques_ans3);
         ans4=v.findViewById(R.id.ques_ans4);

        assignValues(ques);
        return v;
    }

    private void assignValues(Question ques) {
        stmt.setText(ques.getQues_statement());

        if(ques.getQues_type().equals("0")){
            ans3.setVisibility(View.GONE);
            ans4.setVisibility(View.GONE);
            ans1.setText("True");
            ans2.setText("false");

        }else{
            ans1.setText(ques.getAns1());
            ans2.setText(ques.getAns2());
            ans3.setText(ques.getAns3());
            ans4.setText(ques.getAns4());

        }



        switch (ques.getCorrect_answer()) {
            case ("0"):
                ans1.setTextColor(Color.GREEN);
                break;
            case ("1"):
                ans2.setTextColor(Color.GREEN);
                break;
            case ("2"):
                ans3.setTextColor(Color.GREEN);
                break;
            case ("3"):
                ans4.setTextColor(Color.GREEN);
                break;
            default:
        }

    }





}
