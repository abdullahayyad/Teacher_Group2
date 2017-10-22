package ps.wwbtraining.teacher_group2.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Activities.ShowQuizActivity;
import ps.wwbtraining.teacher_group2.Adapters.QuizPagerAdapter;
import ps.wwbtraining.teacher_group2.Adapters.QuizesListAdapter;
import ps.wwbtraining.teacher_group2.Models.QuestionList;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.Models.QuizesList;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;

public class QuizesFragment extends Fragment {

    RecyclerView recycler;
    private ArrayList<Quiz> quizes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_quizes, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

            }
        });
        recycler=(RecyclerView)v.findViewById(R.id.quizes_list_recycler);

        loadQuizes();
        return v;
    }

    private void loadQuizes() {

            ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

            Call<QuizesList> call = service.getQuizesList();

            call.enqueue(new Callback<QuizesList>() {
                @Override
                public void onResponse(Call<QuizesList> call, retrofit2.Response<QuizesList> response) {


                    if(response.body().getState().getStatus().equals("true")) {
                        quizes = new ArrayList<>();
                        quizes = response.body().getQuezesList();

                        recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        QuizesListAdapter adapter=new QuizesListAdapter(getActivity(),quizes);
                        recycler.setAdapter(adapter);

                    }

                }

                @Override
                public void onFailure(Call<QuizesList> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

                }
            });
    }


}
