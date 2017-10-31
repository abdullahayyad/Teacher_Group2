package ps.wwbtraining.teacher_group2.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Adapters.StudentRecyclerViewAdapter;
import ps.wwbtraining.teacher_group2.Constants;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.Models.Students;
import ps.wwbtraining.teacher_group2.Models.User;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;

public class StudentsFragment extends Fragment implements StudentRecyclerViewAdapter.OnStateChangedListener {

    ArrayList<User> students = new ArrayList<>();
    StudentRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    int currentTab;
    private ProgressDialog dialog;
    Button btn_all,btn_approved,btn_unapproved,btn_blocked,btn_rejected;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_students, container, false);

        recyclerView = v.findViewById(R.id.rv_students);
        layoutManager = new LinearLayoutManager(getActivity());

        dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ", true);
        loadStudents();


        btn_all=v.findViewById(R.id.btn_all);
        btn_all.setTextColor(Color.WHITE);
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_all.setTextColor(Color.WHITE);
                btn_approved.setTextColor(Color.BLACK);
                btn_unapproved.setTextColor(Color.BLACK);
                btn_rejected.setTextColor(Color.BLACK);
                btn_blocked.setTextColor(Color.BLACK);
               if(currentTab != Constants.TAB_ALL) {
                   dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ", true);
                   loadStudents();
               }
            }
        });


        btn_approved=v.findViewById(R.id.btn_approved);
        btn_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_all.setTextColor(Color.BLACK);
                btn_approved.setTextColor(Color.WHITE);
                btn_unapproved.setTextColor(Color.BLACK);
                btn_rejected.setTextColor(Color.BLACK);
                btn_blocked.setTextColor(Color.BLACK);
                if( currentTab != Constants.TAB_APPROVED) {
                    dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ", true);
                    studentsFilter(Constants.TAB_APPROVED);
                }
            }
        });

        btn_blocked=v.findViewById(R.id.btn_blocked);
        btn_blocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_all.setTextColor(Color.BLACK);
                btn_approved.setTextColor(Color.BLACK);
                btn_unapproved.setTextColor(Color.BLACK);
                btn_rejected.setTextColor(Color.BLACK);
                btn_blocked.setTextColor(Color.WHITE);
                if( currentTab != Constants.TAB_BLOCKED) {
                    dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ", true);
                    studentsFilter(Constants.TAB_BLOCKED);
                }
            }
        });

        btn_unapproved=v.findViewById(R.id.btn_unApproved);
        btn_unapproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_all.setTextColor(Color.BLACK);
                btn_approved.setTextColor(Color.BLACK);
                btn_unapproved.setTextColor(Color.WHITE);
                btn_rejected.setTextColor(Color.BLACK);
                btn_blocked.setTextColor(Color.BLACK);
                if (currentTab != Constants.TAB_UNAPPROVED){
                    dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ", true);
                studentsFilter(Constants.TAB_UNAPPROVED);
            }
            }
        });
        btn_rejected=v.findViewById(R.id.btn_rejected);
        btn_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_all.setTextColor(Color.BLACK);
                btn_approved.setTextColor(Color.BLACK);
                btn_unapproved.setTextColor(Color.BLACK);
                btn_rejected.setTextColor(Color.WHITE);
                btn_blocked.setTextColor(Color.BLACK);
                if(currentTab != Constants.TAB_REJECTED) {
                    dialog = ProgressDialog.show(getActivity(), "Loading ...", "Please wait ", true);
                    studentsFilter(Constants.TAB_REJECTED);
                }
            }
        });

        return v;
    }

    private void loadStudents() {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        Call<Students> call = service.getStudents(Constants.GET_ALL_STDS_FLAG);
        call.enqueue(new Callback<Students>() {
            @Override
            public void onResponse(Call<Students> call, retrofit2.Response<Students> response) {
                try {
                    students = new ArrayList<>();
                    students = response.body().getStudents();

                    adapter = new StudentRecyclerViewAdapter(getActivity(), students);
                    adapter.setOnStateChangedListener(StudentsFragment.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    currentTab=Constants.TAB_ALL;
                    if(dialog!=null && dialog.isShowing()) dialog.dismiss();

                } catch (Exception e) {
                    if(dialog!=null && dialog.isShowing()) dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Students> call, Throwable t) {
                Toast.makeText(getContext(), "There is no student here", Toast.LENGTH_SHORT).show();
                students = new ArrayList<>();
                adapter = new StudentRecyclerViewAdapter(getActivity(), students);
                adapter.setOnStateChangedListener(StudentsFragment.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                if(dialog!=null && dialog.isShowing()) dialog.dismiss();

            }
        });
    }

    private void studentsFilter(final int state) {
        currentTab = state;
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        Call<Students> call = service.studentsFilter(state);
        call.enqueue(new Callback<Students>() {
            @Override
            public void onResponse(Call<Students> call, retrofit2.Response<Students> response) {
                try {
                    students = new ArrayList<>();
                    students = response.body().getStudents();

                    adapter = new StudentRecyclerViewAdapter(getActivity(), students);
                    adapter.setOnStateChangedListener(StudentsFragment.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);


                    if(dialog!=null && dialog.isShowing()) dialog.dismiss();

                } catch (Exception e) {
                    if(dialog!=null && dialog.isShowing()) dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Students> call, Throwable t) {
                Toast.makeText(getContext(), "There is no student here", Toast.LENGTH_SHORT).show();
                students = new ArrayList<>();
                adapter = new StudentRecyclerViewAdapter(getActivity(), students);
                adapter.setOnStateChangedListener(StudentsFragment.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                if(dialog!=null && dialog.isShowing()) dialog.dismiss();

            }
        });
    }

    @Override
    public void onStateChanged(final User std, int state) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.updateStdState(std.getUid(), state);

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {

                if (response.body().getStatus().equals("true")) {
                    Toast.makeText(getActivity(), "State Updated", Toast.LENGTH_SHORT).show();
                    if(currentTab!= Constants.TAB_ALL){
                        students.remove(std);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {

            }
        });
    }
}
