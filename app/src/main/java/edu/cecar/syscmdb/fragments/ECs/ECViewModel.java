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
    private  final String jsonData ="{\"operation\":\"core/get\","+
            "\"class\":\"?\","+
            "\"key\":\"SELECT ?\""+
            "}";
    private String newJsonData;
    public LiveData<List<FunctionalCI>> getFuncTionalCI(String CItype){
        if (functionalCIList == null){
            functionalCIList = new MutableLiveData<>();
            loadFunctionalCI(CItype);
        }else{
            if (!currentCItype.equalsIgnoreCase(CItype)){
                currentCItype = CItype;
                loadFunctionalCI(CItype);
            }
        }
        return functionalCIList;
    }

    private void loadFunctionalCI(String CItype) {
         final String parameter = String.valueOf("\\?");
         //Log.i("signo",jsonData.charAt(jsonData.indexOf('?'))+"");
        switch (CItype){
            case "Aplicación Web":
                newJsonData = jsonData.replaceAll(parameter,"WebApplication");
                break;
            case "Dispositivos fisicos":
                newJsonData = jsonData.replaceAll(parameter,"PhysicalDevice");
                break;
            case "Dispositivos Virtuales":
                newJsonData = jsonData.replaceAll(parameter,"VirtualDevice");
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
                        JSONObject functionalCI = objects.getJSONObject( jsonArray.getString(i));
                        newFunctionalCI.setKey(Integer.parseInt(functionalCI.getString("key")));
                        JSONObject fields =  functionalCI.getJSONObject("fields");

                        //Log.i("Fields",fields.toString());

                        newFunctionalCI.setClass_(fields.getString("finalclass"));
                        newFunctionalCI.setName(fields.getString("friendlyname"));
                        newFunctionalCI.setDesc(fields.getString("description"));
                        newFunctionalCI.setOrg_name(fields.getString("organization_name"));
                        newFunctionalCI.setOrg_id(Integer.parseInt(fields.getString("org_id")));
                        functionalCIS.add(newFunctionalCI);
                    }
                    functionalCIList.setValue(functionalCIS);
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
                parametros.put("json_data",newJsonData);
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
