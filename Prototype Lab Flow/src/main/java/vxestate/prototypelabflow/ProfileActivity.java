package vxestate.prototypelabflow;
//Kenneth Mendoza(N00598007)
//Sukhdeep Sehra (N01046228)
//Matheus Almeida (N00739768)

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView full_name, Username, Student_num, myAppointment, bookSchedule;
    String json_url = "http://prototypelabflow.esy.es/test.php";
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;
    String test, student_Num, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        full_name = (TextView) findViewById(R.id.full_name);
        Username = (TextView) findViewById(R.id.username);
        Student_num = (TextView) findViewById(R.id.student_num);
        myAppointment = (TextView)findViewById(R.id.start);
        bookSchedule = (TextView)findViewById(R.id.bookSchedule);

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);

        String first_name = sharedPref.getString("first_name", "");
        String last_name = sharedPref.getString("last_name", "");
        String username = sharedPref.getString("username", "");
        String student_num = sharedPref.getString("student_num", "");

        full_name.setText(first_name +" " +last_name);
        Username.setText(username);
        Student_num.setText(student_num);

        myAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestIntent = new Intent(getApplicationContext(), MyAppointmentActivity.class);
                Bundle requestBundle = new Bundle();
                requestBundle.putString("student_num", student_Num);
                requestIntent.putExtras(requestBundle);
                startActivity(requestIntent);
            }
        });

        bookSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scheduleIntent = new Intent(getApplicationContext(), TimeSlotActivity.class);
                Bundle scheduleBundle = new Bundle();
                scheduleBundle.putString("student_num", student_Num);
                scheduleBundle.putString("name", name);
                scheduleIntent.putExtras(scheduleBundle);
                startActivity(scheduleIntent);
            }
        });

        /*
        student_Num = student_num.getText().toString();
        name = full_name.getText().toString();
        */


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
