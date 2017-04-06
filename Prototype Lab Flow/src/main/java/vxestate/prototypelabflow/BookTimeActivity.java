package vxestate.prototypelabflow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.HashMap;
import java.util.Map;

public class BookTimeActivity extends AppCompatActivity {
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    TextView student_num, name, DATE, TIME, test;
    String bookflow_url = "http://prototypelabflow.esy.es/BookFlow.php";
    Button bookBtn;
    String date, time, slot, NAME, STUDENT_NUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_time);

        student_num = (TextView)findViewById(R.id.student_num);
        name = (TextView)findViewById(R.id.name);
        DATE = (TextView)findViewById(R.id.date);
        TIME = (TextView)findViewById(R.id.time);
        bookBtn = (Button)findViewById(R.id.bookBtn);
        test = (TextView)findViewById(R.id.test);

        Bundle bundle = getIntent().getExtras();
        student_num.setText(bundle.getString("student_num"));
        name.setText(bundle.getString("name"));
        DATE.setText(bundle.getString("date"));
        TIME.setText(bundle.getString("time"));

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = DATE.getText().toString();
                time = TIME.getText().toString();
                NAME = name.getText().toString();
                STUDENT_NUM = student_num.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, bookflow_url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            slot = jsonObject.getString("total");
                            test.setText(slot);
                            int data = Integer.valueOf(slot);

                            if (data < 2){
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(BookTimeActivity.this);
                                mBuilder.setTitle("Success!")
                                        .setMessage("Your schedule has been booked.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = mBuilder.create();
                                alert.show();
                            }
                            else {
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(BookTimeActivity.this);
                                mBuilder.setTitle("Failed!")
                                        .setMessage("Sorry, this slot is fully booked.")
                                        .setNegativeButton("Reschedule", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent scheduleIntent = new Intent(getApplicationContext(), TimeSlotActivity.class);
                                                Bundle scheduleBundle = new Bundle();
                                                scheduleBundle.putString("student_num", STUDENT_NUM);
                                                scheduleBundle.putString("name", NAME);
                                                scheduleIntent.putExtras(scheduleBundle);
                                                startActivity(scheduleIntent);
                                            }
                                        });

                                AlertDialog alert = mBuilder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(BookTimeActivity.this,"Error",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", NAME);
                        params.put("student_num", STUDENT_NUM);
                        params.put("date", date);
                        params.put("time", time);
                        return params;
                    }
                };
                MySingleton.getMyInstance(BookTimeActivity.this).addToRequestque(stringRequest);
            }
        });

        mdrawerLayout = (DrawerLayout)findViewById(R.id.activity_book_time);
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
                        Bundle requestBundle = new Bundle();
                        requestBundle.putString("student_num", STUDENT_NUM);
                        requestIntent.putExtras(requestBundle);
                        startActivity(requestIntent);
                        break;
                    case R.id.nav_scan:
                        Intent qrIntent = new Intent(getApplicationContext(), ScanQRActivity.class);
                        Bundle qrBundle = new Bundle();
                        qrBundle.putString("student_num", STUDENT_NUM);
                        qrIntent.putExtras(qrBundle);
                        startActivity(qrIntent);
                        break;
                    case R.id.nav_schedule:
                        Intent scheduleIntent = new Intent(getApplicationContext(), TimeSlotActivity.class);
                        Bundle scheduleBundle = new Bundle();
                        scheduleBundle.putString("student_num", STUDENT_NUM);
                        scheduleBundle.putString("name", NAME);
                        scheduleIntent.putExtras(scheduleBundle);
                        startActivity(scheduleIntent);
                        break;
                    case R.id.nav_about:
                        Intent usIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
                        Bundle usBundle = new Bundle();
                        usBundle.putString("student_num", STUDENT_NUM);
                        usIntent.putExtras(usBundle);
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
