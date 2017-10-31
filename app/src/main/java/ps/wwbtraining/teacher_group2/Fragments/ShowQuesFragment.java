package ps.wwbtraining.teacher_group2.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ps.wwbtraining.teacher_group2.Models.Question;
import ps.wwbtraining.teacher_group2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowQuesFragment extends Fragment {


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

        Question ques=(Question)getArguments().getSerializable("ques");

        Log.d("q",ques.getQues_type()+ques.getQues_statement() + ques.getAns1() +ques.getAns2() + ques.getAns3() +ques.getAns4());
        TextView stmt=v.findViewById(R.id.ques_stmt);
        TextView ans1=v.findViewById(R.id.ques_ans1);
        TextView ans2=v.findViewById(R.id.ques_ans2);
        TextView ans3=v.findViewById(R.id.ques_ans3);
        TextView ans4=v.findViewById(R.id.ques_ans4);

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
        stmt.setText(ques.getQues_statement());
        try{
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


        }catch (Exception ex){

        }
        return v;
    }

}
