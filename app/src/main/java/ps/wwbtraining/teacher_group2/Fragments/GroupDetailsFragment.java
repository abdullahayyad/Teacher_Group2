package ps.wwbtraining.teacher_group2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ps.wwbtraining.teacher_group2.Adapters.StudentRecyclerViewAdapter;
import ps.wwbtraining.teacher_group2.Constants;
import ps.wwbtraining.teacher_group2.Models.CheckedStudents;
import ps.wwbtraining.teacher_group2.Models.Group;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.Models.Students;
import ps.wwbtraining.teacher_group2.Models.User;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailsFragment extends Fragment {
    ArrayList<User> students = new ArrayList<>();
    ArrayList<Integer> checkedstudents , newCheckedstudents;
    boolean [] checked;
    StudentRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    EditText et_group_name, et_group_desc;
    String  group_name, group_desc;
    int gid;
    Group group;

    ImageButton save;

    public GroupDetailsFragment() {
        // Required empty public constructor
    }

    public static GroupDetailsFragment newInstance(Group group) {

        Bundle args = new Bundle();
        args.putSerializable("group",group);
        GroupDetailsFragment fragment = new GroupDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_group_details, container, false);

        group =(Group) getArguments().getSerializable("group");

        v.findViewById(R.id.btn_save_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGroup(group.getGid());
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_Stdgroups);
        layoutManager = new LinearLayoutManager(getActivity());

        et_group_name = (EditText) v.findViewById(R.id.et_groupName);
        et_group_desc = (EditText) v.findViewById(R.id.et_groupDesc);
        et_group_desc.setText(group.getGroup_desc());
        et_group_name.setText(group.getGroup_name());
/*
        save = (ImageButton) v.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group_name = et_group_name.getText().toString().trim();
                group_desc = et_group_desc.getText().toString().trim();

                if (!et_group_name.equals("") && !et_group_desc.equals("")) {
                    // addNewGroup(group_name, group_desc);
                } else {
                    //snack
                    Toast.makeText(getActivity(), "You should fill all fields to sign up", Toast.LENGTH_SHORT).show();
                }

            }
        });
        */
        loadCheckedStds();

        return  v;
    }

    private void updateGroup(int gid) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        String name=et_group_name.getText().toString().trim();
        String desc=et_group_desc.getText().toString().trim();

        if(!name.equals("")) {
            group.setGroup_name(name);

        }
        if(!desc.equals("")) {
            group.setGroup_desc(desc);

        }

            Call<Response_State> call = service.updateGroup(group.getGid(),group.getGroup_name(),group.getGroup_desc());

            call.enqueue(new Callback<Response_State>() {
                @Override
                public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {

                    //try {
                    if (response.body().getStatus().equals("true")) {


                        Toast.makeText(getActivity(), "  Group updated", Toast.LENGTH_LONG).show();
                        updateStds();
                        //  }
//                } catch (Exception e) {
//
//                }
                    }
                }

                @Override
                public void onFailure(Call<Response_State> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });


    }

    private void updateStds() {

        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);




        HashMap<String,Object> map=new HashMap<>();
        newCheckedstudents=new ArrayList<>();
        for(int i=0; i<checked.length ;i++){
            if(checked[i]){
                newCheckedstudents.add(students.get(i).getUid());
            }
        }
        map.put("gid",group.getGid());
        map.put("stds",newCheckedstudents);

        JSONObject obj=new JSONObject(map);




        Call<Response_State> call = service.updateGroupStds(obj.toString());

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {

                //try {
                if (response.body().getStatus().equals("true")) {


                    Toast.makeText(getActivity(), "  students updated", Toast.LENGTH_LONG).show();
                    updateStds();
                    //  }
//                } catch (Exception e) {
//
//                }
                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void loadCheckedStds() {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<CheckedStudents> call = service.getGroupStds(group.getGid());

        call.enqueue(new Callback<CheckedStudents>() {
            @Override
            public void onResponse(Call<CheckedStudents> call, retrofit2.Response<CheckedStudents> response) {

                try {
                    //    if(response.body().getState().getStatus().equals("true")){
                    checkedstudents = new ArrayList<>();
                    // checkedstudents.add(5);
                    checkedstudents = response.body().getCheckedStds();
                    Log.d("stds",checkedstudents.get(0)+"  ");
                    Toast.makeText(getActivity(), checkedstudents.toString()+"  ", Toast.LENGTH_LONG).show();
                    //  }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<CheckedStudents> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });

        loadStudents();
    }

    private void loadStudents() {

        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Students> call = service.getStudents(Constants.GET_APPROVED_STDS_FLAG);

        call.enqueue(new Callback<Students>() {
            @Override
            public void onResponse(Call<Students> call, retrofit2.Response<Students> response) {

                try {

                    students = new ArrayList<>();
                    students = response.body().getStudents();
                    checked=new boolean[students.size()];
                    Log.d("students",students.get(0).getUid()+ ""+students.get(0).getUser_name());
                    for(int j=0;j<checkedstudents.size();j++){
                        for(int i=0;i<students.size();i++) {
                            if (checkedstudents.get(j)== students.get(i).getUid()) {
                                checked[i] = true;
                                Log.d("checked", checked[i] + "");
                                break;
                            }
                        }

                    }


                    adapter = new StudentRecyclerViewAdapter(getActivity(), students,checked);
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
