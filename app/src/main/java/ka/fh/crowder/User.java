package ka.fh.crowder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class User {

    private int id;
    private String name;
    private String password;
    private String city;
    private String nation;

    private SQLiteDatabase dbConnection;

    public User(SQLiteDatabase dbConnection, int id) {
        this.dbConnection = dbConnection;
        this.id = id;

        getUserData();
    }

    public User(SQLiteDatabase dbConnection, String name, String password, String city, String nation) {
        this.name = name;
        this.password = password;
        this.city = city;
        this.nation = nation;
        this.dbConnection = dbConnection;

        dbConnection.execSQL("INSERT INTO User (Name, Password, City, Nation) VALUES('" + name + "', '" + password + "', '" + city + "', '" + nation + "');");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public String getNation() {
        return nation;
    }

    public void setName(String name) {
        dbConnection.execSQL("UPDATE User SET Name = " + name + " WHERE ID =" + id + ";");
        this.name = name;
    }

    public void setPassword(String password) {
        dbConnection.execSQL("UPDATE User SET Password = " + password + " WHERE ID =" + id + ";");
        this.password = password;
    }

    public void setCity(String city) {
        dbConnection.execSQL("UPDATE User SET City = " + city + " WHERE ID =" + id + ";");
        this.city = city;
    }

    public void setNation(String nation) {
        dbConnection.execSQL("UPDATE User SET Nation = " + nation + " WHERE ID =" + id + ";");
        this.nation = nation;
    }

    private void getUserData() {
        Cursor cursor = dbConnection.rawQuery("SELECT Name, Password, City, Nation FROM User WHERE ID = " + id, null);
        cursor.moveToFirst();

        name = cursor.getString(0);
        password = cursor.getString(1);
        city = cursor.getString(2);
        nation = cursor.getString(3);

        cursor.close();
    }
}
