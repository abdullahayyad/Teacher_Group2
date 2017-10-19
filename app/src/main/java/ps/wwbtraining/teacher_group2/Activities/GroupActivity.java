package ps.wwbtraining.teacher_group2.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ps.wwbtraining.teacher_group2.Fragments.AddNewGroupFragment;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.FragmentUtil;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        FragmentUtil.addFragment(this, new AddNewGroupFragment(), R.id.content_group);
    }
}
