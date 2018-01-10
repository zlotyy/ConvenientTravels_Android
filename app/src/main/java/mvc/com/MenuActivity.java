package mvc.com;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by zloty on 2018-01-07.
 */

public class MenuActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private Context mContext;
    private static final String TAG = "MenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button mNewDriveButton = (Button) findViewById(R.id.addNewDrive);
        mNewDriveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNewDriveActivity.class);
                startActivity(intent);
            }
        });

        Button mSearchDriveButton = (Button) findViewById(R.id.searchDrive);

        Button mMyDrivesButton = (Button) findViewById(R.id.myDrives);
        mMyDrivesButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyDrivesListActivity.class);
                startActivity(intent);
            }
        });

        Button mMyBookingsButton = (Button) findViewById(R.id.myBookings);

        Button mExitButton = (Button) findViewById(R.id.exit_button);
        mExitButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                attemptLogout();
                closeApp();
            }
        });

        Button mLogoutButton = (Button) findViewById(R.id.logout);
        mLogoutButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                attemptLogout();
                finish();
            }
        });

    }

    private void attemptLogout(){
        // todo: dodac wylogowanie po stronie serwera

        // usuniecie userId i tokena z SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("appPrefs", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();

        Log.i(TAG, "Wylogowano. SharedPreferences: UserId = " + sharedPref.getString("userId", "")
                + " Token = " + sharedPref.getString("token", ""));
    }


    private void closeApp(){
        finish();
        getParent().finish();
        System.exit(0);
        Log.i(TAG, "Zamknieto aplikacje");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }



    protected void onPostExecute(final Boolean success) {

        if (success) {
            //finish();
        } else {

        }
    }

    protected void onCancelled() {

    }
}
