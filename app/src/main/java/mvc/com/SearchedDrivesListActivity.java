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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import mvc.com.adapters.CustomDriveAdapter;
import mvc.com.dto.DriveDTO_String;
import mvc.com.dto.SearchDrivesDTO;
import mvc.com.dto.SearchDrivesDTO_String;
import mvc.com.helpers.DriveParser;
import mvc.com.model.DriveModel;

/**
 * Created by zloty on 2018-01-13.
 */

public class SearchedDrivesListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "SearchedDrivesListActivity";

    // UI references.
    private ListView mSearchedDrives;
    private CustomDriveAdapter adapter;
    List<DriveModel> searchedDrives = new ArrayList<>();
    SearchDrivesDTO searchDrivesDTO;


    public CustomDriveAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomDriveAdapter adapter) {
        this.adapter = adapter;
    }

    public SearchDrivesDTO getSearchDrivesDTO() {
        return searchDrivesDTO;
    }

    public void setSearchDrivesDTO(SearchDrivesDTO searchDrivesDTO) {
        this.searchDrivesDTO = searchDrivesDTO;
    }

    public List<DriveModel> getSearchedDrives() {
        return searchedDrives;
    }

    public void setSearchedDrives(List<DriveModel> searchedDrives) {
        this.searchedDrives = searchedDrives;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcheddriveslist);

        Intent intent = getIntent();
        SearchDrivesDTO_String searchDrivesDTO_string = intent.getParcelableExtra("SearchDrivesDTO_String");

        searchDrivesDTO = DriveParser.parseSearchDrivesDTOString_TO_SearchDrivesDTO(searchDrivesDTO_string);

        mSearchedDrives = (ListView) findViewById(R.id.searched_drives_list_view);

        new SearchDriveTask(this, searchDrivesDTO, getApplicationContext()).execute();

        mSearchedDrives.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DriveModel drive = searchedDrives.get(position);
                DriveDTO_String driveDTO_string = DriveParser.parseDriveModel_TO_DriveDTOString(drive);

                Intent intent = new Intent(getApplicationContext(), BookDriveActivity.class);
                intent.putExtra("Drive", driveDTO_string);
                intent.putExtra("DriveId", drive.getDriveId());

                startActivity(intent);
            }
        });
    }






    public class SearchDriveTask extends AsyncTask<Void, Void, Boolean> {

        SearchedDrivesListActivity activity;
        private SearchDrivesDTO searchDrivesDTO;
        private Context mContext;

        SearchDriveTask(SearchedDrivesListActivity activity, SearchDrivesDTO searchDrivesDTO, Context context) {
            this.activity = activity;
            this.searchDrivesDTO = searchDrivesDTO;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String URL = getString(R.string.server_url) + "/rest/searchDrives";

            // parsowanie na DriveDTO
            JSONObject searchDrivesJSON = new JSONObject();

            try {
                ObjectMapper mapper = new ObjectMapper();
                String searchDrives_String = mapper.writeValueAsString(searchDrivesDTO);
                searchDrivesJSON  = new JSONObject(searchDrives_String);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, searchDrivesJSON,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.i("TAGOnResponse", "Wyszukano przejazdy" );

                                ObjectMapper mapper = new ObjectMapper();
                                TypeReference<List<DriveModel>> mapType = new TypeReference<List<DriveModel>>() {};

                                // wyciaganie tablicy jsonow z response'a
                                JSONArray arrayJSON = new JSONArray();
                                JSONObject objectJSON = new JSONObject(String.valueOf(response));
                                arrayJSON = (JSONArray) objectJSON.get("searchedDrives");

                                // parsowanie arrayJSON na List<DriveModel>
                                ArrayList<DriveModel> searchedDrives = new ArrayList<>();
                                searchedDrives = mapper.readValue(String.valueOf(arrayJSON), mapType);

                                // ustaw liste przejazdow
                                activity.setSearchedDrives(searchedDrives);

                                adapter = new CustomDriveAdapter(searchedDrives, getApplicationContext());
                                mSearchedDrives.setAdapter(adapter);

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

                            Toast toast = Toast.makeText(mContext, "Błąd podczas wyszukiwania przejazdów", duration);
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
