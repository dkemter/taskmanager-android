package se.rejjd.taskmanager.repository.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import se.rejjd.taskmanager.model.Team;
import se.rejjd.taskmanager.repository.TeamRepository;
import se.rejjd.taskmanager.sql.DatabaseContract;
import se.rejjd.taskmanager.sql.WorkItemDbHelper;

public class SqlTeamRepository implements TeamRepository {

    private static SqlTeamRepository instance;

    public static synchronized SqlTeamRepository getInstance(Context context) {
        if(instance == null) {
            instance = new SqlTeamRepository(context);
        }

        return instance;
    }

    private final SQLiteDatabase database;

    private SqlTeamRepository(Context context) {
        database = WorkItemDbHelper.getInstance(context).getWritableDatabase();
    }

    @Override
    public List<Team> getTeams() {
        TeamCursorWrapper cursor = queryTeams(null, null);

        List<Team> teams = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                teams.add(cursor.getTeam());
            }
        }
        cursor.close();
        return teams;
    }

    @Override
    public Team getTeam(String id) {
        TeamCursorWrapper cursor = queryTeams(DatabaseContract.ModelEntry._ID + " = ?", new String[]{id});
        if(cursor.getCount() > 0){
            Team team = cursor.getFirstTeam();
            cursor.close();
            return team;

        }
        cursor.close();
        return null;
    }

    @Override
    public Long addTeam(Team team) {
        //        TODO: IF PERSIST
        ContentValues cv = getContentValues(team);
        return database.insert(DatabaseContract.ModelEntry.TEAM_TABLE_NAME, null, cv);
    }



    private ContentValues getContentValues(Team team) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.ModelEntry.TEAM_COLUMN_NAME_TEAM_NAME, team.getTeamName());
        cv.put(DatabaseContract.ModelEntry.TEAM_COLUMN_NAME_ACTIVE_TEAM, team.isActiveTeam());

        return cv;
    }

    private TeamCursorWrapper queryTeams(String where, String[] whereArg) {
        @SuppressLint("Recycle")
        Cursor cursor = database.query(DatabaseContract.ModelEntry.TEAM_TABLE_NAME,
                null,
                where,
                whereArg,
                null,
                null,
                null
        );
        return new TeamCursorWrapper(cursor);
    }
}