package ps.wwbtraining.teacher_group2.Activities;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Adapters.QuizesListAdapter;
import ps.wwbtraining.teacher_group2.Constants;
import ps.wwbtraining.teacher_group2.Models.LastQuizeIDResponse;
import ps.wwbtraining.teacher_group2.Models.Question;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddQuestionsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RadioButton rd1,rd2,rd3,rd4,rd_true,rd_false;
    RadioGroup rg;
    EditText et_ans1,et_ans2,et_ans3,et_ans4;
    Button btn_add_ques,btn_clear;
    String ques_type,ques_stmt,ans1,ans2,ans3,ans4,correct;
    private ArrayList<Question> TFList;
    private ArrayList<Question> MultiChoiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        toolbar = (Toolbar) findViewById(R.id.addQues_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Question");

        // Prevent keyboard from opening automatically//
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent i=getIntent();
        String name = i.getStringExtra("qname");
        String desc=i.getStringExtra("qdesc");

        //Ui components
        final Spinner spinner=(Spinner)findViewById(R.id.qtype_spinner);
        final EditText et_stmt=(EditText)findViewById(R.id.quesStmt);
         rg=(RadioGroup)findViewById(R.id.tf_group);
         et_ans1=(EditText)findViewById(R.id.et_ans1);
         et_ans2=(EditText)findViewById(R.id.et_ans2);
         et_ans3=(EditText)findViewById(R.id.et_ans3);
         et_ans4=(EditText)findViewById(R.id.et_ans4);
         rd1=(RadioButton)findViewById(R.id.radio_ans1);
         rd2=(RadioButton)findViewById(R.id.radio_ans2);
         rd3=(RadioButton)findViewById(R.id.radio_ans3);
         rd4=(RadioButton)findViewById(R.id.radio_ans4);
         rd_true=(RadioButton)findViewById(R.id.radio_true);
         rd_false=(RadioButton)findViewById(R.id.radio_false);

         btn_add_ques=(Button)findViewById(R.id.btn_add_ques);
         btn_clear=(Button)findViewById(R.id.btn_clear_data);


        ////////////// handling Events////////////////////
        btn_add_ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ques_stmt=et_stmt.getText().toString().trim();
               
                if(!ques_stmt.equals("") && correct!=null) {
                    
                    if (ques_type.equals(Constants.MULTI_CHOICE_TYPE)) {
                        ans1=et_ans1.getText().toString().trim();
                        ans2=et_ans1.getText().toString().trim();
                        ans3=et_ans1.getText().toString().trim();
                        ans4=et_ans1.getText().toString().trim();
                        if(!ans1.equals("") && !ans2.equals("") && !ans3.equals("") && !ans4.equals("")){
                            insertMultiChoiceQues(new Question(ques_type,ques_stmt,correct,ans1,ans2,ans3,ans4));
                        }
                        
                    } else if (ques_type.equals(Constants.TF_TYPE)) {
                            insertTFQues(new Question(ques_type,ques_stmt,correct));
                    } else {
                        Toast.makeText(AddQuestionsActivity.this, "You must select the question type", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddQuestionsActivity.this, "You must enter the question statement and choose the correct answer ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==1){
                    ques_type= Constants.TF_TYPE;
                    correct=null;
                    rg.setVisibility(View.VISIBLE);
                    rd1.setVisibility(View.INVISIBLE);
                    rd2.setVisibility(View.INVISIBLE);
                    rd3.setVisibility(View.INVISIBLE);
                    rd4.setVisibility(View.INVISIBLE);
                    et_ans1.setVisibility(View.INVISIBLE);
                    et_ans2.setVisibility(View.INVISIBLE);
                    et_ans3.setVisibility(View.INVISIBLE);
                    et_ans4.setVisibility(View.INVISIBLE);
                }
                else if(position==2){
                    ques_type= Constants.MULTI_CHOICE_TYPE;
                    correct=null;
                    rg.setVisibility(View.GONE);
                    rd1.setVisibility(View.VISIBLE);
                    rd2.setVisibility(View.VISIBLE);
                    rd3.setVisibility(View.VISIBLE);
                    rd4.setVisibility(View.VISIBLE);
                    et_ans1.setVisibility(View.VISIBLE);
                    et_ans2.setVisibility(View.VISIBLE);
                    et_ans3.setVisibility(View.VISIBLE);
                    et_ans4.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        rd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                correct=Constants.ANS1;
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
            }
        });

        rd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                correct=Constants.ANS2;
                rd1.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
            }
        });

        rd3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                correct=Constants.ANS3;
                rd2.setChecked(false);
                rd1.setChecked(false);
                rd4.setChecked(false);
            }
        });

        rd4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                correct=Constants.ANS4;
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd1.setChecked(false);
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                if(id == R.id.radio_true){
                    correct=Constants.ANS1; 
                }else{
                    correct=Constants.ANS2;
                }
            }
        });
        /////////////////////////////////////////////////
    }

    private void insertTFQues(Question q) {
       TFList.add(q);
    }

    private void insertMultiChoiceQues(Question q) {
        MultiChoiceList.add(q);
    }

    private void addNewQuize(final String quiz_name, final String quiz_desc, ArrayList<Question> list) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<LastQuizeIDResponse> call = service.addQuiz(quiz_name,quiz_desc);

        call.enqueue(new Callback<LastQuizeIDResponse>() {
            @Override
            public void onResponse(Call<LastQuizeIDResponse> call, Response<LastQuizeIDResponse> response) {
                try {
                    if (response.body().getState().getStatus().equals("true")) {
                        Toast.makeText(AddQuestionsActivity.this, "Quiz added", Toast.LENGTH_SHORT).show();

                      /*  quizes.add(new Quiz(response.body().getGid(), quiz_name, quiz_desc,"0","0"));

                        recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        QuizesListAdapter adapter=new QuizesListAdapter(getActivity(),quizes);
                        recycler.setAdapter(adapter);
*/

                    }
                } catch (Exception ex) {
                    Toast.makeText(AddQuestionsActivity.this, "catch", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call< LastQuizeIDResponse > call, Throwable t) {
                Toast.makeText(AddQuestionsActivity.this, "Failed", Toast.LENGTH_SHORT).show();


                // show error dialog
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_questions_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save_quiz){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
