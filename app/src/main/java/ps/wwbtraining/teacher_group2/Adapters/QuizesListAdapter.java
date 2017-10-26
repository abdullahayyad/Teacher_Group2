package ps.wwbtraining.teacher_group2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ps.wwbtraining.teacher_group2.Activities.ShowQuizActivity;
import ps.wwbtraining.teacher_group2.Fragments.GroupsFragment;
import ps.wwbtraining.teacher_group2.Models.CheckedStudents;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.FragmentUtil;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Eman on 10/19/2017.
 */

public class QuizesListAdapter extends RecyclerView.Adapter<QuizesListAdapter.QuizesViewHolder> {


    private final Context context;
    private final ArrayList<Quiz> list;

    public QuizesListAdapter(Context context, ArrayList<Quiz> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public QuizesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_row_design,null,false);
        QuizesViewHolder vh=new QuizesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(QuizesViewHolder holder, int position) {
        Quiz q = list.get(position);
        holder.qName.setText(q.getQuizName());
        holder.qData.setText(q.getQuiz_date());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class QuizesViewHolder extends RecyclerView.ViewHolder {
        TextView qName;
        TextView qData;
        ImageView menueImage;
        PopupMenu pop;

        public QuizesViewHolder(View itemView) {
            super(itemView);
            qName=itemView.findViewById(R.id.quiz_name);
            qData=itemView.findViewById(R.id.quiz_data);
            menueImage=itemView.findViewById(R.id.menu_icon);
            pop= new PopupMenu(context, menueImage, Gravity.START);
            pop.getMenuInflater().inflate(R.menu.quiz_option_menu,pop.getMenu());
            pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.notify:
                            Quiz q=list.get(getAdapterPosition());
                            String notified=q.getNotified();
                            Toast.makeText(context, notified, Toast.LENGTH_SHORT).show();
                            /// show confirmaion dialog
                            q.setNotified(Integer.parseInt(notified)+1+"");
                            // if accepted set notified =1 and update quiz informatiion in date


                            FragmentUtil.replaceFragment(context, new GroupsFragment(), R.id.content);
                            (pop.getMenu().getItem(0)).setTitle("Re notify");
                            setQuizNotified(q.getQid(),q.getNotified());
                            return true;
                        case R.id.delete:
                            list.remove(getAdapterPosition());
                            notifyDataSetChanged();
                            return true;
                        default:
                            return false;       }

                }
            });

            menueImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    pop.show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(context, ShowQuizActivity.class);
                    i.putExtra("quiz_id",list.get(getAdapterPosition()).getQid());
                    context.startActivity(i);
                }
            });
        }


    }

    private void setQuizNotified(int qid, String notified) {
        ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);

        Call<Response_State> call = service.setQuizNotified(qid,notified);

        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, retrofit2.Response<Response_State> response) {


                if (response.body().getStatus().equals("true")) {

                    Toast.makeText(context,  "Quiz Notified ", Toast.LENGTH_LONG).show();
//

                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
