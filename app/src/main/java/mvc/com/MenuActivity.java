package mvc.com;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by zloty on 2018-01-07.
 */

public class MenuActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_menu);

        Button mNewDriveButton = (Button) findViewById(R.id.addNewDrive);
        Button mSearchDriveButton = (Button) findViewById(R.id.searchDrive);
        Button mMyDrivesButton = (Button) findViewById(R.id.myDrives);
        Button mMyBookingsButton = (Button) findViewById(R.id.myBookings);
        Button logout = (Button) findViewById(R.id.logout);

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
