package ka.fh.crowder;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DonateActivity extends App {

    // UI reference
    private TextView titleView;
    private TextView moneyLeftView;
    private ProgressBar budgetBar;
    private EditText moneyAmountView;
    private Button payButton;

    // Input Value
    private float moneyDonated;

    private int projectId;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        // display back-button
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } finally {
        }

        Bundle bundle = getIntent().getExtras();
        projectId = bundle.getInt("project");

        project = new Project(dbConnection, projectId);

        titleView = (TextView) findViewById(R.id.donate_title);
        titleView.setText(project.getName());

        moneyLeftView = (TextView) findViewById(R.id.donate_money_left);
        moneyLeftView.setText(moneyLeft(project.getBudget(), project.getDonated()) +
                getString(R.string.project_currency) + " " +
                getString(R.string.donate_money_left));

        budgetBar = (ProgressBar) findViewById(R.id.donate_budget_bar);
        budgetBar.setMax((int) project.getBudget());
        budgetBar.setProgress((int) project.getDonated());

        moneyAmountView = (EditText) findViewById(R.id.donate_amount);


        payButton = (Button) findViewById(R.id.donate_pay_button);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!moneyAmountView.getText().toString().isEmpty()) {
                    moneyDonated = Float.parseFloat(moneyAmountView.getText().toString());
                    if (checkInput()) {
                        saveDonation();
                        closeActivity();
                    }
                }
            }
        });
    }

    private float moneyLeft(float budget, float donated) {
        float money = budget - donated;
        if (money < 0) {
            return 0;
        } else {
            return money;
        }
    }

    private boolean checkInput() {
        if (moneyDonated < 1) {
            moneyAmountView.setError(getString(R.string.error_donate_to_little_money));
            moneyAmountView.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void saveDonation() {
        float donated = project.getDonated() + moneyDonated;
        project.setDonated(donated);
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

    private void closeActivity() {
        setResult(RESULT_OK, null);
        this.finish();
    }
}
