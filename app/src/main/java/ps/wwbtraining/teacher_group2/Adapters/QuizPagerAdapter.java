package ps.wwbtraining.teacher_group2.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Fragments.ShowQuesFragment;
import ps.wwbtraining.teacher_group2.Models.Question;

/**
 * Created by Eman on 10/22/2017.
 */

public class QuizPagerAdapter extends FragmentPagerAdapter{

    ArrayList<Question> questions;

    public QuizPagerAdapter(FragmentManager fm, ArrayList<Question>questions) {
        super(fm);
        this.questions=questions;
    }

    @Override
    public ShowQuesFragment getItem(int position) {

        return ShowQuesFragment.newInstance(questions.get(position));
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}
