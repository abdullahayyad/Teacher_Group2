package ps.wwbtraining.teacher_group2.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import ps.wwbtraining.teacher_group2.Adapters.QuizesListAdapter;
import ps.wwbtraining.teacher_group2.Constants;
import ps.wwbtraining.teacher_group2.Models.AddQuesResponse;
import ps.wwbtraining.teacher_group2.Models.LastQuesIDResponse;
import ps.wwbtraining.teacher_group2.Models.LastQuizeIDResponse;
import ps.wwbtraining.teacher_group2.Models.Question;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.Models.Response_State;
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
    private ArrayList<JSONObject> TFList=new ArrayList<>();
    private ArrayList<JSONObject> MultiChoiceList=new ArrayList<>();
    private int qid;
    String name,desc;
    ArrayList<Question> quesList=new ArrayList<>();

    LastQuizeIDResponse res;;
    private String deadline;
int flag;
    private ProgressDialog dialog;

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
        flag=i.getIntExtra("flag",-1);
        qid=i.getIntExtra("qid",0);
         name = i.getStringExtra("qname");
         desc=i.getStringExtra("qdesc");
        deadline=i.getStringExtra("qdeadline");

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
                    JSONObject obj=new JSONObject();
                    if (ques_type.equals(Constants.MULTI_CHOICE_TYPE)) {
                        ans1=et_ans1.getText().toString().trim();
                        ans2=et_ans2.getText().toString().trim();
                        ans3=et_ans3.getText().toString().trim();
                        ans4=et_ans3.getText().toString().trim();
                        if(!ans1.equals("") && !ans2.equals("") && !ans3.equals("") && !ans4.equals("")){

                            Toast.makeText(AddQuestionsActivity.this, "question added", Toast.LENGTH_SHORT).show();

                               try {
                                   obj.put("ques_type", ques_type);
                                   obj.put("ques_statement", ques_stmt);
                                   obj.put("correct_answer", correct);
                                   obj.put("ans1", ans1);
                                   obj.put("ans2", ans2);
                                   obj.put("ans3", ans3);
                                   obj.put("ans4", ans4);


                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }

                               insertMultiChoiceQues(obj);

                        }
                        
                    } else if (ques_type.equals(Constants.TF_TYPE)) {
                        try {
                            obj.put("ques_type",ques_type);
                            obj.put("ques_statement",ques_stmt);
                            obj.put("correct_answer",correct);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        insertTFQues(obj);
                        Toast.makeText(AddQuestionsActivity.this, "question added", Toast.LENGTH_SHORT).show();

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
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked) {
                    correct = Constants.ANS1;
                    rd2.setChecked(false);
                    rd3.setChecked(false);
                    rd4.setChecked(false);
                }
            }
        });

        rd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    correct = Constants.ANS2;
                    rd1.setChecked(false);
                    rd3.setChecked(false);
                    rd4.setChecked(false);
                }
            }
        });

        rd3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    correct = Constants.ANS3;
                    rd2.setChecked(false);
                    rd1.setChecked(false);
                    rd4.setChecked(false);
                }
            }
        });

        rd4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    correct = Constants.ANS4;
                    rd2.setChecked(false);
                    rd3.setChecked(false);
                    rd1.setChecked(false);
                }
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

    private void insertTFQues(JSONObject obj) {
       
        TFList.add(obj);
        Log.d("TF",TFList.toString());

    }

    private void insertMultiChoiceQues(JSONObject q) {
        MultiChoiceList.add(q);
    }

    private void addNewQuize(final String quiz_name, final String quiz_desc,final String deadline, final ArrayList<JSONObject> TFlist , final ArrayList<JSONObject> Multilist) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<LastQuizeIDResponse> call = service.addQuiz(quiz_name,quiz_desc,deadline);

        call.enqueue(new Callback<LastQuizeIDResponse>() {
            @Override
            public void onResponse(Call<LastQuizeIDResponse> call, Response<LastQuizeIDResponse> response) {


                //try{
                    if (response.body().getState().getStatus().equals("true")) {
                        qid = response.body().getQid();
                        Log.d("qid",qid+"");
                        Toast.makeText(AddQuestionsActivity.this, "Quiz added", Toast.LENGTH_SHORT).show();

                        addQuizQuestions(qid, TFlist, Multilist);
                    }
                /*}
                catch (NullPointerException ex){
                    Toast.makeText(AddQuestionsActivity.this, "No response", Toast.LENGTH_SHORT).show();

                }*/

                      /*  quizes.add(new Quiz(response.body().getGid(), quiz_name, quiz_desc,"0","0"));

                        recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        QuizesListAdapter adapter=new QuizesListAdapter(getActivity(),quizes);
                        recycler.setAdapter(adapter);
*/
            }
            @Override
            public void onFailure(Call< LastQuizeIDResponse > call, Throwable t) {
                Toast.makeText(AddQuestionsActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                // show error dialog
            }
        });


    }

    private void addQuizQuestions(int qid,ArrayList<JSONObject> tFlist, ArrayList<JSONObject> multilist) {

        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        HashMap<String,Object> map=new HashMap<>();
        map.put("qid",qid);
        map.put("TF",tFlist);
        map.put("multi",multilist);
        JSONObject json=new JSONObject(map);

        Log.d("json",json.toString());
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json.toString());
        Call<AddQuesResponse> call = service.addQuizQuestions(body);

        call.enqueue(new Callback<AddQuesResponse>() {
            @Override
            public void onResponse(Call<AddQuesResponse> call, Response<AddQuesResponse> response) {
                try {
                    if (response.body().getState().getStatus().equals("true")) {
                        Toast.makeText(AddQuestionsActivity.this, "Questions added", Toast.LENGTH_SHORT).show();

                      /*  quizes.add(new Quiz(response.body().getGid(), quiz_name, quiz_desc,"0","0"));

                        recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        QuizesListAdapter adapter=new QuizesListAdapter(getActivity(),quizes);
                        recycler.setAdapter(adapter);
*/
                        if(dialog!=null && dialog.isShowing()) dialog.dismiss();


                    }
                } catch (Exception ex) {
                    Toast.makeText(AddQuestionsActivity.this, "Failed to add Questions\n"+ex.getMessage(), Toast.LENGTH_SHORT).show();
                    if(dialog!=null && dialog.isShowing()) dialog.dismiss();

                }
            }
            @Override
            public void onFailure(Call<AddQuesResponse> call, Throwable t) {
                Toast.makeText(AddQuestionsActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                if(dialog!=null && dialog.isShowing()) dialog.dismiss();

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
            if(TFList.size()!=0 || MultiChoiceList.size() !=0) {
                dialog = ProgressDialog.show(this, "Loading ...", "Please wait ", true);

                if(flag==Constants.QUIZES_FRAGMENT) {
                    addNewQuize(name, desc, deadline, TFList, MultiChoiceList);
                }
                else if(flag==Constants.SHOW_QUIZ_ACTIVITY) {
                    addQuizQuestions(qid,TFList, MultiChoiceList);
                }
                finish();
            }
            else{
                Toast.makeText(this, "You must add questions to the quiz to be added", Toast.LENGTH_SHORT).show();

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
