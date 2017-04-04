package vxestate.prototypelabflow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TimeSlotActivity extends AppCompatActivity {
    TextView student_num, name;
    TextView test;
    Button mon_slot1, mon_slot2;
    String NAME, STUDENT_NUM, Date, Time, Slot_Remaining, TEST;
    String bookflow_url = "http://prototypelabflow.esy.es/BookFlow.php";
    String date = "03/30/2017", time = "8:00-9:45 AM";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);

        student_num = (TextView) findViewById(R.id.student_num);
        name = (TextView) findViewById(R.id.name);

        Bundle bundle = getIntent().getExtras();
        student_num.setText(bundle.getString("student_num"));
        name.setText(bundle.getString("name"));

        NAME = name.getText().toString();
        STUDENT_NUM = student_num.getText().toString();

        final MaterialCalendarView materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2016, 12, 31))
                .setMaximumDate(CalendarDay.from(2200, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String DATE = simpleDateFormat.format(date.getDate());
                Intent scheduleIntent = new Intent(getApplicationContext(), BookTimeActivity.class);
                Bundle scheduleBundle = new Bundle();
                scheduleBundle.putString("student_num", STUDENT_NUM);
                scheduleBundle.putString("name", NAME);
                scheduleBundle.putString("date", DATE);
                scheduleIntent.putExtras(scheduleBundle);
                startActivity(scheduleIntent);
            }
        });

        /*
        student_num = (TextView)findViewById(R.id.student_num);
        name = (TextView)findViewById(R.id.name);
        mon_slot1 = (Button)findViewById(R.id.mon_slot1);
        mon_slot2 = (Button)findViewById(R.id.mon_slot2);
        test = (TextView)findViewById(R.id.test);

        Bundle bundle = getIntent().getExtras();
        student_num.setText(bundle.getString("student_num"));
        name.setText(bundle.getString("name"));



        mon_slot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, bookflow_url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            Slot_Remaining = jsonObject.getString("total");
                            SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("username", NAME);
                            editor.putString("studenr_num", STUDENT_NUM);
                            editor.putString("date", "3/30/2017");
                            editor.putString("time", "8:00-9:45 AM");
                            editor.putString("slot", Slot_Remaining);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(TimeSlotActivity.this,"Error",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("date", date);
                        params.put("time", time);
                        return params;
                    }
                };
                MySingleton.getMyInstance(TimeSlotActivity.this).addToRequestque(stringRequest);



                Intent scheduleIntent = new Intent(getApplicationContext(), BookTimeActivity.class);
                startActivity(scheduleIntent);


                Intent scheduleIntent = new Intent(getApplicationContext(), BookTimeActivity.class);
                Bundle scheduleBundle = new Bundle();
                scheduleBundle.putString("student_num", STUDENT_NUM);
                scheduleBundle.putString("name", NAME);
                scheduleBundle.putString("date", "3/30/2017");
                scheduleBundle.putString("time", "8:00-9:45 AM");
                scheduleIntent.putExtras(scheduleBundle);
                startActivity(scheduleIntent);

            }
        });

        mon_slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, bookflow_url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            Slot_Remaining = jsonObject.getString("total");
                            test.setText(Slot_Remaining);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(TimeSlotActivity.this,"Error",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("date", date);
                        params.put("time", time);
                        return params;
                    }
                };
                MySingleton.getMyInstance(TimeSlotActivity.this).addToRequestque(stringRequest);
            }
        });
        */

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("TimeSlot Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
