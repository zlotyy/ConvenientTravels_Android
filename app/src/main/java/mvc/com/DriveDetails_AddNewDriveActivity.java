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
import android.view.View.OnClickListener;
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
import java.util.HashMap;

import mvc.com.dto.DriveDTO;
import mvc.com.dto.DriveDTO_String;
import mvc.com.enums.LuggageSize;
import mvc.com.helpers.DateFormatHelper;

import static mvc.com.helpers.DriveParser.parseDriveDTOString_TO_DriveDTO;

/**
 * Created by zloty on 2018-01-09.
 */

public class DriveDetails_AddNewDriveActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private static final String TAG = "DriveDetails_AddNewDriveActivity";

    // UI references.
    private View mDriveDetailsNewDriveFormView;
    private EditText mDriverCommentView;
    private EditText mPassengersQuantityView;
    private EditText mCostView;
    private Spinner mLuggageSpinner;
    private CheckBox mSmokePermittedCheckbox;
    private CheckBox mRoundTripCheckbox;
    private DatePicker mDatePickerReturnDate;
    private TimePicker mTimePickerReturnDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewdrive_drivedetails);

        mDriverCommentView = findViewById(R.id.driver_comment);
        mPassengersQuantityView = findViewById(R.id.passengers_quantity);
        mCostView = findViewById(R.id.cost);
        mSmokePermittedCheckbox = findViewById(R.id.smoke_permitted_checkbox);
        mRoundTripCheckbox = findViewById(R.id.round_trip_checkbox);
        mDatePickerReturnDate = findViewById(R.id.datePicker_returnDate);
        mTimePickerReturnDate = findViewById(R.id.timePicker_returnDate);

        mLuggageSpinner = (Spinner) findViewById(R.id.luggage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.luggage_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLuggageSpinner.setAdapter(adapter);

        Button mSubmitNewDriveButton = findViewById(R.id.submit_new_drive_button);
        mSubmitNewDriveButton.setOnClickListener(new OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // odebranie obiektu z poprzedniego kroku formularza
                Intent intent = getIntent();
                DriveDTO_String drive = intent.getParcelableExtra("Drive");

                String isSmokePermitted = "false";
                String isRoundTrip = "false";

                if(mSmokePermittedCheckbox.isChecked()){
                    isSmokePermitted = "true";
                }
                if(mRoundTripCheckbox.isChecked()){
                    isRoundTrip = "true";

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
                }


                drive.setDriverComment(mDriverCommentView.getText().toString());
                drive.setPassengersQuantity(mPassengersQuantityView.getText().toString());
                drive.setCost(mCostView.getText().toString());
                drive.setLuggageSize(mLuggageSpinner.getSelectedItem().toString());
                drive.setIsSmokePermitted(isSmokePermitted);
                drive.setIsRoundTrip(isRoundTrip);

                DriveDTO driveDTO = parseDriveDTOString_TO_DriveDTO(drive);

                new AddNewDriveTask(driveDTO, getApplicationContext()).execute((Void) null);
            }
        });

        mDriveDetailsNewDriveFormView = findViewById(R.id.drive_details_new_drive_form);

    }




    public class AddNewDriveTask extends AsyncTask<Void, Void, Boolean> {

        private final DriveDTO driveDTO;
        private Context mContext;

        AddNewDriveTask(DriveDTO driveDTO, Context context) {
            this.driveDTO = driveDTO;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String URL = getString(R.string.server_url) + "/rest/addNewDrive";

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
                                Log.i("TAGOnResponse", "Poprawnie dodano przejazd do bazy danych" );

                                CharSequence text = getString(R.string.success_add_new_drive);
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(mContext, text, duration);
                                toast.setGravity(Gravity.BOTTOM, 0, 150);
                                toast.show();

                                Intent intent = new Intent(getBaseContext(), MenuActivity.class);
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

                            Toast toast = Toast.makeText(mContext, text, duration);
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





    public Spinner getmLuggageSpinner() {
        return mLuggageSpinner;
    }

    public void setmLuggageSpinner(Spinner mLuggageSpinner) {
        this.mLuggageSpinner = mLuggageSpinner;
    }
}
