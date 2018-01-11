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
import android.widget.AdapterView;
import android.widget.ListView;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import mvc.com.adapters.CustomDriveAdapter;
import mvc.com.model.DriveModel;

/**
 * Created by zloty on 2018-01-11.
 */

public class MyBookingsListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MyBookingsListActivity";

    // UI references.
    private ListView mMyBookings;
    private CustomDriveAdapter adapter;
    List<DriveModel> myBookings = new ArrayList<>();

    public void setMyBookings(List<DriveModel> myBookings) {
        this.myBookings = myBookings;
    }

    public List<DriveModel> getMyBookings() {
        return myBookings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybookingslist);

        mMyBookings = (ListView) findViewById(R.id.my_bookings_list_view);

        new MyBookingsListTask(this, getApplicationContext()).execute();

        mMyBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DriveModel drive = myBookings.get(position);

                Intent intent = new Intent(getApplicationContext(), MyBookingActivity.class);
                intent.putExtra("DriveId", drive.getDriveId());
                startActivity(intent);
            }
        });
    }






    public class MyBookingsListTask extends AsyncTask<Void, Void, List<DriveModel>> {

        private Context mContext;
        private MyBookingsListActivity activity;

        MyBookingsListTask(MyBookingsListActivity activity, Context context) {
            this.activity = activity;
            mContext = context;
        }

        @Override
        protected List<DriveModel> doInBackground(final Void... params) {

            String URL = getString(R.string.server_url) + "/rest/myBookings";


            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, URL,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.i("TAGOnResponse", "Poprawnie pobrano zarezerwowane przejazdy z bazy" );

                                ObjectMapper mapper = new ObjectMapper();
                                TypeReference<List<DriveModel>> mapType = new TypeReference<List<DriveModel>>() {};

                                // wyciaganie tablicy jsonow z response'a
                                JSONArray arrayJSON = new JSONArray();
                                JSONObject objectJSON = new JSONObject(String.valueOf(response));
                                arrayJSON = (JSONArray) objectJSON.get("myBookedDrivesList");

                                // parsowanie arrayJSON na List<DriveModel>
                                ArrayList<DriveModel> myBookings = new ArrayList<>();
                                myBookings = mapper.readValue(String.valueOf(arrayJSON), mapType);

                                // ustaw liste przejazdow
                                activity.setMyBookings(myBookings);

                                adapter = new CustomDriveAdapter(myBookings, getApplicationContext());
                                mMyBookings.setAdapter(adapter);

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

                            CharSequence text = getString(R.string.error_get_my_drives);
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
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

            return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(List<DriveModel> myBookings) {

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
