package vxestate.prototypelabflow;
//Kenneth Mendoza(N00598007)
//Sukhdeep Sehra (N01046228)
//Matheus Almeida
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import java.util.HashMap;
import java.util.Map;

public class RequestItemActivity extends AppCompatActivity {
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    private int mCounter1 = 0;
    private int mCounter2 = 0;
    Button item1minusbtn, item1plusbtn, item2minusbtn, item2plusbtn, item1requestbtn, item2requestbtn;
    TextView item1quantity, item2quantity, description1, description2, studentNum;
    String reguest_url = "http://prototypelabflow.esy.es/Request.php";
    String item1, quantity1, item2, quantity2, student_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_item);

        item1minusbtn = (Button)findViewById(R.id.item1minusbtn);
        item1plusbtn = (Button)findViewById(R.id.item1plusbtn);
        item2minusbtn = (Button)findViewById(R.id.item2minusbtn);
        item2plusbtn = (Button)findViewById(R.id.item2plusbtn);
        item1requestbtn = (Button)findViewById(R.id.item1requestbtn);
        item2requestbtn = (Button)findViewById(R.id.item2requestbtn);
        description1 = (TextView)findViewById(R.id.description1);
        description2 = (TextView)findViewById(R.id.description2);
        item1quantity = (TextView)findViewById(R.id.item1quantity);
        item2quantity = (TextView)findViewById(R.id.item2quantity);
        studentNum = (TextView)findViewById(R.id.student_num);

        Bundle bundle = getIntent().getExtras();
        studentNum.setText(bundle.getString("student_num"));

        item1plusbtn.setOnClickListener(new ViewPager.OnClickListener(){
            @Override
            public void onClick(View view){
                mCounter1++;
                item1quantity.setText(Integer.toString(mCounter1));
            }
        });

        item1minusbtn.setOnClickListener(new ViewPager.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mCounter1 > 0) {
                    mCounter1--;
                    item1quantity.setText(Integer.toString(mCounter1));
                }
            }
        });

        item2plusbtn.setOnClickListener(new ViewPager.OnClickListener(){
            @Override
            public void onClick(View view){
                mCounter2++;
                item2quantity.setText(Integer.toString(mCounter2));
            }
        });

        item2minusbtn.setOnClickListener(new ViewPager.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mCounter2 > 0) {
                    mCounter2--;
                    item2quantity.setText(Integer.toString(mCounter2));
                }
            }
        });

        item1requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item1 = description1.getText().toString();
                quantity1 = item1quantity.getText().toString();
                student_num = studentNum.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,reguest_url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Toast.makeText(RequestItemActivity.this,"Successfully Requested Item",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(RequestItemActivity.this,"Error",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("description", item1);
                        params.put("quantity", quantity1);
                        params.put("student_num", student_num);
                        return params;
                    }
                };
                MySingleton.getMyInstance(RequestItemActivity.this).addToRequestque(stringRequest);
            }
        });

        item2requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item2 = description2.getText().toString();
                    quantity2 = item2quantity.getText().toString();
                    student_num = studentNum.getText().toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,reguest_url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            Toast.makeText(RequestItemActivity.this,"Successfully Requested Item",Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Toast.makeText(RequestItemActivity.this,"Error",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("description", item2);
                            params.put("quantity", quantity2);
                            params.put("student_num", student_num);
                            return params;
                        }
                };
                MySingleton.getMyInstance(RequestItemActivity.this).addToRequestque(stringRequest);
            }
        });

        student_num = studentNum.getText().toString();
        mdrawerLayout = (DrawerLayout)findViewById(R.id.activity_request_item);
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
                        Intent requestIntent = new Intent(getApplicationContext(), RequestItemActivity.class);
                        Bundle requestBundle = new Bundle();
                        requestBundle.putString("student_num", student_num);
                        requestIntent.putExtras(requestBundle);
                        startActivity(requestIntent);
                        break;
                    case R.id.nav_scan:
                        Intent qrIntent = new Intent(getApplicationContext(), ScanQRActivity.class);
                        Bundle qrBundle = new Bundle();
                        qrBundle.putString("student_num", student_num);
                        qrIntent.putExtras(qrBundle);
                        startActivity(qrIntent);
                        break;
                    case R.id.nav_about:
                        Intent usIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
                        Bundle usBundle = new Bundle();
                        usBundle.putString("student_num", student_num);
                        usIntent.putExtras(usBundle);
                        startActivity(usIntent);
                        break;
                    case R.id.nav_schedule:
                        Intent scheduleIntent = new Intent(getApplicationContext(), ScheduleActivity.class);
                        Bundle scheduleBundle = new Bundle();
                        scheduleBundle.putString("student_num", student_num);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
