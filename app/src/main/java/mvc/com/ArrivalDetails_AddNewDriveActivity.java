package mvc.com;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mvc.com.dto.DriveDTO_String;

/**
 * Created by zloty on 2018-01-09.
 */

public class ArrivalDetails_AddNewDriveActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {

    private Context mContext;
    private static final String TAG = "ArrivalDetails_AddNewDriveActivity";


    // UI references.
    private View mArrivalDetailsNewDriveFormView;
    private EditText mCityArrivalView;
    private EditText mStreetArrivalView;
    private EditText mExactPlaceArrivalView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewdrive_arrivaldetails);


        mCityArrivalView = findViewById(R.id.city_arrival);
        mStreetArrivalView = findViewById(R.id.street_arrival);
        mExactPlaceArrivalView = findViewById(R.id.exact_place_arrival);

        Button mAcceptArrivalDetailsButton = findViewById(R.id.accept_arrival_details_button);
        mAcceptArrivalDetailsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // odebranie obiektu z poprzedniego kroku formularza
                Intent intent = getIntent();
                DriveDTO_String drive = intent.getParcelableExtra("Drive");

                drive.setCityArrival(mCityArrivalView.getText().toString());
                drive.setStreetArrival(mStreetArrivalView.getText().toString());
                drive.setExactPlaceArrival(mExactPlaceArrivalView.getText().toString());

                Intent nextIntent = new Intent(getApplicationContext(), DriveDetails_AddNewDriveActivity.class);
                nextIntent.putExtra("Drive", drive);

                startActivity(nextIntent);
            }
        });

        mArrivalDetailsNewDriveFormView = findViewById(R.id.arrival_details_new_drive_form);

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
}
