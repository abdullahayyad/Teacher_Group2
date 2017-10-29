package ps.wwbtraining.teacher_group2.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Activities.AddQuestionsActivity;
import ps.wwbtraining.teacher_group2.Activities.ShowQuizActivity;
import ps.wwbtraining.teacher_group2.Adapters.QuizPagerAdapter;
import ps.wwbtraining.teacher_group2.Adapters.QuizesListAdapter;
import ps.wwbtraining.teacher_group2.Models.LastQuizeIDResponse;
import ps.wwbtraining.teacher_group2.Models.QuestionList;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.Models.QuizesList;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizesFragment extends Fragment {

    RecyclerView recycler;
    private ArrayList<Quiz> quizes;
    Dialog dialog;
    String quiz_name,quiz_desc;
    EditText et_quiz_name,et_quiz_desc;
    Button add_questions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_quizes, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.create_quize_dialog);
                dialog.setTitle("Create Quize");
                et_quiz_name = dialog.findViewById(R.id.et_quizName);
                et_quiz_desc = dialog.findViewById(R.id.et_quizDesc);
                add_questions = dialog.findViewById(R.id.add_questions);

                add_questions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        quiz_name = et_quiz_name.getText().toString().trim();
                        quiz_desc = et_quiz_name.getText().toString().trim();
                        if (!quiz_name.equals("") && !quiz_desc.equals("")) {
                           // addNewQuize(quiz_name,quiz_desc);
                            Intent i=new Intent(getActivity(), AddQuestionsActivity.class);
                            i.putExtra("qname",quiz_name);
                            i.putExtra("qdesc",quiz_desc);
                            dialog.dismiss();
                            startActivity(i);


                        } else {
                            //snack
                            Toast.makeText(getActivity(), "You should fill all fields to create the group", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.show();

            }
        });
        recycler=(RecyclerView)v.findViewById(R.id.quizes_list_recycler);

        loadQuizes();
        return v;
    }



    private void loadQuizes() {

        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<QuizesList> call = service.getQuizesList();

        call.enqueue(new Callback<QuizesList>() {
            @Override
            public void onResponse(Call<QuizesList> call, retrofit2.Response<QuizesList> response) {

try {
    if (response.body().getState().getStatus().equals("true")) {
        quizes = new ArrayList<>();
        quizes = response.body().getQuezesList();

        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        QuizesListAdapter adapter = new QuizesListAdapter(getActivity(), quizes);
        recycler.setAdapter(adapter);

    }

}catch (Exception ex){

}

            }

            @Override
            public void onFailure(Call<QuizesList> call, Throwable t) {
                // Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
