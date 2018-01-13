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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
 * Created by zloty on 2018-01-13.
 */

public class DriveDetails_EditDriveActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String TAG = "DriveDetails_EditDriveActivity";
    DriveDTO_String drive;
    Long driveId;
    DriveDetails_EditDriveActivity activity;


    // UI references.
    private View mDriveDetails_EditDriveFormView;
    private EditText mDriverCommentView;
    private EditText mPassengersQuantityView;
    private EditText mCostView;
    private Spinner mLuggageSpinner;
    private CheckBox mSmokePermittedCheckbox;
    private CheckBox mRoundTripCheckbox;
    private DatePicker mDatePickerReturnDate;
    private TimePicker mTimePickerReturnDate;



    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdrive_drivedetails);


        mDriverCommentView = findViewById(R.id.editdrive_driver_comment);
        mPassengersQuantityView = findViewById(R.id.editdrive_passengers_quantity);
        mCostView = findViewById(R.id.editdrive_cost);
        mSmokePermittedCheckbox = findViewById(R.id.editdrive_smoke_permitted_checkbox);
        mRoundTripCheckbox = findViewById(R.id.editdrive_round_trip_checkbox);
        mDatePickerReturnDate = findViewById(R.id.editdrive_datePicker_returnDate);
        mTimePickerReturnDate = findViewById(R.id.editdrive_timePicker_returnDate);

        mLuggageSpinner = (Spinner) findViewById(R.id.editdrive_luggage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.luggage_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLuggageSpinner.setAdapter(adapter);

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

            Calendar returnDateTime = Calendar.getInstance();
            String returnDateTime_String = drive.getReturnDate();

            if(returnDateTime_String != null){
                DateFormatHelper dateFormatHelper = new DateFormatHelper(returnDateTime_String, "yyyy-MM-dd HH:mm");
                returnDateTime = dateFormatHelper.stringToCalendar_DateTimeFormat();

                mDatePickerReturnDate.updateDate(returnDateTime.get(Calendar.YEAR), returnDateTime.get(Calendar.MONTH) - 1, returnDateTime.get(Calendar.DATE));
                mTimePickerReturnDate.setHour(returnDateTime.get(Calendar.HOUR_OF_DAY));
                mTimePickerReturnDate.setMinute(returnDateTime.get(Calendar.MINUTE));
            }

            mDriverCommentView.setText(drive.getDriverComment());
            mPassengersQuantityView.setText(drive.getPassengersQuantity());
            mCostView.setText(drive.getCost());
            mLuggageSpinner.setSelection(adapter.getPosition(drive.getLuggageSize()));

            mSmokePermittedCheckbox.setChecked(drive.getIsSmokePermitted().equals("true") || drive.getIsSmokePermitted().equals("Tak"));
            mRoundTripCheckbox.setChecked(drive.getIsRoundTrip().equals("true") || drive.getIsRoundTrip().equals("Tak"));
        }


        Button mEditDriveAcceptDriveDetailsButton = findViewById(R.id.editdrive_submit_drive_details_button);
        mEditDriveAcceptDriveDetailsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String isSmokePermitted = "Nie";
                String isRoundTrip = "Nie";

                if(mSmokePermittedCheckbox.isChecked()){
                    isSmokePermitted = "Tak";
                }
                if(mRoundTripCheckbox.isChecked()){
                    isRoundTrip = "Tak";

                    // parsowanie daty i czasu i dodanie do obiektu drive
                    String returnDate;
                    String returnTime;
                    String returnDateTime;

                    // parsowanie datepickera na stringa
                    DateFormatHelper dateHelper = new DateFormatHelper(mDatePickerReturnDate, "yyyy-MM-dd");
                    returnDate = dateHelper.datepickerToString_DateFormat();

                    // parsowanie timepickera na stringa
                    DateFormatHelper timeHelper = new DateFormatHelper(mTimePickerReturnDate, "HH:mm");
                    returnTime = timeHelper.timepickerToString_DateFormat();

                    returnDateTime = returnDate + " " + returnTime;

                    drive.setReturnDate(returnDateTime);
                } else {
                    drive.setReturnDate(null);
                }

                drive.setDriverComment(mDriverCommentView.getText().toString());
                drive.setPassengersQuantity(mPassengersQuantityView.getText().toString());
                drive.setCost(mCostView.getText().toString());
                drive.setLuggageSize(mLuggageSpinner.getSelectedItem().toString());
                drive.setIsSmokePermitted(isSmokePermitted);
                drive.setIsRoundTrip(isRoundTrip);

                DriveDTO driveDTO = parseDriveDTOString_TO_DriveDTO(drive);

                new EditDriveTask(activity, driveDTO, driveId, getApplicationContext()).execute();
            }
        });


        mDriveDetails_EditDriveFormView = findViewById(R.id.drive_details_edit_drive_form);

    }





    public class EditDriveTask extends AsyncTask<Void, Void, Boolean> {

        DriveDetails_EditDriveActivity activity;
        private Long driveId;
        private final DriveDTO driveDTO;
        private Context mContext;

        EditDriveTask(DriveDetails_EditDriveActivity activity, DriveDTO driveDTO, Long driveId, Context context) {
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

    public DriveDetails_EditDriveActivity getActivity() {
        return activity;
    }

    public void setActivity(DriveDetails_EditDriveActivity activity) {
        this.activity = activity;
    }


}
