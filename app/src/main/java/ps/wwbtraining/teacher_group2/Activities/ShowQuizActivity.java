package ps.wwbtraining.teacher_group2.Activities;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Adapters.QuizPagerAdapter;
import ps.wwbtraining.teacher_group2.Adapters.StudentRecyclerViewAdapter;
import ps.wwbtraining.teacher_group2.Constants;
import ps.wwbtraining.teacher_group2.Models.Group;
import ps.wwbtraining.teacher_group2.Models.LastGroupIDResponse;
import ps.wwbtraining.teacher_group2.Models.Question;
import ps.wwbtraining.teacher_group2.Models.QuestionList;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.Models.Students;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowQuizActivity extends AppCompatActivity {

    ArrayList<Question> questions;
    int qid;
    ViewPager pager;
    FragmentManager fm;



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
        fm=getSupportFragmentManager();

        qid=getIntent().getIntExtra("quiz_id",0);

        loadQuestions();
    }

    private void loadQuestions() {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<QuestionList> call = service.getQuestionsList(qid);

        call.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, retrofit2.Response<QuestionList> response) {

try {


    if (response.body().getState().getStatus().equals("true")) {
        questions = new ArrayList<>();
        questions = response.body().getQuestionslist();
        Toast.makeText(getApplicationContext(), questions.toString() + "", Toast.LENGTH_SHORT).show();

        QuizPagerAdapter adapter = new QuizPagerAdapter(ShowQuizActivity.this.getSupportFragmentManager(), questions);
        pager.setAdapter(adapter);

    }
}catch (Exception ex){

}

            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quiz_pager_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ques_add) {

            return true;
        }
        else  if (id == R.id.ques_edit) {
            return true;
        }
        else  if (id == R.id.ques_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}