package ps.wwbtraining.teacher_group2.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Adapters.StudentRecyclerViewAdapter;
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

public class AddNewGroupFragment extends Fragment {
    ArrayList<User> students = new ArrayList<>();
    StudentRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    EditText et_group_name , et_group_desc;
    String group_name , group_desc;

    ImageButton save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_add_new_group, container, false);

        recyclerView = v.findViewById(R.id.rv_Stdgroups);
        layoutManager = new LinearLayoutManager(getActivity());
        loadStudents();

        et_group_name = v.findViewById(R.id.et_groupName);
        et_group_desc = v.findViewById(R.id.et_groupDesc);
        save = v.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group_name=et_group_name.getText().toString().trim();
                group_desc=et_group_desc.getText().toString().trim();

                if(!et_group_name.equals("") && !et_group_desc.equals("")){
                    addNewGroup(group_name, group_desc);
                }
                else{
                    //snack
                    Toast.makeText(getActivity(), "You should fill all fields to sign up", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return v;
    }


    private void addNewGroup(String group_name, String group_desc) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.addGroup(group_name,group_desc);

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, Response<Response_State> response) {

                if(response.body().getStatus().equals("true")){
                    Toast.makeText(getActivity(), "Group added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

                // show error dialog
            }
        });

    }

    private void addStdGroup(String gid, String uid) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.addGroup(group_name,group_desc);

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, Response<Response_State> response) {

                if(response.body().getStatus().equals("true")){

                    Toast.makeText(getActivity(), "Group added", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();

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

                    adapter = new StudentRecyclerViewAdapter(getActivity(), students);
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

}
