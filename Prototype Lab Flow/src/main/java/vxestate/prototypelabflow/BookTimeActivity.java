package vxestate.prototypelabflow;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    TextView student_num, name, DATE, TIME, test, sup, sup1;
    String bookflow_url = "http://prototypelabflow.esy.es/BookFlow.php";
    String Slot_Remaining, date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_time);

        student_num = (TextView)findViewById(R.id.student_num);
        name = (TextView)findViewById(R.id.name);
        DATE = (TextView)findViewById(R.id.date);
        TIME = (TextView)findViewById(R.id.time);
        test = (TextView)findViewById(R.id.test);
        sup = (TextView)findViewById(R.id.sup);
        sup1 = (TextView)findViewById(R.id.sup1);


        Bundle bundle = getIntent().getExtras();
        //test.setText(bundle.getString("slot"));
        student_num.setText(bundle.getString("student_num"));
        name.setText(bundle.getString("name"));
        DATE.setText(bundle.getString("date"));
        //TIME.setText(bundle.getString("time"));



        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);

        Slot_Remaining = sharedPref.getString("slot", "null");
        sup1.setText(Slot_Remaining);


        /*
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
                Toast.makeText(BookTimeActivity.this,"Error",Toast.LENGTH_LONG).show();
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
        MySingleton.getMyInstance(BookTimeActivity.this).addToRequestque(stringRequest);
        */
    }
}
