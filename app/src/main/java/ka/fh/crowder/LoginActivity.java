package ka.fh.crowder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends App {


    // UI references
    private EditText usernameView;
    private EditText passwordView;

    private Button loginButton;
    private Button registerButton;

    // Input Values
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DbHelper dbHelper = new DbHelper(dbConnection);
        dbHelper.createAllTables();

        if (sharedPreferences.getBoolean("Login", false)) {
            login();
        }

        usernameView = (EditText) findViewById(R.id.login_username);
        passwordView = (EditText) findViewById(R.id.login_password);

        loginButton = (Button) findViewById(R.id.login_submit);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValues()) {
                    login();
                }
            }
        });

        registerButton = (Button) findViewById(R.id.login_register);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }


    private boolean checkValues() {
        username = usernameView.getText().toString();
        password = passwordView.getText().toString();

        if (!checkUsername()) {
            usernameView.requestFocus();
            return false;
        }
        else if (!checkPassword()) {
            passwordView.requestFocus();
            return false;
        }
        else {
            Cursor cursor = dbConnection.rawQuery("SELECT ID FROM User WHERE Name = '" + username + "';", null);
            cursor.moveToFirst();
            sharedPreferences.edit().putInt("UserID", cursor.getInt(0)).apply();
            sharedPreferences.edit().putBoolean("Login", true).apply();
            return true;
        }
    }

    private boolean checkUsername() {
        if (username.isEmpty()) {
            usernameView.setError(getString(R.string.error_field_required));
            return false;
        }
        else {
            Cursor cursor = dbConnection.rawQuery("SELECT Name FROM User WHERE Name = '" + username  + "';", null);
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                usernameView.setError(getString(R.string.error_invalid_username));
                return false;
            }
            else {
                return true;
            }
        }
    }

    private boolean checkPassword() {
        if (password.isEmpty()) {
            passwordView.setError(getString(R.string.error_field_required));
            return false;
        }
        else {
            Cursor cursor = dbConnection.rawQuery("SELECT Password FROM User WHERE Name = '" + username + "';", null);
            cursor.moveToFirst();
            if (!password.equals(cursor.getString(0))) {
                passwordView.setError(getString(R.string.error_incorrect_password));
                return false;
            }
            else {
                return true;
            }
        }
    }

    private void login() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        this.finish();
    }
}

