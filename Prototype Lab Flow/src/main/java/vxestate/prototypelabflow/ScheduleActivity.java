package vxestate.prototypelabflow;
//Kenneth Mendoza(N00598007)
//Sukhdeep Sehra (N01046228)
//Matheus Almeida (N00739768)
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ScheduleActivity extends AppCompatActivity {
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    private static Button date, time;
    private static TextView set_date, set_time, student_num;
    private static final int Date_id = 0;
    private static final int Time_id = 1;
    String schedule_url = "http://prototypelabflow.esy.es/Schedule.php";
    Button schedulebtn;
    String Date, Time, Student_Num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        date = (Button) findViewById(R.id.selectdate);
        time = (Button) findViewById(R.id.selecttime);
        set_date = (TextView) findViewById(R.id.set_date);
        set_time = (TextView) findViewById(R.id.set_time);
        student_num = (TextView)findViewById(R.id.student_num);
        schedulebtn = (Button)findViewById(R.id.schedulebtn);

        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show Date dialog
                showDialog(Date_id);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show time dialog
                showDialog(Time_id);
            }
        });

        Bundle bundle = getIntent().getExtras();
        student_num.setText(bundle.getString("student_num"));

        schedulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date = set_date.getText().toString();
                Time = set_time.getText().toString();
                Student_Num = student_num.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, schedule_url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Toast.makeText(ScheduleActivity.this,"Your schedule has been book",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(ScheduleActivity.this,"Error",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("date", Date);
                        params.put("time", Time);
                        params.put("student_num", Student_Num);
                        return params;
                    }
                };
                MySingleton.getMyInstance(ScheduleActivity.this).addToRequestque(stringRequest);
            }
        });

        Student_Num = student_num.getText().toString();

        mdrawerLayout = (DrawerLayout)findViewById(R.id.activity_schedule);
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
                        requestBundle.putString("student_num", Student_Num);
                        requestIntent.putExtras(requestBundle);
                        startActivity(requestIntent);
                        break;
                    case R.id.nav_scan:
                        Intent qrIntent = new Intent(getApplicationContext(), ScanQRActivity.class);
                        Bundle qrBundle = new Bundle();
                        qrBundle.putString("student_num", Student_Num);
                        qrIntent.putExtras(qrBundle);
                        startActivity(qrIntent);
                        break;
                    case R.id.nav_about:
                        Intent usIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
                        Bundle usBundle = new Bundle();
                        usBundle.putString("student_num", Student_Num);
                        usIntent.putExtras(usBundle);
                        startActivity(usIntent);
                        break;
                    case R.id.nav_schedule:
                        Intent scheduleIntent = new Intent(getApplicationContext(), ScheduleActivity.class);
                        Bundle scheduleBundle = new Bundle();
                        scheduleBundle.putString("student_num", Student_Num);
                        scheduleIntent.putExtras(scheduleBundle);
                        startActivity(scheduleIntent);
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                }

                return true;
            }
        });
    }

    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(ScheduleActivity.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(ScheduleActivity.this, time_listener, hour,
                        minute, false);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.valueOf(month) + "/" + String.valueOf(day)
                    + "/" + String.valueOf(year);
            set_date.setText(date1);
        }
    };
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            set_time.setText(time1);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
