package ps.wwbtraining.teacher_group2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ps.wwbtraining.teacher_group2.Models.User;
import ps.wwbtraining.teacher_group2.R;


public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.ViewHolder> {
    private final List<User> mValues;

    Context context;

    public StudentRecyclerViewAdapter(Context context, List<User> users) {
        this.context = context;
        mValues = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.student_name.setText(mValues.get(position).getUser_name());
        holder.student_email.setText(mValues.get(position).getUser_email());

        if (mValues.get(position).getState() == 2) {
            holder.student_status.setText("Approved");
            holder.student_status.setTextColor(context.getResources().getColor(R.color.green));
        } else if (mValues.get(position).getState() == 3) {
            holder.student_status.setText("Blocked");
            holder.student_status.setTextColor(context.getResources().getColor(R.color.red));
        } else if (mValues.get(position).getState() == 4) {
            holder.student_status.setText("Unapproved");
            holder.student_status.setTextColor(context.getResources().getColor(R.color.orange));
        } else if (mValues.get(position).getState() == 5) {
            holder.student_status.setText("Rejected");
            holder.student_status.setTextColor(context.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView student_name;
        public final TextView student_email;
        public final TextView student_status;
        public final ImageView image;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            student_name = (TextView) view.findViewById(R.id.student_name);
            student_email = (TextView) view.findViewById(R.id.student_email);
            student_status = (TextView) view.findViewById(R.id.student_status);
            image = (ImageView) view.findViewById(R.id.student_img);
        }
    }
}
