package ka.fh.crowder;

import android.database.sqlite.SQLiteDatabase;

public class DbHelper {

    // Connect to DB
    private SQLiteDatabase dbConnection;

    public DbHelper(SQLiteDatabase dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void createAllTables() {
        createTableProject();
        createTableUser();
    }

    public void createTableProject() {

        // dbConnection.execSQL("DROP TABLE IF EXISTS Project");

        // Create table if it doesn't exist
        dbConnection.execSQL("CREATE TABLE IF NOT EXISTS Project(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT, " +
                "Description TEXT, " +
                "Budget REAL, " +
                "Donated REAL," +
                "Date TEXT," +
                "User INTEGER);");
    }

    public void createTableUser() {

        // dbConnection.execSQL("DROP TABLE IF EXISTS User");

        // Create table if it doesn't exist
        dbConnection.execSQL("CREATE TABLE IF NOT EXISTS User(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT, " +
                "Password TEXT," +
                "City TEXT," +
                "Nation TEXT);");
    }
}
