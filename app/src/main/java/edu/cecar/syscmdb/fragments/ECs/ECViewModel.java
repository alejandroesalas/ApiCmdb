package edu.cecar.syscmdb.fragments.ECs;

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

import edu.cecar.syscmdb.data.model.Constantes;
import edu.cecar.syscmdb.data.model.FunctionalCI;
import edu.cecar.syscmdb.data.model.Location;
import edu.cecar.syscmdb.data.model.Person;
import edu.cecar.syscmdb.data.model.Picture;
import edu.cecar.syscmdb.data.model.VolleYSingleton;

public final class ECViewModel extends ViewModel {

    private VolleYSingleton volleYSingleton;
    private MutableLiveData<List<FunctionalCI>>functionalCIList;
    private String currentCItype = "Aplicacion Web";
   /* private final String[] functionalCItypes ={"Aplicacion Web","Conexion Electrica","Dispositivo fisico","Dispositivo Virtual","Dispositivo de Red","Disp Centro de datos","EC Conectable","EC Telefónico","Eclosure","Esquema de Base de Datos"
    ,"Fuente de Poder", "Granja","Host Virtual","Hypervisor","Impresora","Instalacion Middleware","Instalacion software","Libreria de Cintas",
    "Middleware","Maquina Virtual"};*/
   private final String[] functionalCItypes ={"Aplicación Web","Dispositivos fisicos","Dispositivos Virtuales",
   "Base de datos(Esquema)","Instalacion Middleware","Instalacion Software",
           "Proceso de Negocio","Solucion Aplicativa"};
    private  String jsonData ="{\"operation\":\"core/get\","+
            "\"class\":\"?\","+
            "\"key\":\"SELECT ?\""+
            "}";
    public LiveData<List<FunctionalCI>> getFuncTionalCI(String CItype){
        if (functionalCIList == null){
            functionalCIList = new MutableLiveData<>();
            loadFunctionalCI(CItype);
        }else{
            if (!currentCItype.equalsIgnoreCase(CItype)){
                loadFunctionalCI(CItype);
            }
        }
        return functionalCIList;
    }

    private void loadFunctionalCI(String CItype) {
         final String parameter = String.valueOf('?');
        switch (CItype){
            case "Aplicación Web":
                jsonData = jsonData.replaceAll(parameter,"WebApplication");
                break;
            case "Dispositivos fisicos":
                jsonData = jsonData.replaceAll(parameter,"PhysicalDevice");
                break;
            case "Dispositivos Virtuales":
                jsonData = jsonData.replaceAll(parameter,"VirtualDevice");
                break;
        }
        loadFunctionalCI();
    }

    private void loadFunctionalCI() {
        final List<FunctionalCI> functionalCIS = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonjObject = new JSONObject(response);
                    Log.i("responde",jsonjObject.toString());
                    JSONObject objects = jsonjObject.getJSONObject("objects");
                    JSONArray jsonArray = objects.names();
                    for (int i = 0; i<jsonArray.length();i++){
                        FunctionalCI newFunctionalCI = new FunctionalCI();
                        JSONObject person = objects.getJSONObject( jsonArray.getString(i));
                        JSONObject fields =  person.getJSONObject("fields");
                        //Log.i("Fields",fields.toString());
/*
                        newFunctionalCI.setFriendlyname(fields.getString("friendlyname"));
                        newFunctionalCI.setEmployeeNumber(fields.getString("employee_number"));
                        newFunctionalCI.setFunction(fields.getString("function"));
                        newFunctionalCI.setEmail(fields.getString("email"));
                        newFunctionalCI.setPhone(fields.getString("phone"));
                        newFunctionalCI.setMobilePhone(fields.getString("employee_number"));
                        newFunctionalCI.setStatus(fields.getString("status"));
                        Location newLocation =new Location();
                        newLocation.setId(fields.getInt("location_id"));
                        newLocation.setName(fields.getString("location_name"));
                        newFunctionalCI.setLocation(newLocation);
                        newFunctionalCI.setOrgId(fields.getString("org_id"));
                        newFunctionalCI.setOrgName(fields.getString("org_name"));
                        Picture newPicture = new Picture();
                        JSONObject pictureJsonObject = fields.getJSONObject("picture");
                        newPicture.setData(pictureJsonObject.getString("data"));
                        newPicture.setFilename(pictureJsonObject.getString("filename"));
                        newPicture.setMimetype(pictureJsonObject.getString("mimetype"));
                        newFunctionalCI.setPicture(newPicture);*/
                        functionalCIS.add(newFunctionalCI);
                    }
                    functionalCIList.setValue(functionalCIS);
                    //totalContacts.setValue( totalContacts.getValue()+persons.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                functionalCIList = null;
                Log.i("Error",error.toString());
                //falta mostrar error al usuario.
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