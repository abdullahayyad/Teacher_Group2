package ps.wwbtraining.teacher_group2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Adapters.StudentRecyclerViewAdapter;
import ps.wwbtraining.teacher_group2.Models.Students;
import ps.wwbtraining.teacher_group2.Models.User;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;

public class StudentsFragment extends Fragment {

    ArrayList<User> students = new ArrayList<>();
    StudentRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_students, container, false);

        recyclerView = v.findViewById(R.id.rv_students);
        layoutManager = new LinearLayoutManager(getActivity());

        loadStudents();
        return v;
    }

    private void loadStudents() {

        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        Call<Students> call = service.getStudents();
        call.enqueue(new Callback<Students>() {
            @Override
            public void onResponse(Call<Students> call, retrofit2.Response<Students> response) {

                try {
                    //students = new ArrayList<>();
                    students = response.body().getStudents();
                    adapter = new StudentRecyclerViewAdapter(getActivity(), students);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    students=null;
                }
            }

            @Override
            public void onFailure(Call<Students> call, Throwable t) {
                Toast.makeText(getActivity(),"failed",Toast.LENGTH_LONG).show();
            }
        });
    }

}
