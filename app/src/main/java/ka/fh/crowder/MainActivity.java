package ka.fh.crowder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends App
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Integer> cursorToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button addProjectButton = (Button) findViewById(R.id.add_project_button);
        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newProjectIntent = new Intent(getApplicationContext(), NewProjectActivity.class);
                startActivityForResult(newProjectIntent, 1);
            }
        });

        final ListView feed = (ListView) findViewById(R.id.feed_list);
        populateListView(feed);

        feed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int projectId = cursorToList.get(position);

                Intent projectIntent = new Intent(getApplicationContext(), ProjectActivity.class);
                projectIntent.putExtra("project", projectId);
                startActivity(projectIntent);
            }
        });
    }

    private void populateListView(ListView list) {
        List values = getProjectList();
        ListAdapter feedAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_view, values);
        list.setAdapter(feedAdapter);
    }

    private List getProjectList() {

        Cursor projCursor = dbConnection.rawQuery("SELECT ID, Name FROM Project ORDER BY ID DESC", null);
        projCursor.moveToFirst();

        List<String> projects = new ArrayList<>();
        cursorToList = new ArrayList<>();

        if (projCursor.getCount() > 0) {

            for (int i = 0; i < projCursor.getCount(); i++) {
                // add project title to the listarray
                projects.add(projCursor.getString(1));

                // save location of DB ID
                cursorToList.add(projCursor.getInt(0));

                projCursor.moveToNext();
            }
        }

        projCursor.close();

        return projects;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            this.finish();
        }
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

        if (id == R.id.nav_search) {

        } else if (id == R.id.nav_feed) {
            Intent feedIntent = new Intent(this, MainActivity.class);
            startActivity(feedIntent);
            this.finish();

        } else if (id == R.id.nav_stats) {

        } else if (id == R.id.nav_profile) {
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);

        } else if (id == R.id.nav_logout) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            sharedPreferences.edit().putBoolean("Login", false).apply();
            startActivity(loginIntent);
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}