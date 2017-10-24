package ps.wwbtraining.teacher_group2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ps.wwbtraining.teacher_group2.Activities.GroupActivity;
import ps.wwbtraining.teacher_group2.Models.Group;
import ps.wwbtraining.teacher_group2.R;


public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>  {
    List<Group> mValues;

    Context context;


    public GroupRecyclerViewAdapter(Context context, List<Group> groups) {
        this.context = context;
        mValues = groups;
    }

    GroupRecyclerViewAdapter() {
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

//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, GroupActivity.class);
                intent.putExtra("group",group);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public final View mView;
        public final TextView group_name;
        public final TextView group_desc;
      //  public final ImageView delete;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            group_name = (TextView) view.findViewById(R.id.group_name);
            group_desc = (TextView) view.findViewById(R.id.group_desc);
            //delete = (ImageView) view.findViewById(R.id.group_delete);
            view.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this); //REGISTER ONCREATE MENU LISTENER

        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE, 1, 1, "Delete");
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        //ADD AN ONMENUITEM LISTENER TO EXECUTE COMMANDS ONCLICK OF CONTEXT MENU TASK
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        };




}
    }