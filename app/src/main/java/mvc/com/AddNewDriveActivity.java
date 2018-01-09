package mvc.com;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

import mvc.com.dto.DriveDTO_String;
import mvc.com.helpers.DateFormatHelper;

/**
 * Created by zloty on 2018-01-08.
 */

public class AddNewDriveActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private static final String TAG = "AddNewDriveActivity";


    // UI references.
    private View mNewDriveFormView;
    private EditText mCityStartView;
    private EditText mStreetStartView;
    private EditText mExactPlaceStartView;
    private TextView mDateStartLabel;
    private DatePicker mDatePickerStartDate;
    private TimePicker mTimePickerStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewdrive);

        mCityStartView = findViewById(R.id.city_start);
        mStreetStartView = findViewById(R.id.street_start);
        mExactPlaceStartView = findViewById(R.id.exact_place_start);
        mDateStartLabel = findViewById(R.id.date_start_label);
        mDatePickerStartDate = findViewById(R.id.datePicker_startdate);
        mTimePickerStartDate = findViewById(R.id.timePicker_startdate);

        Button mAcceptStartDetailsButton = findViewById(R.id.accept_start_details_button);
        mAcceptStartDetailsButton.setOnClickListener(new OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // tworzenie obiektu ktory bedzie wyslany do kolejnego kroku formularza
                DriveDTO_String drive = new DriveDTO_String();
                String startDate;
                String startTime;
                String startDateTime;

                // parsowanie datepickera na stringa
                DateFormatHelper dateHelper = new DateFormatHelper(mDatePickerStartDate, "yyyy-MM-dd");
                startDate = dateHelper.datepickerToString_DateFormat();

                // parsowanie timepickera na stringa
                DateFormatHelper timeHelper = new DateFormatHelper(mTimePickerStartDate, "HH:mm");
                startTime = timeHelper.timepickerToString_DateFormat();

                startDateTime = startDate + " " + startTime;

                drive.setCityStart(mCityStartView.getText().toString());
                drive.setStreetStart(mStreetStartView.getText().toString());
                drive.setExactPlaceStart(mExactPlaceStartView.getText().toString());
                drive.setStartDate(startDateTime);


                Intent intent = new Intent(getApplicationContext(), ArrivalDetails_AddNewDriveActivity.class);
                intent.putExtra("Drive", drive);

                startActivity(intent);
            }
        });

        mNewDriveFormView = findViewById(R.id.new_drive_form);

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
