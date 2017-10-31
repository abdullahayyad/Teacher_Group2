package ps.wwbtraining.teacher_group2.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import ps.wwbtraining.teacher_group2.Activities.LoginActivity;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.Models.User;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.SessionManager;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;
import static ps.wwbtraining.teacher_group2.Activities.MainActivity.getMenu;

public class ProfileFragment extends Fragment {

    SessionManager sessionManager;
    EditText et_name, et_email, et_mobile;
    int uid;
    String name, mobile, email;
    Menu menu;
    Dialog dialog;
    EditText et_current, et_new_pass, et_confirm_new_pass;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        menu = getMenu();

        sessionManager = new SessionManager(getContext());
        uid = Integer.parseInt(sessionManager.getUserDetails().get(SessionManager.KEY_ID));

        et_name = v.findViewById(R.id.t_name);
        et_email = v.findViewById(R.id.t_email);
        et_mobile = v.findViewById(R.id.t_mobile);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading ...");

        v.findViewById(R.id.fab_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.setEnabled(true);
                et_email.setEnabled(true);
                et_mobile.setEnabled(true);
                menu.findItem(R.id.action_save).setVisible(true);
                menu.findItem(R.id.action_edit).setVisible(false);
                menu.findItem(R.id.action_delete).setVisible(false);

            }
        });

        menu.findItem(R.id.action_save).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                name = et_name.getText().toString().trim();
                mobile = et_mobile.getText().toString().trim();
                email = et_email.getText().toString().trim();

                if (!TextUtils.isEmpty(et_name.getText()) && !TextUtils.isEmpty(et_mobile.getText()) && !TextUtils.isEmpty(et_email.getText())) {
                    updateProfile(uid, name, mobile, email);
                }else{
                    Toast.makeText(getActivity(), "You should fill all fields", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        et_name.setText(sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
        et_email.setText(sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL));
        et_mobile.setText(sessionManager.getUserDetails().get(SessionManager.KEY_MOBILE));

        v.findViewById(R.id.btn_change_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.change_password_dialog);

                dialog.setTitle("Change Password");

                et_current = dialog.findViewById(R.id.current_pass);
                et_new_pass = dialog.findViewById(R.id.new_pass);
                et_confirm_new_pass = dialog.findViewById(R.id.confirm_new_pass);

                dialog.show();

                Button save = dialog.findViewById(R.id.save);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        progressDialog.show();

                        String old_pass = et_current.getText().toString().trim();
                        String new_pass = et_new_pass.getText().toString().trim();
                        String confirm_new_pass = et_confirm_new_pass.getText().toString().trim();

                        if(old_pass.equals(sessionManager.getUserDetails().get(SessionManager.KEY_PASSWORD))
                                && new_pass.equals(confirm_new_pass)) {
                            updatePassword(uid, new_pass);
                        }
                    }
                });


            }
        });


        return v;
    }

    private void updateProfile(int uid, String username, String mobile, String email) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        Call<Response_State> call = service.updateProfile(uid, username, mobile, email);
        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {
                if (response.body().getStatus().equals("true")) {
                    Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePassword(int uid, String user_password) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        Call<Response_State> call = service.updatePassword(uid, user_password);
        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {
                if (response.body().getStatus().equals("true")) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Password updated", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                progressDialog.dismiss();
                dialog.dismiss();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
