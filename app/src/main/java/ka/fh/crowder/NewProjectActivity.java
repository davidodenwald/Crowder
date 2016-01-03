package ka.fh.crowder;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewProjectActivity extends App {

    // UI references
    private Button createButton;
    private EditText nameView;
    private EditText descriptionView;
    private EditText budgetView;
    private EditText dateView;

    // Input values
    private String name;
    private String description;
    private float budget;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        // display back-button
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } finally {
        }

        createButton = (Button) findViewById(R.id.new_project_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputValues();
                if (checkInputValues()) {
                    saveValues();
                    closeActivity();
                }
            }
        });
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

    private boolean checkInputValues() {
        if (!checkName()) {
            nameView.requestFocus();
            return false;
        } else if (!checkDescription()) {
            descriptionView.requestFocus();
            return false;
        } else if (!checkBudget()) {
            budgetView.requestFocus();
            return false;
        } else if (!checkDate()) {
            dateView.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkName() {
        if (name.length() < 5) {
            nameView.setError(getString(R.string.error_project_name_to_short));
            return false;
        } else if (name.length() > 20) {
            nameView.setError(getString(R.string.error_project_name_to_long));
            return false;
        } else {
            return true;
        }

    }

    private boolean checkDescription() {
        if (description.length() < 10) {
            descriptionView.setError(getString(R.string.error_project_description_to_short));
            return false;
        } else {
            return true;
        }
    }

    private boolean checkBudget() {
        if (budget < 10) {
            budgetView.setError(getString(R.string.error_budget_to_low));
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDate() {
        if (date.isEmpty()) {
            dateView.setError(getString(R.string.error_date_empty));
            return false;
        } else {
            return true;
        }
    }

    private void getInputValues() {
        nameView = (EditText) findViewById(R.id.new_project_name);
        name = nameView.getText().toString();

        descriptionView = (EditText) findViewById(R.id.new_project_description);
        description = descriptionView.getText().toString();

        budgetView = (EditText) findViewById(R.id.new_project_budget);
        if (!budgetView.getText().toString().isEmpty()) {
            budget = Float.parseFloat(budgetView.getText().toString());
        } else {
            budget = 0;
        }

        dateView = (EditText) findViewById(R.id.new_project_date);
        date = dateView.getText().toString();
    }

    private void saveValues() {
        new Project(dbConnection, name, description, budget, date, sharedPreferences.getInt("UserID", -1));
    }

    private void closeActivity() {
        setResult(RESULT_OK, null);
        this.finish();
    }
}
