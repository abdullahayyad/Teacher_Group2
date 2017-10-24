package ps.wwbtraining.teacher_group2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ps.wwbtraining.teacher_group2.Fragments.GroupDetailsFragment;
import ps.wwbtraining.teacher_group2.Models.Group;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.FragmentUtil;


public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder> {
    private final List<Group> mValues;

    Context context;

    public GroupRecyclerViewAdapter(Context context, List<Group> groups) {
        this.context = context;
        mValues = groups;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Group group = mValues.get(position);
        holder.group_name.setText(mValues.get(position).getGroup_name());
        holder.group_desc.setText(mValues.get(position).getGroup_desc());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.replaceFragment(context, GroupDetailsFragment.newInstance(group), R.id.content);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView group_name;
        public final TextView group_desc;
        public final ImageView delete;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            group_name = (TextView) view.findViewById(R.id.group_name);
            group_desc = (TextView) view.findViewById(R.id.group_desc);
            delete = (ImageView) view.findViewById(R.id.group_delete);


        }
    }
}
