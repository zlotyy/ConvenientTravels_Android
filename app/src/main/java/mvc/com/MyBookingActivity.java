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

/**
 * Created by zloty on 2018-01-11.
 */

public class MyBookingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MyBookingActivity";


    private Long driveId;
    private MyBookingActivity activity;

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





    // todo: referencje + wyswietlanie odpowiednich danych

    // UI references.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);

        Intent intent = getIntent();
        driveId = intent.getLongExtra("DriveId", -1);
        if(driveId == -1){
            Log.i("TAG", "myDrive_ID = " + driveId + ". Cos poszlo nie tak");
        } else {
            Log.i("TAG", "myDrive_ID = " + driveId + ". Poprawnie wyswietlono szczegoly rezerwacji");
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
