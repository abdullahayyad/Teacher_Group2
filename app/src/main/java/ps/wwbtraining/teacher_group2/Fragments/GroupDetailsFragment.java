package ps.wwbtraining.teacher_group2.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
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

import static ps.wwbtraining.teacher_group2.Activities.MainActivity.getMenu;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailsFragment extends Fragment {
    ArrayList<User> students = new ArrayList<>();
    ArrayList<Integer>  checkedstudents ;
    ArrayList<Integer> newcheckedStds;
    ArrayList<User> currentStds= new ArrayList<>();
    boolean [] checked;
    StudentRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    EditText et_group_name, et_group_desc;
    String  group_name, group_desc;
    int gid;
    Group group;
    Menu menu;
    ImageButton save;
    private ProgressDialog dialog;

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
        //option menu items
        menu =getMenu();
        MenuItem edit_menu_item=menu.findItem(R.id.action_edit).setVisible(true);
        final MenuItem save_menu_item=menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_delete).setVisible(false);



        group =(Group) getArguments().getSerializable("group");
        edit_menu_item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem menuItem) {
                 save_menu_item.setVisible(true);
                 editGroup();
                 return true;
             }

      });

        save_menu_item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                String name=et_group_name.getText().toString().trim();
                String desc=et_group_desc.getText().toString().trim();

                if(!name.equals("")) {
                    group.setGroup_name(name);

                }
                if(!desc.equals("")) {
                    group.setGroup_desc(desc);

                }
                dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ", true);

                updateGroup(group);
                return true;
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_Stdgroups);
        layoutManager = new LinearLayoutManager(getActivity());

        et_group_name =  v.findViewById(R.id.et_groupName);
        et_group_desc =  v.findViewById(R.id.et_groupDesc);
        et_group_desc.setText(group.getGroup_desc());
        et_group_name.setText(group.getGroup_name());
/*
        save = (ImageButton) v.findViewById(R.id.save);

        save.setOnClickListener(splash View.OnClickListener() {
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

        dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ", true);
        loadCheckedStds();

        return  v;
    }




    private void loadCheckedStds() {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        Call<CheckedStudents> call = service.getGroupStds(group.getGid());

        call.enqueue(new Callback<CheckedStudents>() {
            @Override
            public void onResponse(Call<CheckedStudents> call, retrofit2.Response<CheckedStudents> response) {

               try {
                      if(response.body().getState().getStatus().equals("true")){
                    checkedstudents = new ArrayList<>();
                    // checkedstudents.add(5);
                    checkedstudents = response.body().getCheckedStds();
                    Log.d("stds",checkedstudents.get(0)+"  ");
                    Toast.makeText(getActivity(), checkedstudents.toString()+"  ", Toast.LENGTH_LONG).show();

                     }
                     else{
                          Log.d("stds","  false");
                      }
               } catch (Exception e) {
                    Log.d("stds"," catch");
                }

            }

            @Override
            public void onFailure(Call<CheckedStudents> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed2",Toast.LENGTH_SHORT).show();
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
                                currentStds.add(students.get(i));

                                Log.d("checked", checked[i] + "");
                                break;
                            }
                        }

                    }
                    Log.d("cstudents",currentStds.get(0).getUid()+ ""+currentStds.get(0).getUser_name());

                    adapter = new StudentRecyclerViewAdapter(getActivity(), currentStds);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                if(dialog!=null && dialog.isShowing()) dialog.dismiss();


                } catch (Exception e) {
                  if(dialog!=null && dialog.isShowing()) dialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<Students> call, Throwable t) {
                if(dialog!=null && dialog.isShowing()) dialog.dismiss();

            }
        });
    }

    private void editGroup() {
        et_group_name.setEnabled(true);
        et_group_desc.setEnabled(true);

        adapter = new StudentRecyclerViewAdapter(getActivity(), students,checked);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void updateGroup(final Group group) {



        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.updateGroup(group.getGid(),group.getGroup_name(),group.getGroup_desc());

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {

                try {
                if (response.body().getStatus().equals("true")) {

                    Toast.makeText(getActivity(), "  Group updated", Toast.LENGTH_LONG).show();


                     }
               } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });

        newcheckedStds=new ArrayList<>();
        for (int i=0; i<students.size();i++){
            if(checked[i]){
                //newcheckedStds.put(i+"" , students.get(i).getUid());
                newcheckedStds.add(students.get(i).getUid());
            }

        }
        updateGroupStds(group.getGid(),newcheckedStds);


    }

    private void updateGroupStds(int gid, ArrayList<Integer> newcheckedStds) {


        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);


        HashMap<String,Object> map=new HashMap<>();

        map.put("gid",gid);
        map.put("stds",newcheckedStds);


        JSONObject obj=new JSONObject(map);

        Toast.makeText(getActivity(), obj.toString(), Toast.LENGTH_LONG).show();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj.toString());
        Call<Response_State> call = service.updateGroupStds(body);
        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {

                Toast.makeText(getActivity(), "  GroupStds updated", Toast.LENGTH_LONG).show();
                try {
                 if (response.body().getStatus().equals("true")) {


                     if(dialog!=null && dialog.isShowing()) dialog.dismiss();

                 }
               } catch (Exception e) {
                    if(dialog!=null && dialog.isShowing()) dialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getActivity(), "failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
                if(dialog!=null && dialog.isShowing()) dialog.dismiss();

            }
        });

    }

}
