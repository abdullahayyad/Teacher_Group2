package ps.wwbtraining.teacher_group2.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ps.wwbtraining.teacher_group2.Fragments.ContactFragment;
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
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager=new SessionManager(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            toolbar.setTitle("Home");
            FragmentUtil.replaceFragment(MainActivity.this, new HomeFragment(), R.id.content);

        } else if (id == R.id.nav_profile) {
            toolbar.setTitle("Profile");
            FragmentUtil.replaceFragment(MainActivity.this, new ProfileFragment(), R.id.content);

        } else if (id == R.id.nav_students) {
            toolbar.setTitle("Students");
            FragmentUtil.replaceFragment(MainActivity.this, new StudentsFragment(), R.id.content);

        } else if (id == R.id.nav_groups) {
            toolbar.setTitle("Groups");
            FragmentUtil.replaceFragment(MainActivity.this, new GroupsFragment(), R.id.content);

        } else if (id == R.id.nav_quiz) {
            toolbar.setTitle("Quizes");
            FragmentUtil.replaceFragment(MainActivity.this, new QuizesFragment(), R.id.content);

        } else if (id == R.id.nav_contact) {
            toolbar.setTitle("Contact");
            FragmentUtil.replaceFragment(MainActivity.this, new ContactFragment(), R.id.content);

        } else if (id == R.id.nav_exit) {
            sessionManager.logoutUser();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
