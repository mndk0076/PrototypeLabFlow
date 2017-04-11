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

public class BookTimeActivity extends AppCompatActivity {
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    TextView Student_num, Name, DATE, START, END, test;
    String bookflow_url = "http://prototypelabflow.esy.es/BookFlow.php";
    Button bookBtn;
    String date, start, end, slot, NAME, STUDENT_NUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_time);

        Student_num = (TextView)findViewById(R.id.student_num);
        Name = (TextView)findViewById(R.id.name);
        DATE = (TextView)findViewById(R.id.date);
        START = (TextView)findViewById(R.id.start);
        END = (TextView)findViewById(R.id.end);
        bookBtn = (Button)findViewById(R.id.bookBtn);
        test = (TextView)findViewById(R.id.test);

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);

        String first_name = sharedPref.getString("first_name", "");
        String last_name = sharedPref.getString("last_name", "");
        String student_num = sharedPref.getString("student_num", "");

        Name.setText(first_name +" " +last_name);
        Student_num.setText(student_num);


        Bundle bundle = getIntent().getExtras();
        DATE.setText(bundle.getString("date"));
        START.setText(bundle.getString("start"));
        END.setText(bundle.getString("end"));


        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = DATE.getText().toString();
                start = START.getText().toString();
                end = END.getText().toString();
                NAME = Name.getText().toString();
                STUDENT_NUM = Student_num.getText().toString();

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
                        params.put("start_time", start);
                        params.put("end_time", end);
                        params.put("id", "");
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
