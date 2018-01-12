package mvc.com;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
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
import mvc.com.dto.DriveDTO_String;
import mvc.com.helpers.DateFormatHelper;
import mvc.com.helpers.DriveParser;
import mvc.com.model.DriveModel;
import mvc.com.model.StopOverPlaceModel;

/**
 * Created by zloty on 2018-01-10.
 */

public class MyDrivesListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MyDrivesListActivity";

    // UI references.
    private ListView mMyDrives;
    private CustomDriveAdapter adapter;
    List<DriveModel> myDrives = new ArrayList<>();

    public void setMyDrives(List<DriveModel> myDrives) {
        this.myDrives = myDrives;
    }

    public List<DriveModel> getMyDrives() {
        return myDrives;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydriveslist);

        mMyDrives = (ListView) findViewById(R.id.my_drives_list_view);

        new MyDrivesListTask(this, getApplicationContext()).execute();

        mMyDrives.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DriveModel drive = myDrives.get(position);
                DriveDTO_String driveDTO_string = DriveParser.parseDriveModel_TO_DriveDTOString(drive);

                Intent intent = new Intent(getApplicationContext(), MyDriveActivity.class);
                intent.putExtra("Drive", driveDTO_string);
                intent.putExtra("DriveId", drive.getDriveId());

                startActivity(intent);
            }
        });
    }






    public class MyDrivesListTask extends AsyncTask<Void, Void, List<DriveModel>> {

        private Context mContext;
        private MyDrivesListActivity activity;

        MyDrivesListTask(MyDrivesListActivity activity, Context context) {
            this.activity = activity;
            mContext = context;
        }

        @Override
        protected List<DriveModel> doInBackground(final Void... params) {

            String URL = getString(R.string.server_url) + "/rest/myDrives";


            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, URL,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.i("TAGOnResponse", "Poprawnie pobrano przejazdy z bazy" );

                                ObjectMapper mapper = new ObjectMapper();
                                TypeReference<List<DriveModel>> mapType = new TypeReference<List<DriveModel>>() {};

                                // wyciaganie tablicy jsonow z response'a
                                JSONArray arrayJSON = new JSONArray();
                                JSONObject objectJSON = new JSONObject(String.valueOf(response));
                                arrayJSON = (JSONArray) objectJSON.get("myDrivesList");

                                // parsowanie arrayJSON na List<DriveModel>
                                ArrayList<DriveModel> myDrives = new ArrayList<>();
                                myDrives = mapper.readValue(String.valueOf(arrayJSON), mapType);

                                // ustaw liste przejazdow
                                activity.setMyDrives(myDrives);

                                adapter = new CustomDriveAdapter(myDrives, getApplicationContext());
                                mMyDrives.setAdapter(adapter);

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
        protected void onPostExecute(List<DriveModel> myDrives) {

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
