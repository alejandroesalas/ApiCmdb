package edu.cecar.syscmdb.fragments.locations;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cecar.syscmdb.data.model.Location;
import edu.cecar.syscmdb.data.model.VolleYSingleton;

public final class LocationViewModel  extends ViewModel {

    private MutableLiveData<List<Location>> locationList;
    private final String jsonData ="{\"operation\":\"core/get\","+
            "\"class\":\"Location\","+
            "\"key\":\"SELECT Location\","+
            "\"output_fields\":\"name,status,org_id,org_name"+
            ",address,postal_code,city,country,physicaldevice_list,person_list\""+
            "}";
    private final String url ="http://192.168.1.26:80/itop/webservices/rest.php?version=1.0";
    private VolleYSingleton volleYSingleton;

    public LiveData<List<Location>> getLocations() {

        if (locationList == null){
            locationList = new MutableLiveData<List<Location>>();
            loadLocations();
        }
        return locationList;
    }

    private void loadLocations() {
        final List<Location> locations = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonjObject = new JSONObject(response);
                    Log.i("responde",jsonjObject.toString());
                    JSONObject objects = jsonjObject.getJSONObject("objects");
                    JSONArray jsonteams = objects.names();
                    for (int i = 0; i<jsonteams.length();i++){
                        JSONObject person = objects.getJSONObject( jsonteams.getString(i));
                        JSONObject fields =  person.getJSONObject("fields");
                        //Log.i("Fields",fields.toString());
                        Location newLocation = new Location();
                        newLocation.setName(fields.getString("name"));
                        newLocation.setStatus(fields.getString("status"));
                        newLocation.setOrgId(fields.getString("org_id"));
                        newLocation.setOrgName(fields.getString("org_name"));
                        newLocation.setAddress(fields.getString("address"));
                        newLocation.setPostalCode(fields.getString("postal_code"));
                        //newLocation.setId(fields.getInt("location_id")); por establecer de key
                        newLocation.setCity(fields.getString("city"));
                        newLocation.setCountry(fields.getString("country"));

                        //Falta establecer phisicaldevice y personList
                        locations.add(newLocation);
                    }
                    locationList.setValue(locations);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("auth_user","restUser");
                parametros.put("auth_pwd","1301");
                parametros.put("json_data",jsonData);
                return parametros;
            }
            @Override
            public Map getHeaders() throws AuthFailureError
            {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        stringRequest.setShouldCache(true);
        volleYSingleton.addToRequestQueue(stringRequest);

    }
    public void setVolleySingleton(VolleYSingleton volleYSingleton){
        this.volleYSingleton = volleYSingleton;
    }
}
