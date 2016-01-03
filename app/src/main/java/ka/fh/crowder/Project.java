package ka.fh.crowder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Project {

    SQLiteDatabase dbConnection;

    private int id;
    private String name;
    private String description;
    private float budget;
    private float donated;
    private String date;
    private int userId;

    public Project(SQLiteDatabase dbConnection, int id) {
        this.dbConnection = dbConnection;
        this.id = id;

        getProjectData();
    }

    public Project(SQLiteDatabase dbConnection, String name, String description, float budget, String date, int userId) {
        this.dbConnection = dbConnection;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.date = date;
        this.userId = userId;

        dbConnection.execSQL("INSERT INTO Project (Name, Description, Budget, Date, User) VALUES('" + name + "', '" + description + "', " + budget + ", '" + date + "', " + userId + ");");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getBudget() {
        return budget;
    }

    public float getDonated() {
        return donated;
    }

    public String getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        Cursor cursor = dbConnection.rawQuery("SELECT Name FROM User WHERE ID = " + userId + ";", null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public void setName(String name) {
        dbConnection.execSQL("UPDATE Project SET Name = " + name + " WHERE ID =" + id + ";");
        this.name = name;
    }

    public void setDescription(String description) {
        dbConnection.execSQL("UPDATE Project SET Description = " + description + " WHERE ID =" + id + ";");
        this.description = description;
    }

    public void setBudget(float budget) {
        dbConnection.execSQL("UPDATE Project SET Budget = " + budget + " WHERE ID =" + id + ";");
        this.budget = budget;
    }

    public void setDonated(float donated) {
        dbConnection.execSQL("UPDATE Project SET Donated = " + donated + " WHERE ID =" + id + ";");
        this.donated = donated;
    }

    public void setDate(String date) {
        dbConnection.execSQL("UPDATE Project SET Date = " + date + " WHERE ID =" + id + ";");
        this.date = date;
    }

    private void getProjectData() {
        Cursor cursor = dbConnection.rawQuery("SELECT Name, Description, Budget, Donated, Date, User FROM Project WHERE ID = " + id, null);
        cursor.moveToFirst();

        name = cursor.getString(0);
        description = cursor.getString(1);
        budget = cursor.getFloat(2);
        donated = cursor.getFloat(3);
        date = cursor.getString(4);
        userId = cursor.getInt(5);

        cursor.close();
    }
}
