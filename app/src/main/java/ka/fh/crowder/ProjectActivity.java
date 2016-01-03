package ka.fh.crowder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProjectActivity extends App {

    // UI references
    private TextView userNameView;
    private TextView titleView;
    private TextView descriptionView;
    private TextView budgetView;
    private TextView donatedView;
    private ProgressBar budgetBarView;
    private TextView budgetPercentView;
    private TextView dateView;
    private Button donateButton;

    private int projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        // display back-button
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } finally {
        }

        Bundle bundle = getIntent().getExtras();
        projectId = bundle.getInt("project");

        Project project = new Project(dbConnection, projectId);

        userNameView = (TextView) findViewById(R.id.project_username);
        userNameView.setText(project.getUsername());

        userNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileIntent);
            }
        });

        titleView = (TextView) findViewById(R.id.project_title);
        titleView.setText(project.getName());

        descriptionView = (TextView) findViewById(R.id.project_description);
        descriptionView.setText(project.getDescription());

        budgetView = (TextView) findViewById(R.id.project_budget);
        budgetView.setText(String.valueOf(project.getBudget()) + getString(R.string.project_currency));

        donatedView = (TextView) findViewById(R.id.project_donated);
        donatedView.setText(String.valueOf(project.getDonated()) + getString(R.string.project_currency));

        budgetBarView = (ProgressBar) findViewById(R.id.project_budget_bar);
        budgetBarView.setMax((int) project.getBudget());
        budgetBarView.setProgress((int) project.getDonated());

        budgetPercentView = (TextView) findViewById(R.id.project_budget_percent);
        budgetPercentView.setText(getBudgetPercent(project.getBudget(), project.getDonated()) + "%");

        dateView = (TextView) findViewById(R.id.project_date);
        dateView.setText(project.getDate());

        donateButton = (Button) findViewById(R.id.donate_button);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateIntent = new Intent(getApplicationContext(), DonateActivity.class);
                donateIntent.putExtra("project", projectId);
                startActivityForResult(donateIntent, 1);
            }
        });

    }

    private int getBudgetPercent(float budget, float donated) {
        return (int) ((donated / budget) * 100);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Intent refresh = new Intent(this, ProjectActivity.class);
            refresh.putExtra("project", projectId);
            startActivity(refresh);
            this.finish();
        }
    }
}
