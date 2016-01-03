package ka.fh.crowder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends App {

    User user;
    List<Integer> cursorToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // display back-button
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } finally {
        }

        user = new User(dbConnection, sharedPreferences.getInt("UserID", -1));

        TextView profileName = (TextView) findViewById(R.id.profile_name);
        profileName.setText(user.getName());

        TextView profileCity = (TextView) findViewById(R.id.profile_city);
        profileCity.setText(user.getCity());

        TextView profileCountry = (TextView) findViewById(R.id.profile_country);
        profileCountry.setText(user.getNation());

        TextView projectsTitle = (TextView) findViewById(R.id.profile_project_title);

        final ListView feed = (ListView) findViewById(R.id.profile_listview);
        populateListView(feed);

        if (cursorToList.isEmpty()) {
            projectsTitle.setText("");
        }

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

        Cursor cursor = dbConnection.rawQuery("SELECT ID, Name FROM Project WHERE User = " + user.getId() + " ORDER BY ID DESC", null);
        cursor.moveToFirst();

        List<String> projects = new ArrayList<>();
        cursorToList = new ArrayList<>();

        if (cursor.getCount() > 0) {

            for (int i = 0; i < cursor.getCount(); i++) {
                // add project title to the listarray
                projects.add(cursor.getString(1));

                // save location of DB ID
                cursorToList.add(cursor.getInt(0));

                cursor.moveToNext();
            }
        }

        cursor.close();

        return projects;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
