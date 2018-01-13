package mvc.com;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mvc.com.adapters.CustomDriveAdapter;
import mvc.com.dto.DriveDTO;
import mvc.com.dto.DriveDTO_String;
import mvc.com.dto.SearchDrivesDTO;
import mvc.com.dto.SearchDrivesDTO_String;
import mvc.com.helpers.DateFormatHelper;
import mvc.com.helpers.DriveParser;
import mvc.com.model.DriveModel;

import static mvc.com.helpers.DriveParser.parseDriveDTOString_TO_DriveDTO;

/**
 * Created by zloty on 2018-01-13.
 */

public class SearchDriveActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "SearchDriveActivity";
    SearchDriveActivity activity;



    // UI references
    private View mSearchDriveForm;
    private EditText mStartPlaceView;
    private EditText mArrivalPlaceView;
    private EditText mCostView;
    private DatePicker mDatePickerStartDate;
    private TimePicker mTimePickerStartDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdrive);

        mStartPlaceView = findViewById(R.id.searchdrive_place_start);
        mArrivalPlaceView = findViewById(R.id.searchdrive_place_arrival);
        mCostView = findViewById(R.id.searchdrive_cost);
        mDatePickerStartDate = findViewById(R.id.searchdrive_datePicker_startdate);
        mTimePickerStartDate = findViewById(R.id.searchdrive_timePicker_startdate);


        Button mSearchDriveSubmitButton = findViewById(R.id.searchdrive_submit_button);
        mSearchDriveSubmitButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                SearchDrivesDTO searchDrivesDTO = new SearchDrivesDTO();

                String startDate;
                String startTime;
                String startDateTime;
                int maxCost;

                // parsowanie datepickera na stringa
                DateFormatHelper dateHelper = new DateFormatHelper(mDatePickerStartDate, "yyyy-MM-dd");
                startDate = dateHelper.datepickerToString_DateFormat();
                // parsowanie timepickera na stringa
                DateFormatHelper timeHelper = new DateFormatHelper(mTimePickerStartDate, "HH:mm");
                startTime = timeHelper.timepickerToString_DateFormat();

                startDateTime = startDate + " " + startTime;

                try {
                    String tmp = mCostView.getText().toString();
                    maxCost = tmp.equals("") ? 999999 : Integer.parseInt(tmp);
                } catch (Exception e) {
                    maxCost = 999999;
                }

                searchDrivesDTO.setStartPlace(mStartPlaceView.getText().toString());
                searchDrivesDTO.setArrivalPlace(mArrivalPlaceView.getText().toString());
                searchDrivesDTO.setMaxCost(maxCost);
                searchDrivesDTO.setStartDate(startDateTime);

                SearchDrivesDTO_String searchDrivesDTO_String;
                searchDrivesDTO_String = DriveParser.parseSearchDrivesDTO_TO_SearchDrivesDTOString(searchDrivesDTO);

                Intent intent = new Intent(getApplicationContext(), SearchedDrivesListActivity.class);
                intent.putExtra("SearchDrivesDTO_String", searchDrivesDTO_String);
                startActivity(intent);
            }

        });


        mSearchDriveForm = findViewById(R.id.search_drive_form);
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





    public SearchDriveActivity getActivity() {
        return activity;
    }

    public void setActivity(SearchDriveActivity activity) {
        this.activity = activity;
    }
}
