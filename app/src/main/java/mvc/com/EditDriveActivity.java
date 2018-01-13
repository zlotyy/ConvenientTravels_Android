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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import mvc.com.dto.DriveDTO;
import mvc.com.dto.DriveDTO_String;
import mvc.com.helpers.DateFormatHelper;

import static mvc.com.helpers.DriveParser.parseDriveDTOString_TO_DriveDTO;

/**
 * Created by zloty on 2018-01-11.
 */

public class EditDriveActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "EditDriveActivity";
    DriveDTO_String drive;
    Long driveId;
    EditDriveActivity activity;


    // UI references.
    private View mNewDriveFormView;
    private EditText mCityStartView;
    private EditText mStreetStartView;
    private EditText mExactPlaceStartView;
    private TextView mDateStartLabel;
    private DatePicker mDatePickerStartDate;
    private TimePicker mTimePickerStartDate;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdrive);


        mCityStartView = findViewById(R.id.editdrive_city_start);
        mStreetStartView = findViewById(R.id.editdrive_street_start);
        mExactPlaceStartView = findViewById(R.id.editdrive_exact_place_start);
        mDateStartLabel = findViewById(R.id.editdrive_date_start_label);
        mDatePickerStartDate = findViewById(R.id.editdrive_datePicker_startdate);
        mTimePickerStartDate = findViewById(R.id.editdrive_timePicker_startdate);

        Intent intent = getIntent();
        drive = intent.getParcelableExtra("Drive");
        driveId = intent.getLongExtra("DriveId", -1);

        if(driveId == -1){
            Log.i("TAG", "drive = null. Cos poszlo nie tak");

            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(getApplicationContext(), "Błąd podczas wyświetlania danych przejazdu", duration);
            toast.setGravity(Gravity.TOP, 0, 150);
            toast.show();
        } else {
            Log.i("TAG", "Poprawnie wyswietlono szczegoly przejazdu");

            Calendar startDateTime = Calendar.getInstance();
            String startDateTime_String = drive.getStartDate();

            if(startDateTime_String != null){
                DateFormatHelper dateFormatHelper = new DateFormatHelper(startDateTime_String, "yyyy-MM-dd HH:mm");
                startDateTime = dateFormatHelper.stringToCalendar_DateTimeFormat();

                mDatePickerStartDate.updateDate(startDateTime.get(Calendar.YEAR), startDateTime.get(Calendar.MONTH) - 1, startDateTime.get(Calendar.DATE));
                mTimePickerStartDate.setHour(startDateTime.get(Calendar.HOUR_OF_DAY));
                mTimePickerStartDate.setMinute(startDateTime.get(Calendar.MINUTE));
            }
            mCityStartView.setText(drive.getCityStart());
            mStreetStartView.setText(drive.getStreetStart());
            mExactPlaceStartView.setText(drive.getStreetStart());
        }


        Button mEditDriveAcceptStartDetailsButton = findViewById(R.id.editdrive_accept_start_details_button);
        mEditDriveAcceptStartDetailsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
                drive.setStartDate(startDate);

                // todo: NAPRAWIC BLEDY NA SZCZEGOLACH PRZEJAZDU (NULLE CHYBA)

                DriveDTO driveDTO = parseDriveDTOString_TO_DriveDTO(drive);

                new EditDriveTask(activity, driveDTO, driveId, getApplicationContext()).execute();
            }
        });


        mNewDriveFormView = findViewById(R.id.editdrive_drive_form);

    }





    public class EditDriveTask extends AsyncTask<Void, Void, Boolean> {

        EditDriveActivity activity;
        private Long driveId;
        private final DriveDTO driveDTO;
        private Context mContext;

        EditDriveTask(EditDriveActivity activity, DriveDTO driveDTO, Long driveId, Context context) {
            this.activity = activity;
            this.driveDTO = driveDTO;
            this.driveId = driveId;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String URL = getString(R.string.server_url) + "/rest/editDrive";

            // parsowanie na DriveDTO
            JSONObject jsonDrive = new JSONObject();

            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonDriveString = mapper.writeValueAsString(driveDTO);
                jsonDrive  = new JSONObject(jsonDriveString);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonDrive,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.i("TAGOnResponse", "Poprawnie edytowano przejazd" );

                                CharSequence text = getString(R.string.success_add_new_drive);
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(mContext, "Zmiany zostały zapisane", duration);
                                toast.setGravity(Gravity.BOTTOM, 0, 150);
                                toast.show();

                                Intent intent = new Intent(getApplicationContext(), MyDrivesListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("TAGOnError " + error.getMessage(), "Error: " + error.getMessage());

                            //CharSequence text = getString(R.string.error_add_new_drive);
                            CharSequence text = error.getMessage();
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(mContext, "Błąd podczas zapisywania zmian", duration);
                            toast.setGravity(Gravity.TOP, 0, 150);
                            toast.show();
                        }
                    }
            ){
                @Override
                public HashMap<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();

                    SharedPreferences sharedPref = getSharedPreferences("appPrefs", mContext.MODE_PRIVATE);
                    String userId = sharedPref.getString("userId", "");
                    String token = sharedPref.getString("token", "");

                    //headers.put("Content-Type", "application/json");
                    headers.put("userid", userId);
                    headers.put("token", token);
                    headers.put("driveid", Long.toString(driveId));

                    return headers;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(postRequest);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
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





    public Long getDriveId() {
        return driveId;
    }

    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }

    public DriveDTO_String getDrive() {
        return drive;
    }

    public void setDrive(DriveDTO_String drive) {
        this.drive = drive;
    }

    public EditDriveActivity getActivity() {
        return activity;
    }

    public void setActivity(EditDriveActivity activity) {
        this.activity = activity;
    }
}
