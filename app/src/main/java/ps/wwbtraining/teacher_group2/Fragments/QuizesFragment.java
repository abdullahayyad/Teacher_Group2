package ps.wwbtraining.teacher_group2.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ps.wwbtraining.teacher_group2.Activities.AddQuestionsActivity;
import ps.wwbtraining.teacher_group2.Activities.ShowQuizActivity;
import ps.wwbtraining.teacher_group2.Adapters.QuizPagerAdapter;
import ps.wwbtraining.teacher_group2.Adapters.QuizesListAdapter;
import ps.wwbtraining.teacher_group2.Constants;
import ps.wwbtraining.teacher_group2.Models.LastQuizeIDResponse;
import ps.wwbtraining.teacher_group2.Models.QuestionList;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.Models.QuizesList;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.DateAndTimeUtil;
import ps.wwbtraining.teacher_group2.Utils.FragmentUtil;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizesFragment extends Fragment implements QuizesListAdapter.OnQuizModifiedListener{

    RecyclerView recycler;
    private ArrayList<Quiz> quizes;
    Dialog dialog;
    String quiz_name,quiz_desc,quiz_dealine;
    EditText et_quiz_name,et_quiz_desc;
    TextView tv_quiz_deadline;
    Button add_questions;
    QuizesListAdapter adapter;
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
                tv_quiz_deadline=dialog.findViewById(R.id.quizDeadline);
                add_questions = dialog.findViewById(R.id.add_questions);

                tv_quiz_deadline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datePicker();
                    }
                });
                add_questions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        quiz_name = et_quiz_name.getText().toString().trim();
                        quiz_desc = et_quiz_desc.getText().toString().trim();
                        quiz_dealine = tv_quiz_deadline.getText().toString().trim();
                        if (!quiz_name.equals("") && !quiz_desc.equals("") && !quiz_dealine.equals("")) {
                           // addNewQuize(quiz_name,quiz_desc);
                            Intent i=new Intent(getActivity(), AddQuestionsActivity.class);
                            i.putExtra("flag", Constants.QUIZES_FRAGMENT);
                            i.putExtra("qname",quiz_name);
                            i.putExtra("qdesc",quiz_desc);
                            i.putExtra("qdeadline",quiz_dealine);
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
        dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ",true);
        loadQuizes();
        return v;
    }

    @Override
    public void onResume() {
        Toast.makeText(getActivity(), "resume", Toast.LENGTH_SHORT).show();
        //dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ", true);
       // loadQuizes();
        super.onResume();


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
         adapter = new QuizesListAdapter(getActivity(), quizes);
        adapter.setOnQuizModifiedListener(QuizesFragment.this);
        recycler.setAdapter(adapter);
        if(dialog!=null && dialog.isShowing()) dialog.dismiss();

    }

}catch (Exception ex){
    if(dialog!=null && dialog.isShowing()) dialog.dismiss();

}

            }

            @Override
            public void onFailure(Call<QuizesList> call, Throwable t) {
                // Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                if(dialog!=null && dialog.isShowing()) dialog.dismiss();

            }
        });
    }


    private void datePicker() {
        final Calendar calendar=Calendar.getInstance();

        DatePickerDialog DatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker DatePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tv_quiz_deadline.setText(DateAndTimeUtil.toStringReadableDate(calendar));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();

    }


    @Override
    public void setQuizNotified(int qid, String notified) {
       // FragmentUtil.replaceFragment(getActivity(), splash GroupsFragment(), R.id.content);
        // بعد م يختار ويبعت الكويز لازم نعدل قيمة ال  notified
        /*
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.setQuizNotified(qid,notified);

        call.enqueue(splash Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {


                if (response.body().getStatus().equals("true")) {

//                    Toast.makeText(getActivity(),  "Quiz Notified ", Toast.LENGTH_LONG).show();
//

                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    @Override
    public void deleteQuiz(int qid, final int adapterPosition) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.deleteQuiz(qid);

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {


                if (response.body().getStatus().equals("true")) {
                    Toast.makeText(getActivity(),  "Quiz Deleted ", Toast.LENGTH_LONG).show();
                    quizes.remove(adapterPosition);
                    recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    adapter = new QuizesListAdapter(getActivity(), quizes);
                    recycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
