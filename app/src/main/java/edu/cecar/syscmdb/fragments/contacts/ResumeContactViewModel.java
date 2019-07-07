package edu.cecar.syscmdb.fragments.contacts;

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

import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.data.model.Location;
import edu.cecar.syscmdb.data.model.Person;
import edu.cecar.syscmdb.data.model.Picture;
import edu.cecar.syscmdb.data.model.Team;
import edu.cecar.syscmdb.data.model.VolleYSingleton;

public class ResumeContactViewModel extends ViewModel {

    private MutableLiveData<List<Person>>personList;
    private MutableLiveData<List<Team>>teamList;
    private final String jsonData ="{\"operation\":\"core/get\","+
            "\"class\":\"Person\","+
            "\"key\":\"SELECT Person\","+
            "\"output_fields\":\"friendlyname,employee_number,status,email,org_id,org_name"+
            ",mobile_phone,phone,function,picture,location_id,location_name,team_list"+
            ",cis_list\""+
            "}";
    private final String jsonDataTeam="{\"operation\":\"core/get\"," +
            "\"class\":\"Team\"," +
            "\"key\":\"SELECT Team\"," +
            "\"output_fields\":\"name,status,org_id,org_name,email,persons_list\"}";
    private final String url ="http://192.168.1.26:80/itop/webs ervices/rest.php?version=1.0";
    private VolleYSingleton volleYSingleton;
    public LiveData<List<Person>> getPersons() {

        if (personList == null){
            personList = new MutableLiveData<List<Person>>();
            loadPersons();
        }
        return personList;
    }
    public LiveData<List<Team>>getTeams(){
        if (teamList == null){
            teamList = new MutableLiveData<List<Team>>();
            loadTeams();
        }
        return teamList;
    }
    public int totalContacts(){
        return personList.getValue().size()+teamList.getValue().size();
    }
    //carga los datos de las personas
    private void loadPersons(){
        final List<Person> teams = new ArrayList<>();
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
                        Person newPerson = new Person();
                        newPerson.setFriendlyname(fields.getString("friendlyname"));
                        newPerson.setEmployeeNumber(fields.getString("employee_number"));
                        newPerson.setFunction(fields.getString("function"));
                        newPerson.setEmail(fields.getString("email"));
                        newPerson.setPhone(fields.getString("phone"));
                        newPerson.setMobilePhone(fields.getString("employee_number"));
                        newPerson.setStatus(fields.getString("status"));
                        Location newLocation =new Location();
                        newLocation.setId(fields.getInt("location_id"));
                        newLocation.setName(fields.getString("location_name"));
                        newPerson.setLocation(newLocation);
                        newPerson.setOrgId(fields.getString("org_id"));
                        newPerson.setOrgName(fields.getString("org_name"));
                        Picture newPicture = new Picture();
                        JSONObject pictureJsonObject = fields.getJSONObject("picture");
                        newPicture.setData(pictureJsonObject.getString("data"));
                        newPicture.setFilename(pictureJsonObject.getString("filename"));
                        newPicture.setMimetype(pictureJsonObject.getString("mimetype"));
                        newPerson.setPicture(newPicture);
                        teams.add(newPerson);
                    }
                    personList.setValue(teams);
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
    //carga los datos de los equipos
    private  void loadTeams(){
        final List<Team> teams = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonjObject = new JSONObject(response);
                    Log.i("response",jsonjObject.toString());
                    JSONObject objects = jsonjObject.getJSONObject("objects");
                    JSONArray jsonTeams = objects.names();
                    for (int i = 0; i<jsonTeams.length();i++){
                        JSONObject team = objects.getJSONObject( jsonTeams.getString(i));
                        JSONObject fields =  team.getJSONObject("fields");
                        Team newTeam = new Team();
                        newTeam.setName(fields.getString("name"));
                        newTeam.setEmail(fields.getString("email"));
                        newTeam.setStatus(fields.getString("status"));
                        newTeam.setOrgId(fields.getString("org_id"));
                        newTeam.setOrgName(fields.getString("org_name"));
                        teams.add(newTeam);
                    }
                    teamList.setValue(teams);
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
                parametros.put("json_data",jsonDataTeam);
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
