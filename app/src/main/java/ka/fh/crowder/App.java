package ka.fh.crowder;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class App extends AppCompatActivity {

        protected SQLiteDatabase dbConnection;
        protected SharedPreferences sharedPreferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            dbConnection = openOrCreateDatabase("Crowder", MODE_PRIVATE, null);

            sharedPreferences = this.getSharedPreferences("ka.fh.crowder", MODE_PRIVATE);
        }

        @Override
        protected void onPause() {
            dbConnection.close();

            super.onPause();
        }

        @Override
        protected void onResume() {
            super.onResume();

            dbConnection = openOrCreateDatabase("Crowder", MODE_PRIVATE, null);
        }
}
