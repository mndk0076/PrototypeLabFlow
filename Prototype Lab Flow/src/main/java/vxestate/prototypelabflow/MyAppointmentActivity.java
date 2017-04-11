package vxestate.prototypelabflow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyAppointmentActivity extends AppCompatActivity {
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    TextView appointment, Student_num, name;
    String myAppointment_url = "http://prototypelabflow.esy.es/MyAppointment.php";
    String Student_Num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);

        appointment = (TextView)findViewById(R.id.start);
        Student_num = (TextView)findViewById(R.id.student_num);
        name = (TextView)findViewById(R.id.name);

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);

        String student_num = sharedPref.getString("student_num", "");
        Student_num.setText(student_num);
        Student_Num = Student_num.getText().toString();



        /*
        Bundle bundle = getIntent().getExtras();
        student_num.setText(bundle.getString("student_num"));
        name.setText(bundle.getString("name"));
        Student_num = student_num.getText().toString();
        Name = name.getText().toString();
        */

        StringRequest stringRequest = new StringRequest(Request.Method.POST, myAppointment_url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    int app = 1;
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String date = jsonObject.getString("Date");
                        String start = jsonObject.getString("Start");
                        String end = jsonObject.getString("End");

                        appointment.append("Appointment "+app+"\n"+"Date: "+date+" | "+"Time: "+start+" - "+end+"\n\n");
                        app++;
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
                params.put("student_num", Student_Num);
                return params;
            }
        };
        MySingleton.getMyInstance(MyAppointmentActivity.this).addToRequestque(stringRequest);

        mdrawerLayout = (DrawerLayout)findViewById(R.id.activity_profile);
        mDrawerToggle = new ActionBarDrawerToggle(this, mdrawerLayout,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mdrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case R.id.nav_request:
                        Intent requestIntent = new Intent(getApplicationContext(), MyAppointmentActivity.class);
                        startActivity(requestIntent);
                        break;
                    case R.id.nav_scan:
                        Intent qrIntent = new Intent(getApplicationContext(), ScanQRActivity.class);
                        startActivity(qrIntent);
                        break;
                    case R.id.nav_schedule:
                        Intent scheduleIntent = new Intent(getApplicationContext(), TimeSlotActivity.class);
                        startActivity(scheduleIntent);
                        break;
                    case R.id.nav_about:
                        Intent usIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
                        startActivity(usIntent);
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
