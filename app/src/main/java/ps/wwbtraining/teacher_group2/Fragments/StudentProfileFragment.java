package ps.wwbtraining.teacher_group2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ps.wwbtraining.teacher_group2.R;

public class StudentProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_profile, container, false);

        TextView tv_name = v.findViewById(R.id.tv_std_name);
        TextView tv_mobiel = v.findViewById(R.id.tv_std_mobile);
        TextView tv_email = v.findViewById(R.id.tv_std_email);
        TextView tv_state = v.findViewById(R.id.tv_std_state);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int state= (bundle.getInt("state"));
            tv_name.setText(bundle.getString("name"));
            tv_mobiel.setText(bundle.getString("mobile"));
            tv_email.setText(bundle.getString("email"));

            switch (state){
                case 2 :
                    tv_state.setText("Approved");
                    tv_state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_approved_circle, 0, 0, 0); break;
                case 4 :
                    tv_state.setText("Unpproved");
                    tv_state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_unapproved_circle, 0, 0, 0); break;
                case 3:
                    tv_state.setText("Blocked");
                    tv_state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_blocked_circle, 0, 0, 0); break;
                case 5 :
                    tv_state.setText("Rejected");
                    tv_state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rejected_circle, 0, 0, 0); break;

            }
        }

        return v;
    }

}
