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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import mvc.com.helpers.DateFormatHelper;
import mvc.com.model.DriveModel;

/**
 * Created by zloty on 2018-01-10.
 */

public class MyDrivesListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "DriveDetails_AddNewDriveActivity";

    // UI references.
    private ListView mMyDrives;
    private ArrayAdapter<String> adapter;

    List<DriveModel> myDrives = new ArrayList<>();

    public void setMyDrives(List<DriveModel> myDrives) {
        this.myDrives = myDrives;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydriveslist);

        mMyDrives = (ListView) findViewById(R.id.my_drives_list_view);

        new MyDrivesListTask(this).execute();

        String drives[] = {"Mercedes", "Fiat", "Ferrari", "Aston Martin", "Lamborghini", "Skoda", "Volkswagen", "Audi", "Citroen",
                "Fiat", "Ferrari", "Aston Martin", "Lamborghini", "Skoda", "Volkswagen", "Audi", "Citroen"};

//        ArrayList<String> drivesList = new ArrayList<>();
//        for(DriveModel drive : myDrives){
//            drivesList.add(drive.getCityStart());
//        }
////        drivesList.addAll(Arrays.asList(drives));
//
//        adapter = new ArrayAdapter<String>(this, R.layout.textview_mydrive_row, drivesList);
//
//        mMyDrives.setAdapter(adapter);
    }






    public class MyDrivesListTask extends AsyncTask<Void, Void, List<DriveModel>> {

        private Context mContext;
        private MyDrivesListActivity activity;

        MyDrivesListTask(MyDrivesListActivity activity) {
            this.activity = activity;
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
                                List<DriveModel> myDrives = new ArrayList<>();
                                myDrives = mapper.readValue(String.valueOf(arrayJSON), mapType);

                                // ustaw liste przejazdow
                                activity.setMyDrives(myDrives);

                                // wyswietl przejazdy na liscie
                                ArrayList<String> drivesList = new ArrayList<>();
                                for(DriveModel drive : myDrives){
                                    DateFormatHelper dateFormatHelper = new DateFormatHelper(drive.getStartDate(), "yyyy-MM-dd HH:mm");
                                    String startDate = dateFormatHelper.calendarToString_DateTimeFormat();

                                    String element = startDate + " " + drive.getCityStart() + " -> " + drive.getCityArrival();
                                    drivesList.add(element);
                                }
                                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.textview_mydrive_row, drivesList);
                                mMyDrives.setAdapter(adapter);

                                // czynnosc po kliknieciu elementu
                                mMyDrives.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {

                                    }
                                });

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
