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
import android.widget.TextView;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mvc.com.dto.DriveDTO_String;
import mvc.com.enums.LuggageSize;
import mvc.com.model.DriveDetailsModel;

/**
 * Created by zloty on 2018-01-11.
 */

public class MyBookingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MyBookingActivity";


    MyBookingActivity activity;
    DriveDTO_String drive;
    private Long driveId;


    // UI references.
    private TextView mStartPlace;
    private TextView mArrivalPlace;
    private TextView mStopOverPlaces;
    private TextView mStartDate;
    private TextView mPassengersQuantity;
    private TextView mCost;
    private TextView mLuggage;
    private TextView mSmokePermitted;
    private TextView mRoundTrip;
    private TextView mReturnDate;
    private TextView mDriverComment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);

        mStartPlace = findViewById(R.id.mybooking_start_place);
        mArrivalPlace = findViewById(R.id.mybooking_arrival_place);
        mStopOverPlaces = findViewById(R.id.mybooking_stop_over_places);
        mStartDate = findViewById(R.id.mybooking_start_date);
        mPassengersQuantity = findViewById(R.id.mybooking_passengers_quantity);
        mCost = findViewById(R.id.mybooking_cost);
        mLuggage = findViewById(R.id.mybooking_luggage);
        mSmokePermitted = findViewById(R.id.mybooking_smoke_permitted);
        mRoundTrip = findViewById(R.id.mybooking_round_trip);
        mReturnDate = findViewById(R.id.mybooking_return_date);
        mDriverComment = findViewById(R.id.mybooking_driver_comment);


        Intent intent = getIntent();
        drive = intent.getParcelableExtra("Drive");
        driveId = intent.getLongExtra("DriveId", -1);

        if(driveId == -1){
            Log.i("TAG", "myBooking_ID = " + driveId + ". Cos poszlo nie tak");

            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(getApplicationContext(), "Błąd podczas wyświetlania danych rezerwacji", duration);
            toast.setGravity(Gravity.TOP, 0, 150);
            toast.show();
        } else {
            Log.i("TAG", "myBooking_ID = " + driveId + ". Poprawnie wyswietlono szczegoly rezerwacji");

            new GetDriveDetailsTask(this, drive, driveId, getApplicationContext()).execute();
        }

        Button mCancelBookingButton = findViewById(R.id.mybooking_cancel_button);
        mCancelBookingButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                new CancelBookingTask(driveId, getApplicationContext()).execute((Void) null);
                onNavigateUp();
            }
        });
    }



    public class CancelBookingTask extends AsyncTask<Void, Void, Boolean> {

        private Context mContext;
        private Long driveId;

        CancelBookingTask(Long driveId, Context context) {
            this.driveId = driveId;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String URL = getString(R.string.server_url) + "/rest/myBookings/unbookDrive";

            Map<String, Long> driveId_Map = new HashMap<>();
            driveId_Map.put("driveId", driveId);
            JSONObject driveIdJSON = new JSONObject(driveId_Map);

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, driveIdJSON,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.i("TAGOnResponse", "Poprawnie dodano przejazd do bazy danych" );

                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(mContext, "Rezerwacja została anulowana", duration);
                                toast.setGravity(Gravity.BOTTOM, 0, 150);
                                toast.show();

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

                            CharSequence text = error.getMessage();
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(mContext, "Nie udało się anulować rezerwacji", duration);
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




    public class GetDriveDetailsTask extends AsyncTask<Void, Void, Boolean> {

        MyBookingActivity activity;
        private Context mContext;
        private Long driveId;
        DriveDTO_String drive;

        GetDriveDetailsTask(MyBookingActivity activity, DriveDTO_String drive, Long driveId, Context context) {
            this.activity = activity;
            this.drive = drive;
            this.driveId = driveId;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String URL = getString(R.string.server_url) + "/rest/getDriveDetails";

            Map<String, Long> driveId_Map = new HashMap<>();
            driveId_Map.put("driveId", driveId);
            JSONObject driveIdJSON = new JSONObject(driveId_Map);

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, driveIdJSON,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.i("TAGOnResponse", "Poprawnie pobrano szczegoly rezerwacji z bazy danych" );

                                String cityStart = drive.getCityStart() != null ? drive.getCityStart() : "";
                                String streetStart = drive.getStreetStart() != null ? drive.getStreetStart() : "";
                                String exactPlaceStart = drive.getExactPlaceStart() != null ? drive.getExactPlaceStart() : "";
                                String startPlace = cityStart + " " + streetStart + " " + exactPlaceStart;

                                String cityArrival = drive.getCityArrival() != null ? drive.getCityArrival() : "";
                                String streetArrival = drive.getStreetArrival() != null ? drive.getStreetArrival() : "";
                                String exactPlaceArrival = drive.getExactPlaceArrival() != null ? drive.getExactPlaceArrival() : "";
                                String arrivalPlace = cityArrival + " " + streetArrival + " " + exactPlaceArrival;

                                mStartPlace.setText(startPlace);
                                mArrivalPlace.setText(arrivalPlace);
                                mStopOverPlaces.setText(drive.getStopOverPlaces());
                                mStartDate.setText(drive.getStartDate());
                                mPassengersQuantity.setText(drive.getPassengersQuantity());
                                mCost.setText(drive.getCost());
                                mLuggage.setText(drive.getLuggageSize());

                                DriveDetailsModel driveDetails;
                                ObjectMapper mapper = new ObjectMapper();
                                TypeReference<DriveDetailsModel> mapType = new TypeReference<DriveDetailsModel>() {};
                                driveDetails = mapper.readValue(String.valueOf(response), mapType);

                                String passengers = Integer.toString(driveDetails.getPassengersQuantity());
                                String smokePermitted = driveDetails.isSmokePermitted() ? "Tak" : "Nie";
                                String driverComment = driveDetails.getDriverComment();

                                String luggage = "";
                                if(driveDetails.getLuggageSize() != null) {
                                    if (driveDetails.getLuggageSize().equals(LuggageSize.MALY)) {
                                        luggage = "Mały";
                                    } else if (driveDetails.getLuggageSize().equals(LuggageSize.SREDNI)) {
                                        luggage = "Średni";
                                    } else if (driveDetails.getLuggageSize().equals(LuggageSize.DUZY)) {
                                        luggage = "Duży";
                                    }
                                }

                                // dodanie szczegolow do formularza DriveDTO_String
                                activity.getDrive().setPassengersQuantity(passengers);
                                activity.getDrive().setIsSmokePermitted(smokePermitted);
                                activity.getDrive().setLuggageSize(luggage);
                                activity.getDrive().setDriverComment(driverComment);

                                mSmokePermitted.setText(passengers);
                                mRoundTrip.setText(smokePermitted);
                                mLuggage.setText(luggage);
                                mDriverComment.setText(driverComment);

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

                            CharSequence text = error.getMessage();
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(mContext, "Nie udało się pobrać szczegółów rezerwacji", duration);
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





    public DriveDTO_String getDrive() {
        return drive;
    }

    public void setDrive(DriveDTO_String drive) {
        this.drive = drive;
    }

    public Long getDriveId() {
        return driveId;
    }

    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }

    public MyBookingActivity getActivity() {
        return activity;
    }

    public void setActivity(MyBookingActivity activity) {
        this.activity = activity;
    }

}
