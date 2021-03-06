package ps.wwbtraining.teacher_group2.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.iid.FirebaseInstanceId;

import ps.wwbtraining.teacher_group2.Constants;
import ps.wwbtraining.teacher_group2.Fragments.GroupsFragment;
import ps.wwbtraining.teacher_group2.Fragments.HomeFragment;
import ps.wwbtraining.teacher_group2.Fragments.ProfileFragment;
import ps.wwbtraining.teacher_group2.Fragments.QuizesFragment;
import ps.wwbtraining.teacher_group2.Fragments.StudentsFragment;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.FragmentUtil;
import ps.wwbtraining.teacher_group2.Utils.SessionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    int currentFragment;
    SessionManager sessionManager;
    static Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      Log.d("token" , FirebaseInstanceId.getInstance().getToken()+"");
//        SharedPreferences prefs = getSharedPreferences("app", MODE_PRIVATE);
//            String name = prefs.getString("name", "");//"No name defined" is the default value.
//            String idName = prefs.getString("token", ""); //0 is the default value.
//
//        Log.d("ttttttttt",name+idName);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_delete).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);
        return true;
    }


    public static Menu getMenu() {
        return menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            toolbar.setTitle("Home");
            currentFragment = Constants.HOME;
            onCreateOptionsMenu(menu);
            FragmentUtil.replaceFragment(MainActivity.this, new HomeFragment(), R.id.content);

        } else if (id == R.id.nav_profile) {
            currentFragment = Constants.PORFILE;
            toolbar.setTitle("Profile");
            onCreateOptionsMenu(menu);
            FragmentUtil.replaceFragment(MainActivity.this, new ProfileFragment(), R.id.content);

        } else if (id == R.id.nav_students) {
            currentFragment = Constants.STUDENTS;
            toolbar.setTitle("Students");
            onCreateOptionsMenu(menu);
            FragmentUtil.replaceFragment(MainActivity.this, new StudentsFragment(), R.id.content);

        } else if (id == R.id.nav_groups) {
            currentFragment = Constants.GROUPS;
            toolbar.setTitle("Groups");
            onCreateOptionsMenu(menu);
            FragmentUtil.replaceFragment(MainActivity.this, new GroupsFragment(), R.id.content);

        } else if (id == R.id.nav_quiz) {
            currentFragment = Constants.QUIZES;
            toolbar.setTitle("Quizes");
            onCreateOptionsMenu(menu);
            FragmentUtil.replaceFragment(MainActivity.this, new QuizesFragment(), R.id.content);


        } else if (id == R.id.nav_exit) {
            sessionManager.logoutUser();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
