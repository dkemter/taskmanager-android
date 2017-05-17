package se.rejjd.taskmanager.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IssuesDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "taskmanager_database";
    private static final int DB_VERSION = 1;
    private static IssuesDbHelper instance;

    public IssuesDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized IssuesDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new IssuesDbHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseQueries.CREATE_TABLE_ISSUES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseQueries.DROP_TABLE_ISSUES);
        onCreate(db);
    }
}