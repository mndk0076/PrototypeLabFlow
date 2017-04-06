package vxestate.prototypelabflow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAppointmentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Appointment> arrayList = new ArrayList<>();
    TextView appointment, student_num;
    RequestQueue requestQueue;
    String myAppointment_url = "http://prototypelabflow.esy.es/MyAppointment.php";
    String slot, Student_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);

        appointment = (TextView)findViewById(R.id.myAppointment);
        student_num = (TextView)findViewById(R.id.student_num);
        //requestQueue = Volley.newRequestQueue(this);

        /*
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, myAppointment_url, (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");

                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String date = jsonObject.getString("date");
                        String time = jsonObject.getString("time");

                        appointment.append(date+" "+time+"\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyAppointmentActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
        */


        Bundle bundle = getIntent().getExtras();
        student_num.setText(bundle.getString("student_num"));
        Student_num = student_num.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, myAppointment_url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String date = jsonObject.getString("Date");
                        String time = jsonObject.getString("Time");

                        appointment.append("Date: "+date+"    "+"Time: "+time+"\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(MyAppointmentActivity.this,"Error",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_num", Student_num);
                return params;
            }
        };
        MySingleton.getMyInstance(MyAppointmentActivity.this).addToRequestque(stringRequest);
        /*
        recyclerView = (RecyclerView)findViewById(R.id.myAppointment);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        AppointmentRequest appointmentRequest = new AppointmentRequest(MyAppointmentActivity.this);
        arrayList = appointmentRequest.getList();
        adapter = new AppointmentAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        */
    }
}
