package ps.wwbtraining.teacher_group2.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ps.wwbtraining.teacher_group2.Activities.GroupActivity;
import ps.wwbtraining.teacher_group2.Adapters.GroupRecyclerViewAdapter;
import ps.wwbtraining.teacher_group2.Models.Group;
import ps.wwbtraining.teacher_group2.Models.GroupList;
import ps.wwbtraining.teacher_group2.Models.GroupResponse;
import ps.wwbtraining.teacher_group2.Models.LastGroupIDResponse;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsFragment extends Fragment {
    List<Group> groups = new ArrayList<>();
    GroupRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String group_name, group_desc;
    EditText et_group_name, et_group_desc;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_groups, container, false);
        v.findViewById(R.id.btn_add_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.create_group_dialog);
                dialog.setTitle("Create Group");

                et_group_name = dialog.findViewById(R.id.et_groupName);
                et_group_desc = dialog.findViewById(R.id.et_groupDesc);
                Button save = dialog.findViewById(R.id.save);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        group_name = et_group_name.getText().toString().trim();
                        group_desc = et_group_desc.getText().toString().trim();

                        if (!group_name.equals("") && !group_desc.equals("")) {
                            addNewGroup(group_name, group_desc);

                        } else {
                            //snack
                            Toast.makeText(getActivity(), "You should fill all fields to create the group", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


                dialog.show();
            }
        });
        recyclerView = v.findViewById(R.id.rv_groups);

        loadGroup();
        return v;
    }

    @Override
    public void onResume() {
        // loadGroup();
        super.onResume();
        //Toast.makeText(getActivity(), "resume", Toast.LENGTH_SHORT).show();


    }

    private void addNewGroup(final String group_name, final String group_desc) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<LastGroupIDResponse> call = service.addGroup(group_name,group_desc);

        call.enqueue(new Callback<LastGroupIDResponse>() {
            @Override
            public void onResponse(Call<LastGroupIDResponse> call, Response<LastGroupIDResponse> response) {
                try {
                    if (response.body().getState().getStatus().equals("true")) {
                        Toast.makeText(getActivity(), "Group added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        groups.add(new Group(response.body().getGid(), group_name, group_desc));
                        layoutManager = new LinearLayoutManager(getActivity());
                        adapter = new GroupRecyclerViewAdapter(getActivity(), groups);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getActivity(), "catch", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LastGroupIDResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                // show error dialog
            }
        });



    }

    private void refreshGroupsList() {
        //get Last Group Id


    }


    private void loadGroup() {

        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        Call<GroupList> call = service.getGroups();
        call.enqueue(new Callback<GroupList>() {
            @Override
            public void onResponse(Call<GroupList> call, retrofit2.Response<GroupList> response) {

                try {

                    groups = new ArrayList<>();
                    groups = response.body().getGroups();

                    layoutManager = new LinearLayoutManager(getActivity());
                    adapter = new GroupRecyclerViewAdapter(getActivity(), groups);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<GroupList> call, Throwable t) {
            }
        });
    }


}
