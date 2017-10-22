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

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Adapters.QuizesListAdapter;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.R;

public class QuizesFragment extends Fragment {

    RecyclerView recycler;
    private ArrayList<Quiz> list;

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
        recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
        list=new ArrayList<>();
        list.add(new Quiz("Q1","","1/1/2012","1"));
        list.add(new Quiz("Q2","","1/1/2012","0"));
        list.add(new Quiz("Q3","","1/1/2012","0"));
        list.add(new Quiz("Q1","","1/1/2012","0"));
        list.add(new Quiz("Q1","","1/1/2012","0"));
        list.add(new Quiz("Q1","","1/1/2012","0"));
        list.add(new Quiz("Q1","","1/1/2012","0"));
        list.add(new Quiz("Q1","","1/1/2012","0"));
        list.add(new Quiz("Q1","","1/1/2012","0"));
        list.add(new Quiz("Q1","","1/1/2012","0"));
        list.add(new Quiz("Q1","","1/1/2012","0"));

        QuizesListAdapter adapter=new QuizesListAdapter(getActivity(),list);
        recycler.setAdapter(adapter);

        return v;
    }



}
