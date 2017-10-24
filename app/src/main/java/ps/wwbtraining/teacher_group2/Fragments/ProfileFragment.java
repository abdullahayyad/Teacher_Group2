package ps.wwbtraining.teacher_group2.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import ps.wwbtraining.teacher_group2.Models.User;
import ps.wwbtraining.teacher_group2.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);

        EditText et_name = v.findViewById(R.id.t_name);
        EditText et_email = v.findViewById(R.id.t_email);
        EditText et_mobile = v.findViewById(R.id.t_mobile);

        et_name.setText(prefs.getString("name",""));
        et_email.setText(prefs.getString("email",""));
        et_mobile.setText(prefs.getString("mobile",""));

        return v;
    }

   
}
