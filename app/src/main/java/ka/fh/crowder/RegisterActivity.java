package ka.fh.crowder;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends App {

    // UI reference
    private EditText nameView;
    private EditText passwordView;
    private EditText passwordAgainView;
    private EditText cityView;
    private EditText nationView;

    private Button submitButton;

    // Input Values
    private String name;
    private String password;
    private String passwordAgain;
    private String city;
    private String nation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // display back-button
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } finally {}

        submitButton = (Button) findViewById(R.id.register_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
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

    private void getInputValues() {
        nameView = (EditText) findViewById(R.id.register_username);
        name = nameView.getText().toString();

        passwordView = (EditText) findViewById(R.id.register_password);
        password = passwordView.getText().toString();

        passwordAgainView  = (EditText) findViewById(R.id.register_password_again);
        passwordAgain = passwordAgainView.getText().toString();

        cityView = (EditText) findViewById(R.id.register_city);
        city = cityView.getText().toString();

        nationView = (EditText) findViewById(R.id.register_nation);
        nation = nationView.getText().toString();
    }

    private boolean checkInputValues() {
        if (!checkName()) {
            nameView.requestFocus();
            return false;
        }
        else if (!checkPassword()) {
            passwordView.requestFocus();
            passwordView.setText("");
            passwordAgainView.setText("");
            return false;
        }
        else if (!checkSimilar(password, passwordAgain)){
            passwordView.requestFocus();
            passwordView.setText("");
            passwordAgainView.setText("");
            return false;
        }
        else if (!checkCity()) {
            cityView.requestFocus();
            return false;
        }
        else if (!checkNation()) {
            nationView.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkName() {
        if (name.isEmpty()) {
            nameView.setError(getString(R.string.error_field_required));
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkPassword() {
        if (password.isEmpty()) {
            nameView.setError(getString(R.string.error_field_required));
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkCity() {
        return true;
    }

    private boolean checkNation() {
        return true;
    }

    private boolean checkSimilar(String p1, String p2) {
        if (p1.equals(p2)) {
            return true;
        }
        else {
            passwordView.setError(getString(R.string.error_register_password_not_similar));
            return false;
        }
    }

    private void saveValues() {
        new User(dbConnection, name, password, city, nation);
    }

    private void closeActivity() {
        this.finish();
    }
}
