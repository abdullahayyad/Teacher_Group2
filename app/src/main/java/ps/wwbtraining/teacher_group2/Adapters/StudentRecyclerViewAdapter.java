package ps.wwbtraining.teacher_group2.Adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ps.wwbtraining.teacher_group2.Fragments.GroupsFragment;
import ps.wwbtraining.teacher_group2.Models.Quiz;
import ps.wwbtraining.teacher_group2.Models.User;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.FragmentUtil;

import static android.R.id.list;


public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.ViewHolder> {
    private final List<User> mValues;
    boolean[] checked;
    Context context;
    OnStateChangedListener listener;
 int [] colors={R.drawable.ic_approved_circle,R.drawable.ic_blocked_circle,R.drawable.ic_unapproved_circle,R.drawable.ic_rejected_circle};
    public StudentRecyclerViewAdapter(Context context, List<User> users,boolean[] checked) {
        this.context = context;
        this.checked = checked;
        mValues = users;
    }

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

        holder.state_img.setImageResource(colors[mValues.get(position).getState()-2]);

        if(checked == null) {
            holder.checkBox.setVisibility(View.GONE);
        }else {
            holder.checkBox.setChecked(checked[position]);
            holder.checkBox.setVisibility(View.VISIBLE);

        }
/*
        if (mValues.get(position).getState() == 2) {
            holder.student_status.setText("Approved");
            holder.student_status.setTextColor(context.getResources().getColor(R.color.green));
        } else if (mValues.get(position).getState() == 3) {
            holder.student_status.setText("Blocked");
            holder.student_status.setTextColor(context.getResources().getColor(R.color.orange));
        } else if (mValues.get(position).getState() == 4) {
            holder.student_status.setText("Unapproved");
            holder.student_status.setTextColor(context.getResources().getColor(R.color.gray));
        } else if (mValues.get(position).getState() == 5) {
            holder.student_status.setText("Rejected");
            holder.student_status.setTextColor(context.getResources().getColor(R.color.red));
        }
        */
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView student_name;
        public final TextView student_email;
        //public final TextView student_status;
        public final CheckBox checkBox;
        public final ImageView image;
        ImageView state_img;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            student_name = (TextView) view.findViewById(R.id.student_name);
            student_email = (TextView) view.findViewById(R.id.student_email);
            //student_status = (TextView) view.findViewById(R.id.student_status);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            image = (ImageView) view.findViewById(R.id.student_img);
            state_img = (ImageView) view.findViewById(R.id.student_status);

            final PopupMenu pop = new PopupMenu(context, state_img, Gravity.START);
            pop.getMenuInflater().inflate(R.menu.std_state_menu,pop.getMenu());
            pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int state;
                    switch (item.getItemId()) {
                        case R.id.menu_approved:
                            state=2;
                            state_img.setImageResource(colors[0]);
                            if(listener!=null){
                                listener.onStateChanged(mValues.get(getAdapterPosition()).getUid(),state);
                            }
                            return true;
                        case R.id.menu_unapproved:
                            state=4;
                            state_img.setImageResource(colors[2]);
                            if(listener!=null){
                                listener.onStateChanged(mValues.get(getAdapterPosition()).getUid(),state);
                            }
                            return true;
                        case R.id.menu_blocked:
                            state=3;
                            state_img.setImageResource(colors[1]);
                            if(listener!=null){
                                listener.onStateChanged(mValues.get(getAdapterPosition()).getUid(),state);
                            }
                            return true;
                        case R.id.menu_rejected:
                            state=5;
                            state_img.setImageResource(colors[3]);
                            if(listener!=null){
                                listener.onStateChanged(mValues.get(getAdapterPosition()).getUid(),state);
                            }
                            return true;
                        default:
                            return false;
                    }

                }
            });

            state_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop.show();
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        checked[getAdapterPosition()]=true;
                    }
                    else{
                        checked[getAdapterPosition()]=false;
                    }
                }
            });
        }
    }

    public interface OnStateChangedListener{
        public void onStateChanged(int sid,int state);
    }

    public void setOnStateChangedListener(OnStateChangedListener listener){
        this.listener=listener;
    }
}
