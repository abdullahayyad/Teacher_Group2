package ps.wwbtraining.teacher_group2.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group2.Adapters.QuizPagerAdapter;
import ps.wwbtraining.teacher_group2.Adapters.StudentRecyclerViewAdapter;
import ps.wwbtraining.teacher_group2.Constants;
import ps.wwbtraining.teacher_group2.Fragments.ShowQuesFragment;
import ps.wwbtraining.teacher_group2.Models.Group;
import ps.wwbtraining.teacher_group2.Models.LastGroupIDResponse;
import ps.wwbtraining.teacher_group2.Models.Question;
import ps.wwbtraining.teacher_group2.Models.QuestionList;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.Models.Students;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowQuizActivity extends AppCompatActivity {

    String[] list = {"a", "b", "c", "d"};
    String[] TF = {"true", "false"};
    String correctAns, s_ans1, s_ans2, s_ans3, s_ans4, s_stmt;
    Dialog updateDialog;
    Spinner spinner;

    ArrayList<Question> questions;
    int qid;
    ViewPager pager;
    FragmentManager fm;
    QuizPagerAdapter adapter;
    private ProgressDialog dialog;
    static Menu menu;
    Question ques;
    int currentPage;
    CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_quiz);


        Toolbar toolbar = (Toolbar) findViewById(R.id.pager_toolbar);
        toolbar.setTitle("Quiz");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pager = (ViewPager) findViewById(R.id.quiz_pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        qid = getIntent().getIntExtra("quiz_id", 0);
        dialog = ProgressDialog.show(this, "Loading ...", "Please wait ", true);

        loadQuestions(qid);
    }

    private void loadQuestions(int qid) {

        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<QuestionList> call = service.getQuestionsList(qid);

        call.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, retrofit2.Response<QuestionList> response) {

                try {

                    if (response.body().getState().getStatus().equals("true")) {
                        questions = new ArrayList<>();
                        questions = response.body().getQuestionslist();
                        // Toast.makeText(getApplicationContext(),questions.get(0).getStmt(), Toast.LENGTH_SHORT).show();
                        Log.d("qlist", questions + "");


                        adapter = new QuizPagerAdapter(ShowQuizActivity.this.getSupportFragmentManager(), questions);
                        pager.setAdapter(adapter);
                        indicator.setViewPager(pager);
                        if (dialog != null && dialog.isShowing()) dialog.dismiss();


                    }
                } catch (Exception ex) {
                    Log.d("catch", "catch");
                    if (dialog != null && dialog.isShowing()) dialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing()) dialog.dismiss();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.quiz_pager_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ques_add) {
            Intent i = new Intent(this, AddQuestionsActivity.class);
            i.putExtra("qid", qid);
            i.putExtra("flag", Constants.SHOW_QUIZ_ACTIVITY);
            startActivity(i);
            return true;
        } else if (id == R.id.ques_edit) {
//            Log.e("currentPage",currentPage+"");
//            ShowQuesFragment shaShowQuesFragment =  adapter.getItem(currentPage);
//            Log.e("currentFrag",shaShowQuesFragment.toString());
//           adapter.getItem(currentPage).onUpdateQues();
//if(listener!= null){
//    listener.onUpdateQues();
//}
            showUpdateQuesDialog();
            return true;
        } else if (id == R.id.ques_delete) {
            deleteQuestion(questions.get(currentPage).getQues_id(),currentPage);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.ADD_QUES_REQUEST && resultCode == Constants.ADD_QUES_RESULT){
            ArrayList<Question>result=(ArrayList<Question>)data.getSerializableExtra("quesList");
            questions.addAll(result);
            adapter.notifyDataSetChanged();
        }
    }
    */

    @Override
    protected void onResume() {
        // dialog = ProgressDialog.show(this, "Loading ...", "Please wait ", true);
        loadQuestions(qid);
        super.onResume();
    }


    public static Menu getPagerMenu() {

        return menu;

    }


    private void showUpdateQuesDialog() {
        ques = questions.get(currentPage);
        updateDialog = new Dialog(this);
        updateDialog.setContentView(R.layout.update_ques_dialog);
        updateDialog.setTitle("Update Question");
        final EditText et_quesStmt = updateDialog.findViewById(R.id.et_quesStmt);
        final EditText et_quesAns1 = updateDialog.findViewById(R.id.et_quesAns1);
        final EditText et_quesAns2 = updateDialog.findViewById(R.id.et_quesAns2);
        final EditText et_quesAns3 = updateDialog.findViewById(R.id.et_quesAns3);
        final EditText et_quesAns4 = updateDialog.findViewById(R.id.et_quesAns4);
        final TextView update_tv = updateDialog.findViewById(R.id.udpating_text);
        Button edit_questions = updateDialog.findViewById(R.id.btn_edit_questions);
        final ProgressBar progressBar = updateDialog.findViewById(R.id.editQues_progressBar);
        spinner = updateDialog.findViewById(R.id.correct_spinner);

        correctAns = ques.getCorrect_answer();
        et_quesStmt.setText(ques.getQues_statement());

        if (ques.getQues_type().equals("0")) {
            et_quesAns3.setVisibility(View.GONE);
            et_quesAns4.setVisibility(View.GONE);
            et_quesAns1.setVisibility(View.GONE);
            et_quesAns2.setVisibility(View.GONE);
            // et_quesAns1.setText("True");
            // et_quesAns2.setText("false");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TF);
            spinner.setAdapter(adapter);
            spinner.setSelection(Integer.parseInt(correctAns));

        } else {
            et_quesAns1.setText(ques.getAns1());
            et_quesAns2.setText(ques.getAns2());
            et_quesAns3.setText(ques.getAns3());
            et_quesAns4.setText(ques.getAns4());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            spinner.setAdapter(adapter);
            spinner.setSelection(Integer.parseInt(correctAns));

        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        correctAns = Constants.ANS1;
                        break;
                    case 1:
                        correctAns = Constants.ANS2;
                        break;
                    case 2:
                        correctAns = Constants.ANS3;
                        break;
                    case 3:
                        correctAns = Constants.ANS4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edit_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                update_tv.setVisibility(View.VISIBLE);
                s_stmt = et_quesStmt.getText().toString().trim();
                s_ans1 = et_quesAns1.getText().toString().trim();
                s_ans2 = et_quesAns2.getText().toString().trim();

                if (!s_stmt.equals("")) {
                    ques.setQues_statement(s_stmt);
                }

                if (!s_ans1.equals("")) {
                    ques.setAns1(s_ans1);
                }
                if (!s_ans2.equals("")) {
                    ques.setAns2(s_ans2);
                }
                ques.setCorrect_answer(correctAns);

                if (ques.getQues_type().equals("1")) {

                    s_ans3 = et_quesAns3.getText().toString().trim();
                    s_ans4 = et_quesAns4.getText().toString().trim();

                    if (!s_ans3.equals("")) {
                        ques.setAns3(s_ans3);
                    }
                    if (!s_ans4.equals("")) {
                        ques.setAns4(s_ans4);
                    }

                }
                updateQuestion(ques);
            }
        });
        updateDialog.show();

    }


    private void updateQuestion(final Question ques) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("qus_id", ques.getQues_id());
            obj.put("ques_statement", ques.getQues_statement());
            obj.put("ques_type", ques.getQues_type());
            obj.put("correct_answer", ques.getCorrect_answer());
            obj.put("ans1", ques.getAns1());
            obj.put("ans2", ques.getAns2());
            obj.put("ans3", ques.getAns3());
            obj.put("ans4", ques.getAns4());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj.toString());
        Call<Response_State> call = service.updateQuestion(body);

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, Response<Response_State> response) {
                try {
                    if (response.body().getStatus().equals("true")) {
                        questions.set(currentPage, ques);
                        pager.getAdapter().notifyDataSetChanged();
                        updateDialog.dismiss();


                        Toast.makeText(ShowQuizActivity.this, "Ques updated", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception ex) {
                    Toast.makeText(ShowQuizActivity.this, "catch" + ex.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(ShowQuizActivity.this, "Failed to update Questions", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void deleteQuestion(int qid,int position) {
        JSONObject obj = new JSONObject();


        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.deleteQuestion(qid);

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, Response<Response_State> response) {
               // try {
                    if (response.body().getStatus().equals("true")) {
                        questions.remove(currentPage);
                        pager.getAdapter().notifyDataSetChanged();


                        Toast.makeText(ShowQuizActivity.this, "Ques Deleted", Toast.LENGTH_SHORT).show();

                   }
                   /*
                } catch (Exception ex) {
                    Toast.makeText(ShowQuizActivity.this, "catch" + ex.getMessage(), Toast.LENGTH_SHORT).show();

                }*/
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(ShowQuizActivity.this, "Failed to update Questions", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
