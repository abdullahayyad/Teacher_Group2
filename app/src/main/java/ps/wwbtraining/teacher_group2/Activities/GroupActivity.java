package ps.wwbtraining.teacher_group2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Adapters.StudentRecyclerViewAdapter;
import ps.wwbtraining.teacher_group2.Fragments.AddNewGroupFragment;
import ps.wwbtraining.teacher_group2.Models.Group;
import ps.wwbtraining.teacher_group2.Models.GroupResponse;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.Models.Students;
import ps.wwbtraining.teacher_group2.Models.User;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.FragmentUtil;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupActivity extends AppCompatActivity {
    ArrayList<User> students = new ArrayList<>();
    StudentRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    EditText et_group_name, et_group_desc;
    String  group_name, group_desc;
int gid;
    Group group;

    ImageButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        gid = getIntent().getIntExtra("gid",0);

        recyclerView = (RecyclerView) findViewById(R.id.rv_Stdgroups);
        layoutManager = new LinearLayoutManager(this);
        loadStudents();

        Group group1 = getGroupData(gid);

        et_group_name = (EditText) findViewById(R.id.et_groupName);
        et_group_desc = (EditText) findViewById(R.id.et_groupDesc);
        save = (ImageButton) findViewById(R.id.save);

        Log.d("tttttttttttt" , group1.getGroup_name()+group1.getGroup_desc());
        et_group_name.setText(group1.getGroup_name());
        et_group_desc.setText(group1.getGroup_desc());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group_name = et_group_name.getText().toString().trim();
                group_desc = et_group_desc.getText().toString().trim();

                if (!et_group_name.equals("") && !et_group_desc.equals("")) {
                    addNewGroup(group_name, group_desc);
                } else {
                    //snack
                    Toast.makeText(getApplicationContext(), "You should fill all fields to sign up", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void addNewGroup(String group_name, String group_desc) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.addGroup(group_name, group_desc);

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, Response<Response_State> response) {

                if (response.body().getStatus().equals("true")) {
                    Toast.makeText(getApplicationContext(), "Group added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

                // show error dialog
            }
        });

    }

    private void addStdGroup(String gid, String uid) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.addGroup(group_name, group_desc);

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, Response<Response_State> response) {

                if (response.body().getStatus().equals("true")) {

                    Toast.makeText(getApplicationContext(), "Group added", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

                // show error dialog
            }
        });

    }


    private void loadStudents() {

        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Students> call = service.getStudents();

        call.enqueue(new Callback<Students>() {
            @Override
            public void onResponse(Call<Students> call, retrofit2.Response<Students> response) {

                try {

                    students = new ArrayList<>();
                    students = response.body().getStudents();

                    adapter = new StudentRecyclerViewAdapter(getApplicationContext(), students);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Students> call, Throwable t) {
            }
        });
    }

    public Group getGroupData(int gid) {
        /////////////////////////////////////////////////////////////////////
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        Call<GroupResponse> call = service.getGroupId(gid);

        call.enqueue(new Callback<GroupResponse>() {
            @Override
            public void onResponse(Call<GroupResponse> call, Response<GroupResponse> response) {

                if (response.body().getState().getStatus().equals("true")) {
                    group = response.body().getGroup();

                } else {
                    group = null;
                }
            }

            @Override
            public void onFailure(Call<GroupResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        });

        return group;
    }
}