package ps.wwbtraining.teacher_group2.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ps.wwbtraining.teacher_group2.Activities.MainActivity;
import ps.wwbtraining.teacher_group2.Fragments.GroupDetailsFragment;
import ps.wwbtraining.teacher_group2.Models.Group;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.FragmentUtil;


public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder> {
    private final List<Group> mValues;

    Context context;
    private ActionMode mActionMode;

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


        public ViewHolder(View view) {
            super(view);
            mView = view;
            group_name = (TextView) view.findViewById(R.id.group_name);
            group_desc = (TextView) view.findViewById(R.id.group_desc);


            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mActionMode != null) {
                        return false;
                    }

                    // Start the CAB using the ActionMode.Callback defined above
                    mActionMode = ((AppCompatActivity)context).startSupportActionMode(mActionModeCallback);
                    ((AppCompatActivity)context).findViewById(R.id.toolbar).setVisibility(View.GONE);
                    return true;
                }
            });

        }
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.delete_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                    mode.finish(); // Action picked, so close the CAB

                            ((AppCompatActivity)context).findViewById(R.id.toolbar).setVisibility(View.VISIBLE);



                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
       ((AppCompatActivity)context).findViewById(R.id.toolbar).setVisibility(View.VISIBLE);

        }
    };

}
