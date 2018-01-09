package mvc.com;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mvc.com.dto.DriveDTO_String;

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
        mLuggageSpinner = findViewById(R.id.luggage);
        mSmokePermittedCheckbox = findViewById(R.id.smoke_permitted_checkbox);
        mRoundTripCheckbox = findViewById(R.id.round_trip_checkbox);
        mDatePickerReturnDate = findViewById(R.id.datePicker_returnDate);
        mTimePickerReturnDate = findViewById(R.id.timePicker_returnDate);

        final Spinner mLuggageSpinner = (Spinner) findViewById(R.id.luggage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.luggage_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLuggageSpinner.setAdapter(adapter);

        Button mSubmitNewDriveButton = findViewById(R.id.submit_new_drive_button);
        mSubmitNewDriveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String isSmokePermitted = "false";
                String isRoundTrip = "false";

                if(mSmokePermittedCheckbox.isChecked()){
                    isSmokePermitted = "true";
                }
                if(mRoundTripCheckbox.isChecked()){
                    isRoundTrip = "true";
                }

                // odebranie obiektu z poprzedniego kroku formularza
                Intent intent = getIntent();
                DriveDTO_String drive = intent.getParcelableExtra("Drive");

                drive.setDriverComment(mDriverCommentView.getText().toString());
                drive.setPassengersQuantity(mPassengersQuantityView.getText().toString());
                drive.setCost(mCostView.getText().toString());
                //drive.setLuggageSize(mLuggageSpinner.getSelectedItem().toString());
                drive.setIsSmokePermitted(isSmokePermitted);
                drive.setIsRoundTrip(isRoundTrip);

//                Intent nextIntent = new Intent(getApplicationContext(), MenuActivity.class);
//                startActivity(nextIntent);

                attemptSubmit(drive);
            }
        });

        mDriveDetailsNewDriveFormView = findViewById(R.id.drive_details_new_drive_form);

    }

    private void attemptSubmit(DriveDTO_String drive){
        new AddNewDriveTask(drive, getApplicationContext()).execute((Void) null);
    }






    public class AddNewDriveTask extends AsyncTask<Void, Void, Boolean> {

        private final DriveDTO_String drive;
        private Context mContext;

        AddNewDriveTask(DriveDTO_String drive, Context context) {
            this.drive = drive;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            String URL = "http://192.168.1.38:8080/rest/addNewDrive";

//            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonCredentials,
//                    new Response.Listener<JSONObject>()
//                    {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            String userId;
//                            String token;
//
//                            try {
//                                Log.i("TAGOnResponse", "Zalogowano: userId = " + response.getString("userId")
//                                        + " Token = " + response.getString("token") );
//
//                                userId =  response.getString("userId");
//                                token =  response.getString("token");
//
//                                // dodanie userId i tokena do SharedPreferences
//                                SharedPreferences sharedPref = getSharedPreferences("appPrefs", mContext.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPref.edit();
//                                editor.putString("userId", userId);
//                                editor.putString("token", token);
//                                editor.commit();
//
//                                LoginActivity.loginResult = true;
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener()
//                    {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            //Toast.makeText(mContext, "The login submitted does not exist.", Toast.LENGTH_LONG).show();
//                            VolleyLog.d("TAGOnError " + error.getMessage(), "Error: " + error.getMessage());
////                            Log.i("TAGOnError", error.getMessage());
//                            showProgress(false);
//
//                            LoginActivity.loginResult = false;
//                        }
//                    }
//            ){
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("login", mLogin);
//                    params.put("password", mPassword);
//
//                    return params;
//                }
//            };
//            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//            queue.add(postRequest);


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                //finish();
                Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                startActivity(intent);
            } else {
                Context context = getApplicationContext();
                CharSequence text = getString(R.string.error_add_new_drive);
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.TOP, 0, 150);
                toast.show();
            }
        }

        @Override
        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
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

}
