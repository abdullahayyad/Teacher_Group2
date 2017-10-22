package ps.wwbtraining.teacher_group2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ps.wwbtraining.teacher_group2.Models.Question;
import ps.wwbtraining.teacher_group2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowQuesFragment extends Fragment {


    public ShowQuesFragment() {
        // Required empty public constructor
    }

    public static ShowQuesFragment newInstance(Question q) {

        Bundle args = new Bundle();
        args.putSerializable("ques",q);
        ShowQuesFragment fragment = new ShowQuesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_show_ques, container, false);

        Question ques=(Question)getArguments().getSerializable("ques");

        return v;
    }

}
