package vxestate.prototypelabflow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kristina on 2017-04-06.
 */

public class AppointmentRequest {

    RequestQueue requestQueue;
    Context context;
    ArrayList<Appointment> arrayList = new ArrayList<>();
    String myAppointment_url = "http://prototypelabflow.esy.es/MyAppointment.php";

    public AppointmentRequest(Context context){
        this.context = context;
    }
/*
    public ArrayList<Appointment> getList(){
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, myAppointment_url, (String)null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while(count<response.length()){
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                Appointment appointment = new Appointment(jsonObject.getString("Date"), jsonObject.getString("Time"));
                                arrayList.add(appointment);
                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_num", "N00598007");
                return params;
            }
        };
        MySingleton.getMyInstance(context).addToRequestque(jsonArrayRequest);

*/
        //return arrayList;
//    }

}
